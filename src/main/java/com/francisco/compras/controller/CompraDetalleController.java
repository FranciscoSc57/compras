package com.francisco.compras.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.francisco.compras.models.ListaComprasRequest;
import com.francisco.compras.service.ComprasService;

@RestController
@RequestMapping(value = "api/v1")
public class CompraDetalleController {
	
	@Autowired
	private ComprasService comprasService;
	
	@PostMapping(value="lista-compras")
	public ResponseEntity<Object> guardarListaCompras(@RequestBody ListaComprasRequest listaComprasRequest ){
		return comprasService.guardarListaCompras(listaComprasRequest);
	}
	
	@GetMapping(value = "lista-compras")
	public ResponseEntity<Object> obtenerListaComprasByIdCliente(@RequestParam Long idCliente){
		return comprasService.obtenerListaComprasByIdCliente(idCliente);
	}
	
	@PutMapping(value = "lista-compras")
	public ResponseEntity<Object> actualizarListaComprasByCliente(@RequestParam Long idCliente){
		return comprasService.actualizarListaComprasByIdCliente(idCliente);
	}
	
	@DeleteMapping(value = "lista-compra/eliminar")
	public ResponseEntity<Object> eliminarListaCompras(@RequestParam Long idLista){
		return comprasService.eliminarListaCompras(idLista);
	}

}
