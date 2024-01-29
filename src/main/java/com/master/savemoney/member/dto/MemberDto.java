package com.master.savemoney.member.dto;

import com.master.savemoney.member.entity.Member;
import java.util.Collection;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto implements UserDetails {
  private Long id;
  private String email;
  private String password;
  private String memberName;
  private String phoneNumber;
  private Long point;

  public static MemberDto from(Member member) {
    return MemberDto.builder()
        .id(member.getId())
        .email(member.getEmail())
        .password(member.getPassword())
        .memberName(member.getMemberName())
        .phoneNumber(member.getPhoneNumber())
        .point(member.getPoint())
        .build();
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority("ROLE_MEMBER"));
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return null;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
