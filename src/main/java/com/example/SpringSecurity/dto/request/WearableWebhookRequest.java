package com.example.SpringSecurity.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class WearableWebhookRequest {

    @NotBlank
    private String userId;

    @NotBlank
    private String device;

    @NotBlank
    private String timestamp;

    @Min(0)
    private int heartRate;

    @Min(0)
    private int steps;

    @JsonIgnore
    private String rawPayload; // lưu lại raw JSON để xác thực chữ ký
}
