package com.master.savemoney.challenge.service;

import com.master.savemoney.challenge.dto.ChallengeDetail;
import com.master.savemoney.challenge.dto.ChallengeDto;
import com.master.savemoney.challenge.dto.RegisterChallengeForm;
import com.master.savemoney.challenge.entity.Challenge;
import com.master.savemoney.challenge.repository.ChallengeRepository;
import com.master.savemoney.challenge.type.ChallengeType;
import com.master.savemoney.challengeGoal.dto.ChallengeGoalDto;
import com.master.savemoney.challengeGoal.entity.ChallengeGoal;
import com.master.savemoney.challengeGoal.repository.ChallengeGoalRepository;
import com.master.savemoney.common.exception.CustomException;
import com.master.savemoney.common.exception.ErrorCode;
import com.master.savemoney.member.entity.Member;
import com.master.savemoney.member.repository.MemberRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChallengeService {
  private final MemberRepository memberRepository;
  private final ChallengeRepository challengeRepository;
  private final ChallengeGoalRepository challengeGoalRepository;

  // 챌린지 등록
  @Transactional
  public ChallengeDto registerChallenge(String email, RegisterChallengeForm form) {
    Member member = memberRepository.findByEmail(email)
        .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

    if (form.getChallengeStartDate().isBefore(LocalDate.now())) {
      throw new CustomException(ErrorCode.BEFORE_DATE_TIME);
    } else if(form.getChallengeStartDate().getMonth().equals(LocalDate.now().getMonth())) {
      throw new CustomException(ErrorCode.EQUAL_MONTH);
    }
    // 추가적인 예외 상황 고려

    // TODO : 시각이 고정되어 들어가기에 DATE로만 관리
    Challenge savedChallenge = challengeRepository.save(Challenge.builder()
            .member(member)
            .challengeName(form.getChallengeName())
            .challengeType(ChallengeType.STANDBY)
            .challengeStartDateTime(form.getChallengeStartDate().atTime(0,0,0))
            .challengeEndDateTime(form.getChallengeStartDate().plusMonths(1).minusDays(1).atTime(23,59,59))
            .build());

    return ChallengeDto.from(savedChallenge);
  }

  // 전체 챌린지 조회
  public List<ChallengeDto> getTotalChallenge(String email) {
    Member member = memberRepository.findByEmail(email)
        .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

    List<Challenge> challengeList = challengeRepository.findAllByMemberId(member.getId());

    if (challengeList.isEmpty()) {
      throw new CustomException(ErrorCode.CHALLENGE_NOT_FOUND);
    }

    return challengeList.stream().map(ChallengeDto::from).collect(Collectors.toList());
  }

  // 타입에 따른 챌린지 조회
  public List<ChallengeDetail> getChallengeWithChallengeType(String email, ChallengeType challengeType){
    Member member = memberRepository.findByEmail(email)
        .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

    List<Challenge> challengeList = challengeRepository.findAllByMemberIdAndChallengeType(member.getId(), challengeType);

    if (challengeList.isEmpty()) {
      throw new CustomException(ErrorCode.CHALLENGE_NOT_MATCH_TYPE);
    }

    List<ChallengeDetail> challengeDetailList = new ArrayList<>();
    for (Challenge challenge : challengeList) {
      List<ChallengeGoal> challengeGoalList = challengeGoalRepository.findAllByChallengeId(challenge.getId());
      challengeDetailList.add(ChallengeDetail.from(challenge, challengeGoalList.stream().map(ChallengeGoalDto::from).collect(Collectors.toList())));
    }

    return challengeDetailList;
  }

  // 챌린지 삭제
  @Transactional
  public ChallengeDto deleteChallenge(String email, Long challengId) {
    Member member = memberRepository.findByEmail(email)
        .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

    Challenge deletedChallenge = challengeRepository.findById(challengId)
        .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

    if (!Objects.equals(member.getId(), deletedChallenge.getMember().getId())) {
      throw new CustomException(ErrorCode.FAIL_DELETE_CHALLENGE);
    }

    challengeRepository.delete(deletedChallenge);

    return ChallengeDto.from(deletedChallenge);
  }

}
