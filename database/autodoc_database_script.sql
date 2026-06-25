-- ============================================================
--  AUTODOC – CompMarketPerú
--  Script de Creación de Base de Datos MySQL
--  Herramienta: MySQL Workbench 8.x
--  Versión: 1.0  |  Fecha: 2026-01-01
-- ============================================================

-- ------------------------------------------------------------
-- 1. CREAR Y SELECCIONAR LA BASE DE DATOS
-- ------------------------------------------------------------
DROP DATABASE IF EXISTS CompMarketPeruDB;
CREATE DATABASE CompMarketPeruDB
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE CompMarketPeruDB;

-- ------------------------------------------------------------
-- 2. TABLA: ROLES
--    Define los perfiles de acceso del sistema (ADMIN / VENDEDOR)
-- ------------------------------------------------------------
CREATE TABLE ROLES (
    id_rol        INT          NOT NULL AUTO_INCREMENT,
    nombre_rol    VARCHAR(50)  NOT NULL UNIQUE,
    descripcion   VARCHAR(150)     NULL,
    estado        TINYINT(1)   NOT NULL DEFAULT 1,     -- 1=activo, 0=inactivo

    -- Auditoría
    create_user   VARCHAR(80)      NULL,
    create_date   DATETIME         NULL DEFAULT CURRENT_TIMESTAMP,
    updated_user  VARCHAR(80)      NULL,
    updated_date  DATETIME         NULL ON UPDATE CURRENT_TIMESTAMP,
    deleted_user  VARCHAR(80)      NULL,
    deleted_date  DATETIME         NULL,

    CONSTRAINT pk_roles PRIMARY KEY (id_rol)
) ENGINE=InnoDB;

-- ------------------------------------------------------------
-- 3. TABLA: PERSONAS
--    Datos personales compartidos por los usuarios del sistema
-- ------------------------------------------------------------
CREATE TABLE PERSONAS (
    id_persona    INT          NOT NULL AUTO_INCREMENT,
    apellidos     VARCHAR(100) NOT NULL,
    nombres       VARCHAR(100) NOT NULL,
    dni           VARCHAR(8)       NULL UNIQUE,
    email         VARCHAR(120)     NULL UNIQUE,
    telefono      VARCHAR(15)      NULL,

    -- Auditoría
    create_user   VARCHAR(80)      NULL,
    create_date   DATETIME         NULL DEFAULT CURRENT_TIMESTAMP,
    updated_user  VARCHAR(80)      NULL,
    updated_date  DATETIME         NULL ON UPDATE CURRENT_TIMESTAMP,
    deleted_user  VARCHAR(80)      NULL,
    deleted_date  DATETIME         NULL,

    CONSTRAINT pk_personas PRIMARY KEY (id_persona)
) ENGINE=InnoDB;

-- ------------------------------------------------------------
-- 4. TABLA: USUARIOS
--    Credenciales de acceso vinculadas a una persona y un rol
-- ------------------------------------------------------------
CREATE TABLE USUARIOS (
    id_user       INT          NOT NULL AUTO_INCREMENT,
    username      VARCHAR(80)  NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,             -- BCrypt hash
    estado        TINYINT(1)   NOT NULL DEFAULT 1,
    id_persona    INT          NOT NULL,
    id_usRol      INT          NOT NULL,             -- FK → ROLES

    -- Auditoría
    create_user   VARCHAR(80)      NULL,
    create_date   DATETIME         NULL DEFAULT CURRENT_TIMESTAMP,
    updated_user  VARCHAR(80)      NULL,
    updated_date  DATETIME         NULL ON UPDATE CURRENT_TIMESTAMP,
    deleted_user  VARCHAR(80)      NULL,
    deleted_date  DATETIME         NULL,

    CONSTRAINT pk_usuarios   PRIMARY KEY (id_user),
    CONSTRAINT fk_usu_rol    FOREIGN KEY (id_usRol)   REFERENCES ROLES(id_rol),
    CONSTRAINT fk_usu_per    FOREIGN KEY (id_persona)  REFERENCES PERSONAS(id_persona)
) ENGINE=InnoDB;

-- ------------------------------------------------------------
-- 5. TABLA: CATEGORIAS
--    Clasificación del catálogo de productos tecnológicos
-- ------------------------------------------------------------
CREATE TABLE CATEGORIAS (
    id_categoria  INT          NOT NULL AUTO_INCREMENT,
    nombre        VARCHAR(100) NOT NULL UNIQUE,
    descripcion   VARCHAR(200)     NULL,
    estado        TINYINT(1)   NOT NULL DEFAULT 1,

    -- Auditoría
    create_user   VARCHAR(80)      NULL,
    create_date   DATETIME         NULL DEFAULT CURRENT_TIMESTAMP,
    updated_user  VARCHAR(80)      NULL,
    updated_date  DATETIME         NULL ON UPDATE CURRENT_TIMESTAMP,
    deleted_user  VARCHAR(80)      NULL,
    deleted_date  DATETIME         NULL,

    CONSTRAINT pk_categorias PRIMARY KEY (id_categoria)
) ENGINE=InnoDB;

-- ------------------------------------------------------------
-- 6. TABLA: PRODUCTOS
--    Catálogo completo con stock, precio e imagen
-- ------------------------------------------------------------
CREATE TABLE PRODUCTOS (
    id_producto   INT            NOT NULL AUTO_INCREMENT,
    nombre        VARCHAR(150)   NOT NULL,
    marca         VARCHAR(80)    NOT NULL,
    modelo        VARCHAR(80)        NULL,
    descripcion   TEXT               NULL,
    precio_venta  DECIMAL(10, 2) NOT NULL,
    stock         INT            NOT NULL DEFAULT 0,
    stock_minimo  INT            NOT NULL DEFAULT 5,
    imagen_url    VARCHAR(255)       NULL,
    estado        TINYINT(1)     NOT NULL DEFAULT 1,
    id_categoria  INT            NOT NULL,            -- FK → CATEGORIAS

    -- Auditoría
    create_user   VARCHAR(80)        NULL,
    create_date   DATETIME           NULL DEFAULT CURRENT_TIMESTAMP,
    updated_user  VARCHAR(80)        NULL,
    updated_date  DATETIME           NULL ON UPDATE CURRENT_TIMESTAMP,
    deleted_user  VARCHAR(80)        NULL,
    deleted_date  DATETIME           NULL,

    CONSTRAINT pk_productos  PRIMARY KEY (id_producto),
    CONSTRAINT fk_pro_cat    FOREIGN KEY (id_categoria) REFERENCES CATEGORIAS(id_categoria)
) ENGINE=InnoDB;

-- ------------------------------------------------------------
-- 7. TABLA: CLIENTES
--    Base de datos de clientes con DNI o RUC
-- ------------------------------------------------------------
CREATE TABLE CLIENTES (
    id_cliente    INT          NOT NULL AUTO_INCREMENT,
    apellidos     VARCHAR(100) NOT NULL,
    nombres       VARCHAR(100) NOT NULL,
    tipo_doc      ENUM('DNI','RUC','CE','PASAPORTE') NOT NULL DEFAULT 'DNI',
    nro_doc       VARCHAR(20)  NOT NULL UNIQUE,
    email         VARCHAR(120)     NULL,
    telefono      VARCHAR(15)      NULL,
    direccion     VARCHAR(200)     NULL,
    estado        TINYINT(1)   NOT NULL DEFAULT 1,

    -- Auditoría
    create_user   VARCHAR(80)      NULL,
    create_date   DATETIME         NULL DEFAULT CURRENT_TIMESTAMP,
    updated_user  VARCHAR(80)      NULL,
    updated_date  DATETIME         NULL ON UPDATE CURRENT_TIMESTAMP,
    deleted_user  VARCHAR(80)      NULL,
    deleted_date  DATETIME         NULL,

    CONSTRAINT pk_clientes PRIMARY KEY (id_cliente)
) ENGINE=InnoDB;

-- ------------------------------------------------------------
-- 8. TABLA: VENTAS
--    Cabecera de cada transacción de venta
-- ------------------------------------------------------------
CREATE TABLE VENTAS (
    id_venta      INT             NOT NULL AUTO_INCREMENT,
    serie         VARCHAR(4)      NOT NULL DEFAULT 'B001',  -- B=Boleta, F=Factura
    correlativo   INT             NOT NULL,
    tipo_comprobante ENUM('BOLETA','FACTURA') NOT NULL DEFAULT 'BOLETA',
    fecha_venta   DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    subtotal      DECIMAL(10, 2)  NOT NULL,
    igv           DECIMAL(10, 2)  NOT NULL,               -- 18 %
    total         DECIMAL(10, 2)  NOT NULL,
    descuento     DECIMAL(10, 2)  NOT NULL DEFAULT 0.00,
    id_cliente    INT             NOT NULL,                -- FK → CLIENTES
    id_user       INT             NOT NULL,                -- FK → USUARIOS (vendedor)

    -- Auditoría
    create_user   VARCHAR(80)         NULL,
    create_date   DATETIME            NULL DEFAULT CURRENT_TIMESTAMP,
    updated_user  VARCHAR(80)         NULL,
    updated_date  DATETIME            NULL ON UPDATE CURRENT_TIMESTAMP,
    deleted_user  VARCHAR(80)         NULL,
    deleted_date  DATETIME            NULL,

    CONSTRAINT pk_ventas      PRIMARY KEY (id_venta),
    CONSTRAINT fk_ven_cli     FOREIGN KEY (id_cliente) REFERENCES CLIENTES(id_cliente),
    CONSTRAINT fk_ven_usu     FOREIGN KEY (id_user)    REFERENCES USUARIOS(id_user),
    CONSTRAINT uq_serie_corr  UNIQUE (serie, correlativo)
) ENGINE=InnoDB;

-- ------------------------------------------------------------
-- 9. TABLA: DETALLE_VENTA
--    Líneas de producto por venta (dependiente de VENTAS y PRODUCTOS)
-- ------------------------------------------------------------
CREATE TABLE DETALLE_VENTA (
    id_detalle    INT             NOT NULL AUTO_INCREMENT,
    cantidad      INT             NOT NULL,
    precio_unit   DECIMAL(10, 2)  NOT NULL,
    subtotal      DECIMAL(10, 2)  NOT NULL,             -- cantidad * precio_unit
    id_venta      INT             NOT NULL,             -- FK → VENTAS
    id_producto   INT             NOT NULL,             -- FK → PRODUCTOS

    -- Auditoría
    create_user   VARCHAR(80)         NULL,
    create_date   DATETIME            NULL DEFAULT CURRENT_TIMESTAMP,
    updated_user  VARCHAR(80)         NULL,
    updated_date  DATETIME            NULL ON UPDATE CURRENT_TIMESTAMP,
    deleted_user  VARCHAR(80)         NULL,
    deleted_date  DATETIME            NULL,

    CONSTRAINT pk_detalle     PRIMARY KEY (id_detalle),
    CONSTRAINT fk_det_ven     FOREIGN KEY (id_venta)    REFERENCES VENTAS(id_venta),
    CONSTRAINT fk_det_pro     FOREIGN KEY (id_producto) REFERENCES PRODUCTOS(id_producto)
) ENGINE=InnoDB;

-- ============================================================
-- 10. DATOS INICIALES (SEED DATA)
-- ============================================================

-- --- 10.1 Roles ---
INSERT INTO ROLES (nombre_rol, descripcion, create_user) VALUES
    ('ADMIN',    'Acceso completo al sistema: usuarios, reportes y configuración', 'SYSTEM'),
    ('VENDEDOR', 'Acceso al módulo de ventas, consultas y catálogo',               'SYSTEM');

-- --- 10.2 Categorías iniciales ---
INSERT INTO CATEGORIAS (nombre, descripcion, create_user) VALUES
    ('Computadoras',    'Laptops, desktops y workstations',                      'SYSTEM'),
    ('Impresoras',      'Impresoras de tinta, láser y multifuncionales',          'SYSTEM'),
    ('Componentes',     'Procesadores, memorias RAM, discos duros y SSDs',        'SYSTEM'),
    ('Periféricos',     'Teclados, mouse, monitores y auriculares',               'SYSTEM'),
    ('Redes',           'Routers, switches, cables y puntos de acceso WiFi',      'SYSTEM'),
    ('Accesorios',      'Cables, hubs USB, maletines, cargadores y adaptadores', 'SYSTEM');

-- --- 10.3 Persona y usuario ADMIN ---
INSERT INTO PERSONAS (apellidos, nombres, dni, email, create_user) VALUES
    ('Pérez Paredes', 'Luis', '45678901', 'admin@compmarketperu.pe', 'SYSTEM');

-- Contraseña: Admin2026$ (hash BCrypt generado con factor 10)
INSERT INTO USUARIOS (username, password_hash, id_persona, id_usRol, create_user) VALUES
    ('admin',
     '$2a$10$7EqJtq98hPqEX7fNZaFWoOBpKQ7TZdCqFmVwWgj4ZrNq.LMwW0Tme',
     1, 1, 'SYSTEM');

-- --- 10.4 Persona y usuario VENDEDOR demo ---
INSERT INTO PERSONAS (apellidos, nombres, dni, email, create_user) VALUES
    ('Gómez Torres', 'María', '12345678', 'vendedor@compmarketperu.pe', 'SYSTEM');

-- Contraseña: Vendedor2026$ (hash BCrypt generado con factor 10)
INSERT INTO USUARIOS (username, password_hash, id_persona, id_usRol, create_user) VALUES
    ('vendedor',
     '$2a$10$9K8nXj2Pq1VwYm3RtL5eOO4vQzUd8XaB6sNhWc1Fp7GqD0yMkLk62',
     2, 2, 'SYSTEM');

-- --- 10.5 Clientes de ejemplo ---
INSERT INTO CLIENTES (apellidos, nombres, tipo_doc, nro_doc, email, telefono, direccion, create_user) VALUES
    ('Ramírez López',   'Carlos',  'DNI', '87654321', 'carlos.ramirez@email.com',  '944111222', 'Av. España 123, Trujillo',    'SYSTEM'),
    ('Flores Mendoza',  'Ana',     'DNI', '56781234', 'ana.flores@email.com',      '955333444', 'Jr. Pizarro 456, Trujillo',   'SYSTEM'),
    ('TechSolutions SAC', '',      'RUC', '20601234567', 'info@techsolutions.pe',  '044-287000', 'Av. América Norte 789, Trujillo', 'SYSTEM');

-- --- 10.6 Productos de ejemplo ---
INSERT INTO PRODUCTOS (nombre, marca, modelo, precio_venta, stock, stock_minimo, id_categoria, create_user) VALUES
    ('Laptop Core i5 16GB 512 SSD',  'HP',      'Pavilion 15',  2890.00, 15, 3, 1, 'SYSTEM'),
    ('Laptop Core i7 32GB 1TB SSD',  'Dell',    'Inspiron 16',  4590.00,  8, 2, 1, 'SYSTEM'),
    ('Impresora Multifuncional Ink',  'Epson',   'L3250',         780.00, 20, 5, 2, 'SYSTEM'),
    ('Impresora Láser Mono',          'Brother', 'HL-L2350DW',   890.00, 12, 3, 2, 'SYSTEM'),
    ('Memoria RAM DDR4 8GB 3200MHz', 'Kingston', 'KVR32N22S8/8', 145.00, 50, 10, 3, 'SYSTEM'),
    ('SSD SATA 480GB',               'Kingston', 'SA400S37/480G',195.00, 30, 8, 3, 'SYSTEM'),
    ('Mouse Inalámbrico',             'Logitech', 'M190',          59.00, 40, 10, 4, 'SYSTEM'),
    ('Teclado USB Español',           'HP',       'K1500',         55.00, 35, 10, 4, 'SYSTEM'),
    ('Router WiFi 5 AC1200',         'TP-Link',  'Archer C6',    190.00, 25, 5, 5, 'SYSTEM'),
    ('Cable HDMI 1.8m',              'Genérico', 'HDMI-4K',       25.00, 80, 20, 6, 'SYSTEM');

-- ============================================================
-- 11. VISTAS ÚTILES
-- ============================================================

-- Vista: productos con stock bajo mínimo
CREATE OR REPLACE VIEW v_stock_bajo AS
SELECT  p.id_producto,
        p.nombre,
        p.marca,
        p.stock,
        p.stock_minimo,
        c.nombre AS categoria
FROM    PRODUCTOS p
JOIN    CATEGORIAS c ON p.id_categoria = c.id_categoria
WHERE   p.estado = 1
  AND   p.stock <= p.stock_minimo
ORDER BY p.stock ASC;

-- Vista: resumen de ventas del día
CREATE OR REPLACE VIEW v_cierre_caja_hoy AS
SELECT  v.tipo_comprobante,
        COUNT(*)                        AS cantidad_ventas,
        SUM(v.subtotal)                 AS total_subtotal,
        SUM(v.igv)                      AS total_igv,
        SUM(v.total)                    AS total_general
FROM    VENTAS v
WHERE   DATE(v.fecha_venta) = CURDATE()
  AND   v.deleted_date IS NULL
GROUP BY v.tipo_comprobante;

-- Vista: ranking de productos más vendidos (mes actual)
CREATE OR REPLACE VIEW v_ranking_productos AS
SELECT  p.id_producto,
        p.nombre,
        p.marca,
        SUM(dv.cantidad)              AS unidades_vendidas,
        SUM(dv.subtotal)              AS ingresos_totales
FROM    DETALLE_VENTA dv
JOIN    VENTAS v    ON v.id_venta    = dv.id_venta
JOIN    PRODUCTOS p ON p.id_producto = dv.id_producto
WHERE   YEAR(v.fecha_venta)  = YEAR(CURDATE())
  AND   MONTH(v.fecha_venta) = MONTH(CURDATE())
  AND   v.deleted_date IS NULL
GROUP BY p.id_producto, p.nombre, p.marca
ORDER BY unidades_vendidas DESC;

-- ============================================================
-- 12. STORED PROCEDURES
-- ============================================================

DELIMITER $$

-- SP: Obtener historial de compras de un cliente
CREATE PROCEDURE sp_historial_cliente(IN p_id_cliente INT)
BEGIN
    SELECT  v.id_venta,
            v.serie,
            v.correlativo,
            v.tipo_comprobante,
            v.fecha_venta,
            v.subtotal,
            v.igv,
            v.total,
            CONCAT(u.username) AS vendedor
    FROM    VENTAS v
    JOIN    USUARIOS u ON u.id_user = v.id_user
    WHERE   v.id_cliente  = p_id_cliente
      AND   v.deleted_date IS NULL
    ORDER BY v.fecha_venta DESC;
END$$

-- SP: Reporte cierre de caja por rango de fechas
CREATE PROCEDURE sp_cierre_caja(IN p_fecha_ini DATE, IN p_fecha_fin DATE)
BEGIN
    SELECT  v.tipo_comprobante,
            COUNT(*)         AS cantidad,
            SUM(v.subtotal)  AS subtotal,
            SUM(v.igv)       AS igv,
            SUM(v.total)     AS total
    FROM    VENTAS v
    WHERE   DATE(v.fecha_venta) BETWEEN p_fecha_ini AND p_fecha_fin
      AND   v.deleted_date IS NULL
    GROUP BY v.tipo_comprobante
    WITH ROLLUP;
END$$

DELIMITER ;

-- ============================================================
-- FIN DEL SCRIPT
-- ============================================================
