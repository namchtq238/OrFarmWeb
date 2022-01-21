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
    @Autowired
    private CategoryRepo categoryRepo;
    @Override
    public List<Category> getListCategory() {
        return categoryRepo.findAll();
    }

    @Override
    public Optional<Category> findById(int id) {
        return categoryRepo.findById(id);
    }
}
