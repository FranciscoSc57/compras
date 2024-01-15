package com.francisco.compras.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.francisco.compras.models.request.ListaComprasRequest;
import com.francisco.compras.models.response.ComprasResponse;
import com.francisco.compras.service.ComprasService;

@RestController
@RequestMapping(value = "api/v1")
public class ComprasController {
	
	@Autowired
	private ComprasService comprasService;
	
	@PostMapping(value="compras")
	public ResponseEntity<ComprasResponse> guardarListaCompras(@RequestBody ListaComprasRequest listaComprasRequest ){
		return comprasService.guardarListaCompras(listaComprasRequest);
	}
	
	@GetMapping(value = "compras/{idCliente}")
	public ResponseEntity<ComprasResponse> obtenerListaComprasByIdCliente(@PathVariable Integer idCliente){
		return comprasService.obtenerListaComprasByIdCliente(idCliente);
	}
	
	@PutMapping(value = "compras/{idCliente}")
	public ResponseEntity<ComprasResponse> actualizarListaComprasByCliente(@PathVariable Integer idCliente){
		return comprasService.actualizarListaComprasByIdCliente(idCliente);
	}
	
	@DeleteMapping(value = "compras/{idLista}")
	public ResponseEntity<ComprasResponse> eliminarListaCompras(@PathVariable Integer idLista){
		return comprasService.eliminarListaCompras(idLista);
	}

}
