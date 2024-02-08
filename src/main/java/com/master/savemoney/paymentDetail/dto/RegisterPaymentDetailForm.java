package com.master.savemoney.paymentDetail.dto;

import com.master.savemoney.common.type.ConsumptionCategory;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterPaymentDetailForm {
  private ConsumptionCategory paymentCategory;
  private LocalDateTime paymentDateTime;
  private Integer paymentMoney;
  private String paymentLocation;
}
