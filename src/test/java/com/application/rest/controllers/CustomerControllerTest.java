package com.application.rest.controllers;

import com.application.rest.controllers.DTO.CustomerDTO;
import com.application.rest.entities.Customer;
import com.application.rest.service.ICustomerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public class CustomerControllerTest {

    @InjectMocks
    private CustomerController customerController;

    @Mock
    private ICustomerService customerService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindById() {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("maria");
        customer.setEmail("maria@example.com");
        customer.setAddress("rua dois, 123");

        when(customerService.findById(any())).thenReturn(Optional.of(customer));

        ResponseEntity<?> result = customerController.findById(1L);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(200, result.getStatusCode().value());
    }

    @Test
    public void testFindByIdCustomerNotPresent() {
        when(customerService.findById(any())).thenReturn(Optional.empty());

        ResponseEntity<?> result = customerController.findById(1L);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(404, result.getStatusCode().value());
    }

    @Test
    public void testSave() throws URISyntaxException {
        CustomerDTO customerDTO = CustomerDTO.builder()
                .id(1L)
                .name("janaina")
                .email("janaina.doe@example.com")
                .address("Rua dois, 3")
                .build();

        doNothing().when(customerService).save(any());
        ResponseEntity<?> result = customerController.save(customerDTO);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(201, result.getStatusCode().value());
    }

    @Test
    public void testSaveNotSave() throws URISyntaxException {
        doNothing().when(customerService).save(any());

        ResponseEntity<?> result = customerController.save(CustomerDTO.builder().name("").build());
        Assertions.assertNotNull(result);
        Assertions.assertEquals(400, result.getStatusCode().value());
    }

    @Test
    public void testDeleteById() {
        Long expectedId = 1L;

        doNothing().when(customerService).deleteById(any());
        ResponseEntity<?> result = customerController.deleteById(expectedId);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(200, result.getStatusCode().value());
        Assertions.assertEquals("Cliente deletado", result.getBody().toString());
    }

    @Test
    public void testNotDeleteById() {
        Long expectedId = null;

        ResponseEntity<?> result = customerController.deleteById(expectedId);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(400, result.getStatusCode().value());
    }
}
