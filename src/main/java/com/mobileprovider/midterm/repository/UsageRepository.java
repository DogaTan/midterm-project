package com.mobileprovider.midterm.repository;

import com.mobileprovider.midterm.entity.Usage;
import com.mobileprovider.midterm.entity.Subscriber;
import com.mobileprovider.midterm.entity.UsageType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsageRepository extends JpaRepository<Usage, Long> {
    List<Usage> findBySubscriberAndMonthAndYear(Subscriber subscriber, Integer month, Integer year);
    List<Usage> findBySubscriberAndUsageTypeAndMonthAndYear(Subscriber subscriber, UsageType usageType, Integer month, Integer year);
}
