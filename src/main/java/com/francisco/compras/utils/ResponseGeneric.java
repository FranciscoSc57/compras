package com.francisco.compras.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.francisco.compras.models.response.ComprasResponse;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ResponseGeneric {

	public static ResponseEntity<ComprasResponse> response(ComprasResponse comprasResponse, HttpStatus httpStatus ) {
		log.info("Response: " + comprasResponse.toString());
		
		return new ResponseEntity<>(
				comprasResponse,
				httpStatus
				);
	}
}
