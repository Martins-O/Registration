package com.example.registrationlogin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ApiResponse {
	private Long id;
	private int statusCode;
	private String message;
	private boolean isSuccess;
}
