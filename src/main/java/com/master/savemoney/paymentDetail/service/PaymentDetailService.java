package com.master.savemoney.paymentDetail.service;

import com.master.savemoney.challenge.entity.Challenge;
import com.master.savemoney.challenge.repository.ChallengeRepository;
import com.master.savemoney.challenge.type.ChallengeType;
import com.master.savemoney.challengeGoal.entity.ChallengeGoal;
import com.master.savemoney.challengeGoal.repository.ChallengeGoalRepository;
import com.master.savemoney.common.exception.CustomException;
import com.master.savemoney.common.exception.ErrorCode;
import com.master.savemoney.common.type.ConsumptionCategory;
import com.master.savemoney.member.entity.Member;
import com.master.savemoney.member.repository.MemberRepository;
import com.master.savemoney.paymentDetail.dto.PaymentDetailDto;
import com.master.savemoney.paymentDetail.dto.RegisterPaymentDetailForm;
import com.master.savemoney.paymentDetail.dto.UpdatePaymentDetailForm;
import com.master.savemoney.paymentDetail.entity.PaymentDetail;
import com.master.savemoney.paymentDetail.repository.PaymentDetailRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentDetailService {
  private final PaymentDetailRepository paymentDetailRepository;
  private final MemberRepository memberRepository;
  private final ChallengeRepository challengeRepository;
  private final ChallengeGoalRepository challengeGoalRepository;

  // 결제내역 등록
  @Transactional
  public PaymentDetailDto registerPaymentDetail(String email, Long challengeID, RegisterPaymentDetailForm form){
    Member member = memberRepository.findByEmail(email)
        .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

    Challenge challenge = challengeRepository.findById(challengeID)
        .orElseThrow(() -> new CustomException(ErrorCode.CHALLENGE_NOT_FOUND));

    if (!Objects.equals(member.getId(), challenge.getMember().getId())) {
      throw  new CustomException(ErrorCode.MEMBER_NOT_MATCH_CHALLENGE);
    }

    PaymentDetail savedPaymentDetail =
        paymentDetailRepository.save(PaymentDetail.builder()
            .member(member)
            .challenge(challenge)
            .paymentCategory(form.getPaymentCategory())
            .paymentDateTime(form.getPaymentDateTime())
            .paymentMoney(form.getPaymentMoney())
            .paymentLocation(form.getPaymentLocation())
            .build());

    checkChallengeStatus(challenge);

    return PaymentDetailDto.from(savedPaymentDetail);
  }

  // 결제내역 등록 시 챌린지 실패 여부 확인
  // TODO : 수정, 삭제 시 챌린지 진행 상태 다시 확인
  private void checkChallengeStatus(Challenge challenge) {
    List<ChallengeGoal> challengeGoalList = challengeGoalRepository.findAllByChallengeId(challenge.getId());

    List<PaymentDetail> paymentDetailList = paymentDetailRepository.findAllByChallengeId(challenge.getId());

    Map<ConsumptionCategory, Integer> checkingMap = new HashMap<>();

    for (ChallengeGoal goal : challengeGoalList) {
      checkingMap.put(goal.getTargetCategory(), goal.getTargetMoney());
    }

    for (PaymentDetail paymentDetail : paymentDetailList) {
      checkingMap.put(paymentDetail.getPaymentCategory(),
          checkingMap.get(paymentDetail.getPaymentCategory()) - paymentDetail.getPaymentMoney());

      if (checkingMap.get(paymentDetail.getPaymentCategory()) < 0) {
        challenge.setChallengeType(ChallengeType.FAIL);
        challengeRepository.save(challenge);
      }
    }
  }

  // 결제내역 조회
  public PaymentDetailDto getPaymentDetail(String email, Long paymentDetailId){
    Member member = memberRepository.findByEmail(email)
        .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

    PaymentDetail paymentDetail = paymentDetailRepository.findById(paymentDetailId)
        .orElseThrow(() -> new CustomException(ErrorCode.PAYMENT_DETAIL_NOT_FOUND));

    if (!Objects.equals(member.getId(), paymentDetail.getMember().getId())) {
      throw new CustomException(ErrorCode.MEMBER_NOT_MATCH_PAYMENT_DETAIL);
    }

    return PaymentDetailDto.from(paymentDetail);
  }

  // 회원의 전체 결제내역 조회
  public List<PaymentDetailDto> getAllMemberPaymentDetail(String email) {
    Member member = memberRepository.findByEmail(email)
        .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

    List<PaymentDetail> paymentDetailList = paymentDetailRepository.findAllByMemberId(member.getId());

    if (paymentDetailList.isEmpty()) {
      throw new CustomException(ErrorCode.PAYMENT_DETAIL_EMPTY);
    }

    return paymentDetailList.stream().map(PaymentDetailDto::from).collect(Collectors.toList());
  }

  // 결제내역 수정
  @Transactional
  public PaymentDetailDto updatePaymentDetail(String email, Long paymentDetailId, UpdatePaymentDetailForm form){
    Member member = memberRepository.findByEmail(email)
        .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

    PaymentDetail paymentDetail = paymentDetailRepository.findById(paymentDetailId)
        .orElseThrow(() -> new CustomException(ErrorCode.PAYMENT_DETAIL_NOT_FOUND));

    if (!Objects.equals(member.getId(), paymentDetail.getMember().getId())) {
      throw new CustomException(ErrorCode.MEMBER_NOT_MATCH_PAYMENT_DETAIL);
    }

    paymentDetail.setPaymentCategory(form.getPaymentCategory());
    paymentDetail.setPaymentDateTime(form.getPaymentDateTime());
    paymentDetail.setPaymentLocation(form.getPaymentLocation());
    paymentDetail.setPaymentMoney(form.getPaymentMoney());

    PaymentDetail updatedPaymentDetail = paymentDetailRepository.save(paymentDetail);

    return PaymentDetailDto.from(updatedPaymentDetail);
  }

  // 결제내역 삭제
  @Transactional
  public void deletePaymentDetail(String email, Long paymentDetailId){
    Member member = memberRepository.findByEmail(email)
        .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

    PaymentDetail paymentDetail = paymentDetailRepository.findById(paymentDetailId)
        .orElseThrow(() -> new CustomException(ErrorCode.PAYMENT_DETAIL_NOT_FOUND));

    if (!Objects.equals(member.getId(), paymentDetail.getMember().getId())) {
      throw new CustomException(ErrorCode.MEMBER_NOT_MATCH_PAYMENT_DETAIL);
    }

    paymentDetailRepository.delete(paymentDetail);
  }

}
