package emt.ebook.demo.service.Impl;

import com.stripe.exception.*;
import com.stripe.model.Charge;
import emt.ebook.demo.dto.ChargeRequest;
import emt.ebook.demo.model.Book;
import emt.ebook.demo.model.ShoppingCart;
import emt.ebook.demo.model.ShoppingCartStatus;
import emt.ebook.demo.model.User;
import emt.ebook.demo.model.exceptions.*;
import emt.ebook.demo.repository.ShoppingCartRepository;
import emt.ebook.demo.service.BookService;
import emt.ebook.demo.service.PaymentService;
import emt.ebook.demo.service.ShoppingCartService;
import emt.ebook.demo.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final PaymentService paymentService;
    private final UserService userService;
    private final BookService bookService;

    public ShoppingCartServiceImpl(ShoppingCartRepository shoppingCartRepository, PaymentService paymentService, UserService userService, BookService bookService) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.paymentService = paymentService;
        this.userService = userService;
        this.bookService = bookService;
    }

    @Override
    public ShoppingCart findActiveShoppingCartByUsername(String userId) {
        return this.shoppingCartRepository.findByUserUsernameAndShoppingCartStatus(userId, ShoppingCartStatus.CREATED)
                .orElseThrow(ShoppingCartIsNotActiveException::new);
    }

    @Override
    public List<ShoppingCart> findAllByUsername(String userId) {
        return this.shoppingCartRepository.findAllByUserUsername(userId);
    }

    @Override
    public ShoppingCart createNewShoppingCart(String userId) {
        User user = this.userService.findById(userId);
        if (this.shoppingCartRepository.existsByUserUsernameAndShoppingCartStatus(
                user.getUsername(),
                ShoppingCartStatus.CREATED
        )) {
            throw new ShoppingCartIsAlreadyCreated(userId);
        }
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(user);
        return this.shoppingCartRepository.save(shoppingCart);
    }

    @Override
    @Transactional
    public ShoppingCart addProductToShoppingCart(String userId, Long bookId) {
        ShoppingCart shoppingCart = this.getActiveShoppingCart(userId);
        Book book = this.bookService.findById(bookId);
        for (Book b : shoppingCart.getBooks()) {
            if (b.getId().equals(bookId)) {
                throw new BookIsAlreadyInShoppingCartException(book.getName());
            }
        }
        shoppingCart.getBooks().add(book);
        return this.shoppingCartRepository.save(shoppingCart);
    }

    @Override
    @Transactional
    public ShoppingCart removeProductFromShoppingCart(String userId, Long productId) {
        ShoppingCart shoppingCart = this.getActiveShoppingCart(userId);
        shoppingCart.setBooks(
                shoppingCart.getBooks()
                        .stream()
                        .filter(product -> !product.getId().equals(productId))
                        .collect(Collectors.toList())
        );
        return this.shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public ShoppingCart getActiveShoppingCart(String userId) {
        return this.shoppingCartRepository
                .findByUserUsernameAndShoppingCartStatus(userId, ShoppingCartStatus.CREATED)
                .orElseGet(() -> {
                    ShoppingCart shoppingCart = new ShoppingCart();
                    User user = this.userService.findById(userId);
                    shoppingCart.setUser(user);
                    return this.shoppingCartRepository.save(shoppingCart);
                });
    }

    @Override
    public ShoppingCart cancelActiveShoppingCart(String userId) {

        ShoppingCart shoppingCart = this.shoppingCartRepository
                .findByUserUsernameAndShoppingCartStatus(userId, ShoppingCartStatus.CREATED)
                .orElseThrow(ShoppingCartIsNotActiveException::new);
        shoppingCart.setShoppingCartStatus(ShoppingCartStatus.CANCELED);
        return this.shoppingCartRepository.save(shoppingCart);
    }

    @Override
    @Transactional
    public ShoppingCart checkoutShoppingCart(String userId, ChargeRequest chargeRequest) {
        ShoppingCart shoppingCart = this.shoppingCartRepository
                .findByUserUsernameAndShoppingCartStatus(userId, ShoppingCartStatus.CREATED)
                .orElseThrow(ShoppingCartIsNotActiveException::new);

        List<Book> books = shoppingCart.getBooks();
        float price = 0;

        for (Book book : books) {
            if (book.getQuantity() <= 0) {
                throw new BookIsOutOfStockException(book.getName());
            }
            book.setQuantity(book.getQuantity() - 1);
            price += book.getPrice();
        }
//        Charge charge = null;
        chargeRequest.setAmount((int) price * 100);
        try {
            this.paymentService.pay(chargeRequest);
        } catch (CardException | APIException | AuthenticationException | APIConnectionException | InvalidRequestException e) {
            throw new TransactionFailedException(userId, e.getMessage());
        }

        shoppingCart.setBooks(books);
        shoppingCart.setShoppingCartStatus(ShoppingCartStatus.FINISHED);
        return this.shoppingCartRepository.save(shoppingCart);

    }

}
