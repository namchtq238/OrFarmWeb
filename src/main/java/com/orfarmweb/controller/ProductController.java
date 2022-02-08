package com.orfarmweb.controller;

import com.orfarmweb.constaint.FormatPrice;
import com.orfarmweb.entity.Category;
import com.orfarmweb.entity.Product;
import com.orfarmweb.modelutil.FilterProduct;
import com.orfarmweb.service.CartService;
import com.orfarmweb.service.CategoryService;
import com.orfarmweb.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@Controller
public class ProductController {
    private final ProductService productService;
    private final CategoryService categoryService;
    private final CartService cartService;
    private final FormatPrice formatPrice;

    public ProductController(ProductService productService, CategoryService categoryService, CartService cartService, FormatPrice formatPrice) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.cartService = cartService;
        this.formatPrice = formatPrice;
    }

    @ModelAttribute
    public void addCategoryToHeader(Model model) {
        List<Category> listCategory = categoryService.getListCategory();
        model.addAttribute("listCategory", listCategory);
        model.addAttribute("format", formatPrice);
    }
    @ModelAttribute("countCartItem")
    public Integer addNumberOfCartItemToHeader(){
        return cartService.countNumberOfItemInCart();
    }
    @GetMapping("/category/{id}")
    public String showViewProduct(@PathVariable("id") int id) {
        return "redirect:/category/{id}/1";
    }
    @GetMapping("/category/{id}/filter-result")
    public String showViewProductFilter(@PathVariable("id")int id){
        return "redirect:/category/{id}/filter-result/1";
    }
    @GetMapping("/category/{id}/{page}")
    public String showViewProduct(@PathVariable("id") int id,
                                  @PathVariable("page") long currentPage,
                                  Model model) {
        long totalPage = productService.getTotalPage(id);
        Integer sum = productService.getTotal(id);
        if (sum.equals(null)) sum = 0;
        List<Product> productList = productService.getListProductByHot();
        Collections.shuffle(productList);
        if(productList.size()<4) model.addAttribute("bestSeller", productList);
        else model.addAttribute("bestSeller", productList.subList(0, 3));
        model.addAttribute("filter", new FilterProduct());
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("categoryId", id);
        model.addAttribute("sum", sum);
        model.addAttribute("listProduct", productService.getByPage(currentPage, id));
        model.addAttribute("category", categoryService.findById(id).get());
        return "raucusach";
    }
    @GetMapping("/category/{id}/fill-result/{page}")
    public String showViewProductFill(@PathVariable("id") int id,
                                      @ModelAttribute FilterProduct filter,
                                      @PathVariable("page") long currentPage,
                                      Model model) {
        Float start = filter.getFillStart();
        Float end = filter.getFillEnd();
        if(start>end){
            Float temp = start;
            start = end;
            end= temp;
        }
        Integer sum = productService.getTotalByFill(start,end,id);
        long totalPage = productService.getTotalPageByFill(start, end, id);
        List<Product> productList = productService.getListProductByHot();
        Collections.shuffle(productList);
        if(productList.size()<4) model.addAttribute("bestSeller", productList);
        else model.addAttribute("bestSeller", productList.subList(0, 3));
        model.addAttribute("currentFilter", filter);
        model.addAttribute("filter", new FilterProduct());
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("categoryId", id);
        model.addAttribute("sum", sum);
        model.addAttribute("listProduct", productService.listFillByPage(start,end,currentPage, id));
        model.addAttribute("category", categoryService.findById(id).get());
        return "dokho";
    }

    @GetMapping("/product/{id}")
    public String showViewProductDetail(@PathVariable int id, Model model) {
        Integer idCategory = productService.getCategoryId(id);
        List<Product> list = productService.listAllByCategoryId(idCategory);
        Collections.shuffle(list);
        if(list.size()<9) model.addAttribute("listSimilar", list);
        else{
            list = list.subList(0, 7);
            model.addAttribute("listSimilar", list);
        }
        model.addAttribute("productDetail", productService.findById(id));
        return "productdetail";
    }
}
