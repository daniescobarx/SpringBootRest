package com.application.rest.persistence;

import com.application.rest.entities.Customer;

import java.util.List;
import java.util.Optional;

public interface ICustomerDAO {
    List<Customer> findAll();

    Optional<Customer> findById(Long id);

    void save(Customer customer);

    void deleteById(Long id);
}
