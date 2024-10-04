package com.ft.accounts.dto;

import com.ft.accounts.repository.AccountsRepository;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Schema(
        name = "Customer",
        description = "Schema to hold customer and account details"
)
@Data
public class CustomerDto {

    @Schema(
            description = "Name of customer", example = "Abhishek"
    )
    @NotEmpty(message = "Name can not be null or empty!")
    @Size(min=5, max=30 , message = "the length of the customer name should be between 5 and 30 ")
    private String name;

    @Schema(
            description = "Email of customer", example = "abhi@aa.com"
    )
    @NotEmpty(message = "Email can not be null or empty!")
    @Email(message = "Email address is not valid!")
    private String email;

    @Schema(
            description = "MobileNumber of customer", example = "1236547890"
    )
//    @NotEmpty(message = "Mobile Number can not be null or empty!")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile Number should be 10 digit!")
    private String mobileNumber;

    @Schema(
            description = "Account details of customer"
    )
    private AccountsDto accountsDto;
}
