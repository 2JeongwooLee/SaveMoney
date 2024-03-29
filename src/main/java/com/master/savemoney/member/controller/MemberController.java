package com.master.savemoney.member.controller;

import com.master.savemoney.common.security.TokenProvider;
import com.master.savemoney.member.dto.LoginForm;
import com.master.savemoney.member.dto.MemberDto;
import com.master.savemoney.member.dto.RegisterForm;
import com.master.savemoney.member.dto.UpdateForm;
import com.master.savemoney.member.entity.Member;
import com.master.savemoney.member.service.MemberService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
@Slf4j
public class MemberController {
  private final MemberService memberService;
  private final TokenProvider tokenProvider;

  // 회원가입
  @ApiOperation(value = "회원가입")
  @PostMapping("/register")
  public ResponseEntity<?> registerMember(@RequestBody RegisterForm form) {
    MemberDto member = memberService.register(form);
    log.info(member.getMemberName() + "님 회원가입");
    return ResponseEntity.ok(member.getMemberName() + "님 회원가입이 완료되었습니다.");
  }

  // 로그인
  @ApiOperation(value = "로그인")
  @PostMapping("/login")
  public ResponseEntity<?> loginMember(@RequestBody LoginForm form) {
    Member member = memberService.loginMember(form);
    String token = tokenProvider.generateToken(member.getEmail());
    log.info(member.getMemberName() + "님 로그인");
    return ResponseEntity.ok(token);
  }

  // 회원정보 조회
  @ApiOperation(value = "회원정보 조회")
  @GetMapping("/info")
  @PreAuthorize("hasRole('ROLE_MEMBER')")
  public ResponseEntity<?> infoMember(@RequestHeader("Authorization") String token) {
    MemberDto memberDto = memberService.getMember(tokenProvider.getAuthentication(token.substring(7)).getName());

    return ResponseEntity.ok().body(memberDto);
  }

  // 회원정보 수정
  @ApiOperation(value = "회원정보 수정")
  @PutMapping("/update")
  @PreAuthorize("hasRole('ROLE_MEMBER')")
  public ResponseEntity<?> updateMember(@RequestHeader("Authorization") String token,
      @RequestBody UpdateForm form) {
    MemberDto memberDto = memberService.updateMember(tokenProvider.getAuthentication(token.substring(7)).getName(), form);

    return ResponseEntity.ok().body(memberDto);
  }

  // 회원탈퇴
  @ApiOperation(value = "회원탈퇴")
  @DeleteMapping("/delete")
  @PreAuthorize("hasRole('ROLE_MEMBER')")
  public ResponseEntity<?> deleteMember(@RequestHeader("Authorization") String token) {
    memberService.deleteMember(tokenProvider.getAuthentication(token.substring(7)).getName());

    return ResponseEntity.ok("회원 탈퇴가 완료되었습니다");
  }
}
