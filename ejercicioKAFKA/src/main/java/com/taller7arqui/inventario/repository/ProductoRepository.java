package com.taller7arqui.inventario.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.taller7arqui.inventario.entity.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    
}