package com.nsoft.offer.repository;

import com.nsoft.offer.model.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, String> {

    @Query("SELECT e.id FROM Event e WHERE e.startsAt < CURRENT_TIMESTAMP")
    List<String> findEventIdsOlderThanCurrentTime();

    List<Event> findByStartsAtAfter(Date after);

}
