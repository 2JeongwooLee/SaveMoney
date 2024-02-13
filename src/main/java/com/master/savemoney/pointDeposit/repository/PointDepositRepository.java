package com.master.savemoney.pointDeposit.repository;

import com.master.savemoney.pointDeposit.entity.PointDeposit;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PointDepositRepository extends JpaRepository<PointDeposit, Long> {

  @Query("SELECT p FROM PointDeposit p "
      + "WHERE p.member.id = :memberId AND p.pointDepositDateTime > :startDateTime AND p.pointDepositDateTime < : endDateTime")
  PointDeposit findLastMonthPointDeposit(
      @Param("memberId") Long memberId,
      @Param("startDateTime") LocalDateTime startDateTime,
      @Param("EndDateTime") LocalDateTime endDateTime);
}
