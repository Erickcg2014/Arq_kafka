package com.taller7arqui.pagos.DTO;

import com.taller7arqui.inventario.DTO.ProductoCompra;
import java.util.List;

public class PagoRequest {

    private String cliente;
    private String email; 
    private String metodoPago;
    private Double monto;
    private String moneda;
    private Long usuarioId;
    private String idTransaccion;
    private String direccionEnvio;
    private String ciudadEnvio;
    private String fechaPago;
    private List<ProductoCompra> productos;

    // Getters y setters
    public String getCliente() { return cliente; }
    public void setCliente(String cliente) { this.cliente = cliente; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getMetodoPago() { return metodoPago; }
    public void setMetodoPago(String metodoPago) { this.metodoPago = metodoPago; }

    public Double getMonto() { return monto; }
    public void setMonto(Double monto) { this.monto = monto; }

    public String getMoneda() { return moneda; }
    public void setMoneda(String moneda) { this.moneda = moneda; }

    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }

    public String getIdTransaccion() { return idTransaccion; }
    public void setIdTransaccion(String idTransaccion) { this.idTransaccion = idTransaccion; }

    public String getDireccionEnvio() { return direccionEnvio; }
    public void setDireccionEnvio(String direccionEnvio) { this.direccionEnvio = direccionEnvio; }

    public String getCiudadEnvio() { return ciudadEnvio; }
    public void setCiudadEnvio(String ciudadEnvio) { this.ciudadEnvio = ciudadEnvio; }

    public String getFechaPago() { return fechaPago; }
    public void setFechaPago(String fechaPago) { this.fechaPago = fechaPago; }

    public List<ProductoCompra> getProductos() { return productos; }
    public void setProductos(List<ProductoCompra> productos) { this.productos = productos; }
}
