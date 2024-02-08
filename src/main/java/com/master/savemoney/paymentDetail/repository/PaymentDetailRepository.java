package com.master.savemoney.paymentDetail.repository;

import com.master.savemoney.paymentDetail.entity.PaymentDetail;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentDetailRepository extends JpaRepository<PaymentDetail, Long> {

  List<PaymentDetail> findAllByMemberId(Long memberId);

  List<PaymentDetail> findAllByChallengeId(Long challengeId);

  @Query("select p.paymentCategory, sum(p.paymentMoney) from PaymentDetail p where p.id = :challengeId group by p.paymentCategory")
  List<PaymentDetail> findAllByChallengeIdGroupByPaymentCategory(@Param("challengeId") Long challengeId);
}
