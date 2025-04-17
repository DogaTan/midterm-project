package com.mobileprovider.midterm.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "usages")
public class Usage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "subscriber_id", nullable = false)
    private Subscriber subscriber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UsageType usageType;

    @Column(nullable = false)
    private Integer amount; // Dakika veya MB

    @Column(nullable = false)
    private Integer month;

    @Column(nullable = false)
    private Integer year;

    private LocalDateTime createdAt;

    public Usage() {
        this.createdAt = LocalDateTime.now();
    }

    public Usage(Subscriber subscriber, UsageType usageType, Integer amount, Integer month, Integer year) {
        this.subscriber = subscriber;
        this.usageType = usageType;
        this.amount = amount;
        this.month = month;
        this.year = year;
        this.createdAt = LocalDateTime.now();
    }

    // Getters & Setters ...

    public Long getId() {
        return id;
    }

    public Subscriber getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(Subscriber subscriber) {
        this.subscriber = subscriber;
    }

    public UsageType getUsageType() {
        return usageType;
    }

    public void setUsageType(UsageType usageType) {
        this.usageType = usageType;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
