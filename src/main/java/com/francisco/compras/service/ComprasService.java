package com.francisco.compras.service;

import org.springframework.http.ResponseEntity;

import com.francisco.compras.models.request.ListaComprasRequest;
import com.francisco.compras.models.response.ComprasResponse;

public interface ComprasService {

	ResponseEntity<ComprasResponse> guardarListaCompras(ListaComprasRequest listaComprasRequest);
	ResponseEntity<ComprasResponse> obtenerListaComprasByIdCliente(Integer idCliente);
	ResponseEntity<ComprasResponse> actualizarListaComprasByIdCliente(Integer idCliente);
	ResponseEntity<ComprasResponse> eliminarListaCompras(Integer idLista);
}
