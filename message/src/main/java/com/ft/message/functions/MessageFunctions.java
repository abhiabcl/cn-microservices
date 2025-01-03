package com.ft.message.functions;

import com.ft.message.dto.AccountsMsgDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Configuration
public class MessageFunctions {

    private static final Logger log = LoggerFactory.getLogger(MessageFunctions.class);

    // define the business logic using function Function<T, R> T- input & R - Output
    @Bean
    public Function<AccountsMsgDto, AccountsMsgDto> email(){
        // write lamda expression  /logic for BL
        return accountsMsgDto -> {
            // BL to log the msg when message received.
            log.info("Sending email with the details: " + accountsMsgDto.toString());
            return accountsMsgDto;
        } ;
    }

    @Bean
    public Function<AccountsMsgDto, Long> sms(){
        // write lamda expression  /logic for BL
        return accountsMsgDto -> {
            // BL to log the msg when message received.
            log.info("Sending sms with the details: " + accountsMsgDto.toString());
            return accountsMsgDto.accountNumber();
        } ;
    }
}
