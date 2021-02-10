package emt.ebook.demo.service;

import emt.ebook.demo.model.Author;

import java.util.List;

public interface AuthorService {
    Author findById(Long id);
    List<Author> listAllAuthors();
}
