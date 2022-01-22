package com.orfarmweb.modelutil;

import lombok.Data;


public interface CartDTO {
     Integer getCartId();
     String getProductName();
     Integer getUserId();
     Integer getProductQuantity();
     Float getProductPrice();
     String getImage();
}
