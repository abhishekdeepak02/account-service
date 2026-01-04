package com.lazybytes.account.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Schema(
        name = "Customer Details",
        description = "Schema to hold Customer, Account, cards and loan information"
)
@Data
public class CustomerDetailsDto {

    @Schema(
            description = "Name of the Customer",
            example = "John Doe"
    )
    @NotEmpty(message = "Name cannot be null or empty")
    @Size(min = 5, max = 30, message = "Name must be between 5 and 30 characters")
    private String name;

    @Schema(
            description = "Email address of the Customer",
            example = "abc@xyz.com"
    )
    @NotEmpty(message = "Email cannot be null or empty")
    @Email(message = "Email should be valid")
    private String email;

    @Schema(
            description = "Mobile Number of the Customer",
            example = "9988765434"
    )
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
    private String mobileNumber;

    @Schema(description = "Account details of the customer.")
    private AccountsDto accountsDto;

    @Schema(description = "Card details of the customer.")
    private CardsDto cardsDto;

    @Schema(description = "Loan details of the customer.")
    private LoanDto loanDto;
}
