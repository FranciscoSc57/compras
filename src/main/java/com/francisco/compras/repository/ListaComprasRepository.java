package com.francisco.compras.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.francisco.compras.entity.Clientes;
import com.francisco.compras.entity.ListaCompras;

@Repository
public interface ListaComprasRepository extends JpaRepository<ListaCompras, Long>{
	
	ListaCompras findByNombre(String nombre);
	Optional<List<ListaCompras>> findAllByIdCliente(Clientes cliente);
	Optional<ListaCompras> findByIdCliente(Clientes cliente);
	@Transactional
	void deleteAllByIdLista(Long idLista);
}
