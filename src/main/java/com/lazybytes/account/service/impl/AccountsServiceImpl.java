package com.lazybytes.account.service.impl;

import com.lazybytes.account.constants.AccountsConstants;
import com.lazybytes.account.dto.AccountsDto;
import com.lazybytes.account.dto.CustomerDto;
import com.lazybytes.account.entity.Accounts;
import com.lazybytes.account.entity.Customer;
import com.lazybytes.account.exception.CustomerAlreadyExistsException;
import com.lazybytes.account.exception.ResourceNotFoundException;
import com.lazybytes.account.mapper.AccountMapper;
import com.lazybytes.account.mapper.CustomerMapper;
import com.lazybytes.account.repository.AccountsRepository;
import com.lazybytes.account.repository.CustomerRepository;
import com.lazybytes.account.service.IAccountsService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountsServiceImpl implements IAccountsService {

    private static final Logger logger = LoggerFactory.getLogger(AccountsServiceImpl.class);

    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;

    @Override
    public void createAccount(CustomerDto customerDto) {

        Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());
        Optional<Customer> optionalCustomer = customerRepository.findByMobileNumber(customerDto.getMobileNumber());
        if(optionalCustomer.isPresent()) {
            throw new CustomerAlreadyExistsException("Customer already exist with given mobile number "
                    + customerDto.getMobileNumber());
        }
        Customer savedCustomer = customerRepository.save(customer);
        logger.info("Customer %s is saved into DB.", savedCustomer.getName());
        accountsRepository.save(createNewAccount(savedCustomer));

    }

    @Override
    public CustomerDto fetchAccount(String mobileNumber) {


        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobile number", mobileNumber)
        );

        Accounts account = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                ()-> new ResourceNotFoundException("Account", "customer_id", customer.getCustomerId().toString())
        );

        AccountsDto accountsDto = AccountMapper.mapToAccountsDto(account, new AccountsDto());
        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
        customerDto.setAccountsDto(accountsDto);

        return customerDto;

    }

    @Override
    public boolean updateAccount(CustomerDto customerDto) {

        boolean isUpdated = false;

        Accounts account = accountsRepository.findById(customerDto.getAccountsDto().getAccountNumber()).orElseThrow(
                ()-> new ResourceNotFoundException("Account", "account-number",
                        customerDto.getAccountsDto().getAccountNumber().toString())
        );

        Accounts updatedAccount = AccountMapper.mapToAccounts(customerDto.getAccountsDto(), account);

        accountsRepository.save(updatedAccount);
        Long customerId = account.getCustomerId();
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new ResourceNotFoundException(
                "Customer", "customer_id", customerId.toString()
        ));

        Customer updatedCustomer = CustomerMapper.mapToCustomer(customerDto, customer);
        customerRepository.save(updatedCustomer);
        isUpdated = true;
        return isUpdated;
    }

    @Override
    public boolean deleteAccount(String mobileNumber) {
        boolean isDeleted = false;

        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(() ->
                new ResourceNotFoundException("Cutomer", "mobile-number", mobileNumber));

        customerRepository.deleteById(customer.getCustomerId());
        accountsRepository.deleteByCustomerId(customer.getCustomerId());

        isDeleted = true;
        return isDeleted;

    }

    private Accounts createNewAccount(Customer customer) {
        Accounts newAccount = new Accounts();
        newAccount.setCustomerId(customer.getCustomerId());
        long randomAccountNumber = 1000000000L + new Random().nextInt(900000000);
        newAccount.setAccountNumber(randomAccountNumber);
        newAccount.setAccountType(AccountsConstants.SAVING);
        newAccount.setBranchAddress(AccountsConstants.ADDRESS);
        return newAccount;
    }
}
