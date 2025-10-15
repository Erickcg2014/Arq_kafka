package com.taller7arqui.notificaciones.DTO;

import java.util.List;

public class NotificacionProveedorDTO {
    private Long proveedorId;
    private String nombreProveedor;
    private List<DetalleProductoDTO> productos;
    private String cliente;
    private String direccionEnvio;

    // Getters y setters
    public Long getProveedorId() { return proveedorId; }
    public void setProveedorId(Long proveedorId) { this.proveedorId = proveedorId; }

    public String getNombreProveedor() { return nombreProveedor; }
    public void setNombreProveedor(String nombreProveedor) { this.nombreProveedor = nombreProveedor; }

    public List<DetalleProductoDTO> getProductos() { return productos; }
    public void setProductos(List<DetalleProductoDTO> productos) { this.productos = productos; }

    public String getCliente() { return cliente; }
    public void setCliente(String cliente) { this.cliente = cliente; }

    public String getDireccionEnvio() { return direccionEnvio; }
    public void setDireccionEnvio(String direccionEnvio) { this.direccionEnvio = direccionEnvio; }
}
