package com.ft.accounts.service.impl;

import com.ft.accounts.constants.AccountsConstants;
import com.ft.accounts.dto.AccountsDto;
import com.ft.accounts.dto.AccountsMsgDto;
import com.ft.accounts.dto.CustomerDto;
import com.ft.accounts.entity.Accounts;
import com.ft.accounts.entity.Customer;
import com.ft.accounts.exception.CustomerAlreadyExistsException;
import com.ft.accounts.exception.ResourceNotFoundException;
import com.ft.accounts.mapper.AccountsMapper;
import com.ft.accounts.mapper.CustomerMapper;
import com.ft.accounts.repository.AccountsRepository;
import com.ft.accounts.repository.CustomerRepository;
import com.ft.accounts.service.IAccountService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor // required to automatically create the object of member no need for @Autowire for both repository objects
public class AccountServiceImpl implements IAccountService {

    private static final Logger log = LoggerFactory.getLogger(AccountServiceImpl.class);

    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;
    private final StreamBridge streamBridge;   // use for streaming the message to Queue

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
        Accounts savedAccount = accountsRepository.save(createNewAccount(saveCustomer));
        sendCommunication(savedAccount, saveCustomer);

    }

    private void sendCommunication(Accounts account, Customer customer) {
        var accountsMsgDto = new AccountsMsgDto(account.getAccountNumber(), customer.getName(),
                customer.getEmail(), customer.getMobileNumber());
        log.info("Sending Communication request for the details: {}", accountsMsgDto);
        var result = streamBridge.send("sendCommunication-out-0", accountsMsgDto);
        log.info("Is the Communication request successfully triggered ? : {}", result);
    }

    @Override
    public CustomerDto fetchAccount(String mobileNumber) {

        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                ()-> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber));

        Accounts account = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                ()-> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString()));

        CustomerDto customerDto  = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
        customerDto.setAccountsDto(AccountsMapper.mapToAccountsDto(account, new AccountsDto()));

        return customerDto;
    }

    @Override
    public boolean updateAccount(CustomerDto customerDto) {
        boolean isUpdated = false;
        AccountsDto accountsDto = customerDto.getAccountsDto();

        if (accountsDto != null){
            Accounts accounts = accountsRepository.findById(accountsDto.getAccountNumber()).orElseThrow(
                    ()-> new ResourceNotFoundException("Account", "accountNumber", accountsDto.getAccountNumber().toString()));

            AccountsMapper.mapToAccounts(accountsDto, accounts);
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

    /**
     * @param accountNumber - Long
     * @return boolean indicating if the update of communication status is successful or not
     */
//    @Override
//    public boolean updateCommunicationStatus(Long accountNumber) {
//        boolean isUpdated = false;
//        if(accountNumber !=null ){
//            Accounts accounts = accountsRepository.findById(accountNumber).orElseThrow(
//                    () -> new ResourceNotFoundException("Account", "AccountNumber", accountNumber.toString())
//            );
//            accounts.setCommunicationSw(true);
//            accountsRepository.save(accounts);
//            isUpdated = true;
//        }
//        return  isUpdated;
//    }

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
