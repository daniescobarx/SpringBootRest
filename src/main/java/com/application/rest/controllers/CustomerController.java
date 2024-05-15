package com.application.rest.controllers;

import com.application.rest.controllers.DTO.CustomerDTO;
import com.application.rest.entities.Customer;
import com.application.rest.persistence.ICustomerDAO;
import com.application.rest.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    @Autowired
    private ICustomerDAO customerDAO;

    @Autowired
    private ICustomerService customerService;

    @GetMapping("/find/{id}")
    public ResponseEntity<?> findById(@PathVariable long id){
        Optional<Customer> customerOptional = customerService.findById(id);

        if (customerOptional.isPresent()){
            Customer customer = customerOptional.get();

            CustomerDTO customerDTO = CustomerDTO.builder()
                    .id(customer.getId())
                    .name(customer.getName())
                    .email(customer.getEmail())
                    .address(customer.getAddress())
                    .build();

            return ResponseEntity.ok(customerDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/findAll")
    public ResponseEntity<?> findAll(){
        List<CustomerDTO> customerList = customerDAO.findAll()
                .stream()
                .map(customer -> CustomerDTO.builder()
                        .id(customer.getId())
                        .name(customer.getName())
                        .email(customer.getEmail())
                        .address(customer.getAddress())
                        .build()
                ).toList();

        return ResponseEntity.ok(customerList);
    }

    //* chegando null os dados
    //http://localhost:8080/api/customer/save
    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody CustomerDTO customerDTO) throws URISyntaxException{
        if (customerDTO.getName().isBlank()){
            return ResponseEntity.badRequest().build();
        }

        customerService.save(Customer.builder()
                .name(customerDTO.getName())
                .email(customerDTO.getEmail())
                .address(customerDTO.getAddress())
                .build()
        );
        return ResponseEntity.created(new URI("/api/customer/save")).build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id){
        if (id != null){
            customerService.deleteById(id);
            return ResponseEntity.ok("Cliente deletado");
        }
        return ResponseEntity.badRequest().build();
    }
}

