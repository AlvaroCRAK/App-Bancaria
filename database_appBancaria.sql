CREATE DATABASE AppBancaria;

USE AppBancaria;

CREATE TABLE Roles (
    rol_id INT PRIMARY KEY AUTO_INCREMENT,  -- Cambiado a INT para que coincida con la referencia
    nombre VARCHAR(50) UNIQUE NOT NULL,
    descripcion VARCHAR(255)
);	

-- Crear tabla Usuarios
CREATE TABLE Usuarios (
    usuario_id INT PRIMARY KEY AUTO_INCREMENT,  -- Cambiado a INT para coherencia
    nombre VARCHAR(100) NOT NULL,
    correo VARCHAR(100) UNIQUE NOT NULL,
    contraseña VARCHAR(100) NOT NULL,
    rol_id INT NOT NULL,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (rol_id) REFERENCES Roles(rol_id)
);


-- Crear tabla Sucursales
CREATE TABLE Sucursales (
    sucursal_id INT PRIMARY KEY AUTO_INCREMENT,  -- Cambiado a INT para coherencia
    nombre VARCHAR(100) NOT NULL,
    direccion VARCHAR(255),
    telefono VARCHAR(20)
);

-- Crear tabla Clientes
CREATE TABLE Clientes (
    cliente_id INT PRIMARY KEY AUTO_INCREMENT,  -- Cambiado a INT AUTO_INCREMENT
    usuario_id INT NOT NULL,
    fecha_nacimiento DATE,
    direccion VARCHAR(255),
    telefono VARCHAR(20),
    sucursal_id INT NOT NULL,
    FOREIGN KEY (usuario_id) REFERENCES Usuarios(usuario_id),
    FOREIGN KEY (sucursal_id) REFERENCES Sucursales(sucursal_id)
);

-- Crear tabla Cuentas
CREATE TABLE Cuentas (
    cuenta_id INT PRIMARY KEY AUTO_INCREMENT,  -- Cambiado a INT AUTO_INCREMENT
    cliente_id INT NOT NULL,
    tipo_cuenta VARCHAR(50) NOT NULL,
    saldo DECIMAL(15, 2) DEFAULT 0.0,
    estado VARCHAR(50) CHECK (estado IN ('activa', 'inactiva', 'suspendida')),
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (cliente_id) REFERENCES Clientes(cliente_id)
);

-- Crear tabla Transacciones
CREATE TABLE Transacciones (
    transaccion_id SERIAL PRIMARY KEY,
    cuenta_origen_id INT,
    cuenta_destino_id INT,
    tipo_transaccion VARCHAR(50) CHECK (tipo_transaccion IN ('deposito', 'retiro', 'transferencia')) NOT NULL,
    monto DECIMAL(15, 2) NOT NULL,
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (cuenta_origen_id) REFERENCES Cuentas(cuenta_id),
    FOREIGN KEY (cuenta_destino_id) REFERENCES Cuentas(cuenta_id)
);

-- Crear tabla Auditorías
CREATE TABLE Auditorias (
    auditoria_id SERIAL PRIMARY KEY,
    usuario_id INT NOT NULL,
    accion VARCHAR(255) NOT NULL,
    descripcion TEXT,
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (usuario_id) REFERENCES Usuarios(usuario_id)
);

-- Crear tabla Préstamos
CREATE TABLE Prestamos (
    prestamo_id SERIAL PRIMARY KEY,
    cliente_id INT NOT NULL,
    monto DECIMAL(15, 2) NOT NULL,
    tasa_interes DECIMAL(5, 2) NOT NULL,
    plazo INT NOT NULL,
    fecha_inicio DATE,
    fecha_fin DATE,
    estado VARCHAR(50) CHECK (estado IN ('activo', 'pagado', 'moroso')),
    FOREIGN KEY (cliente_id) REFERENCES Clientes(cliente_id)
);

-- Crear tabla Tarjetas de Crédito
CREATE TABLE TarjetasCredito (
    tarjeta_id SERIAL PRIMARY KEY,
    cliente_id INT NOT NULL,
    numero_tarjeta VARCHAR(16) UNIQUE NOT NULL,
    limite_credito DECIMAL(15, 2) NOT NULL,
    saldo DECIMAL(15, 2) DEFAULT 0.0,
    estado VARCHAR(50) CHECK (estado IN ('activa', 'inactiva', 'suspendida')),
    fecha_expiracion DATE,
    FOREIGN KEY (cliente_id) REFERENCES Clientes(cliente_id)
);

-- Crear tabla Notificaciones
CREATE TABLE Notificaciones (
    notificacion_id SERIAL PRIMARY KEY,
    cliente_id INT NOT NULL,
    mensaje TEXT NOT NULL,
    fecha_envio TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    leido BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (cliente_id) REFERENCES Clientes(cliente_id)
);