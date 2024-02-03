package com.master.savemoney.challengeGoal.service;

import com.master.savemoney.challenge.entity.Challenge;
import com.master.savemoney.challenge.repository.ChallengeRepository;
import com.master.savemoney.challenge.type.ChallengeType;
import com.master.savemoney.challengeGoal.dto.ChallengeGoalDto;
import com.master.savemoney.challengeGoal.dto.RegisterChallengeGoalForm;
import com.master.savemoney.challengeGoal.dto.UpdateChallengeGoalForm;
import com.master.savemoney.challengeGoal.entity.ChallengeGoal;
import com.master.savemoney.challengeGoal.repository.ChallengeGoalRepository;
import com.master.savemoney.common.exception.CustomException;
import com.master.savemoney.common.exception.ErrorCode;
import com.master.savemoney.member.entity.Member;
import com.master.savemoney.member.repository.MemberRepository;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChallengeGoalService {
  private final ChallengeRepository challengeRepository;
  private final MemberRepository memberRepository;
  private final ChallengeGoalRepository challengeGoalRepository;

  // 챌린지목표 등록
  @Transactional
  public ChallengeGoalDto registerChallengeGoal(String email, Long challengeId, RegisterChallengeGoalForm form) {
    Member member = memberRepository.findByEmail(email)
        .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

    Challenge challenge = challengeRepository.findById(challengeId)
        .orElseThrow(() -> new CustomException(ErrorCode.CHALLENGE_NOT_FOUND));

    if (!Objects.equals(member.getId(), challenge.getMember().getId())) {
      throw new CustomException(ErrorCode.MEMBER_NOT_MATCH_CHALLENGE);
    } else if(challenge.getChallengeType() != ChallengeType.STANDBY) {
      throw new CustomException(ErrorCode.FINISHED_CHALLENGE_OR_IN_PROGRESS);
    }

    ChallengeGoal savedChallengeGoal = challengeGoalRepository.save(ChallengeGoal.builder()
            .challenge(challenge)
            .targetCategory(form.getTargetCategory())
            .targetMoney(form.getTargetMoney())
            .build());

    return ChallengeGoalDto.from(savedChallengeGoal);
  }

  // 챌린지 목표 조회
  public List<ChallengeGoalDto> getChallengeGoal(String email, Long challengeId) {
    Member member = memberRepository.findByEmail(email)
        .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

    Challenge challenge = challengeRepository.findById(challengeId)
        .orElseThrow(() -> new CustomException(ErrorCode.CHALLENGE_NOT_FOUND));

    if (!Objects.equals(member.getId(), challenge.getMember().getId())) {
      throw new CustomException(ErrorCode.MEMBER_NOT_MATCH_CHALLENGE);
    }

    List<ChallengeGoal> challengeGoalList = challengeGoalRepository.findAllByChallenge(challenge)
        .orElseThrow(() -> new CustomException(ErrorCode.CHALLENGE_GOAL_NOT_FOUND));

    return challengeGoalList.stream().map(ChallengeGoalDto::from).collect(Collectors.toList());
  }

  // 챌린지 목표 수정
  @Transactional
  public ChallengeGoalDto updateChallengeGoal(String email, Long challengeGoalId, UpdateChallengeGoalForm form) {
    Member member = memberRepository.findByEmail(email)
        .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

    ChallengeGoal challengeGoal = challengeGoalRepository.findById(challengeGoalId)
        .orElseThrow(() -> new CustomException(ErrorCode.CHALLENGE_GOAL_NOT_FOUND));

    if (!Objects.equals(member.getId(), challengeGoal.getChallenge().getMember().getId())) {
      throw new CustomException(ErrorCode.MEMBER_NOT_MATCH_CHALLENGE_GOAL);
    }

    challengeGoal.setTargetCategory(form.getTargetCategory());
    challengeGoal.setTargetMoney(form.getTargetMoney());

    ChallengeGoal updatedChallengeGoal = challengeGoalRepository.save(challengeGoal);

    return ChallengeGoalDto.from(updatedChallengeGoal);
  }

  // 챌린지 목표 삭제
  @Transactional
  public void deleteChallengeGoal(String email, Long challengeGoalId) {
    Member member = memberRepository.findByEmail(email)
        .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

    ChallengeGoal challengeGoal = challengeGoalRepository.findById(challengeGoalId)
        .orElseThrow(() -> new CustomException(ErrorCode.CHALLENGE_GOAL_NOT_FOUND));

    if (!Objects.equals(member.getId(), challengeGoal.getChallenge().getMember().getId())) {
      throw new CustomException(ErrorCode.MEMBER_NOT_MATCH_CHALLENGE_GOAL);
    }

    challengeGoalRepository.delete(challengeGoal);
  }
}
