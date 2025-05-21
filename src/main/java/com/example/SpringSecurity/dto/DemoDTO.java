package com.example.SpringSecurity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Demo dữ liệu người dùng.")
public class DemoDTO {

    @Schema(description = "Tên người dùng.", example = "Alice")
    private String name;

    @Schema(description = "Tuổi người dùng.", example = "30")
    private int age;
}
