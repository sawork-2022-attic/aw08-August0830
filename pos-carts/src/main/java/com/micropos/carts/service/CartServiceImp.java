package com.micropos.carts.service;

import java.util.List;
import java.util.Optional;

import com.micropos.carts.model.Cart;
import com.micropos.carts.model.CartItem;
import com.micropos.carts.repository.CartRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.data.util.Streamable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CartServiceImp implements CartService {

    private CartRepository cartRepository;

    @LoadBalanced
    private RestTemplate restTemplate;
    /**
     * @param cartRepository the cartRepository to set
     */
    @Autowired
    public void setCartRepository(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Override
    public Cart addItemToCart(Cart cart, CartItem item) {
        // TODO Auto-generated method stub
        if(cart.addItem(item))
            return cartRepository.save(cart);
        return null;
    }

    @Override
    public double checkout(Integer cartId) {
        // TODO Auto-generated method stub
        Optional<Cart> cart = this.cartRepository.findById(cartId);
        if(cart.isEmpty())
            return -1.0;
        Cart realCart = cart.get();
        double sum = 0;
        StringBuilder orderBuiler = new StringBuilder();
        for(CartItem item: realCart.cartItems()){
            sum += item.price()*item.quantity();
            orderBuiler.append(item.productName()+"|");
        }
        orderBuiler.append("addr:CityA");
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<String>(orderBuiler.toString());
        restTemplate.postForObject("http://localhost:8085/order", request, Boolean.class);
        return sum;
    }

    @Override
    public List<Cart> getAllCarts() {
        // TODO Auto-generated method stub
        return Streamable.of(cartRepository.findAll()).toList();
    }

    @Override
    public Cart addEmptyCart(Cart cart) {
        // TODO Auto-generated method stub
        return cartRepository.save(cart);
    }

    @Override
    public Cart getCartById(Integer id) {
        // TODO Auto-generated method stub
        Optional<Cart> cart = this.cartRepository.findById(id);
        if(cart.isEmpty())
            return null;
        else
            return cart.get();
    }

}