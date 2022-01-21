package com.orfarmweb.service;

import com.orfarmweb.entity.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    List<Category> getListCategory();
    Optional<Category> findById(int id);
}
