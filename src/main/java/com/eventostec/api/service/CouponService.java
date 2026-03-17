package com.eventostec.api.service;

import com.eventostec.api.domain.coupon.Coupon;
import com.eventostec.api.domain.coupon.CouponRequestDTO;
import com.eventostec.api.repository.CouponRepository;
import com.eventostec.api.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class CouponService {

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private EventRepository eventRepository;

    public Coupon addCouponToEvent(String eventId, CouponRequestDTO data) {
        var event = eventRepository.findById(eventId).orElseThrow(() -> new RuntimeException("Event not found"));
        var coupon = new Coupon();
        coupon.setCode(data.code());
        coupon.setDiscount(data.discount());
        coupon.setValid(new Date(data.valid()));
        coupon.setEvent(event);
        return couponRepository.save(coupon);
    }

    public List<Coupon> counsultCoupons(String eventId, Date currentDate){
        return couponRepository.findByEventIdAndValidAfter(eventId, currentDate);
    }
}
