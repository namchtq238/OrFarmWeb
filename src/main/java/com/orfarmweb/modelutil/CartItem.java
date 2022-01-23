package com.orfarmweb.modelutil;

import lombok.Data;

@Data
public class CartItem {
    String image;
    Integer productId;
    String productName;
    Integer quantity;
    Float discount;
    Float salePrice;
    Float totalPrice;
}
