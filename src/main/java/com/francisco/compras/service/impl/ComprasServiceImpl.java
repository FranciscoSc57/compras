package com.francisco.compras.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.francisco.compras.entity.Clientes;
import com.francisco.compras.entity.CompraDetalle;
import com.francisco.compras.entity.ListaCompraDetalle;
import com.francisco.compras.entity.ListaCompras;
import com.francisco.compras.entity.Productos;
import com.francisco.compras.models.request.ListaComprasRequest;
import com.francisco.compras.models.request.ProductosRequest;
import com.francisco.compras.models.response.ComprasResponse;
import com.francisco.compras.repository.ClientesRepository;
import com.francisco.compras.repository.ListaComprasDetalleRepository;
import com.francisco.compras.repository.ListaComprasRepository;
import com.francisco.compras.repository.ProductosRepository;
import com.francisco.compras.service.ComprasService;
import com.francisco.compras.service.LogAspect;
import com.francisco.compras.utils.ResponseGeneric;

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
	@LogAspect
	public ResponseEntity<ComprasResponse> guardarListaCompras(ListaComprasRequest listaComprasRequest) {
		Clientes clientes = new Clientes();
		ListaCompras listaCompras = new ListaCompras();
		ListaCompraDetalle listaCompraDetalle = new ListaCompraDetalle();		
		Productos productos = new Productos();
		CompraDetalle compraDetalle = new CompraDetalle();
		ComprasResponse comprasResponse = new ComprasResponse();
		
		try {
			
			Optional<Clientes> clienteExiste = clientesRepository.findById(listaComprasRequest.getIdCliente());
			
			if(!clienteExiste.isPresent()) {
				log.info("Cliente no existe: Adding...");
				clientes.setIdCliente(listaComprasRequest.getIdCliente());
				clientes.setNombre("");
				clientes.setActivo(true);
				clientesRepository.save(clientes);
				List<Clientes> clientesNewP = clientesRepository.findAll();
				log.info("Ciente guardado: " + clientesNewP);
			}
				
			clientes.setIdCliente(listaComprasRequest.getIdCliente());
			clientes.setNombre("");
			
			listaCompras.setClientes(clientes);
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
					log.info("Producto no presente: Adding...");
					productos.setActivo(1);
					productos.setIdProducto(producto.getIdProducto());
					
					productosRepository.save(productos);
					log.info("Producto guardado");
				}
				log.info("Consulta Lista Compra por nombre de lista: " + listaComprasRequest.getNombreLista());
				ListaCompras listaCompraExist = listaComprasRepository.findByNombre(listaComprasRequest.getNombreLista());
				productos.setIdProducto(producto.getIdProducto());
				compraDetalle.setProductos(productos);
				compraDetalle.setListaCompras(listaCompraExist);
				
				listaCompraDetalle.setCantidad(producto.getCantidad());
				listaCompraDetalle.setProductos(productos);
				
				listaCompras.setIdLista(listaCompraExist.getIdLista());
		
				listaCompraDetalle.setListaCompras(listaCompras);
				
				log.info("Guardando Lista Compra Detalle");
				listaComprasDetalleRepository.save(listaCompraDetalle);
				log.info("Lista Compra Detalle Guardada");
			});
			
			comprasResponse.setDatos(listaCompraDetalle);
			comprasResponse.setHasError(false);
			comprasResponse.setMensaje("Ejecutado con exito");
			
		}
		catch (IllegalArgumentException e) {
			comprasResponse.setErrors(e.getMessage());
			comprasResponse.setHasError(true);
			comprasResponse.setMensaje("Ocurrio un error: Argumento Ilegal");
			return ResponseGeneric.response(comprasResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		catch (OptimisticLockingFailureException e) {
			comprasResponse.setErrors(e.getMessage());
			comprasResponse.setHasError(true);
			comprasResponse.setMensaje("Ocurrio un error: Entidad no existe en la BD");
			return ResponseGeneric.response(comprasResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		catch (Exception e) {
			comprasResponse.setErrors(e.getMessage());
			comprasResponse.setHasError(true);
			comprasResponse.setMensaje("Ocurrio un error inesperado: " + e.getMessage());
			return ResponseGeneric.response(comprasResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
				
		return ResponseGeneric.response(comprasResponse, HttpStatus.CREATED);
	}


	@Override
	@LogAspect
	public ResponseEntity<ComprasResponse> obtenerListaComprasByIdCliente(Integer idCliente) {
		log.info("Obtener Lista");
		
		Clientes cliente = new Clientes();
		cliente.setIdCliente(idCliente);
		cliente.setNombre("");
		
		List<List<ListaCompraDetalle>> listaDetallesCompra = new ArrayList<>();
		
		ComprasResponse comprasResponse = new ComprasResponse();
		HttpStatus httpStatus;
		
		try {
			log.info("Obteniendo listas por cliente");
			List<ListaCompras> listaCompras = listaComprasRepository.findAllByClientes(cliente);
			
			if(!listaCompras.isEmpty()) {
				log.info("Listas de cliente encontradas");
				
				listaCompras.forEach(compra ->{
					List<ListaCompraDetalle>detalleCompra = listaComprasDetalleRepository.findAllByListaCompras(compra);
					listaDetallesCompra.add(detalleCompra);
				});				
				log.info("ListaComprasDetalle: " + listaDetallesCompra.toString());
				
				comprasResponse.setDatos(listaDetallesCompra);
				comprasResponse.setHasError(false);
				comprasResponse.setMensaje("Ejecutado con exito");
				httpStatus = HttpStatus.ACCEPTED;				
			}else {
				comprasResponse.setHasError(true);
				comprasResponse.setMensaje("Cliente no tiene compras");
				httpStatus = HttpStatus.NOT_FOUND;				
			}
		}
		catch (IllegalArgumentException e) {
			comprasResponse.setErrors(e.getMessage());
			comprasResponse.setHasError(true);
			comprasResponse.setMensaje("Ocurrio un error: Argumento Ilegal");
			return ResponseGeneric.response(comprasResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		catch (OptimisticLockingFailureException e) {
			comprasResponse.setErrors(e.getMessage());
			comprasResponse.setHasError(true);
			comprasResponse.setMensaje("Ocurrio un error: Entidad no existe en la BD");
			return ResponseGeneric.response(comprasResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		catch (Exception e) {
			comprasResponse.setErrors(e.getMessage());
			comprasResponse.setHasError(true);
			comprasResponse.setMensaje("Ocurrio un error inesperado: " + e.getMessage());
			return ResponseGeneric.response(comprasResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return ResponseGeneric.response(comprasResponse, httpStatus);
	}


	@Override
	@LogAspect
	public ResponseEntity<ComprasResponse> actualizarListaComprasByIdCliente(Integer idCliente) {
		log.info("Actualizar Lista");
		
		ComprasResponse comprasResponse = new ComprasResponse();
		HttpStatus httpStatus;
		
		HashMap<String, Object> datos = new HashMap<String, Object>();
		Clientes cliente = new Clientes();
		cliente.setIdCliente(idCliente);
		cliente.setNombre("");
		
		ListaCompras compraModificada = new ListaCompras();
		CompraDetalle compraDetalle = new CompraDetalle();
		ListaCompraDetalle listaCompraDetalle = new ListaCompraDetalle();
		
		try {
			Optional<ListaCompras> compra = listaComprasRepository.findByClientes(cliente);
			
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
					compraDetalle.setListaCompras(compra.get());
					compraDetalle.setProductos(productoNuevo);
					
					listaCompraDetalle.setCantidad(producto.getCantidad());
					listaCompraDetalle.setProductos(productoNuevo);					
					listaCompraDetalle.setListaCompras(compra.get());
					
					log.info("Guardando Lista");
					listaComprasDetalleRepository.save(listaCompraDetalle);
					log.info("Lista Compra Detalle Guardada");
					
					log.info("compraDetalleList");
					
					datos.put("message", "Datos Actualizados");	
				});
				datos.put("data", compraModificada);
				
				comprasResponse.setDatos(compraModificada);
				comprasResponse.setHasError(false);
				comprasResponse.setMensaje("Ejecutado con exito");
				httpStatus = HttpStatus.ACCEPTED;	
				
			}else {
				comprasResponse.setHasError(false);
				comprasResponse.setMensaje("Cliente no tiene compras");
				httpStatus = HttpStatus.NOT_FOUND;	
			}
		}
		catch (IllegalArgumentException e) {
			comprasResponse.setErrors(e.getMessage());
			comprasResponse.setHasError(true);
			comprasResponse.setMensaje("Ocurrio un error: Argumento Ilegal");
			return ResponseGeneric.response(comprasResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		catch (OptimisticLockingFailureException e) {
			comprasResponse.setErrors(e.getMessage());
			comprasResponse.setHasError(true);
			comprasResponse.setMensaje("Ocurrio un error: Entidad no existe en la BD");
			return ResponseGeneric.response(comprasResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		catch (Exception e) {
			comprasResponse.setErrors(e.getMessage());
			comprasResponse.setHasError(true);
			comprasResponse.setMensaje("Ocurrio un error inesperado: " + e.getMessage());
			return ResponseGeneric.response(comprasResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return ResponseGeneric.response(comprasResponse, httpStatus);
	}


	@Override
	@LogAspect
	public ResponseEntity<ComprasResponse> eliminarListaCompras(Integer idLista) {
		HashMap<String, Object> datos = new HashMap<String, Object>();
		ListaCompras listaCompras = new ListaCompras();
		ComprasResponse comprasResponse = new ComprasResponse();
		HttpStatus httpStatus;
				
		try {
			listaCompras.setIdLista(idLista);
			List<ListaCompras> listaExiste = listaComprasRepository.findAllByIdLista(idLista);
			if(listaExiste.isEmpty()) {
				comprasResponse.setHasError(true);
				comprasResponse.setMensaje("Lista no encontrada");
				httpStatus = HttpStatus.NOT_FOUND;
			}else {
				listaComprasDetalleRepository.deleteAllByListaCompras(listaCompras);
				listaComprasRepository.deleteAllByIdLista(idLista);
				datos.put("message", "Ejecutado con exito");
				
				comprasResponse.setHasError(false);
				comprasResponse.setMensaje("Ejecutado con exito");
				httpStatus = HttpStatus.ACCEPTED;	
			}			
		}
		catch (IllegalArgumentException e) {
			comprasResponse.setErrors(e.getMessage());
			comprasResponse.setHasError(true);
			comprasResponse.setMensaje("Ocurrio un error: Argumento Ilegal");
			return ResponseGeneric.response(comprasResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		catch (OptimisticLockingFailureException e) {
			comprasResponse.setErrors(e.getMessage());
			comprasResponse.setHasError(true);
			comprasResponse.setMensaje("Ocurrio un error: Entidad no existe en la BD");
			return ResponseGeneric.response(comprasResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		catch (Exception e) {
			comprasResponse.setErrors(e.getMessage());
			comprasResponse.setHasError(true);
			comprasResponse.setMensaje("Ocurrio un error inesperado: " + e.getMessage());
			return ResponseGeneric.response(comprasResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return ResponseGeneric.response(comprasResponse, httpStatus);
		
	}

}
