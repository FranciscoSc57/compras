package com.francisco.compras.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.francisco.compras.entity.Productos;

@Repository
public interface ProductosRepository extends JpaRepository<Productos, Long>{

}
