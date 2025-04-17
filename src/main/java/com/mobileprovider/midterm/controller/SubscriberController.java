package com.mobileprovider.midterm.controller;

import com.mobileprovider.midterm.entity.Subscriber;
import com.mobileprovider.midterm.service.SubscriberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/subscribers")
@Tag(name = "Subscribers", description = "API for managing mobile subscribers")
public class SubscriberController {

    private final SubscriberService subscriberService;

    public SubscriberController(SubscriberService subscriberService) {
        this.subscriberService = subscriberService;
    }

    @Operation(
            summary = "Create a new subscriber",
            description = "Adds a new mobile subscriber with a unique subscriber number."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Subscriber successfully created"),
            @ApiResponse(responseCode = "400", description = "Subscriber already exists")
    })
    @PostMapping
    public ResponseEntity<Subscriber> createSubscriber(
            @RequestBody Subscriber subscriber
    ) {
        Subscriber created = subscriberService.createSubscriber(subscriber);
        return ResponseEntity.ok(created);
    }

    @Operation(
            summary = "Get all subscribers",
            description = "Returns a list of all registered subscribers."
    )
    @PreAuthorize("isAuthenticated()") // 🔐 JWT ile koruma
    @GetMapping
    public ResponseEntity<List<Subscriber>> getAllSubscribers() {
        return ResponseEntity.ok(subscriberService.getAllSubscribers());
    }

    @Operation(
            summary = "Get a subscriber by subscriber number",
            description = "Returns the subscriber with the given subscriber number."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Subscriber found"),
            @ApiResponse(responseCode = "404", description = "Subscriber not found")
    })
    @PreAuthorize("isAuthenticated()") // 🔐 JWT ile koruma
    @GetMapping("/{subscriberNo}")
    public ResponseEntity<Subscriber> getSubscriberByNo(
            @Parameter(description = "Subscriber Number", example = "1001")
            @PathVariable String subscriberNo
    ) {
        return subscriberService.getSubscriberBySubscriberNo(subscriberNo)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
