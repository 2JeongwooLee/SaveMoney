package com.master.savemoney.challenge.dto;

import com.master.savemoney.challenge.entity.Challenge;
import com.master.savemoney.challenge.type.ChallengeType;
import java.time.LocalDateTime;
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
public class ChallengeDto {
  private Long id;
  private Long memberId;
  private String challengeName;
  private ChallengeType challengeType;
  private LocalDateTime challengeStartDateTime;
  private LocalDateTime challengeEndDateTime;

  public static ChallengeDto from(Challenge challenge) {
    return ChallengeDto.builder()
        .id(challenge.getId())
        .memberId(challenge.getMember().getId())
        .challengeName(challenge.getChallengeName())
        .challengeType(challenge.getChallengeType())
        .challengeStartDateTime(challenge.getChallengeStartDateTime())
        .challengeEndDateTime(challenge.getChallengeEndDateTime())
        .build();
  }
}
