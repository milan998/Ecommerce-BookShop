package emt.ebook.demo.web;

import emt.ebook.demo.model.Book;
import emt.ebook.demo.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping({"/","/home"})
public class HomeController {

    private final BookService bookService;

    public HomeController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public String getHomePage(Model model){
        List<Book> mostRatedBooks = this.bookService.findAllByRatingIsGreaterThan(0.00).stream().limit(4).collect(Collectors.toList());
        model.addAttribute("mostRatedBooks", mostRatedBooks);
        return "home";
    }



}
