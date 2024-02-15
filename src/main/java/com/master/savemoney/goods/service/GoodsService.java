package com.master.savemoney.goods.service;

import com.master.savemoney.common.exception.CustomException;
import com.master.savemoney.common.exception.ErrorCode;
import com.master.savemoney.goods.dto.GoodsDto;
import com.master.savemoney.goods.entity.Goods;
import com.master.savemoney.goods.repository.GoodsRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GoodsService {
  private final GoodsRepository goodsRepository;

  public List<GoodsDto> getAllGoods(int page) {
    Page<Goods> goodsList = getList(page);

    if (goodsList.isEmpty()) {
      throw new CustomException(ErrorCode.GOODS_NOT_REGISTERED);
    }

    return goodsList.stream().map(GoodsDto::from).collect(Collectors.toList());
  }

  public Page<Goods> getList(int page) {
    Pageable pageable = PageRequest.of(page, 10);
    return goodsRepository.findAll(pageable);
  }

  public List<GoodsDto> searchGoods(String searchName, int page) {
    Page<Goods> goodsList = getSearchList(searchName, page);

    return goodsList.stream().map(GoodsDto::from).collect(Collectors.toList());
  }

  public Page<Goods> getSearchList(String searchName, int page) {
    Pageable pageable = PageRequest.of(page, 10);
    return goodsRepository.findByNameContaining(searchName, pageable);
  }
}
