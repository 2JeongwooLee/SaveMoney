package com.master.savemoney.goods.service;

import com.master.savemoney.common.exception.CustomException;
import com.master.savemoney.common.exception.ErrorCode;
import com.master.savemoney.goods.dto.GoodsDto;
import com.master.savemoney.goods.entity.Goods;
import com.master.savemoney.goods.repository.GoodsRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GoodsService {
  private final GoodsRepository goodsRepository;

  public List<GoodsDto> getAllGoods() {
    List<Goods> goodsList = goodsRepository.findAll();

    if (goodsList.isEmpty()) {
      throw new CustomException(ErrorCode.GOODS_NOT_REGISTERED);
    }

    return goodsList.stream().map(GoodsDto::from).collect(Collectors.toList());
  }

  public List<GoodsDto> searchGoods(String searchName) {
    List<Goods> goodsList = goodsRepository.findByNameContaining(searchName);

    if (goodsList.isEmpty()) {
      throw new CustomException(ErrorCode.GOODS_NOT_SEARCHED);
    }

    return goodsList.stream().map(GoodsDto::from).collect(Collectors.toList());
  }


}
