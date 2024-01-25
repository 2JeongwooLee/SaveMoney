package com.master.savemoney.member.service;

import com.master.savemoney.common.exception.CustomException;
import com.master.savemoney.common.exception.ErrorCode;
import com.master.savemoney.member.dto.LoginForm;
import com.master.savemoney.member.dto.MemberDto;
import com.master.savemoney.member.dto.RegisterForm;
import com.master.savemoney.member.dto.UpdateForm;
import com.master.savemoney.member.entity.Member;
import com.master.savemoney.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService{
  private final MemberRepository memberRepository;
  private final PasswordEncoder passwordEncoder;

  // 회원가입
  @Transactional
  public String register(RegisterForm form) {
    if (memberRepository.existsByEmail(form.getEmail())) {
      throw new CustomException(ErrorCode.ALREADY_REGISTERED_EMAIL);
    }

    Member savedMember = memberRepository.save(Member.builder()
            .email(form.getEmail())
            .password(passwordEncoder.encode(form.getPassword()))
            .memberName(form.getMemberName())
            .phoneNumber(form.getPhoneNumber())
            .point(0L)
        .build());

    return savedMember.getMemberName() + "님 회원가입 완료되었습니다.";
  }

  // 로그인
  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    Member member = memberRepository.findByEmail(email)
        .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

    return User.withUsername(member.getEmail())
        .password(passwordEncoder.encode(member.getPassword()))
        .roles("MEMBER")
        .build();
  }

  public Member loginMember(LoginForm form) {
    Member member = memberRepository.findByEmail(form.getEmail())
        .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

    if (!passwordEncoder.matches(form.getPassword(), member.getPassword())) {
      throw new CustomException(ErrorCode.WRONG_EMAIL_OR_PASSWORD);
    }

    return member;
  }

  // 회원정보 조회
  public MemberDto getMember(String email) {
    Member member = memberRepository.findByEmail(email)
        .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

    return MemberDto.from(member);
  }

  // 회원정보 수정
  public MemberDto updateMember(String email, UpdateForm form) {
    Member member = memberRepository.findByEmail(email)
        .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

    member.setMemberName(form.getMemberName());
    member.setEmail(form.getEmail());
    member.setPassword(passwordEncoder.encode(form.getPassword()));
    member.setPhoneNumber(form.getPhoneNumber());

    Member updatedMember = memberRepository.save(member);

    return MemberDto.from(updatedMember);
  }

  // 회원탈퇴
  public void deleteMember(String email) {
    Member member = memberRepository.findByEmail(email)
        .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

    memberRepository.delete(member);
  }



  // 결제내역 등록

  // 포인트 사용내역 조회

}
