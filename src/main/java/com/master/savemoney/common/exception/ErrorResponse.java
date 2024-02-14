package com.master.savemoney.common.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class ErrorResponse {
  private ErrorCode errorCode;
  private int httpStatus;
  private String errorMessage;
}
