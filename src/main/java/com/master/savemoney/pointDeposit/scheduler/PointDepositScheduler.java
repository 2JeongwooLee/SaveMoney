package com.master.savemoney.pointDeposit.scheduler;

import com.master.savemoney.challenge.entity.Challenge;
import com.master.savemoney.challenge.repository.ChallengeRepository;
import com.master.savemoney.pointDeposit.entity.PointDeposit;
import com.master.savemoney.pointDeposit.repository.PointDepositRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class PointDepositScheduler {
  private final ChallengeRepository challengeRepository;
  private final PointDepositRepository pointDepositRepository;

  private final int BEFORE_MONTH_VALUE = 1;
  private final int DEFAULT_POINT = 1000;
  private final int ADDITIONAL_POINT = 100;
  @Scheduled(cron = "${scheduler.point.deposit}")
  public void pointDepositScheduling() {
    log.info("포인트 적립 스케줄러 동작");

    // TODO : paging 처리
    List<Challenge> challengeList = challengeRepository.findAllBySuccessChallenge(LocalDateTime.now().minusDays(BEFORE_MONTH_VALUE));

    LocalDate now = LocalDate.now();
    LocalDateTime firstDay = LocalDateTime.of(now.minusMonths(1).withDayOfMonth(1), LocalTime.of(0,0,0));
    LocalDateTime lastDay = LocalDateTime.of(now.minusMonths(1).withDayOfMonth(firstDay.getDayOfMonth()), LocalTime.of(23,59,59));

    for (Challenge challenge : challengeList) {
      int point = DEFAULT_POINT;
      PointDeposit pointDeposit = pointDepositRepository.findLastMonthPointDeposit(challenge.getMember().getId(), firstDay, lastDay);

      if (pointDeposit != null) {
        point = pointDeposit.getDepositPoint() + ADDITIONAL_POINT;
      }

      pointDepositRepository.save(PointDeposit.builder()
          .member(challenge.getMember())
          .challenge(challenge)
          .pointDepositDateTime(LocalDateTime.now())
          .depositPoint(point)
          .build());
    }
  }
}
