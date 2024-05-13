package com.application.rest.persistence;

import com.application.rest.entities.Order;
import org.aspectj.weaver.ast.Or;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IOrderDAO {
    List<Order> findAll();

    Optional<Order> findById(Long id);

    void save(Order order);

    void deleteById(Long id);

//    List<Order> findByCustomerId(Long customerId);

    List<Order> findByDateRange(LocalDate startDate, LocalDate endDate);

}
