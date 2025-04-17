package com.mobileprovider.midterm.service;

import com.mobileprovider.midterm.entity.Subscriber;
import com.mobileprovider.midterm.repository.SubscriberRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubscriberService {

    private final SubscriberRepository subscriberRepository;

    public SubscriberService(SubscriberRepository subscriberRepository) {
        this.subscriberRepository = subscriberRepository;
    }

    public Subscriber createSubscriber(Subscriber subscriber) {
        if (subscriberRepository.existsBySubscriberNo(subscriber.getSubscriberNo())) {
            throw new RuntimeException("Subscriber with this number already exists.");
        }
        return subscriberRepository.save(subscriber);
    }

    public Optional<Subscriber> getSubscriberBySubscriberNo(String subscriberNo) {
        return subscriberRepository.findBySubscriberNo(subscriberNo);
    }

    public List<Subscriber> getAllSubscribers() {
        return subscriberRepository.findAll();
    }
}
