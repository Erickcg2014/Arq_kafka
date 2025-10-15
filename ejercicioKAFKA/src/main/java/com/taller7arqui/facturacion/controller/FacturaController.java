package com.taller7arqui.facturacion.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.taller7arqui.facturacion.entity.FacturaEntity;
import com.taller7arqui.facturacion.service.FacturaService;

@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:8080"})
@RestController
@RequestMapping("/api/facturacion")
public class FacturaController {

    @Autowired
    private FacturaService facturaService;

    // Listar todas las facturas
    @GetMapping
    public List<FacturaEntity> obtenerFacturas() {
        return facturaService.obtenerFacturas();
    }
}
