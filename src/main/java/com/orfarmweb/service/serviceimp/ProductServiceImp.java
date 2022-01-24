package com.orfarmweb.service.serviceimp;

import com.orfarmweb.constaint.FormatPrice;
import com.orfarmweb.entity.Cart;
import com.orfarmweb.entity.Product;
import com.orfarmweb.modelutil.CartItem;
import com.orfarmweb.repository.ProductRepo;
import com.orfarmweb.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public Product findById(int id) {
        return productRepo.getAllById(id);
    }

    @Override
    public int getTotalByFill(float start, float end, int id) {
        return productRepo.getTotalProductByFill(start, end, id);
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
    public long getTotalPageByFill(float start, float end, int id) {
        return (productRepo.countByCategoryIdAndFill(start,end,id).get(0) % pageSize == 0) ?
                productRepo.countByCategoryIdAndFill(start,end,id).get(0) / pageSize
                : (productRepo.countByCategoryIdAndFill(start,end,id).get(0) / pageSize) + 1;
    }

    @Override
    public List<Product> listFillByPage(float start, float end, long currentPage, int id) {
        return productRepo.listFill(start,end,id,(currentPage-1)*pageSize,pageSize);
    }
    @Override
    public int getCategoryId(int id) {
        return productRepo.findCateGoryIdByProdId(id);
    }

    @Override
    public List<CartItem> getProductFromCart(List<Cart> cartList) {
        List<CartItem> list = new ArrayList<>();
        for (Cart cart: cartList
             ) {
            Product product = findById(cart.getProduct().getId());
            CartItem cartItem = new CartItem();
            cartItem.setProductId(product.getId());
            cartItem.setProductName(product.getName());
            cartItem.setDiscount(product.getPercentDiscount());
            cartItem.setQuantity(cart.getQuantity());
            cartItem.setImage(product.getImage());
            cartItem.setSalePrice(product.getSalePrice() * (1- cartItem.getDiscount()/100));
            cartItem.setTotalPrice(cartItem.getSalePrice() * cartItem.getQuantity());
            list.add(cartItem);
        }
        return list;
    }

    @Override
    public Float getTempPrice(List<CartItem> itemList) {
        Float tempPrice = 0f;
        for (CartItem cartItem: itemList
             ) {
            tempPrice += cartItem.getTotalPrice();
        }
        return tempPrice;
    }

}
