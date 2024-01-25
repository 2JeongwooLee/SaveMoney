package com.master.savemoney.member.dto;

import com.master.savemoney.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {
  private Long id;
  private String email;
  private String memberName;
  private String phoneNumber;
  private Long point;

  public static MemberDto from(Member member) {
    return MemberDto.builder()
        .id(member.getId())
        .email(member.getEmail())
        .memberName(member.getMemberName())
        .phoneNumber(member.getPhoneNumber())
        .point(member.getPoint())
        .build();
  }
}
