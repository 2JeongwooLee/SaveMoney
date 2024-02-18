package com.master.savemoney.pointWithdraw.service;

import com.master.savemoney.common.annotation.DistributedLock;
import com.master.savemoney.common.exception.CustomException;
import com.master.savemoney.common.exception.ErrorCode;
import com.master.savemoney.goods.entity.Goods;
import com.master.savemoney.goods.repository.GoodsRepository;
import com.master.savemoney.member.entity.Member;
import com.master.savemoney.member.repository.MemberRepository;
import com.master.savemoney.pointWithdraw.dto.PointWithdrawDto;
import com.master.savemoney.pointWithdraw.entity.PointWithdraw;
import com.master.savemoney.pointWithdraw.repository.PointWithdrawRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PointWithdrawService {
  private final PointWithdrawRepository pointWithdrawRepository;
  private final GoodsRepository goodsRepository;
  private final MemberRepository memberRepository;

  @DistributedLock(key = "#lockName")
  public PointWithdrawDto paymentGoods(String email, Long goodsId) {
    Member member = memberRepository.findByEmail(email)
        .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

    Goods goods = goodsRepository.findById(goodsId)
        .orElseThrow(() -> new CustomException(ErrorCode.GOODS_NOT_FOUND));

    if (goods.getStock() <= 0) {
      throw new CustomException(ErrorCode.STOCK_IS_NOT_ENOUGH);
    } else if(member.getPoint() < goods.getPrice()) {
      throw new CustomException(ErrorCode.POINT_IS_NOT_ENOUGH);
    }

    member.setPoint(member.getPoint() - goods.getPrice());
    memberRepository.save(member);

    goods.setStock(goods.getStock()-1);
    goodsRepository.save(goods);

    PointWithdraw savedPointWithdraw = pointWithdrawRepository.save(PointWithdraw.builder()
            .member(member)
            .goods(goods)
            .pointWithdrawDateTime(LocalDateTime.now())
            .withdrawPoint(goods.getPrice())
            .build());

    return PointWithdrawDto.from(savedPointWithdraw);
  }


  public List<PointWithdrawDto> getPointWithdraw(String email){
    Member member = memberRepository.findByEmail(email)
        .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

    List<PointWithdraw> pointWithdrawList = pointWithdrawRepository.findAllByMemberId(member.getId());


    return pointWithdrawList.stream().map(PointWithdrawDto::from).collect(Collectors.toList());
  }
}
