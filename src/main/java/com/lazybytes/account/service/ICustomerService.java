package com.lazybytes.account.service;

import com.lazybytes.account.dto.CustomerDetailsDto;

public interface ICustomerService {

    CustomerDetailsDto fetchCustomerDetails(String mobileNumber);
}
