package com.francisco.compras.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;

import lombok.Data;

@Embeddable
@Data
public class CompraDetalle implements Serializable{

	@Column(name = "idListaCompra")
	private Long idLista;
	
	@Column(name = "idProducto")
	private Long codigoProducto;
}
