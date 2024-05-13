package com.application.rest.service.impl;


import com.application.rest.controllers.DTO.OrderDTO;
import com.application.rest.entities.Order;
import com.application.rest.entities.Product;
import com.application.rest.persistence.IOrderDAO;
import com.application.rest.persistence.IProductDAO;
import com.application.rest.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private IOrderDAO orderDAO;

    @Autowired
    private IProductDAO productDAO;

    @Override
    public List<Order> findAll() {
        return orderDAO.findAll();
    }

    @Override
    public Optional<Order> findById(Long id) {
        return orderDAO.findById(id);
    }

    @Override
    public Order saveOrder(OrderDTO orderDTO){
        for (Product product: orderDTO.getProducts()){
            Optional<Product> productFromDb = productDAO.findById(product.getId());
            if (!productFromDb.isPresent()){
                throw new IllegalArgumentException("Produto com id "+ product.getId() + " not found");
            }
        }

        Order order = Order.builder()
                .orderDate(orderDTO.getOrderDate())
                .customer(orderDTO.getCustomer())
                .products(orderDTO.getProducts())
                .build();

        orderDAO.save(order);
        return order;
    }



    @Override
    public void deleteById(Long id) {
        orderDAO.deleteById(id);
    }
//
//    @Override
//    public List<Order> findByCustomerId(Long customerId) {
//        return orderDAO.findByCustomerId(customerId);
//    }

    @Override
    public List<Order> findByDateRange(LocalDate startDate, LocalDate endDate) {
        return orderDAO.findByDateRange(startDate, endDate);
    }



}
