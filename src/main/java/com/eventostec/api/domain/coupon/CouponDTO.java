package com.eventostec.api.domain.coupon;

import java.util.Date;

public record CouponDTO(String code, Integer discount, Date validUntil) {
}
