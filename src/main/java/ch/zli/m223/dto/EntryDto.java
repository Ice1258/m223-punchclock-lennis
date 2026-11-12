package ch.zli.m223.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;

public class EntryDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(readOnly = true)
    private Long id;

    // Wird vom Server aus dem Token gesetzt, nicht vom Client
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(readOnly = true)
    private String username;

    @NotNull(message = "Check-in is required")
    private LocalDateTime checkIn;

    @NotNull(message = "Check-out is required")
    private LocalDateTime checkOut;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDateTime getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(LocalDateTime checkIn) {
        this.checkIn = checkIn;
    }

    public LocalDateTime getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(LocalDateTime checkOut) {
        this.checkOut = checkOut;
    }

    @AssertTrue(message = "Check-in must be before check-out")
    @JsonIgnore
    public boolean isCheckInBeforeCheckOut() {
        if (checkIn == null || checkOut == null) {
            return true;
        }

        return checkIn.isBefore(checkOut);
    }
}