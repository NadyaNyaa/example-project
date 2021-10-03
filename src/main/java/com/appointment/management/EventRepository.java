package com.appointment.management;

import com.appointment.management.data.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {
    boolean existsByTitle(String username);
    Optional<Event> findOneByTitle(String username);
}