package emt.ebook.demo.web;

import emt.ebook.demo.model.Author;
import emt.ebook.demo.model.Book;
import emt.ebook.demo.model.Category;
import emt.ebook.demo.service.AuthorService;
import emt.ebook.demo.service.BookService;
import emt.ebook.demo.service.CategoryService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final CategoryService categoryService;
    private final AuthorService authorService;

    public BookController(BookService bookService, CategoryService categoryService, AuthorService authorService) {
        this.bookService = bookService;
        this.categoryService = categoryService;
        this.authorService = authorService;
    }

    @GetMapping
    public String getBookPage(Model model) {
        List<Book> books = this.bookService.findAll();;
        List<Category> categories = this.categoryService.findAllCategories();
        model.addAttribute("categories", categories);
        model.addAttribute("books", books);
        return "books";
    }

    @PostMapping("/{id}/delete")
    public String deleteBook(@PathVariable Long id) {
        this.bookService.deleteById(id);
        return "redirect:/books";
    }

    @GetMapping("/add-new")
    public String addNewBook(Model model) {
        List<Category> categories = this.categoryService.findAllCategories();
        List<Author> authors = this.authorService.listAllAuthors();
        model.addAttribute("categories", categories);
        model.addAttribute("authors", authors);
        return "add-book";
    }

    @GetMapping("/{id}/edit")
    public String editBookPage(@PathVariable Long id, Model model) {
        Book book = this.bookService.findById(id);
        List<Category> categories = this.categoryService.findAllCategories();
        List<Author> authors = this.authorService.listAllAuthors();
        model.addAttribute("book", book);
        model.addAttribute("categories", categories);
        model.addAttribute("authors", authors);
        return "add-book";
    }

    @PostMapping
    public String saveProduct(@RequestParam String name,
                              @RequestParam Float price,
                              @RequestParam Integer quantity,
                              @RequestParam Float rating,
                              @RequestParam String language,
                              @RequestParam String description,
                              @RequestParam Long author,
                              @RequestParam List<Long> categories,
                              @RequestParam MultipartFile image,
                              @RequestParam("publishDate")
                              @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate publishDate)
            throws IOException {
        this.bookService.save(name, price, rating, publishDate, description, language, quantity, image, author, categories);
        return "redirect:/books";
    }

    @PostMapping("/{id}")
    public String update(
            @PathVariable Long id,
            @RequestParam String name,
            @RequestParam Float price,
            @RequestParam Integer quantity,
            @RequestParam Float rating,
            @RequestParam String language,
            @RequestParam String description,
            @RequestParam Long author,
            @RequestParam List<Long> categories,
            @RequestParam MultipartFile image,
            @RequestParam("publishDate")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate publishDate)
            throws IOException {
        this.bookService.update(id,name,price,rating,publishDate,description,language,quantity,image,author,categories);
        return "redirect:/books";
    }

}
