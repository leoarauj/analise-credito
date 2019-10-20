package br.com.analise.credito.api.response;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public @Data class RestResponse {
	private int code;
	private HttpStatus status;
	private String message;
	private String path;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private LocalDateTime date;
}
