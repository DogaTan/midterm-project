package com.mobileprovider.midterm.repository;

import com.mobileprovider.midterm.entity.Subscriber;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubscriberRepository extends JpaRepository<Subscriber, Long> {
    Optional<Subscriber> findBySubscriberNo(String subscriberNo);
    boolean existsBySubscriberNo(String subscriberNo);
}
