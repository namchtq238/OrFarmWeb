package com.orfarmweb.service.serviceimp;

import com.orfarmweb.constaint.FormatPrice;
import com.orfarmweb.entity.Product;
import com.orfarmweb.repository.CategoryRepo;
import com.orfarmweb.repository.ProductRepo;
import com.orfarmweb.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductServiceImp implements ProductService {
    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private FormatPrice formatPrice;
    @Override
    public List<Product> listAllByCategoryId(int id) {
        return productRepo.findProductByCategoryId(id);
    }
    @Override
    public List<Product> listFill(float a, float b, int id) {
        List<Product> list = productRepo.listFill(a, b, id);
        return list;
    }

    @Override
    public Product findById(int id) {
        return productRepo.getAllById(id);
    }

    @Override
    public int getTotal(int id) {
        return productRepo.getTotal(id);
    }

    @Override
    public List<Product> getListProductByHot() {
        return productRepo.getProductByHot();
    }

    @Override
    public List<Product> getListSaleProduct() {
        return productRepo.getSaleProduct();
    }

    @Override
    public String getSalePriceById(int id) {
        return formatPrice.formatPrice(productRepo.getSalePrice(id));
    }

    @Override
    public String getDiscountPriceById(int id) {
        Product p = productRepo.getAllById(id);
        return formatPrice.formatPrice(p.getSalePrice()*(1-p.getPercentDiscount()/100));
    }
}
