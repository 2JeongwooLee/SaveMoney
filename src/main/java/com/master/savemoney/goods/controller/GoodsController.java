package com.master.savemoney.goods.controller;

import com.master.savemoney.goods.service.GoodsService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/goods")
public class GoodsController {
  private final GoodsService goodsService;

  // 전체 상품 조회
  @ApiOperation(value = "전체 상품 조회")
  @GetMapping("/all")
  public ResponseEntity<?> getAllGoods(@RequestParam(value = "page", defaultValue = "0") int page) {
    return ResponseEntity.ok().body(goodsService.getAllGoods(page));
  }

  // 상품검색
  @ApiOperation(value = "상품 검색")
  @GetMapping("/{searchName}")
  public ResponseEntity<?> searchGoods(
      @PathVariable String searchName,
      @RequestParam(value = "page", defaultValue = "0") int page) {
    return ResponseEntity.ok().body(goodsService.searchGoods(searchName, page));
  }

}
