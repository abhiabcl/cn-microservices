package com.ft.cards.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class ResponseDto {
    public String statusCode;
    public String statusMsg;
}
