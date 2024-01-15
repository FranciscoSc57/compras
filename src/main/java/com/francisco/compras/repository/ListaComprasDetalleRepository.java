package com.francisco.compras.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.francisco.compras.entity.CompraDetalle;
import com.francisco.compras.entity.ListaCompraDetalle;
import com.francisco.compras.entity.ListaCompras;

import jakarta.transaction.Transactional;

@Repository
public interface ListaComprasDetalleRepository extends JpaRepository<ListaCompraDetalle, CompraDetalle>{

	List<ListaCompraDetalle> findAllByListaCompras(ListaCompras listaCompras);
	@Transactional
	void deleteAllByListaCompras(ListaCompras idListaCompra);
}
