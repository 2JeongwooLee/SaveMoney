package com.master.savemoney.goods.controller;

import com.master.savemoney.common.security.TokenProvider;
import com.master.savemoney.goods.service.GoodsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/goods")
public class GoodsController {
  private final GoodsService goodsService;

  // 전체 상품 조회
  @GetMapping("/all")
  public ResponseEntity<?> getAllGoods() {
    return ResponseEntity.ok().body(goodsService.getAllGoods());
  }

  // 상품검색
  @GetMapping("/{searchName}")
  public ResponseEntity<?> searchGoods(@PathVariable String searchName) {
    return ResponseEntity.ok().body(goodsService.searchGoods(searchName));
  }

}
