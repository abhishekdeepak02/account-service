package com.lazybytes.account.mapper;

import com.lazybytes.account.dto.CustomerDetailsDto;
import com.lazybytes.account.dto.CustomerDto;
import com.lazybytes.account.entity.Customer;

public class CustomerDetailsMapper {

    public static CustomerDetailsDto mapToCustomerDetailsDto(Customer customer, CustomerDetailsDto customerDetailsDto) {
        customerDetailsDto.setName(customer.getName());
        customerDetailsDto.setMobileNumber(customer.getMobileNumber());
        customerDetailsDto.setEmail(customer.getEmail());
        return customerDetailsDto;
    }

    public static Customer mapToCustomer(CustomerDetailsDto customerDto, Customer customer) {
        customer.setName(customerDto.getName());
        customer.setMobileNumber(customerDto.getMobileNumber());
        customer.setEmail(customerDto.getEmail());
        return customer;
    }
}
