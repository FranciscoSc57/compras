package com.francisco.compras.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.francisco.compras.entity.Clientes;
import com.francisco.compras.entity.CompraDetalle;
import com.francisco.compras.entity.ListaCompraDetalle;
import com.francisco.compras.entity.ListaCompras;
import com.francisco.compras.entity.Productos;
import com.francisco.compras.models.ListaComprasRequest;
import com.francisco.compras.models.ProductosRequest;
import com.francisco.compras.repository.ClientesRepository;
import com.francisco.compras.repository.ListaComprasDetalleRepository;
import com.francisco.compras.repository.ListaComprasRepository;
import com.francisco.compras.repository.ProductosRepository;
import com.francisco.compras.service.ComprasService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ComprasServiceImpl implements ComprasService{

	@Autowired
	private ClientesRepository clientesRepository;
	
	@Autowired
	private ListaComprasRepository listaComprasRepository;
	
	@Autowired
	private ListaComprasDetalleRepository listaComprasDetalleRepository;
	
	@Autowired
	private ProductosRepository productosRepository;

	
	@Override
	public ResponseEntity<Object> guardarListaCompras(ListaComprasRequest listaComprasRequest) {
		Clientes clientes = new Clientes();
		ListaCompras listaCompras = new ListaCompras();
		ListaCompraDetalle listaCompraDetalle = new ListaCompraDetalle();		
		Productos productos = new Productos();
		CompraDetalle compraDetalle = new CompraDetalle();
		
		Optional<Clientes> clienteExiste = clientesRepository.findById(listaComprasRequest.getIdCliente());
		
		HashMap<String, Object> data = new HashMap<String, Object>();
		
		if(!clienteExiste.isPresent()) {
			log.info("Cliente no existe");
			clientes.setIdCliente(listaComprasRequest.getIdCliente());
			clientes.setActivo(1);
			clientesRepository.save(clientes);
			log.info("Ciente guardado");
		}
			
		clientes.setIdCliente(listaComprasRequest.getIdCliente());
		
		listaCompras.setIdCliente(clientes);
		listaCompras.setNombre(listaComprasRequest.getNombreLista());
		listaCompras.setFechaRegistro(LocalDate.now());
		listaCompras.setFechaUltimaActualizacion(LocalDate.now());
		listaCompras.setActivo(1);
		
		log.info("GuardandoLista");
		listaComprasRepository.save(listaCompras);
		log.info("ListaCompra Guardada");
		
		listaComprasRequest.getProductos().forEach(producto ->{
			Optional<Productos> productoExiste = productosRepository.findById(producto.getIdProducto());
			log.info("Valida producto existe");
			if(!productoExiste.isPresent()) {
				log.info("Producto no presente");
				productos.setActivo(1);
				productos.setIdProducto(producto.getIdProducto());
				
				productosRepository.save(productos);
				log.info("Producto guardado");
			}
			ListaCompras listaCompraExist = listaComprasRepository.findByNombre(listaComprasRequest.getNombreLista());
			log.info("listaCompraExist: " + listaCompraExist.toString());
			productos.setIdProducto(producto.getIdProducto());
			compraDetalle.setCodigoProducto(producto.getIdProducto());
			compraDetalle.setIdLista(listaCompraExist.getIdLista());
			compraDetalle.setCodigoProducto(producto.getIdProducto());
			
			listaCompraDetalle.setCantidad(producto.getCantidad());
			listaCompraDetalle.setIdProducto(productos);
			listaCompraDetalle.setIdListaCompraDetalle(compraDetalle);
			
			listaCompras.setIdLista(listaCompraExist.getIdLista());
			log.info("ListaComprasExistente: " + listaCompraExist.getIdLista().toString());
			log.info("ListaComprasNueva: "+listaCompras.getIdLista().toString());
			
			listaCompraDetalle.setIdLista(listaCompras);
			log.info("ListaComprasNew: " + listaCompraDetalle.getIdLista().toString());
			
			log.info("Guardando Lista");
			listaComprasDetalleRepository.save(listaCompraDetalle);
			log.info("Lista Compra Detalle Guardada");
			data.put("datos", listaCompraDetalle);
		});
		
		//productos.setIdProducto(listaComprasRequest.getIdCliente());
		
		//listaComprasRepository.save(listaCompras);	
		
		data.put("message", "Exito");
		
		return new ResponseEntity<>(
				data,
				HttpStatus.CREATED
				);
	}


	@Override
	public ResponseEntity<Object> obtenerListaComprasByIdCliente(Long idCliente) {
		log.info("Obtener Lista");
		HashMap<String, Object> datos = new HashMap<String, Object>();
		HashMap<String, Object> datosDetalle = new HashMap<String, Object>();
		
		Clientes cliente = new Clientes();
		cliente.setIdCliente(idCliente);
		
		try {
			Optional<List<ListaCompras>> listaCompras = listaComprasRepository.findAllByIdCliente(cliente);
			log.info("ListaCompras");
			
			if(listaCompras.isPresent()) {
				log.info("Encontrada");
				
				listaCompras.get().forEach(compra ->{
					log.info(compra.toString());
					Optional<List<ListaCompraDetalle>>listaCompraDetalle = listaComprasDetalleRepository.findAllByIdLista(compra);
					log.info("ListaComprasDetalle: " + listaCompraDetalle.toString());
					datosDetalle.put("detalleCompra", listaCompraDetalle);
				});				
				datos.put("detalle", datosDetalle);
				datos.put("data", listaCompras);
				datos.put("message", "Exito");
			}else {
				datos.put("message", "No tiene compras");
			}
		}catch (Exception e) {
			log.info("Error: " + e.getMessage());
			datos.put("message", e.getMessage());
		}
		
		
		return new ResponseEntity<>(
				datos,
				HttpStatus.ACCEPTED
				);
	}


	
	@Override
	public ResponseEntity<Object> actualizarListaComprasByIdCliente(Long idCliente) {
		log.info("Actualizar Lista");
		HashMap<String, Object> datos = new HashMap<String, Object>();
		Clientes cliente = new Clientes();
		cliente.setIdCliente(idCliente);
		
		ListaCompras compraModificada = new ListaCompras();
		CompraDetalle compraDetalle = new CompraDetalle();
		ListaCompraDetalle listaCompraDetalle = new ListaCompraDetalle();
		
		try {
			Optional<ListaCompras> compra = listaComprasRepository.findByIdCliente(cliente);
			
			log.info("ListaCompras");
			
			if(compra.isPresent()) {
				log.info("Encontrada");
				compraModificada = compra.get();
				compraModificada.setNombre("Lista Actualizada");
				listaComprasRepository.save(compraModificada);
				log.info("Lista Actualizada");
			
				ProductosRequest productosRequest1 = new ProductosRequest();
				ProductosRequest productosRequest2 = new ProductosRequest();
				
				productosRequest1.setIdProducto(25020L);
				productosRequest1.setCantidad(2);
				
				productosRequest2.setIdProducto(10170L);
				productosRequest2.setCantidad(9);
				List<ProductosRequest> listProductos = new ArrayList<ProductosRequest>();
				listProductos.add(productosRequest1);
				listProductos.add(productosRequest2);
				
				listProductos.forEach(producto ->{
					Optional<Productos> productoExiste = productosRepository.findById(producto.getIdProducto());
					Productos productoNuevo = new Productos();
					log.info("Valida producto existe");
					if(!productoExiste.isPresent()) {						
						log.info("Producto no presente");
						productoNuevo.setActivo(1);
						productoNuevo.setIdProducto(producto.getIdProducto());
						
						productosRepository.save(productoNuevo);
						log.info("Producto guardado");
					}
					
					productoNuevo.setIdProducto(producto.getIdProducto());
					compraDetalle.setIdLista(compra.get().getIdLista());
					compraDetalle.setCodigoProducto(producto.getIdProducto());
					
					listaCompraDetalle.setCantidad(producto.getCantidad());
					listaCompraDetalle.setIdProducto(productoNuevo);					
					listaCompraDetalle.setIdListaCompraDetalle(compraDetalle);
					listaCompraDetalle.setIdLista(compra.get());
					
					log.info("Guardando Lista");
					listaComprasDetalleRepository.save(listaCompraDetalle);
					log.info("Lista Compra Detalle Guardada");
					
					log.info("compraDetalleList");
					
					datos.put("message", "Datos Actualizados");	
				});
				datos.put("data", compraModificada);
			}else {
				datos.put("message", "No tiene compras");
			}
		}catch (Exception e) {
			log.info("Error: " + e.getMessage());
			datos.put("message", e.getMessage());
		}
		return new ResponseEntity<>(
				datos,
				HttpStatus.ACCEPTED
				);
	}


	@Override
	public ResponseEntity<Object> eliminarListaCompras(Long idLista) {
		HashMap<String, Object> datos = new HashMap<String, Object>();
		ListaCompras listaCompras = new ListaCompras();
		listaCompras.setIdLista(idLista);
		
		listaComprasDetalleRepository.deleteAllByIdLista(listaCompras);
		listaComprasRepository.deleteAllByIdLista(idLista);
		datos.put("message", "Ejecutado con exito");
		return new ResponseEntity<>(
				datos,
				HttpStatus.ACCEPTED
				);
	}

}
