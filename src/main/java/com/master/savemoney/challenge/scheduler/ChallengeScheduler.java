package com.master.savemoney.challenge.scheduler;

import com.master.savemoney.challenge.entity.Challenge;
import com.master.savemoney.challenge.repository.ChallengeRepository;
import com.master.savemoney.challenge.type.ChallengeType;
import com.master.savemoney.challengeGoal.entity.ChallengeGoal;
import com.master.savemoney.challengeGoal.repository.ChallengeGoalRepository;
import com.master.savemoney.common.type.ConsumptionCategory;
import com.master.savemoney.paymentDetail.entity.PaymentDetail;
import com.master.savemoney.paymentDetail.repository.PaymentDetailRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class ChallengeScheduler {
  private final ChallengeRepository challengeRepository;
  private final ChallengeGoalRepository challengeGoalRepository;
  private final PaymentDetailRepository paymentDetailRepository;

  @Scheduled(cron = "${scheduler.start.challenge}")
  public void challengeStartScheduling() {
    log.info(LocalDateTime.now().getMonth() + "월 챌린지 시작");

    List<Challenge> challengeList = challengeRepository
        .findAllStartChallenge(LocalDateTime.now());

    for (Challenge challenge : challengeList) {
      challenge.setChallengeType(ChallengeType.IN_PROGRESS);
      challengeRepository.save(challenge);
    }
  }

  @Scheduled(cron = "${scheduler.end.challenge}")
  public void challengeEndScheduling() {
    log.info(LocalDateTime.now().getMonth() + "월 챌린지 종료");

    List<Challenge> challengeList = challengeRepository.findAllEndChallenge();

    for (Challenge challenge : challengeList) {
      List<ChallengeGoal> challengeGoalList = challengeGoalRepository.findAllByChallengeId(challenge.getId());
      List<PaymentDetail> paymentDetailList = paymentDetailRepository.findAllByChallengeIdGroupByPaymentCategory(challenge.getId());

      Map<ConsumptionCategory, Integer> checkingMap = challengeGoalList.stream()
          .collect(Collectors.toMap(ChallengeGoal::getTargetCategory, ChallengeGoal::getTargetMoney));

      for (PaymentDetail paymentDetail : paymentDetailList) {
        checkingMap.put(paymentDetail.getPaymentCategory(), checkingMap.get(paymentDetail.getPaymentCategory()) - paymentDetail.getPaymentMoney());

        if (checkingMap.get(paymentDetail.getPaymentCategory()) < 0) {
          challenge.setChallengeType(ChallengeType.FAIL);
          challengeRepository.save(challenge);
          break;
        }
      }

      challenge.setChallengeType(ChallengeType.SUCCESS);
      challengeRepository.save(challenge);
    }
  }
}
