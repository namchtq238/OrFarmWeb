package com.orfarmweb.service.serviceimp;

import com.orfarmweb.entity.OrderDetail;
import com.orfarmweb.entity.Orders;
import com.orfarmweb.entity.Product;
import com.orfarmweb.modelutil.ChartDTO;
import com.orfarmweb.modelutil.OrderAdmin;
import com.orfarmweb.modelutil.OrderDetailDTO;
import com.orfarmweb.modelutil.ProductAdminDTO;
import com.orfarmweb.repository.OrderDetailRepo;
import com.orfarmweb.repository.OrdersRepo;
import com.orfarmweb.repository.ProductRepo;
import com.orfarmweb.repository.UserRepo;
import com.orfarmweb.service.AdminService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminServiceImp implements AdminService {

    private final OrdersRepo ordersRepo;
    private final UserRepo userRepo;
    private final ProductRepo productRepo;
    private final OrderDetailRepo orderDetailRepo;
    public AdminServiceImp(OrdersRepo ordersRepo, UserRepo userRepo, ProductRepo productRepo, OrderDetailRepo orderDetailRepo) {
        this.ordersRepo = ordersRepo;
        this.userRepo = userRepo;
        this.productRepo = productRepo;
        this.orderDetailRepo = orderDetailRepo;
    }
    private final long pageSize = 7;
    @Override
    public Integer countOrders() {
        return ordersRepo.countOrders();
    }
    @Override
    public Integer countUserByRole() {
        return userRepo.countUserByRole();
    }

    @Override
    public Float getRevenue() {
        if(ordersRepo.getReveune() == null) return 0f;
        return ordersRepo.getReveune();
    }

    @Override
    public long getTotalPageProduct() {
        return (productRepo.countProduct().get(0) % pageSize == 0) ? productRepo.countProduct().get(0) / pageSize
                : (productRepo.countProduct().get(0) / pageSize) + 1;
    }
    @Override
    public List<Product> getProductByPage(long currentPage) {
        return productRepo.findByPage((currentPage - 1) * pageSize, pageSize);
    }

    @Override
    public List<OrderDetailDTO> getTopOrderDetail() {
        List<OrderDetail> listOrderDetail = orderDetailRepo.getTopOrder();
        List<OrderDetailDTO> list = new ArrayList<>();
        listOrderDetail.forEach(orderDetail -> list.add(new OrderDetailDTO(orderDetail)));
        return list;
    }

    @Override
    public List<OrderAdmin> getOrderAdmin() {
        List<Orders> listOrder = ordersRepo.getOrderUser();
        List<OrderAdmin> list = new ArrayList<>();
        listOrder.forEach(orders -> list.add(new OrderAdmin(orders, orderDetailRepo.getTotalProduct(orders.getId()))));
        return list;
    }

    @Override
    public List<ProductAdminDTO> getHubByPage(long currentPage) {
        List<Product> list = productRepo.findByPage((currentPage - 1) * pageSize, pageSize);
        List<ProductAdminDTO> productAdminDTOS = new ArrayList<>();
        list.forEach(product -> productAdminDTOS.add(new ProductAdminDTO(product)));
        return productAdminDTOS;
    }

    @Override
    public List<ProductAdminDTO> searchHubByNameAndPage(String keyWord, long currentPage) {
        List<Product> list = productRepo.searchByNameAndPage(keyWord,(currentPage - 1) * pageSize, pageSize);
        List<ProductAdminDTO> productAdminDTOS = new ArrayList<>();
        list.forEach(product -> productAdminDTOS.add(new ProductAdminDTO(product)));
        return productAdminDTOS;
    }

    @Override
    public long getTotalPageHubByKeyWord(String keyWord) {
        return (productRepo.countByKeyWord(keyWord).get(0) % pageSize == 0) ? productRepo.countByKeyWord(keyWord).get(0) / pageSize
                : (productRepo.countByKeyWord(keyWord).get(0) / pageSize) + 1;
    }

    @Override
    public Float getCostOfProduct() {
        return productRepo.getTotalCostOfProduct();
    }

    @Override
    public ChartDTO getInformationForChart() {
        ChartDTO chartDTO = new ChartDTO();
        chartDTO.setRevenue(getRevenue());
        chartDTO.setCost(getCostOfProduct());
        return chartDTO;
    }
}
