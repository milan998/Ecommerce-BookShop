package emt.ebook.demo.service;

import emt.ebook.demo.model.Author;
import emt.ebook.demo.model.Book;
import emt.ebook.demo.model.Category;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface BookService {
    Book findById(Long id);
    List<Book> findAll();
    List<Book> findAllByRatingIsGreaterThan(double rating);
    Book save(String name, Float price, Float rating, LocalDate publishDate, String description,
              String language, Integer quantity, MultipartFile image, Long authorId, List <Long>categoryIds) throws IOException;
    Book update(Long id,String name, Float price, Float rating, LocalDate publishDate, String description,
                String language, Integer quantity, MultipartFile image, Long authorId, List <Long>categoryIds) throws IOException;
    void deleteById(Long id);

}
