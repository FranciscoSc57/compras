package com.francisco.compras.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.francisco.compras.entity.Clientes;

@Repository
public interface ClientesRepository extends JpaRepository<Clientes, Integer>{

}
