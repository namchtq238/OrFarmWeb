package com.orfarmweb.service;

import com.orfarmweb.constaint.Role;
import com.orfarmweb.entity.OrderDetail;
import com.orfarmweb.entity.Product;
import com.orfarmweb.entity.User;
import com.orfarmweb.modelutil.ChartDTO;
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
    ChartDTO getInformationForChart();
    List<User> getUserByRole(Role role);
    boolean addStaff(User user);
    User getUserById(int id);
    boolean updateStaff(int id, User user);
    boolean deleteStaff(int id);
}
