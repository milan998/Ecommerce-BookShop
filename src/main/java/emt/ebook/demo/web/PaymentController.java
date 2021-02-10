package emt.ebook.demo.web;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import emt.ebook.demo.dto.ChargeRequest;
import emt.ebook.demo.model.Book;
import emt.ebook.demo.model.ShoppingCart;
import emt.ebook.demo.service.AuthService;
import emt.ebook.demo.service.PaymentService;
import emt.ebook.demo.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/payments")
public class PaymentController {

    @Value("${STRIPE_P_KEY}")
    private String publicKey;

    private final AuthService authService;
    private final ShoppingCartService shoppingCartService;
    private final ChargeRequest ChargeRequest;
    private final PaymentService paymentService;

    public PaymentController(AuthService authService, ShoppingCartService shoppingCartService, emt.ebook.demo.dto.ChargeRequest chargeRequest, PaymentService paymentService) {
        this.authService = authService;
        this.shoppingCartService = shoppingCartService;
        ChargeRequest = chargeRequest;
        this.paymentService = paymentService;
    }

    @GetMapping("/buy")
    public String getCheckout(Model model){
        try {
            ShoppingCart shoppingCart = this.shoppingCartService.findActiveShoppingCartByUsername(this.authService.getCurrentUserId());
            model.addAttribute("shoppingCart", shoppingCart);
            model.addAttribute("currency", ChargeRequest.getCurrency());
            model.addAttribute("amount", (int) (shoppingCart.getBooks().stream().mapToDouble(Book::getPrice).sum() * 100));
            model.addAttribute("stripePublicKey", this.publicKey);
            return "checkout";
        }catch (RuntimeException ex){
            return "redirect:/books?error=" + ex.getLocalizedMessage();
        }
    }

    @PostMapping("/buy")
        public String charge(ChargeRequest chargeRequest)
            throws StripeException {
        try {
            Stripe.apiKey = "sk_test_AHejeaf8AhUHyAvOMq46AmDG00sTByE4w1";
            this.shoppingCartService.checkoutShoppingCart(authService.getCurrentUserId(), chargeRequest);
            return "redirect:/books";

        }
        catch (RuntimeException ex) {
            return "redirect:/payments/buy?error=" + ex.getLocalizedMessage();
        }
    }

}
