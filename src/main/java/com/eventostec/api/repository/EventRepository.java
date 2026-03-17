package com.eventostec.api.repository;

import com.eventostec.api.domain.event.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.UUID;

public interface EventRepository extends JpaRepository<Event, String> {

    @Query ("SELECT e FROM Event e LEFT JOIN FETCH e.address a WHERE e.date >= :currentDate")
    public Page<Event> findUpcomingEvents(@Param("currentDate")Date currentDate, Pageable pageable);

//    @Query("""
//    SELECT e.id AS id,
//           e.title AS title,
//           e.description AS description,
//           e.date AS date,
//           e.imageUrl AS imageUrl,
//           e.eventUrl AS eventUrl,
//           e.remote AS remote,
//           a.city AS city,
//           a.uf AS uf
//    FROM Address a
//    JOIN a.event e
//    WHERE (:city = '' OR a.city LIKE CONCAT('%', :city, '%'))
//    AND (:uf = '' OR a.uf LIKE CONCAT('%', :uf, '%'))
//    AND e.date BETWEEN :startDate AND :endDate
//    """)
//    Page<Event> findFilteredEvents(
//            String city,
//            String uf,
//            Date startDate,
//            Date endDate,
//            Pageable pageable);
//
//    @Query("""
//    SELECT e.id AS id,
//           e.title AS title,
//           e.description AS description,
//           e.date AS date,
//           e.imageUrl AS imageUrl,
//           e.eventUrl AS eventUrl,
//           e.remote AS remote,
//           a.city AS city,
//           a.uf AS uf
//    FROM Address a
//    JOIN a.event e
//    WHERE (:title = '' OR e.title LIKE CONCAT('%', :title, '%'))
//    """)
//    List<EventAddressProjection> findEventsByTitle(String title);

    @Query("SELECT e FROM Event e " +
            "LEFT JOIN e.address a " +
            "WHERE (:title = '' OR e.title LIKE CONCAT('%', :title, '%')) AND " +
            "(:city = ''  OR a.city LIKE CONCAT('%', :city, '%')) AND " +
            "(:uf = ''  OR a.uf LIKE CONCAT('%', :uf, '%')) AND" +
            "(e.date >= :startDate AND e.date <= :endDate) ")
    Page<Event>findByFilteredEvent(@Param("title") String title,
                                   @Param("city") String city,
                                   @Param("uf") String uf,
                                   @Param("startDate") Date startDate,
                                   @Param("endDate") Date endDate,
                                   Pageable pageable
                                   );
}
