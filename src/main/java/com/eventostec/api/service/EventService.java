package com.eventostec.api.service;

import com.amazonaws.services.s3.AmazonS3;
import com.eventostec.api.domain.coupon.Coupon;
import com.eventostec.api.domain.coupon.CouponDTO;
import com.eventostec.api.domain.event.Event;
import com.eventostec.api.domain.event.EventDetailsDTO;
import com.eventostec.api.domain.event.EventRequestDTO;
import com.eventostec.api.domain.event.EventResponseDTO;
import com.eventostec.api.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class EventService {

    @Value("${aws.bucket.name}")
    private String bucketName;

    @Autowired
    private AmazonS3 s3Client;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private AddressService addressService;
    @Autowired
    private CouponService couponService;

    public Event createEvent(EventRequestDTO data) {
        String imgUrl=null;
        if (data.image() != null) {
            imgUrl = uploadImg(data.image());
        }

        Event newEvent = new Event();
        newEvent.setTitle(data.title());
        newEvent.setDescription(data.description());
        newEvent.setDate(new Date(data.date()));
        newEvent.setEventUrl(data.eventUrl());
        newEvent.setRemote(data.remote());
        newEvent.setImageUrl(imgUrl);

        eventRepository.save(newEvent);
        if (!data.remote()){
            this.addressService.createAddress(data, newEvent);
        }


        return newEvent;
    }

    public EventDetailsDTO getEventById(String id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        List<Coupon> coupons= couponService.counsultCoupons(id, new Date());

        List<CouponDTO> couponDTOs = coupons.stream()
                .map(coupon -> new CouponDTO(coupon.getCode(), coupon.getDiscount(), coupon.getValid()))
                .toList();

        return new EventDetailsDTO(
                event.getId(),
                event.getTitle(),
                event.getDescription(),
                event.getDate(),
                event.getAddress() != null ? event.getAddress().getCity() : "",
                event.getAddress() != null ? event.getAddress().getUf() : "",
                event.getEventUrl(),
                event.getImageUrl(),
                event.getRemote(),
                couponDTOs
        );
    }

    private String uploadImg(MultipartFile multipartFile){
        String filename = UUID.randomUUID() + "-" + multipartFile.getOriginalFilename();
        try{
            File file = this.convertMultipartFile(multipartFile);
            s3Client.putObject(bucketName, filename, file);
            file.delete();
            return s3Client.getUrl(bucketName,filename).toString();
        }catch (Exception e){
            System.out.println(">> Error uploading file: " + e.getMessage());
            return "";
        }
    }

    private File convertMultipartFile(MultipartFile multipartFile) throws Exception{
        File convFile = new File(multipartFile.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(multipartFile.getBytes());
        fos.close();
        return convFile;
    }

    public List<EventResponseDTO> getEvents(int page, int size){
        Pageable pageable = PageRequest.of(page,size);
        Page<Event> eventsPage = this.eventRepository.findAll(pageable);
        return eventsPage.map(event -> new EventResponseDTO(
                event.getId(),
                event.getTitle(),
                event.getDescription(),
                event.getDate(),
                event.getAddress() !=null ?
                        event.getAddress().getCity() : "",
                event.getAddress() !=null ?
                        event.getAddress().getUf() : "",
                event.getEventUrl(),event.getRemote(),
                event.getImageUrl()))
                .stream().toList();
    }

    public List<EventResponseDTO> getUpcomingEvents(int page, int size){
        Pageable pageable = PageRequest.of(page,size);
        Page<Event> eventsPage = this.eventRepository.findUpcomingEvents(new Date(), pageable);
        return eventsPage.map(event -> new EventResponseDTO(
                        event.getId(),
                        event.getTitle(),
                        event.getDescription(),
                        event.getDate(),
                        event.getAddress() !=null ? event.getAddress().getCity() : "",
                        event.getAddress() !=null ? event.getAddress().getUf() : "",
                        event.getEventUrl(),
                        event.getRemote(),
                        event.getImageUrl()))
                .stream().toList();
    }

    public List<EventResponseDTO> getFilteredEvents(int page, int size, String title, String city, String uf, Date startDate, Date endDate){
        title = (title!=null) ? title : "";
        city = (city!=null) ? city : "";
        uf = (uf!=null) ? uf : "";
        Date now = new Date();
        startDate = (startDate != null) ? startDate : now;
        if (endDate == null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(now);
            cal.add(Calendar.YEAR, 10);
            endDate = cal.getTime();
        }

        Pageable pageable = PageRequest.of(page,size);
        Page<Event> eventsPage = this.eventRepository.findByFilteredEvent(title, city, uf, startDate, endDate, pageable);
        return eventsPage.map(event -> new EventResponseDTO(
                event.getId(),
                event.getTitle(),
                event.getDescription(),
                event.getDate(),
                event.getAddress() !=null ? event.getAddress().getCity() : "",
                event.getAddress() !=null ? event.getAddress().getUf() : "",
                event.getEventUrl(),
                event.getRemote(),
                event.getImageUrl()))
                .stream().toList();
    }
}
