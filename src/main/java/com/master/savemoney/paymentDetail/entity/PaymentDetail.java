package com.master.savemoney.paymentDetail.entity;

import com.master.savemoney.challenge.entity.Challenge;
import com.master.savemoney.common.entity.BaseEntity;
import com.master.savemoney.common.type.ConsumptionCategory;
import com.master.savemoney.member.entity.Member;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
public class PaymentDetail extends BaseEntity{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id")
  private Member member;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "challenge_id")
  private Challenge challenge;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private ConsumptionCategory paymentCategory;

  @Column(nullable = false)
  private LocalDateTime paymentDateTime;

  @Column(nullable = false)
  private Integer paymentMoney;

  @Column(nullable = false)
  private String paymentLocation;

}
