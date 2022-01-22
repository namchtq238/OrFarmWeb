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
    final long pageSize = 6;
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
        Float s = productRepo.getSalePrice(id);
        return formatPrice.formatPrice(s);
    }

    @Override
    public String getDiscountPriceById(int id) {
        Product p = productRepo.getAllById(id);
        return p.getPercentDiscount()!=0?formatPrice.formatPrice(p.getSalePrice()*(1-p.getPercentDiscount()/100)):null;
    }

    @Override
    public long getTotalPage(int id) {
        return (productRepo.countByCategoryId(id).get(0) % pageSize == 0) ?
                productRepo.countByCategoryId(id).get(0) / pageSize
                : (productRepo.countByCategoryId(id).get(0) / pageSize) + 1;
    }

    @Override
    public List<Product> getByPage(long currentPage, int id) {
        return productRepo.findByPage((currentPage - 1) * pageSize, pageSize, id);
    }

    @Override
    public int getCategoryId(int id) {
        return productRepo.findCateGoryIdByProdId(id);
    }

}
