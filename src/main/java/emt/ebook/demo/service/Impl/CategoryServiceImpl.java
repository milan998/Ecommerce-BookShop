package emt.ebook.demo.service.Impl;

import emt.ebook.demo.model.Category;
import emt.ebook.demo.model.exceptions.InvalidCategoryIdException;
import emt.ebook.demo.repository.CategoryRepository;
import emt.ebook.demo.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category findById(Long id) {
        return this.categoryRepository.findById(id)
                .orElseThrow(InvalidCategoryIdException::new);
    }

    @Override
    public List<Category> findAllCategories() {
        return this.categoryRepository.findAll();
    }

    @Override
    public List<Category> searchCategories(String name) {
        return this.categoryRepository.findAllByNameLike(name);
    }

    @Override
    public Category create(String name) {
        if (name==null || name.isEmpty()) {
            throw new IllegalArgumentException();
        }
        Category c = new Category(name);
        categoryRepository.save(c);
        return c;
    }

    @Override
    public Category update(Long id,String name) {
        if (name==null || name.isEmpty()) {
            throw new IllegalArgumentException();
        }
        Category c = this.categoryRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        c.setName(name);
        categoryRepository.save(c);
        return c;
    }

    @Override
    public void delete(String name) {
        if (name==null || name.isEmpty()) {
            throw new IllegalArgumentException();
        }
        categoryRepository.deleteByName(name);
    }
}
