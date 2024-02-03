package com.master.savemoney.challengeGoal.dto;

import com.master.savemoney.challengeGoal.entity.ChallengeGoal;
import com.master.savemoney.common.type.ConsumptionCategory;
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
public class ChallengeGoalDto {
  private Long id;
  private Long challengeId;
  private ConsumptionCategory targetCategory;
  private Integer targetMoney;

  public static ChallengeGoalDto from(ChallengeGoal challengeGoal) {
    return ChallengeGoalDto.builder()
        .id(challengeGoal.getId())
        .challengeId(challengeGoal.getChallenge().getId())
        .targetCategory(challengeGoal.getTargetCategory())
        .targetMoney(challengeGoal.getTargetMoney())
        .build();
  }
}
