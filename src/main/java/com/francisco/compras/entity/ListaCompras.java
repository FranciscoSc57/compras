package com.francisco.compras.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "lista_compra")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListaCompras {

	@Id
	@GeneratedValue
	@Column(name = "idLista", nullable = false)
	private Long idLista;
	
	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Clientes idCliente;
	
	@Column(name = "nombre", nullable = false)
	private String nombre;
	
	@Column(name = "fechaRegistro")
	private LocalDate fechaRegistro;
	
	@Column(name = "fechaUltimaActualizacion")
	private LocalDate fechaUltimaActualizacion;
	
	@Column(name = "activo")
	private int activo;
	
	ListaCompras(Long idLista){
		this.idLista = idLista;
	}
}
