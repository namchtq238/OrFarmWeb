package com.orfarmweb.controller;

import com.orfarmweb.constaint.FormatPrice;
import com.orfarmweb.entity.Category;
import com.orfarmweb.entity.Product;
import com.orfarmweb.modelutil.FilterProduct;
import com.orfarmweb.security.CustomUserDetails;
import com.orfarmweb.service.CartService;
import com.orfarmweb.service.CategoryService;
import com.orfarmweb.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private CartService cartService;
    @Autowired
    private FormatPrice formatPrice;

    @ModelAttribute
    public void addCategoryToHeader(Model model) {
        List<Category> listCategory = categoryService.getListCategory();
        model.addAttribute("listCategory", listCategory);
    }
    @ModelAttribute("countCartItem")
    public Integer addNumberOfCartItemToHeader(Model model){
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
        HashMap<Integer, String> hashMap = new HashMap<Integer, String>();
        for (Product p : productService.getByPage(currentPage, id)) {
            hashMap.put(p.getId(), productService.getSalePriceById(p.getId()));
        }
        HashMap<Integer, String> hashMap1 = new HashMap<Integer, String>();
        for (Product p : productService.getByPage(currentPage, id)) {
            hashMap1.put(p.getId(), productService.getDiscountPriceById(p.getId()));
        }
        List<Product> productList = productService.getListProductByHot();
        Collections.shuffle(productList);
        if(productList.size()<4) model.addAttribute("bestSeller", productList);
        else model.addAttribute("bestSeller", productList.subList(0, 3));
        model.addAttribute("filter", new FilterProduct());
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("categoryId", id);
        model.addAttribute("salePrice", hashMap);
        model.addAttribute("discountPrice", hashMap1);
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
        HashMap<Integer, String> hashMap = new HashMap<Integer, String>();
        for (Product p : productService.listFillByPage(start,end,currentPage, id)) {
            hashMap.put(p.getId(), productService.getSalePriceById(p.getId()));
        }
        HashMap<Integer, String> hashMap1 = new HashMap<Integer, String>();
        for (Product p : productService.listFillByPage(start,end,currentPage, id)) {
            hashMap1.put(p.getId(), productService.getDiscountPriceById(p.getId()));
        }
        List<Product> productList = productService.getListProductByHot();
        Collections.shuffle(productList);
        if(productList.size()<4) model.addAttribute("bestSeller", productList);
        else model.addAttribute("bestSeller", productList.subList(0, 3));
        model.addAttribute("filter", new FilterProduct());
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("categoryId", id);
        model.addAttribute("salePrice", hashMap);
        model.addAttribute("discountPrice", hashMap1);
        model.addAttribute("sum", sum);
        model.addAttribute("listProduct", productService.listFillByPage(start,end,currentPage, id));
        model.addAttribute("category", categoryService.findById(id).get());
        return "dokho";
    }

    @GetMapping("/product/{id}")
    public String showViewProductDetail(@PathVariable int id, Model model) {
        Integer idCategory = productService.getCategoryId(id);
        List<Product> list = productService.listAllByCategoryId(idCategory);
        if(list.size()<9) model.addAttribute("listSimilar", list);
        else model.addAttribute("listSimilar", list.subList(0,8));
        Collections.shuffle(list);
        HashMap<Integer, String> listPrice = new HashMap<Integer,String>();
        HashMap<Integer, String> listDiscount = new HashMap<Integer,String>();
        for (Product p : list.subList(0,8)){
            listPrice.put(p.getId(), productService.getSalePriceById(p.getId()));
            listDiscount.put(p.getId(), productService.getDiscountPriceById(p.getId()));
        }
        model.addAttribute("listSimilarPrice",listPrice);
        model.addAttribute("listSimilarDiscount",listDiscount);
        model.addAttribute("salePriceDetail", productService.getSalePriceById(id));
        model.addAttribute("discountPriceDetail", productService.getDiscountPriceById(id));
        model.addAttribute("productDetail", productService.findById(id));
        return "productdetail";
    }

    @GetMapping("/testapi")
    public @ResponseBody
    Product getApi() {
        Product p = new Product();
        p.setId(productService.listAllByCategoryId(1).get(0).getId());
        p.setName(productService.listAllByCategoryId(1).get(0).getName());
        return p;
    }
}
