package com.lazybytes.account.service.client;

import com.lazybytes.account.dto.CardsDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("cards")
public interface CardsFeignClient {

    @GetMapping(value = "api/cards/fetch", consumes = "application/json")
    public ResponseEntity<CardsDto> getCardDetails(@RequestParam String mobileNumber);
}
