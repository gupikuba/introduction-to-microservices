package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ErrorMessageDTO {
    private final String errorMessage;
    private final int errorCode;
}
