package com.orfarmweb.service;

import com.orfarmweb.entity.OrderDetail;
import com.orfarmweb.entity.Product;
import com.orfarmweb.modelutil.OrderAdmin;
import com.orfarmweb.modelutil.OrderDetailDTO;
import com.orfarmweb.modelutil.ProductAdminDTO;

import java.util.List;

public interface AdminService {
    Integer countOrders();
    Integer countUserByRole();
    Float getRevenue();
    long getTotalPageProduct();
    List<Product> getProductByPage(long currentPage);
    List<OrderDetailDTO> getTopOrderDetail();
    List<OrderAdmin> getOrderAdmin();
    List<ProductAdminDTO> getHubByPage(long currentPage);
    List<ProductAdminDTO> searchHubByNameAndPage(String keyWord, long currentPage);
    long getTotalPageHubByKeyWord(String keyWord);
    Float getCostOfProduct();
}
