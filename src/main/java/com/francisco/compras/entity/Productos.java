package com.francisco.compras.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;

@Entity
@Table(name = "productos")
@Data
public class Productos {

	@Id
	@Column(name = "idProducto", nullable = false)
	private Long idProducto;
	
	@Transient
	@Column(name = "clave")
	private String clave;
	
	@Transient
	@Column(name = "descripcion")
	private String descripcion;
	
	@Column(name = "activo")
	private int activo;
}
