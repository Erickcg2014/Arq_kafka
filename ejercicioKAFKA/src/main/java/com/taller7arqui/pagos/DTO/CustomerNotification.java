package com.taller7arqui.pagos.DTO;

import java.util.List;
import java.util.Map;

public class CustomerNotification {
    private String email;
    private String cliente;
    private Double totalPago;
    private String moneda;
    private List<Map<String, Object>> productos;
    private String mensaje;

    // Getters y setters
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getCliente() { return cliente; }
    public void setCliente(String cliente) { this.cliente = cliente; }

    public Double getTotalPago() { return totalPago; }
    public void setTotalPago(Double totalPago) { this.totalPago = totalPago; }

    public String getMoneda() { return moneda; }
    public void setMoneda(String moneda) { this.moneda = moneda; }

    public List<Map<String, Object>> getProductos() { return productos; }
    public void setProductos(List<Map<String, Object>> productos) { this.productos = productos; }

    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }
}
