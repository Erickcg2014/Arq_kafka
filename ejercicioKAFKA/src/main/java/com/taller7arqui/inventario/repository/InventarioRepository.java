package com.taller7arqui.inventario.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.taller7arqui.inventario.entity.InventarioEntity;

@Repository
public interface InventarioRepository extends JpaRepository<InventarioEntity, Long> {
    boolean existsByIdAndCantidadGreaterThanEqual(Long id, int cantidad);
}
