package emt.ebook.demo.service;

import emt.ebook.demo.model.Book;
import emt.ebook.demo.model.Category;

import java.util.List;

public interface CategoryService {
    Category findById(Long id);
    List<Category> findAllCategories();
    List<Category> searchCategories(String name);
    Category create(String name);
    Category update(Long id,String name);
    void delete(String name);
}
