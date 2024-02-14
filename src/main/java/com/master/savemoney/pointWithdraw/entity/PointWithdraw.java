package com.master.savemoney.pointWithdraw.entity;

import com.master.savemoney.common.entity.BaseEntity;
import com.master.savemoney.goods.entity.Goods;
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
public class PointWithdraw extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @JoinColumn(name = "member_id")
  @ManyToOne(fetch = FetchType.LAZY)
  private Member member;

  @JoinColumn(name = "goods_id")
  @OneToOne(fetch = FetchType.LAZY)
  private Goods goods;

  @Column(nullable = false)
  private LocalDateTime pointWithdrawDateTime;

  @Column
  private int withdrawPoint;

}
