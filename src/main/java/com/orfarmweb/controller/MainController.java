package com.orfarmweb.controller;

import com.orfarmweb.entity.Category;
import com.orfarmweb.repository.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
@Controller
public class MainController {
    @Autowired
    private CategoryRepo categoryRepo;
    @GetMapping
    public String getCategoryInput(Model model){
        List<Category> list = categoryRepo.findAll();
        model.addAttribute("listCategory",list);
        return "index";
    }
}
