package com.master.savemoney.goods.repository;

import com.master.savemoney.goods.entity.Goods;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface GoodsRepository extends JpaRepository<Goods, Long> {

  List<Goods> findByNameContaining(@Param("searchName") String searchName);
}
