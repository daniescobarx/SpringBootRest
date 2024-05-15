package com.application.rest.controllers;

import com.application.rest.controllers.DTO.OrderDTO;
import com.application.rest.entities.Order;
import com.application.rest.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private IOrderService orderService;

    @GetMapping
    public List<Order> findAll() {
        return orderService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> findById(@PathVariable Long id) {
        Optional<Order> order = orderService.findById(id);
        return order.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Order> saveOrder(@RequestBody OrderDTO orderDTO) {
        Order order = orderService.saveOrder(orderDTO);
        return ResponseEntity.ok(order);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        orderService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/customer/{customerId}")
    public List<Order> findByCustomerId(@PathVariable Long customerId) {
        return orderService.findByCustomerId(customerId);
    }

    @GetMapping("/date-range")
    public List<Order> findByDateRange(@RequestParam LocalDateTime startDateTime, @RequestParam LocalDateTime endDateTime) {
        LocalDate startDate = startDateTime.toLocalDate();
        LocalDate endDate = endDateTime.toLocalDate();
        return orderService.findByDateRange(startDate, endDate);
    }

}
