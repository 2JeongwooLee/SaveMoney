package com.master.savemoney.challenge.repository;

import com.master.savemoney.challenge.entity.Challenge;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChallengeRepository extends JpaRepository<Challenge, Long> {
  Optional<List<Challenge>> findAllByMember_Id(Long id);
}
