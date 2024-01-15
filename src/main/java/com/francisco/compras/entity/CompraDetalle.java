package com.francisco.compras.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompraDetalle implements Serializable{

	@ManyToOne
	@JoinColumn(
			name = "id_lista_compra",
			referencedColumnName = "id_lista"
			)
	private ListaCompras listaCompras;
	
	@ManyToOne
	@JoinColumn(
			name = "id_codigo_producto",
			referencedColumnName = "id_producto"
			)
	private Productos productos;
}
