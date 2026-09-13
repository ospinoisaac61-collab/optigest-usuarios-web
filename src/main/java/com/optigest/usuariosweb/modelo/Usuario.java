package com.optigest.usuariosweb.modelo;

import java.time.LocalDateTime;

/**
 * Entidad que representa un usuario del sistema OptiGest ERP.
 * Corresponde a la tabla "usuario" del diseño oficial de base de datos
 * (evidencia GA6-220501096-AA2-EV02/EV03).
 */
public class Usuario {

    // Identificador único (columna id_usuario, autoincremental).
    private int id;

    private String nombre;

    // También funciona como credencial de inicio de sesión (columna UNIQUE).
    private String email;

    // Guarda el hash de la contraseña, nunca el texto plano (ver clase Seguridad).
    private String password;

    private String rol;

    // true = activo, false = inactivo (columna BOOLEAN en la base de datos).
    private boolean estado;

    // La asigna la base de datos automáticamente (DEFAULT CURRENT_TIMESTAMP).
    private LocalDateTime fechaCreacion;

    public Usuario() {
    }

    public Usuario(int id, String nombre, String email, String password,
                   String rol, boolean estado, LocalDateTime fechaCreacion) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.rol = rol;
        this.estado = estado;
        this.fechaCreacion = fechaCreacion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
}
