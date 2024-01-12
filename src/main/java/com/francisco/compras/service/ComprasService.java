package com.francisco.compras.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import com.francisco.compras.models.ListaComprasRequest;

public interface ComprasService {

	ResponseEntity<Object> guardarListaCompras(ListaComprasRequest listaComprasRequest);
	ResponseEntity<Object> obtenerListaComprasByIdCliente(Long idCliente);
	ResponseEntity<Object> actualizarListaComprasByIdCliente(Long idCliente);
	ResponseEntity<Object> eliminarListaCompras(Long idLista);
}
