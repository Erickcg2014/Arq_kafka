--base de datos y tabla pagos
\connect pagosdb;

CREATE TABLE IF NOT EXISTS pagos (
    id SERIAL PRIMARY KEY,
    cliente VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    metodo_pago VARCHAR(50) NOT NULL,
    monto NUMERIC(10,2) NOT NULL,
    moneda VARCHAR(10) DEFAULT 'COP',
    direccion_envio VARCHAR(255),
    usuario_id BIGINT,
    estado VARCHAR(30) DEFAULT 'PAGADO',
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

