package com.master.savemoney.paymentDetail.controller;

import com.master.savemoney.common.security.TokenProvider;
import com.master.savemoney.paymentDetail.dto.PaymentDetailDto;
import com.master.savemoney.paymentDetail.dto.RegisterPaymentDetailForm;
import com.master.savemoney.paymentDetail.dto.UpdatePaymentDetailForm;
import com.master.savemoney.paymentDetail.service.PaymentDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payment-detail")
public class PaymentController {
  private final PaymentDetailService paymentDetailService;
  private final TokenProvider tokenProvider;

  // 결제내역 등록
  @PostMapping("/register")
  @PreAuthorize("hasRole('ROLE_MEMBER')")
  public ResponseEntity<?> registerPaymentDetail(
      @RequestHeader("Authorization") String token,
      @RequestParam Long challengeId,
      @RequestBody RegisterPaymentDetailForm form
  ){
    return ResponseEntity.ok()
        .body(paymentDetailService.registerPaymentDetail(tokenProvider.getAuthentication(token.substring(7)).getName(), challengeId, form));
  }

  // 결제내역 조회
  @GetMapping("/{paymentDetailId}")
  @PreAuthorize("hasRole('ROLE_MEMBER')")
  public ResponseEntity<?> getPaymentDetail(@RequestHeader("Authorization") String token, @PathVariable Long paymentDetailId){

    return ResponseEntity.ok()
        .body(paymentDetailService.getPaymentDetail(tokenProvider.getAuthentication(token.substring(7)).getName(), paymentDetailId));
  }

  // 회원의 전체 결제내역 조회
  @GetMapping("/member")
  @PreAuthorize("hasRole('ROLE_MEMBER')")
  public ResponseEntity<?> getAllMemberPaymentDetail(@RequestHeader("Authorization") String token){

    return ResponseEntity.ok()
        .body(paymentDetailService.getAllMemberPaymentDetail(tokenProvider.getAuthentication(token.substring(7)).getName()));
  }

  // 결제내역 수정
  @PutMapping("/update/{paymentDetailId}")
  @PreAuthorize("hasRole('ROLE_MEMBER')")
  public ResponseEntity<?> updatePaymentDetail(
      @RequestHeader("Authorization") String token,
      @PathVariable Long paymentDetailId,
      @RequestBody UpdatePaymentDetailForm form){

    return ResponseEntity.ok()
        .body(paymentDetailService.updatePaymentDetail(tokenProvider.getAuthentication(token.substring(7)).getName(), paymentDetailId, form));
  }

  // 결제내역 삭제
  @DeleteMapping("/delete/{paymentDetailId}")
  @PreAuthorize("hasRole('ROLE_MEMBER')")
  public ResponseEntity<?> deletePaymentDetail(@RequestHeader("Authorization") String token, @PathVariable Long paymentDetailId){
    paymentDetailService.deletePaymentDetail(tokenProvider.getAuthentication(token.substring(7)).getName(), paymentDetailId);

    return ResponseEntity.ok("결제내역이 삭제되었습니다.");
  }
}
