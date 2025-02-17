package com.ft.accounts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor //By default no all arg const created with @Data
public class ResponseDto {
    private String statusCode;
    private String statusMsg;
}
