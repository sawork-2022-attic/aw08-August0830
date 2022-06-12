package com.micropos.posorder.service;


public interface OrderService {
    boolean placeOrder(String orderInfo);
    String generateOrder(String orderInfo);
}
