package emt.ebook.demo.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/login")
public class LoginController {

    @GetMapping
    public String getLoginPage(){
        return "login";
    }

    @PostMapping
    public String loginUser(HttpServletRequest req) {
        String username = req.getParameter("username");
        if (username != null) {
            req.getSession().setAttribute("username", username);
            return "redirect:/home";
        } else {
            return "redirect:/login";
        }
    }

}
