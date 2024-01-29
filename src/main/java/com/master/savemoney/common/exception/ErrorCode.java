package com.master.savemoney.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
  MEMBER_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), "회원을 찾을 수 없습니다."),
  WRONG_EMAIL_OR_PASSWORD(HttpStatus.BAD_REQUEST.value(), "이메일 혹은 비밀번호가 일치하지 않습니다."),
  ALREADY_REGISTERED_EMAIL(HttpStatus.BAD_REQUEST.value(), "이미 사용중인 이메일입니다.");

  private final int statusCode;
  private final String description;
}
