package com.taller7arqui.inventario.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "inventario")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "titulo_productos", nullable = false)
    private String tituloProductos;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;

    @Column(name = "categoria")
    private String categoria;

    @Column(name = "precio", nullable = false)
    private Double precio;

    @Column(name = "proveedor_id", nullable = false)
    private Long proveedorId;

    // Getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTituloProductos() { return tituloProductos; }
    public void setTituloProductos(String tituloProductos) { this.tituloProductos = tituloProductos; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public Double getPrecio() { return precio; }
    public void setPrecio(Double precio) { this.precio = precio; }

    public Long getProveedorId() { return proveedorId; }
    public void setProveedorId(Long proveedorId) { this.proveedorId = proveedorId; }

}