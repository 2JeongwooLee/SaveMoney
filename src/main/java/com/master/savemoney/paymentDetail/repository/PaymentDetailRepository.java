package com.master.savemoney.paymentDetail.repository;

import com.master.savemoney.paymentDetail.entity.PaymentDetail;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentDetailRepository extends JpaRepository<PaymentDetail, Long> {

  List<PaymentDetail> findAllByMemberId(Long memberId);

  List<PaymentDetail> findAllByChallengeId(Long challengeId);
}
