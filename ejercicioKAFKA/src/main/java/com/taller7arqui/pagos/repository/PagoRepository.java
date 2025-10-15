package com.taller7arqui.pagos.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.taller7arqui.pagos.entity.PagoEntity;

@Repository
public interface PagoRepository extends JpaRepository<PagoEntity, Long> {}
