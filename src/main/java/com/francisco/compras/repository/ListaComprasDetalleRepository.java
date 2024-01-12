package com.francisco.compras.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.francisco.compras.entity.CompraDetalle;
import com.francisco.compras.entity.ListaCompraDetalle;
import com.francisco.compras.entity.ListaCompras;

@Repository
public interface ListaComprasDetalleRepository extends JpaRepository<ListaCompraDetalle, CompraDetalle>{

	Optional<List<ListaCompraDetalle>> findAllByIdLista(ListaCompras idListaCompra);
	@Transactional
	void deleteAllByIdLista(ListaCompras idListaCompra);
}
