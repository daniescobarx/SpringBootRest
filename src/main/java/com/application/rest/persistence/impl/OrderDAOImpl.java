package com.application.rest.persistence.impl;

import com.application.rest.entities.Order;
import com.application.rest.persistence.IOrderDAO;
import com.application.rest.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
public class OrderDAOImpl implements IOrderDAO {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public List<Order> findAll() {
        return (List<Order>) orderRepository.findAll();
    }

    @Override
    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    public void save(Order order) {
         orderRepository.save(order);
    }

    @Override
    public void deleteById(Long id) {
        orderRepository.deleteById(id);
    }

    @Override
    public List<Order> findByCustomerId(Long customerId) {
        return orderRepository.findByCustomer_Id(customerId);
    }

    @Override
    public List<Order> findByDateRange(LocalDate startDate, LocalDate endDate) {
        return orderRepository.findByOrderDateBetween(startDate, endDate);
    }
}
