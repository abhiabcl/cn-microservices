package com.ft.accounts.service.impl;

import com.ft.accounts.constants.AccountsConstants;
import com.ft.accounts.dto.AccountsDto;
import com.ft.accounts.dto.CustomerDto;
import com.ft.accounts.entity.Accounts;
import com.ft.accounts.entity.Customer;
import com.ft.accounts.exception.CustomerAlreadyExistsException;
import com.ft.accounts.exception.ResourceNotFoundException;
import com.ft.accounts.mapper.AccountMapper;
import com.ft.accounts.mapper.CustomerMapper;
import com.ft.accounts.repository.AccountsRepository;
import com.ft.accounts.repository.CustomerRepository;
import com.ft.accounts.service.IAccountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor // required to automatically create the object of member no need for @Autowire for both repository objects
public class AccountServiceImpl implements IAccountService {

    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;

    @Override
    public void createAccount(CustomerDto customerDto) throws CustomerAlreadyExistsException {
        Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());
        Optional<Customer> optionalCustomer = customerRepository.findByMobileNumber(customerDto.getMobileNumber());

        if (optionalCustomer.isPresent()) {
            throw new CustomerAlreadyExistsException("Customer already registed with given mobileNumer: "
                    +customerDto.getMobileNumber());
        }

        customer.setCreatedAt(LocalDateTime.now());
        customer.setUpdatedAt(LocalDateTime.now());
        customer.setUpdatedBy("ADMIN");
        customer.setCreatedBy("ADMIN");
        Customer saveCustomer = customerRepository.save(customer);
        accountsRepository.save(createNewAccount(saveCustomer));
    }

    @Override
    public CustomerDto fetchAccount(String mobileNumber) {

        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                ()-> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber));

        Accounts account = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                ()-> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString()));

        CustomerDto customerDto  = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
        customerDto.setAccountsDto(AccountMapper.mapToAccountsDto(account, new AccountsDto()));

        return customerDto;
    }

    @Override
    public boolean updateAccount(CustomerDto customerDto) {
        boolean isUpdated = false;
        AccountsDto accountsDto = customerDto.getAccountsDto();

        if (accountsDto != null){
            Accounts accounts = accountsRepository.findById(accountsDto.getAccountNumber()).orElseThrow(
                    ()-> new ResourceNotFoundException("Account", "accountNumber", accountsDto.getAccountNumber().toString()));

            AccountMapper.mapToAccounts(accountsDto, accounts);
            accounts = accountsRepository.save(accounts);

            Long customerId = accounts.getCustomerId();
            Customer customer = customerRepository.findById(customerId).orElseThrow(
                    ()-> new ResourceNotFoundException("Customer", "mobileNumber", customerDto.getMobileNumber()));
            CustomerMapper.mapToCustomer(customerDto, customer);
            customerRepository.save(customer);
            isUpdated = true;
        }
        return isUpdated;
    }

    @Override
    public boolean deleteAccount(String mobileNumber) {
        boolean isDeleted = false;

        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                ()-> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber));
        if ( customer != null ) {
            Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                    ()-> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString()));
            customerRepository.deleteById(customer.getCustomerId());
            accountsRepository.deleteByCustomerId(customer.getCustomerId());
            isDeleted = true;
        }

        return isDeleted;
    }

    private Accounts createNewAccount(Customer customer) {
        Accounts newAccount = new Accounts();
        newAccount.setCustomerId(customer.getCustomerId());
        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);

        newAccount.setAccountNumber(randomAccNumber);
//        newAccount.setCreatedAt(LocalDateTime.now());
//        newAccount.setUpdatedAt(LocalDateTime.now());
//        newAccount.setCreatedBy("ADMIN");
        newAccount.setAccountType(AccountsConstants.SAVINGS);
        newAccount.setBranchAddress(AccountsConstants.ADDRESS);
        return newAccount;
    }
}
