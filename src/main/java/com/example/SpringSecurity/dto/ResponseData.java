package com.example.SpringSecurity.dto;

import com.example.SpringSecurity.dto.response.BaseResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Lớp phản hồi dữ liệu tổng quát chứa thông tin trạng thái và dữ liệu trả về.")
public class ResponseData<T> extends BaseResponse implements Serializable {

    @Schema(
            description = "Tin nhắn mô tả phản hồi (thông thường là thông báo về trạng thái).",
            example = "Giao dịch thành công.",
            required = true
    )
    private String message;

    @Schema(
            description = "Danh sách các hành động có thể thực hiện (nếu có).",
            example = "[\"Tải lại\", \"Quay lại\"]"
    )
    private List<String> action;

    @Schema(
            description = "Dữ liệu trả về, có thể là bất kỳ loại đối tượng nào.",
            required = true
    )
    private T data;

    public ResponseData(T data) {
        this.data = data;
    }

    public ResponseData(String message, T data) {
        this.message = message;
        this.data = data;
    }
}
