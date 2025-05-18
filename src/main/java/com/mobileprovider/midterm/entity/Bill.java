package com.mobileprovider.midterm.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "bills")
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "subscriber_id", nullable = false)
    private Subscriber subscriber;

    private Integer month;
    private Integer year;

    private Integer totalMinutes;
    private Integer totalMb;

    private BigDecimal totalAmount;

    private BigDecimal paidAmount = BigDecimal.ZERO;
    private Boolean isPaid = false;

    // Varsayılan Constructor
    public Bill() {}

    // Tüm Alanları Kapsayan Constructor
    public Bill(Subscriber subscriber, Integer month, Integer year, Integer totalMinutes, Integer totalMb, BigDecimal totalAmount, BigDecimal paidAmount, Boolean isPaid) {
        this.subscriber = subscriber;
        this.month = month;
        this.year = year;
        this.totalMinutes = totalMinutes;
        this.totalMb = totalMb;
        this.totalAmount = totalAmount;
        this.paidAmount = paidAmount;
        this.isPaid = isPaid;
    }

    // Sadece subscriber, month, year alanlarını alan Constructor (Gereken ekleme)
    public Bill(Subscriber subscriber, Integer month, Integer year) {
        this.subscriber = subscriber;
        this.month = month;
        this.year = year;
        this.paidAmount = BigDecimal.ZERO;
        this.isPaid = false;
    }

    // Getters ve Setters
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

    public Integer getTotalMinutes() {
        return totalMinutes;
    }

    public void setTotalMinutes(Integer totalMinutes) {
        this.totalMinutes = totalMinutes;
    }

    public Integer getTotalMb() {
        return totalMb;
    }

    public void setTotalMb(Integer totalMb) {
        this.totalMb = totalMb;
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
}
