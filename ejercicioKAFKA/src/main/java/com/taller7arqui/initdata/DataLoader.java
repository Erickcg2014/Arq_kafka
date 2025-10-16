package com.taller7arqui.initdata;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import com.taller7arqui.inventario.entity.Producto;
import com.taller7arqui.inventario.repository.ProductoRepository;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initDatabase(ProductoRepository productoRepository) {
        return args -> {
            if (productoRepository.count() == 0) {
                int conteo = 0;
                try (BufferedReader reader = new BufferedReader(
                        new InputStreamReader(
                                getClass().getResourceAsStream("/data_inventario.txt"),
                                StandardCharsets.UTF_8))) {

                    String line;
                    int numeroLinea = 0;
                    
                    while ((line = reader.readLine()) != null) {
                        numeroLinea++;
                        line = line.trim();
                        
                        // Salta líneas vacías y comentarios
                        if (line.isEmpty() || line.startsWith("#")) {
                            continue;
                        }

                        String[] parts = line.split("\\|");
                        
                        if (parts.length != 6) {
                            System.err.println("⚠️ Línea " + numeroLinea + " ignorada: se esperan 6 campos, se encontraron " + parts.length);
                            continue;
                        }

                        try {
                            Producto p = new Producto();
                            p.setTituloProductos(parts[0].trim());
                            p.setDescripcion(parts[1].trim());
                            p.setCantidad(Integer.parseInt(parts[2].trim()));
                            p.setCategoria(parts[3].trim());
                            p.setPrecio(Double.parseDouble(parts[4].trim()));
                            p.setProveedorId(Long.parseLong(parts[5].trim()));
                            
                            productoRepository.save(p);
                            conteo++;
                            System.out.println("✅ [" + conteo + "] " + p.getTituloProductos() + " | Proveedor: " + p.getProveedorId());
                            
                        } catch (NumberFormatException e) {
                            System.err.println("❌ Línea " + numeroLinea + " - Error al parsear valores numéricos:");
                            System.err.println("   Cantidad: '" + parts[2].trim() + "' | Precio: '" + parts[4].trim() + "' | Proveedor ID: '" + parts[5].trim() + "'");
                        }
                    }
                    
                    System.out.println("\n✅ Carga completada: " + conteo + " productos insertados correctamente\n");
                    
                } catch (NullPointerException e) {
                    System.err.println("❌ ERROR: Archivo 'data_inventario.txt' no encontrado en src/main/resources/");
                } catch (Exception e) {
                    System.err.println("❌ ERROR durante la carga de datos: " + e.getMessage());
                    e.printStackTrace();
                }
            } else {
                System.out.println("📊 Base de datos ya contiene " + productoRepository.count() + " productos. Saltando carga inicial.");
            }
        };
    }
}