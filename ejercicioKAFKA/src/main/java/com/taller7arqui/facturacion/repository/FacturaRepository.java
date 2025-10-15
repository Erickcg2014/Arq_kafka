package com.taller7arqui.facturacion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.taller7arqui.facturacion.entity.FacturaEntity;

@Repository
public interface FacturaRepository extends JpaRepository<FacturaEntity, Long> {}
