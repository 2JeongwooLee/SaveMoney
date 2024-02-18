package com.master.savemoney.common.exception;



import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionHandler {

  @org.springframework.web.bind.annotation.ExceptionHandler(CustomException.class)
  public ErrorResponse handleCustomeException(CustomException e) {
    log.error("CustomException is occured" + e);
    return new ErrorResponse(e.getErrorCode(), e.getErrorCode().getStatusCode(), e.getErrorCode().getDescription());
  }

  @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
  public ErrorResponse handleException(Exception e) {
    log.error("Exception is occured" + e);
    return new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), ErrorCode.INTERNAL_SERVER_ERROR.getDescription());
  }
}
