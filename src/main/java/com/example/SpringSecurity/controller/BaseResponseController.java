package com.example.SpringSecurity.controller;

import com.example.SpringSecurity.dto.DemoDTO;
import com.example.SpringSecurity.dto.ResponseData;

import com.example.SpringSecurity.dto.response.ResponseUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
@RequestMapping("/api/public/base-response")
@RestController
public class BaseResponseController {

    @GetMapping("/success")
    @Operation(
            summary = "Lấy danh sách DemoDTO thành công",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Trả về danh sách DemoDTO trong ResponseData",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ResponseData.class),
                                    array = @ArraySchema(schema = @Schema(implementation = DemoDTO.class))
                            )
                    )
            }
    )
    public ResponseData<List<DemoDTO>> getSuccess() {
        List<DemoDTO> data = Arrays.asList(
                new DemoDTO("Alice", 30),
                new DemoDTO("Bob", 25)
        );
        return ResponseUtil.ok(data, "Lấy dữ liệu thành công");
    }

}
