package me.whiteship.demoinfleanrestapi.events;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.time.LocalDateTime;


public interface EventRepository extends JpaRepository<Event, Integer> {
    public Page<Event> findByNameContaining(String name, Pageable pageable);
    public Page<Event> findByfree(boolean free, Pageable pageable);
    public Page<Event> findByBasePriceGreaterThanEqual(int basePrice, Pageable pageable);
    Page<Event> findByBasePriceBetween(int startBasePrice, int endBasePrice, Pageable pageable);

    Page<Event> findByCloseEnrollmentDateTimeAfter(Timestamp timestamp, Pageable pageable);
//    Page<Event> findByCloseEnrollmentDateTimeAfter(LocalDateTime now, Pageable pageable);
}
