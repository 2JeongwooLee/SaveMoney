package com.master.savemoney.pointDeposit.entity;

import com.master.savemoney.challenge.entity.Challenge;
import com.master.savemoney.common.entity.BaseEntity;
import com.master.savemoney.member.entity.Member;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.Audited;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Audited
@AuditOverride(forClass = BaseEntity.class)
public class PointDeposit extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @JoinColumn(name = "member_id")
  @ManyToOne(fetch = FetchType.LAZY)
  private Member member;

  @JoinColumn(name = "challenge_id")
  @OneToOne(fetch = FetchType.LAZY)
  private Challenge challenge;

  @Column(nullable = false)
  private LocalDateTime pointDepositDateTime;

  @Column(nullable = false)
  private Integer depositPoint;

}
