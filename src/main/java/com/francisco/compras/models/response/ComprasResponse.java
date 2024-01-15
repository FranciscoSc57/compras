package com.francisco.compras.models.response;

import lombok.Data;

@Data
public class ComprasResponse {

	private String mensaje;	
	private Object datos;
	private boolean hasError;
	private Object errors;
}
