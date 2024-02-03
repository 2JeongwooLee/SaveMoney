package com.master.savemoney.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
  MEMBER_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), "회원을 찾을 수 없습니다."),
  WRONG_EMAIL_OR_PASSWORD(HttpStatus.BAD_REQUEST.value(), "이메일 혹은 비밀번호가 일치하지 않습니다."),
  ALREADY_REGISTERED_EMAIL(HttpStatus.BAD_REQUEST.value(), "이미 사용중인 이메일입니다."),
  BEFORE_DATE_TIME(HttpStatus.BAD_REQUEST.value(), "지난 날짜의 챌린지는 생성할 수 없습니다."),
  EQUAL_MONTH(HttpStatus.BAD_REQUEST.value(), "당월의 챌린지는 생성할 수 없습니다."),
  CHALLENGE_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), "챌린지를 찾을 수 없습니다."),
  MEMBER_NOT_MATCH_CHALLENGE(HttpStatus.BAD_REQUEST.value(), "회원의 챌린지가 아닙니다."),
  MEMBER_NOT_MATCH_CHALLENGE_GOAL(HttpStatus.BAD_REQUEST.value(), "회원의 챌린지 목표가 아닙니다."),
  FINISHED_CHALLENGE_OR_IN_PROGRESS(HttpStatus.BAD_REQUEST.value(), "진행 중이거나 종료된 챌린지입니다."),
  CHALLENGE_GOAL_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), "챌린지 목표를 찾을 수 없습니다."),
  FAIL_DELETE(HttpStatus.BAD_REQUEST.value(), "챌린지를 삭제할 수 없습니다.")
  ;

  private final int statusCode;
  private final String description;
}
