package com.francisco.compras.models.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ComprasResponse {

	private String mensaje;	
	private Object datos;
	private boolean hasError;
	private Object errors;
}
