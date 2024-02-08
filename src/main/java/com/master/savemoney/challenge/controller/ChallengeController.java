package com.master.savemoney.challenge.controller;

import com.master.savemoney.challenge.dto.ChallengeDto;
import com.master.savemoney.challenge.dto.RegisterChallengeForm;
import com.master.savemoney.challenge.service.ChallengeService;
import com.master.savemoney.challenge.type.ChallengeType;
import com.master.savemoney.common.security.TokenProvider;
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
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/challenge")
public class ChallengeController {
  private final ChallengeService challengeService;
  private final TokenProvider tokenProvider;

  // 챌린지 등록
  @PostMapping("/register")
  @PreAuthorize("hasRole('ROLE_MEMBER')")
  public ResponseEntity<?> registerChallenge(@RequestHeader("Authorization") String token, @RequestBody RegisterChallengeForm form) {
    return ResponseEntity.ok().body(challengeService.registerChallenge(tokenProvider.getAuthentication(token.substring(7)).getName(), form));
  }

  // 전체 챌린지 조회
  @GetMapping("/total")
  @PreAuthorize("hasRole('ROLE_MEMBER')")
  public ResponseEntity<?> getTotalChallenge(@RequestHeader("Authorization") String token) {
    return ResponseEntity.ok().body(challengeService.getTotalChallenge(tokenProvider.getAuthentication(token.substring(7)).getName()));
  }

  // 챌린지 타입에 따른 챌린지 조회
  @GetMapping("/Type/{challengeType}")
  @PreAuthorize("hasRole('ROLE_MEMBER')")
  public ResponseEntity<?> getChallengeWithChallengeType(
      @RequestHeader("Authorization") String token,
      @PathVariable ChallengeType challengeType) {
    return ResponseEntity.ok()
        .body(challengeService.getChallengeWithChallengeType(tokenProvider.getAuthentication(token.substring(7)).getName(), challengeType));
  }

  // 진행 중인 챌린지 조회
  @GetMapping("/in-progress")
  @PreAuthorize("hasRole('ROLE_MEMBER')")
  public ResponseEntity<?> getChallengeWithInProgress(@RequestHeader("Authorization") String token) {
    return ResponseEntity.ok()
        .body(challengeService.getChallengeWithInProgress(tokenProvider.getAuthentication(token.substring(7)).getName()));
  }

  // 챌린지 삭제
  @DeleteMapping("/delete/{challengId}")
  @PreAuthorize("hasRole('ROLE_MEMBER')")
  public ResponseEntity<?> getTotalChallenge(@RequestHeader("Authorization") String token, @PathVariable Long challengId) {
    ChallengeDto deletedChallenge = challengeService.deleteChallenge(tokenProvider.getAuthentication(token.substring(7)).getName(), challengId);
    return  ResponseEntity.ok().body(deletedChallenge.getChallengeName()  + "가 삭제되었습니다.");
  }

}
