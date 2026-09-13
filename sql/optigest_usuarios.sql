-- ============================================================
-- OptiGest ERP - Modulo Web de Usuarios (Servlets + JSP)
-- Script de creacion de base de datos y tabla para PostgreSQL
-- Evidencia GA7-220501096-AA2-EV02
--
-- La tabla sigue el diseno oficial de base de datos del proyecto
-- (evidencia GA6-220501096-AA2-EV02/EV03: optigest_bd.usuario),
-- adaptado de MySQL a PostgreSQL.
--
-- Nota: si ya ejecutaste este mismo script para la evidencia AA2-EV01
-- (modulo de escritorio JDBC), la base de datos "optigest_bd" y la tabla
-- "usuario" ya existen y son reutilizadas por este modulo web sin
-- necesidad de volver a correr este script.
--
-- Como ejecutarlo (PowerShell o CMD, con PostgreSQL instalado):
--   psql -U postgres -f sql/optigest_usuarios.sql
-- Te pedira la contrasenia del usuario "postgres".
-- ============================================================

CREATE DATABASE optigest_bd;

\c optigest_bd

CREATE TABLE IF NOT EXISTS usuario (
  id_usuario     SERIAL PRIMARY KEY,
  nombre         VARCHAR(100) NOT NULL,
  email          VARCHAR(100) NOT NULL UNIQUE,
  password       VARCHAR(255) NOT NULL,
  rol            VARCHAR(50)  NOT NULL,
  estado         BOOLEAN      NOT NULL DEFAULT TRUE,
  fecha_creacion TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Datos de ejemplo. La columna "password" guarda el hash SHA-256 de la
-- contrasenia (nunca texto plano), igual que lo hace la aplicacion Java.
-- Contrasenias en texto plano de referencia: admin123, lm123, ch123, ps123, dr123, ag123
INSERT INTO usuario (nombre, email, password, rol, estado) VALUES
  ('Isaac Ospino',   'admin@optigest.co',     '240be518fabd2724ddb6f04eeb1da5967448d7e831c08c8fa822809f74c720a9', 'Administrador',   TRUE),
  ('Laura Martinez', 'lmartinez@optigest.co', '89f47494d297f9d32d560ed935ab7628ce85107354c58f48fe3208edd663cb3b', 'Vendedor',        TRUE),
  ('Carlos Herrera', 'cherrera@optigest.co',  '67d4c265e628c2223f2c5687d9a9d7008251449de410cf9c8970bb1a7bb0eb72', 'Jefe de taller',  TRUE),
  ('Pedro Salcedo',  'psalcedo@optigest.co',  'd82cff8c766e6f52e11516f3c732c467e3370590097876e311086c3b839b3b5f', 'Jefe de compras', FALSE),
  ('Diana Ruiz',     'druiz@optigest.co',     '05dd70aadc6f102a1b10a067f22bbe88d6ac2f4838fdccc789a21e8ac88e434f', 'Bodega',          TRUE),
  ('Ana Gomez',      'agomez@optigest.co',    '3a5974451e543318073bd4779677068dce9ef4746b4bd3ed40a6edc2989b58e7', 'Contador',        TRUE)
ON CONFLICT (email) DO NOTHING;
