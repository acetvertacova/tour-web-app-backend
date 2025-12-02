package com.tour_web_app.dto.response;

import com.tour_web_app.exception.enums.ErrorType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorResponse {
    private String errorCode;
    private String errorDescription;
    private String impact;
    private String resolution;
    private String errorType;
}
