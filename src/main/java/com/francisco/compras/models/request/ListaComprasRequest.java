package com.francisco.compras.models.request;

import java.util.List;

import lombok.Data;

@Data
public class ListaComprasRequest {

	private Integer idCliente;
	
	private String nombreLista;
	
	private List<ProductosRequest> productos;
		
}
