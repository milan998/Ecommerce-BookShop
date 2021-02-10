package emt.ebook.demo.service;

import emt.ebook.demo.dto.ChargeRequest;
import emt.ebook.demo.model.ShoppingCart;
import emt.ebook.demo.model.ShoppingCartStatus;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ShoppingCartService {
    ShoppingCart findActiveShoppingCartByUsername(String userId);

    List<ShoppingCart> findAllByUsername(String userId);

    ShoppingCart createNewShoppingCart(String userId);

    ShoppingCart addProductToShoppingCart(String userId,
                                          Long bookId);

    ShoppingCart removeProductFromShoppingCart(String userId,
                                               Long bookId);

    ShoppingCart getActiveShoppingCart(String userId);

    ShoppingCart cancelActiveShoppingCart(String userId);

    ShoppingCart checkoutShoppingCart(String userId, ChargeRequest chargeRequest);
}
