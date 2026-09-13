package com.optigest.usuariosweb.modelo;

/**
 * Entidad que representa un usuario del sistema OptiGest ERP.
 * Corresponde a la tabla "usuarios" de la base de datos.
 */
public class Usuario {

    private int id;
    private String nombre;
    private String usuario;
    private String correo;
    private String clave;
    private String celular;
    private String rol;
    private String estado;

    public Usuario() {
    }

    public Usuario(int id, String nombre, String usuario, String correo,
                   String clave, String celular, String rol, String estado) {
        this.id = id;
        this.nombre = nombre;
        this.usuario = usuario;
        this.correo = correo;
        this.clave = clave;
        this.celular = celular;
        this.rol = rol;
        this.estado = estado;
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

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
