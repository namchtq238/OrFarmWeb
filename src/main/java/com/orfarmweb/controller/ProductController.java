package com.orfarmweb.controller;

import com.orfarmweb.constaint.FormatPrice;
import com.orfarmweb.entity.Category;
import com.orfarmweb.entity.Product;
import com.orfarmweb.modelutil.FilterProduct;
import com.orfarmweb.service.CategoryService;
import com.orfarmweb.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
@Controller
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private FormatPrice formatPrice;
    @ModelAttribute
    public void addCategoryToHeader(Model model){
        List<Category> listCategory = categoryService.getListCategory();
        model.addAttribute("listCategory",listCategory);
    }
    @GetMapping("/category/{id}")
    public String showViewProduct(@PathVariable("id") int id, Model model){
            Integer sum = productService.getTotal(id);
            if(sum.equals(null)) sum = 0;
            HashMap<Integer, String> hashMap = new HashMap<Integer,String>();
            for(Product p: productService.listAllByCategoryId(id)){
                hashMap.put(p.getId(), productService.getSalePriceById(p.getId()));
            }
            HashMap<Integer, String> hashMap1 = new HashMap<Integer,String>();
            for(Product p: productService.listAllByCategoryId(id)){
                hashMap1.put(p.getId(), productService.getDiscountPriceById(p.getId()));
            }
            List<Product> productList = productService.getListProductByHot();
            Collections.shuffle(productList);
            model.addAttribute("salePrice", hashMap);
            model.addAttribute("discountPrice", hashMap1);
            model.addAttribute("bestSeller", productList.subList(0,3));
            model.addAttribute("sum", sum);
            model.addAttribute("listProduct", productService.listAllByCategoryId(id));
            model.addAttribute("category", categoryService.findById(id).get());
        return "raucusach";
    }
    @PostMapping("/category/{id}/fill-result")
    public String showViewProductFill(@PathVariable("id") int id, @ModelAttribute FilterProduct filter, Model model){
        model.addAttribute("productFill", productService.listFill(filter.getFillStart(), filter.getFillEnd(), id));
        return "dokho";
    }
    @GetMapping("/product/{id}")
    public String showViewProductDetail(@PathVariable int id, Model model){
        model.addAttribute("salePriceDetail", productService.getSalePriceById(id));
        model.addAttribute("discountPriceDetail", productService.getDiscountPriceById(id));
        model.addAttribute("productDetail", productService.findById(id));
        return "productdetail";
    }

    @GetMapping("/testapi")
    public  @ResponseBody Product getApi(){
        Product p = new Product();
        p.setId(productService.listAllByCategoryId(1).get(0).getId());
        p.setName(productService.listAllByCategoryId(1).get(0).getName());
        return p;
    }
}
