package com.mobileprovider.midterm.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "bills")
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "subscriber_id", nullable = false)
    private Subscriber subscriber;

    @Column(nullable = false)
    private Integer month;

    @Column(nullable = false)
    private Integer year;

    @Column(nullable = false)
    private Integer phoneMinutes;

    @Column(nullable = false)
    private Integer internetMb;

    @Column(nullable = false)
    private BigDecimal totalAmount;

    @Column(nullable = false)
    private BigDecimal paidAmount;

    @Column(nullable = false)
    private Boolean isPaid;

    private LocalDateTime createdAt;

    public Bill() {
        this.createdAt = LocalDateTime.now();
        this.phoneMinutes = 0;
        this.internetMb = 0;
        this.totalAmount = BigDecimal.ZERO;
        this.paidAmount = BigDecimal.ZERO;
        this.isPaid = false;
    }

    public Bill(Subscriber subscriber, Integer month, Integer year, Integer phoneMinutes, Integer internetMb, BigDecimal totalAmount) {
        this.subscriber = subscriber;
        this.month = month;
        this.year = year;
        this.phoneMinutes = phoneMinutes;
        this.internetMb = internetMb;
        this.totalAmount = totalAmount;
        this.paidAmount = BigDecimal.ZERO;
        this.isPaid = false;
        this.createdAt = LocalDateTime.now();
    }

    // Getters ve Setters ...

    public Long getId() {
        return id;
    }

    public Subscriber getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(Subscriber subscriber) {
        this.subscriber = subscriber;
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

    public Integer getPhoneMinutes() {
        return phoneMinutes;
    }

    public void setPhoneMinutes(Integer phoneMinutes) {
        this.phoneMinutes = phoneMinutes;
    }

    public Integer getInternetMb() {
        return internetMb;
    }

    public void setInternetMb(Integer internetMb) {
        this.internetMb = internetMb;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(BigDecimal paidAmount) {
        this.paidAmount = paidAmount;
    }

    public Boolean getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(Boolean isPaid) {
        this.isPaid = isPaid;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
