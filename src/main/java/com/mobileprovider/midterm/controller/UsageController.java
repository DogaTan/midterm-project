package com.mobileprovider.midterm.controller;

import com.mobileprovider.midterm.entity.Usage;
import com.mobileprovider.midterm.entity.UsageType;
import com.mobileprovider.midterm.service.UsageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/usage")
@Tag(name = "Usage", description = "Phone & Internet Usage API")
public class UsageController {

    private final UsageService usageService;

    public UsageController(UsageService usageService) {
        this.usageService = usageService;
    }

    @Operation(
            summary = "Add usage for subscriber",
            description = "Adds 10 minutes (for PHONE) or 1MB (for INTERNET) usage to the specified subscriber for a given month and year. Requires JWT token."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usage successfully added"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Missing or invalid JWT"),
            @ApiResponse(responseCode = "404", description = "Subscriber not found")
    })
    @PreAuthorize("isAuthenticated()") // 🔐 JWT ile koruma
    @PostMapping
    public ResponseEntity<Usage> addUsage(
            @Parameter(description = "Subscriber Number", example = "1001")
            @RequestParam String subscriberNo,

            @Parameter(description = "Usage Type: PHONE or INTERNET", example = "PHONE")
            @RequestParam UsageType usageType,

            @Parameter(description = "Month", example = "4")
            @RequestParam Integer month,

            @Parameter(description = "Year", example = "2025")
            @RequestParam Integer year
    ) {
        Usage usage = usageService.addUsage(subscriberNo, usageType, month, year);
        return ResponseEntity.ok(usage);
    }
}
