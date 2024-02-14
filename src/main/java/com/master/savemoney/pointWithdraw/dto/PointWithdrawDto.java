package com.master.savemoney.pointWithdraw.dto;

import com.master.savemoney.pointWithdraw.entity.PointWithdraw;
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
public class PointWithdrawDto {
  private Long id;
  private Long memberId;
  private Long goodsId;
  private LocalDateTime pointWithdrawDateTime;
  private int withdrawPoint;

  public static PointWithdrawDto from(PointWithdraw pointWithdraw) {
    return PointWithdrawDto.builder()
        .id(pointWithdraw.getId())
        .memberId(pointWithdraw.getMember().getId())
        .goodsId(pointWithdraw.getGoods().getId())
        .pointWithdrawDateTime(pointWithdraw.getPointWithdrawDateTime())
        .withdrawPoint(pointWithdraw.getWithdrawPoint())
        .build();
  }
}
