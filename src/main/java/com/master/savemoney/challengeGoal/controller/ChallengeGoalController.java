package com.master.savemoney.challengeGoal.controller;


import com.master.savemoney.challengeGoal.dto.RegisterChallengeGoalForm;
import com.master.savemoney.challengeGoal.dto.UpdateChallengeGoalForm;
import com.master.savemoney.challengeGoal.service.ChallengeGoalService;
import com.master.savemoney.common.security.TokenProvider;
import io.swagger.annotations.ApiOperation;
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
@RequestMapping("/api/challenge-goal")
public class ChallengeGoalController {
  private final ChallengeGoalService challengeGoalService;
  private final TokenProvider tokenProvider;

  // 챌린지 소비항목별 목표금액 등록
  @ApiOperation(value = "챌린지 목표 등록")
  @PostMapping("/register/{challengeId}")
  @PreAuthorize("hasRole('ROLE_MEMBER')")
  public ResponseEntity<?> registerChallengeGoal(@RequestHeader("Authorization") String token,
      @RequestBody RegisterChallengeGoalForm form, @PathVariable Long challengeId) {
    return ResponseEntity.ok()
        .body(challengeGoalService.registerChallengeGoal(tokenProvider.getAuthentication(token.substring(7)).getName(), challengeId, form));
  }

  // 챌린지 목표 조회
  @ApiOperation(value = "챌린지 목표 조회")
  @GetMapping("/{challengeGoalId}")
  @PreAuthorize("hasRole('ROLE_MEMBER')")
  public ResponseEntity<?> getChallengeGoal(@RequestHeader("Authorization") String token, @PathVariable Long challengeGoalId) {
    return ResponseEntity.ok()
        .body(challengeGoalService.getChallengeGoal(tokenProvider.getAuthentication(token.substring(7)).getName(), challengeGoalId));
  }

  // 챌린지 목표 수정
  @ApiOperation(value = "챌린지 목표 수정")
  @PutMapping("/update/{challengeGoalId}")
  @PreAuthorize("hasRole('ROLE_MEMBER')")
  public ResponseEntity<?> updateChallengeGoal(@RequestHeader("Authorization") String token,
      @RequestBody UpdateChallengeGoalForm form, @PathVariable Long challengeGoalId) {
    return ResponseEntity.ok()
        .body(challengeGoalService.updateChallengeGoal(tokenProvider.getAuthentication(token.substring(7)).getName(), challengeGoalId, form));
  }

  // 챌린지 목표 삭제
  @ApiOperation(value = "챌린지 목표 삭제")
  @DeleteMapping("/delete/{challengeGoalId}")
  @PreAuthorize("hasRole('ROLE_MEMBER')")
  public ResponseEntity<?> deleteChallengeGoal(@RequestHeader("Authorization") String token, @PathVariable Long challengeGoalId) {
    challengeGoalService.deleteChallengeGoal(tokenProvider.getAuthentication(token.substring(7)).getName(), challengeGoalId);
    return ResponseEntity.ok("챌린지 목표가 삭제되었습니다.");
  }
}
