package ch.zli.m223.service;

import ch.zli.m223.repository.CategoryRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import ch.zli.m223.category.Category;
import java.util.List;

@ApplicationScoped 
public class CategoryService {
    @Inject CategoryRepository categoryRepository;

    public List<Category> findAll() {
        return categoryRepository.listAll();
    } 

    @Transactional
    public Category create(Category category) {
        categoryRepository.persist(category);
        return category;
    }

    @Transactional
    public Category update(Long id, Category category) {
        Category existingCategory = categoryRepository.findById(id);

        if (existingCategory == null) {
            throw new NotFoundException();
        }

        existingCategory.setTitle(category.getTitle());
        
        return existingCategory;
    }

    @Transactional
    public void delete(long id) {

        if(!categoryRepository.deleteById(id)) {
            throw new NotFoundException();
        }


    }
}
