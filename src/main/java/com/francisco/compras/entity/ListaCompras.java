package com.francisco.compras.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ListaCompras")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListaCompras {

	@Id
	@GeneratedValue(
			strategy = GenerationType.IDENTITY)
	@Column(name = "id_lista")
	private Integer idLista;
	
	@ManyToOne
	@JoinColumn(
			name = "customer_id",
			referencedColumnName = "id_cliente"
	)
	private Clientes clientes;
	
	private String nombre;
	
	@Column(name = "fechaRegistro")
	private LocalDate fechaRegistro;
	
	@Column(name = "fechaUltimaActualizacion")
	private LocalDate fechaUltimaActualizacion;
	
	@Column(name = "activo")
	private int activo;
	
	ListaCompras(Integer idLista){
		this.idLista = idLista;
	}
}
