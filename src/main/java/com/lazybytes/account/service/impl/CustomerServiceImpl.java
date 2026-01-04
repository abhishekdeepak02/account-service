package com.lazybytes.account.service.impl;

import com.lazybytes.account.dto.AccountsDto;
import com.lazybytes.account.dto.CardsDto;
import com.lazybytes.account.dto.CustomerDetailsDto;
import com.lazybytes.account.dto.LoanDto;
import com.lazybytes.account.entity.Accounts;
import com.lazybytes.account.entity.Customer;
import com.lazybytes.account.exception.ResourceNotFoundException;
import com.lazybytes.account.mapper.AccountMapper;
import com.lazybytes.account.mapper.CustomerDetailsMapper;
import com.lazybytes.account.repository.AccountsRepository;
import com.lazybytes.account.repository.CustomerRepository;
import com.lazybytes.account.service.ICustomerService;
import com.lazybytes.account.service.client.CardsFeignClient;
import com.lazybytes.account.service.client.LoansFeignClient;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerServiceImpl implements ICustomerService {

    private CustomerRepository customerRepository;
    private AccountsRepository accountsRepository;
    private LoansFeignClient loansClient;
    private CardsFeignClient cardsClient;

    public CustomerServiceImpl(CustomerRepository customerRepository,
                               AccountsRepository accountsRepository,
                               LoansFeignClient loansClient,
                               CardsFeignClient cardsClient) {
        this.customerRepository = customerRepository;
        this.accountsRepository = accountsRepository;
        this.loansClient = loansClient;
        this.cardsClient = cardsClient;
    }

    @Override
    public CustomerDetailsDto fetchCustomerDetails(String mobileNumber) {

        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobile number", mobileNumber)
        );

        Accounts account = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account", "Customer_Id", customer.getCustomerId().toString()));

        CustomerDetailsDto customerDetailsDto = CustomerDetailsMapper.mapToCustomerDetailsDto(customer, new CustomerDetailsDto());
        customerDetailsDto.setAccountsDto(AccountMapper.mapToAccountsDto(account, new AccountsDto()));

        ResponseEntity<LoanDto> loanDtoResponseEntity = loansClient.fetchLoanDetails(mobileNumber);
        customerDetailsDto.setLoanDto(loanDtoResponseEntity.getBody());

        ResponseEntity<CardsDto> cardDetails = cardsClient.getCardDetails(mobileNumber);
        customerDetailsDto.setCardsDto(cardDetails.getBody());

        return customerDetailsDto;

    }
}
