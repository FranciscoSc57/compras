package com.francisco.compras.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "lista_compra_detalle")
@Data
public class ListaCompraDetalle {

	@EmbeddedId
	private CompraDetalle idListaCompraDetalle;
	
	@ManyToOne
	@JoinColumn(name = "idListaCompra", insertable = false, updatable = false)
	private ListaCompras idLista;
	
	@ManyToOne
	@JoinColumn(name = "idCodigoProducto")
	private Productos idProducto;
	
	@Column(name = "cantidad")
	private Integer cantidad;
}
