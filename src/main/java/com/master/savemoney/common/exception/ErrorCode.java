package com.master.savemoney.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
  // 회원
  MEMBER_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), "회원을 찾을 수 없습니다."),
  WRONG_EMAIL_OR_PASSWORD(HttpStatus.BAD_REQUEST.value(), "이메일 혹은 비밀번호가 일치하지 않습니다."),
  ALREADY_REGISTERED_EMAIL(HttpStatus.BAD_REQUEST.value(), "이미 사용중인 이메일입니다."),

  // 챌린지
  BEFORE_DATE_TIME(HttpStatus.BAD_REQUEST.value(), "지난 날짜의 챌린지는 생성할 수 없습니다."),
  EQUAL_MONTH(HttpStatus.BAD_REQUEST.value(), "당월의 챌린지는 생성할 수 없습니다."),
  CHALLENGE_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), "챌린지를 찾을 수 없습니다."),
  CHALLENGE_NOT_MATCH_TYPE(HttpStatus.BAD_REQUEST.value(), "타입에 맞는 챌린지를 찾을 수 없습니다."),
  MEMBER_NOT_MATCH_CHALLENGE(HttpStatus.BAD_REQUEST.value(), "회원의 챌린지가 아닙니다."),
  FINISHED_CHALLENGE_OR_IN_PROGRESS(HttpStatus.BAD_REQUEST.value(), "진행 중이거나 종료된 챌린지입니다."),
  FAIL_DELETE_CHALLENGE(HttpStatus.BAD_REQUEST.value(), "챌린지를 삭제할 수 없습니다."),
  NO_ONGOING_CHALLENGE(HttpStatus.BAD_REQUEST.value(), "진행 중인 챌린지가 없습니다."),

  // 챌린지 목표
  MEMBER_NOT_MATCH_CHALLENGE_GOAL(HttpStatus.BAD_REQUEST.value(), "회원의 챌린지 목표가 아닙니다."),
  CHALLENGE_GOAL_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), "챌린지 목표를 찾을 수 없습니다."),

  // 결제내역
  PAYMENT_DETAIL_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), "결제내역을 찾을 수 없습니다."),
  MEMBER_NOT_MATCH_PAYMENT_DETAIL(HttpStatus.BAD_REQUEST.value(), "회원님의 결제내역이 아닙니다."),
  PAYMENT_DETAIL_EMPTY(HttpStatus.BAD_REQUEST.value(), "등록된 결제내역이 없습니다."),

  // 상품
  GOODS_NOT_REGISTERED(HttpStatus.BAD_REQUEST.value(), "등록된 상품이 없습니다."),
  GOODS_NOT_SEARCHED(HttpStatus.BAD_REQUEST.value(), "검색된 상품이 없습니다.")
  ;

  private final int statusCode;
  private final String description;
}
