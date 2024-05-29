package com.application.rest.controllers;

import com.application.rest.controllers.DTO.OrderDTO;
import com.application.rest.entities.Order;
import com.application.rest.persistence.IOrderDAO;
import com.application.rest.service.IOrderService;
import org.aspectj.weaver.ast.Or;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class OrderControllerTest {

    @InjectMocks
    private OrderController orderController;

    @Mock
    private IOrderService orderService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Método FindAll - todos pedidos")
    public void testFindAll(){
        List<Order> orders = new ArrayList<>();
        when(orderService.findAll()).thenReturn(orders);

        List<Order> result = orderController.findAll();

        assertEquals(orders, result);
    }

    @Test
    @DisplayName("Método findById - procurar por id")
    public void testFindById(){
        Long orderId = 1L;
        Order order = new Order();

        when(orderService.findById(orderId)).thenReturn(Optional.of(order));

        ResponseEntity<Order> responseEntity = orderController.findById(orderId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(order, responseEntity.getBody());
    }

    @Test
    @DisplayName("Método saveOrder - salvar pedido")
    public void testSaveOrder(){
        OrderDTO orderDTO = new OrderDTO();
        Order savedOrder = new Order();
        when(orderService.saveOrder(orderDTO)).thenReturn(savedOrder);

        ResponseEntity<Order> responseEntity = orderController.saveOrder(orderDTO);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(savedOrder, responseEntity.getBody());
    }

    @Test
    @DisplayName("Método DeleteById - deletarPorId")
    public void testDeleteById(){
        Long orderId = 1L;
        ResponseEntity<Void> responseEntity = orderController.deleteById(orderId);

        verify(orderService, times(1)).deleteById(orderId);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    @DisplayName("Método FindByCustomerId - procurar pedido por id do cliente")
    public void testFindByCustomerId(){
        Long customerId = 1L;
        List<Order> orders = new ArrayList<>();
        when(orderService.findByCustomerId(customerId)).thenReturn(orders);

        List<Order> result = orderController.findByCustomerId(customerId);

        assertEquals(orders, result);
    }

    @Test
    @DisplayName("Método findByDateRange - procurar por data")
    public void testFindByDateRange(){
        LocalDateTime startDateTime = LocalDateTime.now().minusDays(7);
        LocalDateTime endDateTime = LocalDateTime.now();
        List<Order> orders = new ArrayList<>();
        when(orderService.findByDateRange(startDateTime.toLocalDate(), endDateTime.toLocalDate())).thenReturn(orders);

        List<Order> result = orderController.findByDateRange(startDateTime, endDateTime);

        assertEquals(orders, result);
    }
}
