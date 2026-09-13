-- ============================================================
-- OptiGest ERP - Modulo Web de Usuarios (Servlets + JSP)
-- Script de creacion de base de datos y tabla para PostgreSQL
-- Evidencia GA7-220501096-AA2-EV02
--
-- Nota: si ya ejecutaste este mismo script para la evidencia AA2-EV01
-- (modulo de escritorio JDBC), la base de datos "optigest_db" y la tabla
-- "usuarios" ya existen y son reutilizadas por este modulo web sin
-- necesidad de volver a correr este script.
--
-- Como ejecutarlo (PowerShell o CMD, con PostgreSQL instalado):
--   psql -U postgres -f sql/optigest_usuarios.sql
-- Te pedira la contrasenia del usuario "postgres".
-- ============================================================

CREATE DATABASE optigest_db;

\c optigest_db

CREATE TABLE IF NOT EXISTS usuarios (
  id       SERIAL PRIMARY KEY,
  nombre   VARCHAR(100) NOT NULL,
  usuario  VARCHAR(50)  NOT NULL UNIQUE,
  correo   VARCHAR(100) NOT NULL UNIQUE,
  clave    VARCHAR(255) NOT NULL,
  celular  VARCHAR(20),
  rol      VARCHAR(50)  NOT NULL,
  estado   VARCHAR(20)  NOT NULL DEFAULT 'Activo'
);

-- Datos de ejemplo. La columna "clave" guarda el hash SHA-256 de la
-- contrasenia (nunca texto plano), igual que lo hace la aplicacion Java.
-- Contrasenias en texto plano de referencia: admin123, lm123, ch123, ps123, dr123, ag123
INSERT INTO usuarios (nombre, usuario, correo, clave, celular, rol, estado) VALUES
  ('Isaac Ospino',     'admin',     'admin@optigest.co',     '240be518fabd2724ddb6f04eeb1da5967448d7e831c08c8fa822809f74c720a9', '3001112233', 'Administrador',   'Activo'),
  ('Laura Martinez',   'lmartinez', 'lmartinez@optigest.co', '89f47494d297f9d32d560ed935ab7628ce85107354c58f48fe3208edd663cb3b', '3012223344', 'Vendedor',        'Activo'),
  ('Carlos Herrera',   'cherrera',  'cherrera@optigest.co',  '67d4c265e628c2223f2c5687d9a9d7008251449de410cf9c8970bb1a7bb0eb72', '3023334455', 'Jefe de taller',  'Activo'),
  ('Pedro Salcedo',    'psalcedo',  'psalcedo@optigest.co',  'd82cff8c766e6f52e11516f3c732c467e3370590097876e311086c3b839b3b5f', '3034445566', 'Jefe de compras', 'Inactivo'),
  ('Diana Ruiz',       'druiz',     'druiz@optigest.co',     '05dd70aadc6f102a1b10a067f22bbe88d6ac2f4838fdccc789a21e8ac88e434f', '3045556677', 'Bodega',          'Activo'),
  ('Ana Gomez',        'agomez',    'agomez@optigest.co',    '3a5974451e543318073bd4779677068dce9ef4746b4bd3ed40a6edc2989b58e7', '3056667788', 'Contador',        'Activo')
ON CONFLICT (usuario) DO NOTHING;
