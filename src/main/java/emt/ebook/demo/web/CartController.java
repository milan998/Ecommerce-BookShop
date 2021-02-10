package emt.ebook.demo.web;

import emt.ebook.demo.model.ShoppingCart;
import emt.ebook.demo.service.AuthService;
import emt.ebook.demo.service.ShoppingCartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final ShoppingCartService shoppingCartService;
    private final AuthService authService;

    public CartController(ShoppingCartService shoppingCartService, AuthService authService) {
        this.shoppingCartService = shoppingCartService;
        this.authService = authService;
    }


    @GetMapping
    public String getCartPage(Model model){
        ShoppingCart shoppingCart = this.shoppingCartService.findActiveShoppingCartByUsername(this.authService.getCurrentUserId());
        model.addAttribute("shoppingCart", shoppingCart);
        return "cart";
    }


    @PostMapping("/{bookId}/add-book")
    public String addBookToCart(@PathVariable Long bookId){
        try{
            ShoppingCart shoppingCart = this.shoppingCartService.addProductToShoppingCart(this.authService.getCurrentUserId(), bookId);
            return "redirect:/cart";
        }catch(RuntimeException ex){
            return "redirect:/cart?error=" + ex.getLocalizedMessage();
        }
            
    }

    @PostMapping("/{bookId}/remove-book")
    public String deleteBook(@PathVariable Long bookId){
        this.shoppingCartService.removeProductFromShoppingCart(this.authService.getCurrentUserId(), bookId);
        return "redirect:/cart";
    }

}
