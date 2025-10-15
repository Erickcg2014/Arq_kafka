package com.taller7arqui.pagos.DTO;

import java.time.LocalDateTime;
import java.util.List;
import com.taller7arqui.inventario.DTO.ProductoCompra;

public class ProviderNotification {
    private Long proveedorId;
    private List<ProductoCompra> productos;
    private LocalDateTime fecha;
    private String mensaje;

    // Getters y setters
    public Long getProveedorId() { return proveedorId; }
    public void setProveedorId(Long proveedorId) { this.proveedorId = proveedorId; }

    public List<ProductoCompra> getProductos() { return productos; }
    public void setProductos(List<ProductoCompra> productos) { this.productos = productos; }

    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }

    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }
}
