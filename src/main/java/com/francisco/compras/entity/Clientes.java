package com.francisco.compras.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;

@Entity
@Table(name = "clientes")
@Data
public class Clientes {
	
	@Id
	@Column(name = "idCliente", nullable = false)
	private Long idCliente;
	
	@Transient
	@Column(name = "nombre", nullable = false)
	private String nombre;
	
	@Column(name = "activo")
	private int activo;

}
