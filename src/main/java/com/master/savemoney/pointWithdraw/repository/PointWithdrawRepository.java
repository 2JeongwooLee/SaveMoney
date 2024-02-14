package com.master.savemoney.pointWithdraw.repository;

import com.master.savemoney.pointWithdraw.entity.PointWithdraw;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PointWithdrawRepository extends JpaRepository<PointWithdraw, Long> {

  @Query("SELECT p FROM PointWithdraw p WHERE p.member.id = :memberId")
  List<PointWithdraw> findAllByMemberId(@Param("memberId") Long memberId);
}
