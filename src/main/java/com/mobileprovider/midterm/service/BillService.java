package com.mobileprovider.midterm.service;

import com.mobileprovider.midterm.entity.*;
import com.mobileprovider.midterm.repository.BillRepository;
import com.mobileprovider.midterm.repository.SubscriberRepository;
import com.mobileprovider.midterm.repository.UsageRepository;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class BillService {

    private final SubscriberRepository subscriberRepository;
    private final UsageRepository usageRepository;
    private final BillRepository billRepository;

    public BillService(SubscriberRepository subscriberRepository, UsageRepository usageRepository,
            BillRepository billRepository) {
        this.subscriberRepository = subscriberRepository;
        this.usageRepository = usageRepository;
        this.billRepository = billRepository;
    }

    public Bill calculateBill(String subscriberNo, Integer month, Integer year) {
        Subscriber subscriber = subscriberRepository.findBySubscriberNo(subscriberNo)
                .orElseThrow(() -> new RuntimeException("Subscriber not found"));

        List<Usage> phoneUsages = usageRepository.findBySubscriberAndUsageTypeAndMonthAndYear(subscriber,
                UsageType.PHONE, month, year);
        List<Usage> internetUsages = usageRepository.findBySubscriberAndUsageTypeAndMonthAndYear(subscriber,
                UsageType.INTERNET, month, year);

        int totalMinutes = phoneUsages.stream().mapToInt(Usage::getAmount).sum();
        int totalMb = internetUsages.stream().mapToInt(Usage::getAmount).sum();

        BigDecimal phoneCharge = BigDecimal.ZERO;
        if (totalMinutes > 1000) {
            phoneCharge = BigDecimal.valueOf((totalMinutes - 1000) / 1000).multiply(BigDecimal.TEN);
        }

        BigDecimal internetCharge = BigDecimal.valueOf(50);
        if (totalMb > 20000) {
            int extra10GbCount = (totalMb - 20000) / 10000;
            internetCharge = internetCharge.add(BigDecimal.valueOf(extra10GbCount).multiply(BigDecimal.TEN));
        }

        BigDecimal total = phoneCharge.add(internetCharge);

        // Daha önce varsa getir, yoksa yeni oluştur
        Optional<Bill> existingBillOpt = billRepository.findBySubscriberAndMonthAndYear(subscriber, month, year);
        Bill bill = existingBillOpt.orElseGet(() -> new Bill(subscriber, month, year));

        // Güncelle
        bill.setTotalMinutes(totalMinutes);
        bill.setTotalMb(totalMb);
        bill.setTotalAmount(total);

        return billRepository.save(bill);
    }

    public Optional<Bill> getBill(Subscriber subscriber, Integer month, Integer year) {
        return billRepository.findBySubscriberAndMonthAndYear(subscriber, month, year);
    }

    public Subscriber getSubscriberByNo(String subscriberNo) {
        return subscriberRepository.findBySubscriberNo(subscriberNo)
                .orElseThrow(() -> new RuntimeException("Subscriber not found"));
    }

    public Bill payBill(String subscriberNo, Integer month, Integer year, BigDecimal amount) {
        Subscriber subscriber = getSubscriberByNo(subscriberNo);
        Bill bill = billRepository.findBySubscriberAndMonthAndYear(subscriber, month, year)
                .orElseThrow(() -> new RuntimeException("Bill not found"));

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }

        BigDecimal updatedPaid = bill.getPaidAmount().add(amount);
        bill.setPaidAmount(updatedPaid);

        if (updatedPaid.compareTo(bill.getTotalAmount()) >= 0) {
            bill.setIsPaid(true);
        }

        return billRepository.save(bill);
    }


    // ✅ Yeni eklenen: Abonenin tüm geçmiş faturalarını getir
    public List<Bill> getBillHistory(String subscriberNo) {
        Subscriber subscriber = getSubscriberByNo(subscriberNo);
        return billRepository.findBySubscriber(subscriber);
    }

    public Page<Bill> getBillDetails(String subscriberNo, Pageable pageable) {
        Subscriber subscriber = getSubscriberByNo(subscriberNo);
        return billRepository.findBySubscriber(subscriber, pageable);
    }
}
