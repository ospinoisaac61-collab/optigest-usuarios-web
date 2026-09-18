-- ============================================================
-- OptiGest ERP - Modulos adicionales: Producto, Proveedor, Compra
-- Adaptado de MySQL a PostgreSQL a partir del diseno oficial
-- (evidencia GA6-220501096-AA2-EV02/EV03).
--
-- Requiere que la tabla "usuario" ya exista (sql/optigest_usuarios.sql).
--
-- Como ejecutarlo:
--   psql -U postgres -d optigest_bd -f sql/optigest_modulos_adicionales.sql
-- ============================================================

\c optigest_bd

CREATE TABLE IF NOT EXISTS producto (
  id_producto     SERIAL PRIMARY KEY,
  nombre          VARCHAR(100) NOT NULL,
  tipo            VARCHAR(50)  NOT NULL,
  precio_unitario DECIMAL(10,2) NOT NULL,
  stock_actual    INT NOT NULL DEFAULT 0
);
CREATE INDEX IF NOT EXISTS idx_producto_tipo ON producto (tipo);

CREATE TABLE IF NOT EXISTS proveedor (
  id_proveedor   SERIAL PRIMARY KEY,
  nombre         VARCHAR(100) NOT NULL,
  telefono       VARCHAR(20),
  email          VARCHAR(100) UNIQUE,
  direccion      VARCHAR(200),
  fecha_registro DATE NOT NULL DEFAULT CURRENT_DATE
);

CREATE TABLE IF NOT EXISTS compra (
  id_compra    SERIAL PRIMARY KEY,
  fecha_compra TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  total        DECIMAL(12,2) NOT NULL,
  estado       VARCHAR(30) NOT NULL DEFAULT 'PENDIENTE',
  id_proveedor INT NOT NULL REFERENCES proveedor (id_proveedor) ON DELETE RESTRICT ON UPDATE CASCADE,
  id_usuario   INT NOT NULL REFERENCES usuario (id_usuario) ON DELETE RESTRICT ON UPDATE CASCADE
);

-- Datos de ejemplo
INSERT INTO producto (nombre, tipo, precio_unitario, stock_actual) VALUES
  ('Perfil de aluminio 10x5 cm', 'Perfil',  85000.00, 120),
  ('Vidrio templado 6 mm',       'Vidrio', 120000.00, 60),
  ('Bisagra reforzada',          'Herraje',  15000.00, 300)
ON CONFLICT DO NOTHING;

INSERT INTO proveedor (nombre, telefono, email, direccion) VALUES
  ('Aluminios del Caribe S.A.',   '3016789234', 'jtorres@alucaribe.co', 'Cra 45 #12-30, Barranquilla'),
  ('Vidrex Colombia Ltda.',       '3124567890', 'scano@vidrex.co',      'Calle 8 #22-15, Cartagena'),
  ('Herrajes & Accesorios SAS',   '3209876543', 'mrincon@herrajes.co',  'Av. Circunvalar #5-40, Santa Marta')
ON CONFLICT (email) DO NOTHING;
