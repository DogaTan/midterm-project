package com.mobileprovider.midterm.controller;

import com.mobileprovider.midterm.entity.Bill;
import com.mobileprovider.midterm.service.BillService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/bill")
@Tag(name = "Bill", description = "Billing API for calculating and retrieving subscriber bills")
public class BillController {

    private final BillService billService;

    public BillController(BillService billService) {
        this.billService = billService;
    }

    @Operation(summary = "Calculate bill for a subscriber", description = "Calculates the monthly bill for the subscriber based on their usage.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Bill successfully calculated"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - JWT token missing or invalid"),
            @ApiResponse(responseCode = "404", description = "Subscriber not found")
    })
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/calculate")
    public ResponseEntity<Bill> calculateBill(
            @Parameter(description = "Subscriber Number", example = "1001") @RequestParam String subscriberNo,
            @Parameter(description = "Month", example = "4") @RequestParam Integer month,
            @Parameter(description = "Year", example = "2025") @RequestParam Integer year) {
        Bill bill = billService.calculateBill(subscriberNo, month, year);
        return ResponseEntity.ok(bill);
    }

    @Operation(summary = "Get all bills for a subscriber", description = "Returns the list of all bills for the specified subscriber.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Bills retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - JWT token missing or invalid"),
            @ApiResponse(responseCode = "404", description = "Subscriber not found")
    })
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/history")
    public ResponseEntity<List<Bill>> getBillHistory(
            @Parameter(description = "Subscriber Number", example = "1001") @RequestParam String subscriberNo) {
        List<Bill> bills = billService.getBillHistory(subscriberNo);
        return ResponseEntity.ok(bills);
    }

    @Operation(summary = "Get paged bill details for a subscriber", description = "Returns paged bill data with customizable page, size, and sorting options.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Paged bill details returned"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "Subscriber not found")
    })
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/detailed")
    public ResponseEntity<Page<Bill>> getBillDetailed(
            @Parameter(description = "Subscriber Number", example = "1001") @RequestParam String subscriberNo,

            @Parameter(description = "Page number (default is 0)", example = "0") @RequestParam(defaultValue = "0") int page,

            @Parameter(description = "Page size (default is 10)", example = "10") @RequestParam(defaultValue = "10") int size,

            @Parameter(description = "Sort by fields, e.g. 'year,desc' or 'totalAmount,asc'", example = "year,desc") @RequestParam(defaultValue = "id,asc") String sortBy) {
        // "year,desc" gibi gelen stringi işleyelim
        String[] sortParams = sortBy.split(",");
        Sort sort;

        if (sortParams.length == 2) {
            sort = Sort.by(Sort.Direction.fromString(sortParams[1].trim()), sortParams[0].trim());
        } else {
            sort = Sort.by(sortParams[0].trim());
        }

        PageRequest pageable = PageRequest.of(page, size, sort);
        Page<Bill> pagedBills = billService.getBillDetails(subscriberNo, pageable);
        return ResponseEntity.ok(pagedBills);
    }

    @Operation(summary = "Pay bill for a subscriber")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Payment successful"),
            @ApiResponse(responseCode = "404", description = "Bill not found"),
            @ApiResponse(responseCode = "400", description = "Invalid payment amount")
    })
    @PostMapping("/pay")
    public ResponseEntity<Bill> payBill(
            @Parameter(description = "Subscriber Number", example = "1001") @RequestParam String subscriberNo,
            @Parameter(description = "Month", example = "4") @RequestParam Integer month,
            @Parameter(description = "Year", example = "2025") @RequestParam Integer year,
            @Parameter(description = "Amount to pay", example = "20.00") @RequestParam BigDecimal amount) {
        try {
            Bill paid = billService.payBill(subscriberNo, month, year, amount);
            return ResponseEntity.ok(paid);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

}
