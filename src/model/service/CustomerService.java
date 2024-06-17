package model.service;

import model.dto.CustomerDto;
import model.entity.Customer;
import exception.ExceptionHandling;

import java.util.List;

public interface CustomerService  {
    List<CustomerDto> queryAllCustomers() throws ExceptionHandling;
    void addNewCustomer(Customer customer) throws ExceptionHandling;
    void updateCustomerById(Integer id) throws ExceptionHandling;
    void deleteCustomerById(Integer id);
}
