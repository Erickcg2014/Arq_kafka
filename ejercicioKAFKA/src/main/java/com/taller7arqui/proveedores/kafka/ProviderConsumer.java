package com.taller7arqui.proveedores.kafka;

import com.taller7arqui.pagos.DTO.ProviderNotification;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ProviderConsumer {

    @KafkaListener(topics = "proveedor-notifications", groupId = "proveedor-group", containerFactory = "kafkaListenerContainerFactory")
    public void escucharNotificacionProveedor(ProviderNotification notificacion) {
        System.out.println("📦 Notificación recibida para proveedor ID: " + notificacion.getProveedorId());
        System.out.println("Mensaje: " + notificacion.getMensaje());
        notificacion.getProductos().forEach(p -> {
            System.out.println(" - Producto ID: " + p.getProductoId() + ", cantidad: " + p.getCantidad());
        });
    }
}
