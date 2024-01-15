package com.francisco.compras.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.francisco.compras.entity.Clientes;
import com.francisco.compras.entity.ListaCompras;

import jakarta.transaction.Transactional;

@Repository
public interface ListaComprasRepository extends JpaRepository<ListaCompras, Integer>{
	
	ListaCompras findByNombre(String nombre);
	Optional<List<ListaCompras>> findAllByClientes(Clientes cliente);
	Optional<ListaCompras> findByClientes(Clientes cliente);
	@Transactional
	void deleteAllByIdLista(Integer idLista);
}
