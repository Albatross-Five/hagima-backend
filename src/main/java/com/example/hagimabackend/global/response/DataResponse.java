package com.example.hagimabackend.global.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * 상태코드와 데이터, 메시지를 반환할 경우 응답 형식
 * @param <T> 응답 데이터를 담는 dto가 주로 들어감
 */
@Getter
public class DataResponse<T> extends DefaultResponse {

  @Schema(description = "응답 데이터")
  private final T data;

  private DataResponse(HttpStatus status, String message, T data) {
    super(status.value(), message);
    this.data = data;
  }

  public static <T> DataResponse<T> of(HttpStatus status, String message, T data) {
    return new DataResponse<>(status, message, data);
  }

}
