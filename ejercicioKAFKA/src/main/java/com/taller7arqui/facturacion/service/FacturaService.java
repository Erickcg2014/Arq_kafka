package com.taller7arqui.facturacion.service;

import com.taller7arqui.facturacion.entity.FacturaEntity;
import com.taller7arqui.facturacion.repository.FacturaRepository;

import org.springframework.stereotype.Service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class FacturaService {

    @Autowired
    private FacturaRepository facturaRepository;


    public List<FacturaEntity> obtenerFacturas() {
        return facturaRepository.findAll();
    }



}
