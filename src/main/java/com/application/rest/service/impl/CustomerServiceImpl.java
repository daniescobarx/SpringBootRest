package com.application.rest.service.impl;

import com.application.rest.entities.Customer;
import com.application.rest.persistence.ICustomerDAO;
import com.application.rest.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements ICustomerService {

    @Autowired
    private ICustomerDAO customerDAO;


    @Override
    public List<Customer> findAll() {
        return customerDAO.findAll();
    }

    @Override
    public Optional<Customer> findById(Long id) {
        return customerDAO.findById(id);
    }

    @Override
    public void save(Customer customer) {
        customerDAO.save(customer);
    }

    @Override
    public void deleteById(Long id) {
        customerDAO.deleteById(id);
    }
}
