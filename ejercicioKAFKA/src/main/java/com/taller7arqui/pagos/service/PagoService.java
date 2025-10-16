package com.taller7arqui.pagos.service;

import com.taller7arqui.inventario.DTO.ProductoCompra;
import com.taller7arqui.inventario.entity.InventarioEntity;
import com.taller7arqui.facturacion.entity.FacturaEntity;
import com.taller7arqui.pagos.DTO.CustomerNotification;
import com.taller7arqui.pagos.DTO.PagoRequest;
import com.taller7arqui.pagos.DTO.ProviderNotification;
import com.taller7arqui.pagos.entity.PagoEntity;
import com.taller7arqui.inventario.repository.InventarioRepository;
import com.taller7arqui.pagos.repository.PagoRepository;
import com.taller7arqui.facturacion.repository.FacturaRepository;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class PagoService {

    private final InventarioRepository inventarioRepository;
    private final PagoRepository pagoRepository;
    private final FacturaRepository facturacionRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public PagoService(
            InventarioRepository inventarioRepository,
            PagoRepository pagoRepository,
            FacturaRepository facturacionRepository,
            KafkaTemplate<String, Object> kafkaTemplate
    ) {
        this.inventarioRepository = inventarioRepository;
        this.pagoRepository = pagoRepository;
        this.facturacionRepository = facturacionRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    public List<PagoEntity> obtenerPagos() {
        return pagoRepository.findAll();
    }

    @Transactional
    public void procesarPago(PagoRequest request) {

        // 1️⃣ Validar y actualizar inventario
        Map<Long, InventarioEntity> productosInventario = new HashMap<>();
        Map<Long, List<ProductoCompra>> productosPorProveedor = new HashMap<>();

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

            // Guardar para reutilizar después en las facturas
            productosInventario.put(producto.getProductoId(), inventario);

            // Agrupar por proveedor
            Long proveedorId = inventario.getProveedorId(); // <-- asegúrate que esta columna existe en InventarioEntity
            productosPorProveedor
                    .computeIfAbsent(proveedorId, k -> new ArrayList<>())
                    .add(producto);
        }

        // 2️⃣ Registrar pago general
        PagoEntity pago = new PagoEntity();
        pago.setCliente(request.getCliente());
        pago.setEmail(request.getEmail()); // obligatorio
        pago.setMetodoPago(request.getMetodoPago());
        pago.setMonto(request.getMonto());
        pago.setMoneda(request.getMoneda() != null ? request.getMoneda() : "COP");
        pago.setDireccionEnvio(request.getDireccionEnvio());
        pago.setUsuarioId(request.getUsuarioId());
        pago.setFecha(LocalDateTime.now());
        pago.setEstado("PAGADO");

        pagoRepository.save(pago);

        // 3️⃣ Generar facturas por producto
        List<Map<String, Object>> detalleProductos = new ArrayList<>();

        for (ProductoCompra producto : request.getProductos()) {
            InventarioEntity inventario = productosInventario.get(producto.getProductoId());
            FacturaEntity factura = new FacturaEntity();

            factura.setCliente(request.getCliente());
            factura.setDescripcionPedido("Compra de producto ID: " + producto.getProductoId());
            factura.setProductoId(producto.getProductoId());
            factura.setCantidad(producto.getCantidad());
            factura.setProveedorId(inventario.getProveedorId());

            double precio = inventarioRepository.findById(producto.getProductoId())
                    .map(InventarioEntity::getPrecio)
                    .orElse(0.0);

            factura.setTotal(producto.getCantidad() * precio);
            factura.setFecha(LocalDateTime.now());
            facturacionRepository.save(factura);

            Map<String, Object> item = new HashMap<>();
            item.put("productoId", producto.getProductoId());
            item.put("cantidad", producto.getCantidad());
            item.put("precioUnitario", precio);
            item.put("total", factura.getTotal());
            detalleProductos.add(item);
        }

        // 4️⃣ Enviar mensaje a cliente (correo)
        CustomerNotification notificacionCliente = new CustomerNotification();
        notificacionCliente.setEmail(request.getEmail());
        notificacionCliente.setCliente(request.getCliente());
        notificacionCliente.setTotalPago(request.getMonto());
        notificacionCliente.setMoneda(request.getMoneda());
        notificacionCliente.setProductos(detalleProductos);
        notificacionCliente.setMensaje("Gracias por tu compra. Tu pedido ha sido procesado con éxito.");

        kafkaTemplate.send("notificacion-mail", request.getEmail(), notificacionCliente);

        // 5️⃣ Enviar mensaje a proveedores agrupados
        for (Map.Entry<Long, List<ProductoCompra>> entry : productosPorProveedor.entrySet()) {
            Long proveedorId = entry.getKey();
            List<ProductoCompra> productosProveedor = entry.getValue();

            ProviderNotification notificacionProveedor = new ProviderNotification();
            notificacionProveedor.setProveedorId(proveedorId);
            notificacionProveedor.setProductos(productosProveedor);
            notificacionProveedor.setFecha(LocalDateTime.now());
            notificacionProveedor.setMensaje("Se ha registrado una nueva compra de tus productos.");

            kafkaTemplate.send("proveedor-notifications", String.valueOf(proveedorId), notificacionProveedor);
        }

    }
}