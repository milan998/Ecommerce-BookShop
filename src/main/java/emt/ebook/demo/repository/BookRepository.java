package emt.ebook.demo.repository;

import emt.ebook.demo.model.Book;
import emt.ebook.demo.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findAllByRatingIsGreaterThanOrderByRatingDesc(double rating);
}
