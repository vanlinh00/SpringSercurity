package com.example.SpringSecurity.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Base response chứa status và mã phản hồi.")
public class BaseResponse {

    @Schema(description = "Trạng thái của response (thường là SUCCESS/FAILURE).", example = "SUCCESS")
    private String status;

    @Schema(description = "Mã trạng thái HTTP tương ứng.", example = "200")
    private Integer code;
}
