package com.taller7arqui.pagos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.List;
import com.taller7arqui.pagos.service.PagoService;
import com.taller7arqui.pagos.DTO.PagoRequest;
import com.taller7arqui.pagos.entity.PagoEntity;

@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:8080"})
@RestController
@RequestMapping("/api/pagos")
public class PagoController {

    @Autowired
    private PagoService pagoService;

    // Listar todos los pagos
    @GetMapping
    public List<PagoEntity> obtenerPagos() {
        return pagoService.obtenerPagos();
    }

    // Procesar un pago completo (inventario + pago + facturación)
    @PostMapping
    public ResponseEntity<String> procesarPago(@RequestBody PagoRequest request) {
        try {
            pagoService.procesarPago(request);
            return ResponseEntity.ok("Pago procesado correctamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error en el proceso de pago: " + e.getMessage());
        }
    }
}
