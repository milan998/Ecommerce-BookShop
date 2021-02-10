package emt.ebook.demo.service.Impl;

import emt.ebook.demo.model.Author;
import emt.ebook.demo.model.exceptions.InvalidAuthorIdException;
import emt.ebook.demo.repository.AuthorRepository;
import emt.ebook.demo.service.AuthorService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public Author findById(Long id) {
        return this.authorRepository.findById(id)
                .orElseThrow(InvalidAuthorIdException::new);
    }

    @Override
    public List<Author> listAllAuthors() {
        return this.authorRepository.findAll();
    }
}
