package com.francisco.compras.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "clientes")
@Data
public class Clientes {
	
	@Id
	@Column(name = "id_cliente")
	private Integer idCliente;
	
	@Column(name = "nombre", nullable = false)
	private String nombre;
	
	private boolean activo;

}
