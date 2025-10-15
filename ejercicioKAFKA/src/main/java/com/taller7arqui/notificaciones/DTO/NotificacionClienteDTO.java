package com.taller7arqui.notificaciones.DTO;

import java.util.List;

public class NotificacionClienteDTO {
    private String email;
    private String cliente;
    private String direccionEnvio;
    private Double totalPago;
    private List<DetalleProductoDTO> productos;

    // Getters y setters
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getCliente() { return cliente; }
    public void setCliente(String cliente) { this.cliente = cliente; }

    public String getDireccionEnvio() { return direccionEnvio; }
    public void setDireccionEnvio(String direccionEnvio) { this.direccionEnvio = direccionEnvio; }

    public Double getTotalPago() { return totalPago; }
    public void setTotalPago(Double totalPago) { this.totalPago = totalPago; }

    public List<DetalleProductoDTO> getProductos() { return productos; }
    public void setProductos(List<DetalleProductoDTO> productos) { this.productos = productos; }
}
