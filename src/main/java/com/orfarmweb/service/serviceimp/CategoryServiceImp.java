package com.orfarmweb.service.serviceimp;

import com.orfarmweb.entity.Category;
import com.orfarmweb.repository.CategoryRepo;
import com.orfarmweb.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImp implements CategoryService {
    private final CategoryRepo categoryRepo;

    public CategoryServiceImp(CategoryRepo categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    @Override
    public List<Category> getListCategory() {
        return categoryRepo.findAll();
    }

    @Override
    public Optional<Category> findById(int id) {
        return categoryRepo.findById(id);
    }

    @Override
    public boolean addCategory(Category category) {
        categoryRepo.save(category);
        return true;
    }

    @Override
    public boolean deleteCategory(int id) {
        categoryRepo.delete(categoryRepo.getById(id));
        return true;
    }

    @Override
    public void updateCategory(int id, Category category) {
        Category category1 = categoryRepo.getById(id);
        if(!category.getName().isEmpty()) category1.setName(category.getName());
        if(!category.getDescription().isEmpty())category1.setDescription(category.getDescription());
        categoryRepo.save(category1);
    }

}
