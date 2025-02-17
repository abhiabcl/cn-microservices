package com.ft.cards.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

@Schema(
        name = "Cards",
        description = "Schema to hold the card information."
)
@Data
public class CardsDto {
    @Schema(
            description = "Mobile number of customer.", example = "7896541203"
    )
    @NotEmpty(message = "Mobile Number can not be null or empty!")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile Number must be 10 digits!")
    private String mobileNumber;

    @Schema(
            description = "Card Number of customer.", example = "789645632589"
    )
    @NotEmpty(message = "Card Number can not be null or empty!")
    @Pattern(regexp = "(^$|[0-9]{12})", message = "CardNumber must be 12 digits!")
    private String cardNumber;

    @Schema(
            description = "Card type of customer", example = "Credit Card"
    )
    @NotEmpty(message = "Card Type can not be null or empty!")
    private String cardType;

    @Positive(message = "Total card limit should be greater than zero")
    @Schema(
            description = "Total amount limit available against a card", example = "100000"
    )
    private int totalLimit;

    @PositiveOrZero(message = "Total amount used should be equal or greater than zero")
    @Schema(
            description = "Total amount used by a Customer", example = "1000"
    )
    private int amountUsed;

    @PositiveOrZero(message = "Total available amount should be equal or greater than zero")
    @Schema(
            description = "Total available amount against a card", example = "90000"
    )
    private int availableAmount;
}
