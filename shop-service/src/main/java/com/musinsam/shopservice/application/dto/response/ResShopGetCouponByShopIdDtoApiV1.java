package com.musinsam.shopservice.application.dto.response;

import com.musinsam.shopservice.infrastructure.dto.CouponDto;
import java.time.ZonedDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.web.PagedModel;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResShopGetCouponByShopIdDtoApiV1 {

  private ShopCouponPage shopCouponPage;

  public static ResShopGetCouponByShopIdDtoApiV1 of(Page<CouponDto> couponDtoPage) {
    return ResShopGetCouponByShopIdDtoApiV1.builder()
        .shopCouponPage(new ShopCouponPage(couponDtoPage))
        .build();
  }

  @Getter
  @ToString
  public static class ShopCouponPage extends PagedModel<ShopCouponPage.ShopCoupon> {

    public ShopCouponPage(Page<CouponDto> couponDtoPage) {
      super(
          new PageImpl<>(
              ShopCoupon.from(couponDtoPage.getContent()),
              couponDtoPage.getPageable(),
              couponDtoPage.getTotalPages()
          )
      );
    }


    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ShopCoupon {

      private String name;
      private ZonedDateTime startTime;
      private ZonedDateTime endTime;

      public static List<ShopCoupon> from(List<CouponDto> couponDtoList) {
        return couponDtoList.stream()
            .map(couponDto -> ShopCoupon.from(couponDto))
            .toList();
      }

      public static ShopCoupon from(CouponDto dto) { //쿠폰정보
        return ShopCoupon.builder()
            .name(null)
            .startTime(null)
            .endTime(null)
            .build();
      }
    }

  }

}
