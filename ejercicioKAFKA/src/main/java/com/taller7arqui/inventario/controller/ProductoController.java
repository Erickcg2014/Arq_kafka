package com.taller7arqui.inventario.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.taller7arqui.inventario.entity.Producto;
import com.taller7arqui.inventario.service.ProductoService;

@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:8080"})
@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    // Verificar conexión
    @GetMapping("/test")
    public String verificarConexion() {
        return "Conexión OK con backend!";
    }

    // Listar productos
    @GetMapping
    public List<Producto> obtenerProductos() {
        return productoService.findAll();
    }

    // Crear un nuevo producto
    @PostMapping
    public Producto crearProducto(@RequestBody Producto producto) {
        return productoService.saveProducto(producto);
    }
}
