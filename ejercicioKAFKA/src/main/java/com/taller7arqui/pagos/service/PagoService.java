package com.taller7arqui.pagos.service;

import com.taller7arqui.inventario.DTO.*;
import com.taller7arqui.inventario.entity.*;
import com.taller7arqui.pagos.repository.PagoRepository;
import com.taller7arqui.inventario.repository.InventarioRepository;
import com.taller7arqui.facturacion.repository.FacturaRepository;
import com.taller7arqui.pagos.entity.PagoEntity;
import com.taller7arqui.pagos.DTO.PagoRequest;
import com.taller7arqui.facturacion.entity.FacturaEntity;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PagoService {

    private final InventarioRepository inventarioRepository;
    private final PagoRepository pagoRepository;
    private final FacturaRepository facturacionRepository;

    public PagoService(InventarioRepository inventarioRepository,
                       PagoRepository pagoRepository,
                       FacturaRepository facturacionRepository) {
        this.inventarioRepository = inventarioRepository;
        this.pagoRepository = pagoRepository;
        this.facturacionRepository = facturacionRepository;
    }

    public List<PagoEntity> obtenerPagos() {
        return pagoRepository.findAll();
    }

    @Transactional
    public void procesarPago(PagoRequest request) {
        // 1. Validar y actualizar inventario
        for (ProductoCompra producto : request.getProductos()) {
            InventarioEntity inventario = inventarioRepository.findById(producto.getProductoId())
                    .orElseThrow(() -> new RuntimeException(
                            "Producto con ID " + producto.getProductoId() + " no encontrado."));

            if (inventario.getCantidad() < producto.getCantidad()) {
                throw new RuntimeException("Stock insuficiente para el producto: " + inventario.getTituloProductos());
            }

            // Descontar stock
            inventario.setCantidad(inventario.getCantidad() - producto.getCantidad());
            inventarioRepository.save(inventario);
        }

        // 2. Registrar pago
        PagoEntity pago = new PagoEntity();
        pago.setCliente(request.getCliente());
        pago.setMonto(request.getMonto());
        pago.setMetodoPago(request.getMetodoPago());
        pago.setFecha(LocalDateTime.now());
        pagoRepository.save(pago);

        // 3. Generar facturas asociadas al pago
        for (ProductoCompra producto : request.getProductos()) {
            FacturaEntity factura = new FacturaEntity();
            factura.setCliente(request.getCliente());
            factura.setDescripcionPedido("Compra de producto ID: " + producto.getProductoId());
            factura.setProductoId(producto.getProductoId());
            factura.setCantidad(producto.getCantidad());

            double precio = inventarioRepository.findById(producto.getProductoId())
                    .map(InventarioEntity::getPrecio)
                    .orElse(0.0);
            factura.setTotal(producto.getCantidad() * precio);
            factura.setFecha(LocalDateTime.now());
            facturacionRepository.save(factura);
        }
    }
}
