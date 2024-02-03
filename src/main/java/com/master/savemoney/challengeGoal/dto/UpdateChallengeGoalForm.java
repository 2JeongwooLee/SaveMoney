package com.master.savemoney.challengeGoal.dto;

import com.master.savemoney.common.type.ConsumptionCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateChallengeGoalForm {
  private ConsumptionCategory targetCategory;
  private Integer targetMoney;
}
