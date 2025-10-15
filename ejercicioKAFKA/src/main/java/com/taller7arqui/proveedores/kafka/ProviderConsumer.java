package com.taller7arqui.proveedores.kafka;

import com.taller7arqui.pagos.DTO.ProviderNotification;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ProviderConsumer {

    @KafkaListener(
        topics = "proveedor-notifications",
        groupId = "proveedor-group",
        containerFactory = "proveedorKafkaListenerContainerFactory"
    )
    public void escucharNotificacionProveedor(ProviderNotification notificacion) {
        System.out.println("📦 Notificación recibida para proveedor ID: " + notificacion.getProveedorId());
        System.out.println("Mensaje: " + notificacion.getMensaje());
        
        if (notificacion.getProductos() != null && !notificacion.getProductos().isEmpty()) {
            System.out.println("Productos incluidos en la notificación:");
            notificacion.getProductos().forEach(p -> {
                System.out.println(" - Producto ID: " + p.getProductoId() + ", cantidad: " + p.getCantidad());
            });
        } else {
            System.out.println("⚠️ No se recibieron productos asociados en la notificación.");
        }
    }
}
