package com.francisco.compras.models;

import java.util.List;

import lombok.Data;

@Data
public class ListaComprasRequest {

	private Long idCliente;
	
	private String nombreLista;
	
	private List<ProductosRequest> productos;
		
}
