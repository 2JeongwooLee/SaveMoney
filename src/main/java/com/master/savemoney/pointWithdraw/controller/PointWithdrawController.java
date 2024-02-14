package com.master.savemoney.pointWithdraw.controller;

import com.master.savemoney.common.security.TokenProvider;
import com.master.savemoney.pointWithdraw.service.PointWithdrawService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/point-withdraw")
public class PointWithdrawController {
  private final PointWithdrawService pointWithdrawService;
  private final TokenProvider tokenProvider;

  // 결제
  @PostMapping("/payment/{goodsId}")
  @PreAuthorize("hasRole('ROLE_MEMBER')")
  public ResponseEntity<?> paymentGoods(@RequestHeader("Authorization") String token, @PathVariable Long goodsId) {
    return ResponseEntity.ok().body(pointWithdrawService.paymentGoods(tokenProvider.getAuthentication(token.substring(7)).getName(), goodsId));
  }

  // 포인트 출금 조회
  @GetMapping("/member")
  @PreAuthorize("hasRole('ROLE_MEMBER')")
  public ResponseEntity<?> getPointWithdraw(@RequestHeader("Authorization") String token) {
    return ResponseEntity.ok().body(pointWithdrawService.getPointWithdraw(tokenProvider.getAuthentication(token.substring(7)).getName()));
  }
}
