package com.master.savemoney.challenge.scheduler;

import com.master.savemoney.challenge.entity.Challenge;
import com.master.savemoney.challenge.repository.ChallengeRepository;
import com.master.savemoney.challenge.type.ChallengeType;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class ChallengeScheduler {
  private final ChallengeRepository challengeRepository;

  @Scheduled(cron = "${scheduler.start.challenge}")
  public void challengStartScheduling() {
    log.info("해당 월 챌린지 시작");

    List<Challenge> challengeList = challengeRepository.findAll();

    for (Challenge challenge : challengeList) {
      if (challenge.getChallengeStartDateTime().getMonth().equals(LocalDate.now().getMonth())
      && challenge.getChallengeType().equals(ChallengeType.STANDBY)) {

        challenge.setChallengeType(ChallengeType.IN_PROGRESS);
        challengeRepository.save(challenge);
      }
    }
  }

  @Scheduled(cron = "${scheduler.end.challenge}")
  public void challengeEndScheduling() {
    log.info("해당 월 챌린지 종료");

    List<Challenge> challengeList = challengeRepository.findAll();

    for (Challenge challenge : challengeList) {
      if (challenge.getChallengeType().equals(ChallengeType.IN_PROGRESS)) {
        // TODO 성공실패 여부 판단하여 저장
      }
    }
  }
}
