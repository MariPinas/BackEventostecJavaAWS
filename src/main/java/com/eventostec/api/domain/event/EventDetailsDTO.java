package com.eventostec.api.domain.event;

import com.eventostec.api.domain.coupon.CouponDTO;

import java.util.Date;
import java.util.List;

public record EventDetailsDTO(String id, String title, String description, Date date, String city, String state, String imgUrl, String eventUrl,  Boolean remote, List<CouponDTO> coupons) {
}
