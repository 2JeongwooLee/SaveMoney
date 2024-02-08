package com.master.savemoney.challenge.dto;

import com.master.savemoney.challenge.entity.Challenge;
import com.master.savemoney.challenge.type.ChallengeType;
import com.master.savemoney.challengeGoal.dto.ChallengeGoalDto;
import com.master.savemoney.challengeGoal.entity.ChallengeGoal;
import com.master.savemoney.common.type.ConsumptionCategory;
import com.master.savemoney.paymentDetail.dto.PaymentDetailDto;
import io.swagger.models.auth.In;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
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
public class ChallengeDetail {
  private Long id;
  private Long memberId;
  private String challengeName;
  private ChallengeType challengeType;
  private List<ChallengeGoalDto> challengeGoalList;
  private Map<ConsumptionCategory, Integer> paymentMap;
  private LocalDateTime challengeStartDateTime;
  private LocalDateTime challengeEndDateTime;

  public static ChallengeDetail from(Challenge challenge, List<ChallengeGoalDto> challengeGoalList, Map<ConsumptionCategory, Integer> paymentMap) {
    return ChallengeDetail.builder()
        .id(challenge.getId())
        .memberId(challenge.getMember().getId())
        .challengeName(challenge.getChallengeName())
        .challengeType(challenge.getChallengeType())
        .challengeGoalList(challengeGoalList)
        .paymentMap(paymentMap)
        .challengeStartDateTime(challenge.getChallengeStartDateTime())
        .challengeEndDateTime(challenge.getChallengeEndDateTime())
        .build();
  }
}
