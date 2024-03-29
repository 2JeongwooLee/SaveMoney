package com.master.savemoney.challenge.repository;

import com.master.savemoney.challenge.entity.Challenge;
import com.master.savemoney.challenge.type.ChallengeType;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ChallengeRepository extends JpaRepository<Challenge, Long> {
  List<Challenge> findAllByMemberId(Long id);

  @Query("SELECT c FROM Challenge c WHERE c.challengeType = 'STANDBY' AND c.challengeStartDateTime <= :now AND c.challengeEndDateTime > :now")
  List<Challenge> findAllStartChallenge(@Param("now") LocalDateTime now);

  @Query("SELECT c FROM Challenge c WHERE c.challengeType = 'IN_PROGRESS'")
  List<Challenge> findAllEndChallenge();

  List<Challenge> findAllByMemberIdAndChallengeType(Long memberId, ChallengeType challengeType);

  @Query("SELECT c FROM Challenge c WHERE c.challengeType = 'IN_PROGRESS' AND c.member.id = :memberId")
  Optional<Challenge> findByInProgressChallenge(Long memberId);

  @Query("SELECT c FROM Challenge c WHERE c.challengeType = 'SUCCESS' AND c.challengeStartDateTime <= :dateTime AND c.challengeEndDateTime > :dateTime")
  List<Challenge> findAllBySuccessChallenge(@Param("dateTime") LocalDateTime dateTime);


}
