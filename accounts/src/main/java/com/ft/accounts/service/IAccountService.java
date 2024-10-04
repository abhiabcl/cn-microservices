package com.ft.accounts.service;

import com.ft.accounts.dto.CustomerDto;
import com.ft.accounts.entity.Customer;
import com.ft.accounts.exception.CustomerAlreadyExistsException;

public interface IAccountService {
    void createAccount(CustomerDto customerDto) throws CustomerAlreadyExistsException;
    CustomerDto fetchAccount(String mobileNumber);
    boolean updateAccount(CustomerDto customerDto);
    boolean deleteAccount(String mobileNumber);
}
