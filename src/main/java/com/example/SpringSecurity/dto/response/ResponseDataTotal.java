package com.example.SpringSecurity.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Phản hồi dữ liệu có tổng số bản ghi.")
public class ResponseDataTotal<T> extends BaseResponse implements Serializable {

    @Schema(description = "Thông báo phản hồi.", example = "Lấy dữ liệu thành công")
    private String message;

    @Schema(description = "Dữ liệu trả về.")
    private T data;

    @Schema(description = "Danh sách hành động.", example = "[\"XEM\", \"SỬA\"]")
    private List<String> action;

    @Schema(description = "Tổng số bản ghi tìm được.", example = "100")
    private Long totalRecord;
}
