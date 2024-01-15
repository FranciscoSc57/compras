package com.francisco.compras.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "lista_compra_detalle")
@Data
@IdClass(CompraDetalle.class)
public class ListaCompraDetalle {

	@Id
	private ListaCompras listaCompras;
	
	@Id
	private Productos productos;
	
	@Column(name = "cantidad")
	private Integer cantidad;
}
