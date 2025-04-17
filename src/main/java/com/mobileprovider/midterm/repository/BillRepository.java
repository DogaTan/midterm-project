package com.mobileprovider.midterm.repository;

import com.mobileprovider.midterm.entity.Bill;
import com.mobileprovider.midterm.entity.Subscriber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface BillRepository extends JpaRepository<Bill, Long> {
    Optional<Bill> findBySubscriberAndMonthAndYear(Subscriber subscriber, Integer month, Integer year);
    List<Bill> findBySubscriber(Subscriber subscriber);
    Page<Bill> findBySubscriber(Subscriber subscriber, Pageable pageable);
}
