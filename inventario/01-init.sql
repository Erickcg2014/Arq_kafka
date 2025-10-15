\connect inventariodb;

-- ===================================
-- TABLA PROVEEDORES
-- ===================================
-- CREATE TABLE IF NOT EXISTS proveedores (
--     id SERIAL PRIMARY KEY,
--     nombre VARCHAR(150) NOT NULL,
--     contacto VARCHAR(150),
--     telefono VARCHAR(50),
--     direccion VARCHAR(255),
--     correo VARCHAR(150),
--     activo BOOLEAN DEFAULT TRUE
-- );

-- ===================================
-- TABLA INVENTARIO
-- ===================================
CREATE TABLE IF NOT EXISTS inventario (
    id SERIAL PRIMARY KEY,
    titulo_productos VARCHAR(255) NOT NULL,
    descripcion TEXT,
    cantidad INTEGER NOT NULL DEFAULT 0,
    categoria VARCHAR(100),
    precio NUMERIC(10,2) NOT NULL DEFAULT 0,
    proveedor_id INTEGER,
    -- CONSTRAINT fk_proveedor
    --     FOREIGN KEY (proveedor_id)
    --     REFERENCES proveedores(id)
    --     ON UPDATE CASCADE
    --     ON DELETE SET NULL
);
