package com.taller7arqui.Servicio;

import com.taller7arqui.inventario.entity.Producto;
import java.util.List;
import java.util.Optional;

public interface IService {
    List<Producto> getAllProductos();
    Optional<Producto> getProductoById(Long id);
    Producto saveProducto(Producto producto);
    void deleteProducto(Long id);
    Producto updateProducto(Long id, Producto producto);
    List<Producto> findAll();
    int count();
}