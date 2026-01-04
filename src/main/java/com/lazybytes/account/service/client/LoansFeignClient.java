package com.lazybytes.account.service.client;

import com.lazybytes.account.dto.LoanDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("loan")
public interface LoansFeignClient {

    @GetMapping(value = "api/loans/fetch", consumes = "application/json")
    public ResponseEntity<LoanDto> fetchLoanDetails(@RequestParam String mobileNumber);
}
