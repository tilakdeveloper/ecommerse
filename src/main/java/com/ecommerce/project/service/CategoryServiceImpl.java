package com.ecommerce.project.service;

import com.ecommerce.project.exceptions.APIException;
import com.ecommerce.project.exceptions.ResourceNotFoundException;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService{
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories() {
        List<Category> allCategories = categoryRepository.findAll();
        if(allCategories.isEmpty()) {
            throw new APIException("No category created till now..!");
        }
        return allCategories;
    }

    @Override
    public void createCategory(Category category) {
        Category existingCategory = categoryRepository.findByCategoryName(category.getCategoryName());
        if (existingCategory != null) {
            throw new APIException("Category with the name " + category.getCategoryName() + " already exist");
        }
        categoryRepository.save(category);
    }

    @Override
    public String deleteCategory(Long categoryId){
       Category category = categoryRepository.findById(categoryId)
               .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId ));

       categoryRepository.delete(category);
       return "Category with categoryId: " + categoryId + " deleted successfully";
    }

    @Override
    public Category updateCategory(Category category, Long categoryId) {
        categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId ));

        category.setCategoryId(categoryId);
        return categoryRepository.save(category);
    }
}
