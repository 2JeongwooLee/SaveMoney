package com.master.savemoney.challengeGoal.repository;

import com.master.savemoney.challenge.entity.Challenge;
import com.master.savemoney.challengeGoal.entity.ChallengeGoal;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChallengeGoalRepository extends JpaRepository<ChallengeGoal, Long> {
  Optional<List<ChallengeGoal>> findAllByChallenge(Challenge challengeId);
}
