package emt.ebook.demo.web.RestController;

import emt.ebook.demo.model.Category;
import emt.ebook.demo.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<Category>findAll(@RequestParam(required = false) String name) {
        if (name == null) {
            return this.categoryService.findAllCategories();
        }
        else {
        return this.categoryService.searchCategories(name);
        }
    }
    @GetMapping("/search-by-name")
    public List<Category>findAllByName(@RequestParam String name){
        return this.categoryService.searchCategories(name);
    }
    @GetMapping("/{id}")
    public Category findById(@PathVariable Long id){
        return this.categoryService.findById(id);
    }
    @PostMapping
    public Category save(@RequestParam String name){
        return this.categoryService.create(name);
    }
    @PutMapping("/{id}")
    public Category update(@PathVariable Long id,@RequestParam String name){
        return this.categoryService.update(id,name);
    }
    @DeleteMapping("/{name}")
    public void delete(@PathVariable String name) {
        this.categoryService.delete(name);
    }

}
