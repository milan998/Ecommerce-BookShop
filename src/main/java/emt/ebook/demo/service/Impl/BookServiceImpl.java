package emt.ebook.demo.service.Impl;

import emt.ebook.demo.model.Author;
import emt.ebook.demo.model.Book;
import emt.ebook.demo.model.Category;

import emt.ebook.demo.model.exceptions.InvalidBookIdException;
import emt.ebook.demo.repository.BookRepository;
import emt.ebook.demo.repository.CategoryRepository;
import emt.ebook.demo.service.AuthorService;
import emt.ebook.demo.service.BookService;
import emt.ebook.demo.service.CategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Base64;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final CategoryService categoryService;
    private final CategoryRepository categoryRepository;
    private final AuthorService authorService;

    public BookServiceImpl(BookRepository bookRepository, CategoryService categoryService, CategoryRepository categoryRepository, AuthorService authorService) {
        this.bookRepository = bookRepository;
        this.categoryService = categoryService;
        this.categoryRepository = categoryRepository;
        this.authorService = authorService;
    }

    @Override
    public Book findById(Long id) {
        return this.bookRepository.findById(id)
                .orElseThrow(InvalidBookIdException::new);
    }

    @Override
    public List<Book> findAll() {
        return this.bookRepository.findAll();
    }

    @Override
    public List<Book> findAllByRatingIsGreaterThan(double rating) {
        return this.bookRepository.findAllByRatingIsGreaterThanOrderByRatingDesc(rating);
    }


    @Override
    public Book save(String name, Float price, Float rating, LocalDate publishDate, String description, String language, Integer quantity, MultipartFile image, Long authorId, List<Long> categoryIds) throws IOException {
        Author author = this.authorService.findById(authorId);
        List<Category> categories = this.categoryRepository.findAllById(categoryIds);
        Book book = new Book(name, price, rating, publishDate, description, language, quantity,author, categories);
        if (image != null && !image.getName().isEmpty()) {
            byte[] bytes = image.getBytes();
            String base64Image = String.format("data:%s;base64,%s", image.getContentType(), Base64.getEncoder().encodeToString(bytes));
            book.setImageBase64(base64Image);
        }
        return this.bookRepository.save(book);

    }

    @Override
    public Book update(Long id,String name, Float price, Float rating, LocalDate publishDate, String description,
                       String language, Integer quantity, MultipartFile image, Long authorId, List <Long>categoryIds) throws IOException {
        Book b = this.findById(id);
        Author author = this.authorService.findById(authorId);
        List<Category> categories = this.categoryRepository.findAllById(categoryIds);
        b.setName(name);
        b.setAuthor(author);
        b.setCategories(categories);
        b.setDescription(description);
        b.setLanguage(language);
        b.setPrice(price);
        b.setPublishDate(publishDate);
        b.setQuantity(quantity);
        b.setRating(rating);
        if (image != null && !image.getName().isEmpty()) {
            byte[] bytes = image.getBytes();
            String base64Image = String.format("data:%s;base64,%s", image.getContentType(), Base64.getEncoder().encodeToString(bytes));
            b.setImageBase64(base64Image);
        }
        return this.bookRepository.save(b);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        this.bookRepository.deleteById(id);
    }

}
