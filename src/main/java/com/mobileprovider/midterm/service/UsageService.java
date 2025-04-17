package com.mobileprovider.midterm.service;

import com.mobileprovider.midterm.entity.*;
import com.mobileprovider.midterm.repository.SubscriberRepository;
import com.mobileprovider.midterm.repository.UsageRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsageService {

    private final UsageRepository usageRepository;
    private final SubscriberRepository subscriberRepository;

    public UsageService(UsageRepository usageRepository, SubscriberRepository subscriberRepository) {
        this.usageRepository = usageRepository;
        this.subscriberRepository = subscriberRepository;
    }

    public Usage addUsage(String subscriberNo, UsageType usageType, Integer month, Integer year) {
        // Kullanıcıyı bul
        Subscriber subscriber = subscriberRepository.findBySubscriberNo(subscriberNo)
                .orElseThrow(() -> new RuntimeException("Subscriber not found with no: " + subscriberNo));

        // Her kullanım için 10 dakika veya 1 MB ekliyoruz
        int amount = usageType == UsageType.PHONE ? 10 : 1;

        // Yeni usage kaydı oluştur
        Usage usage = new Usage(subscriber, usageType, amount, month, year);
        return usageRepository.save(usage);
    }
}
