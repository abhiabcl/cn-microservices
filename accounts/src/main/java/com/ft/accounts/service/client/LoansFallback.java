package com.ft.accounts.service.client;

import com.ft.accounts.dto.LoansDto;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;

@Configuration
public class LoansFallback implements LoansFeignClient{
    @Override
    public ResponseEntity<LoansDto> fetchLoanDetails(String correlationId, String mobileNumber) {
        return null;
    }
}
