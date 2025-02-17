package com.ft.accounts.functions;

import com.ft.accounts.service.IAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;


@Configuration
public class AccountFunctions {

    private final static Logger log = LoggerFactory.getLogger(AccountFunctions.class);

//  no response needed to used consumer functional interface from java function
    @Bean
    public Consumer<Long> updateCommunication(IAccountService accountsService) {
        return accountNumber -> {
            log.info("Updating Communication status for the account number : " + accountNumber.toString());
            accountsService.updateCommunicationStatus(accountNumber);
        };
    }
}
