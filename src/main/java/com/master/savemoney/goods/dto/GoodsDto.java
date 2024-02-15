package com.master.savemoney.goods.dto;

import com.master.savemoney.goods.entity.Goods;
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
public class GoodsDto {
  private Long id;
  private String name;
  private int price;
  private int stock;

  public static GoodsDto from(Goods goods) {
    return GoodsDto.builder()
        .id(goods.getId())
        .name(goods.getName())
        .price(goods.getPrice())
        .stock(goods.getStock())
        .build();
  }
}
