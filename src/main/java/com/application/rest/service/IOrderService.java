package com.application.rest.service;

import com.application.rest.controllers.DTO.OrderDTO;
import com.application.rest.entities.Order;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IOrderService {
    List<Order> findAll();

    Optional<Order> findById(Long id);

    Order saveOrder(OrderDTO orderDTO);

    void deleteById(Long id);

//   List<Order> findByCustomerId(Long customerId);

    List<Order> findByDateRange(LocalDate startDate, LocalDate endDate);
}
