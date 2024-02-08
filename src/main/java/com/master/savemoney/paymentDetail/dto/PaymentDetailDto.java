package com.master.savemoney.paymentDetail.dto;

import com.master.savemoney.common.type.ConsumptionCategory;
import com.master.savemoney.paymentDetail.entity.PaymentDetail;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDetailDto {
  private Long id;
  private Long memberId;
  private Long challengeId;
  private ConsumptionCategory paymentCategory;
  private LocalDateTime paymentDateTime;
  private Integer paymentMoney;
  private String paymentLocation;

  public static PaymentDetailDto from(PaymentDetail paymentDetail) {
    return PaymentDetailDto.builder()
        .id(paymentDetail.getId())
        .memberId(paymentDetail.getMember().getId())
        .challengeId(paymentDetail.getChallenge().getId())
        .paymentCategory(paymentDetail.getPaymentCategory())
        .paymentDateTime(paymentDetail.getPaymentDateTime())
        .paymentMoney(paymentDetail.getPaymentMoney())
        .paymentLocation(paymentDetail.getPaymentLocation())
        .build();
  }

}
