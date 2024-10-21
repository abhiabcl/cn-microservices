package com.ft.accounts.service;

import com.ft.accounts.dto.CustomerDetailsDto;

public interface ICustomersService {
    CustomerDetailsDto fetchCustomerDetails(String mobileNumber);
}
