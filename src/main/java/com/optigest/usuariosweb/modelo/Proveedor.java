package com.optigest.usuariosweb.modelo;

import java.time.LocalDate;

/**
 * Entidad que representa un proveedor de OptiGest ERP.
 * Corresponde a la tabla "proveedor" del diseño oficial de base de datos.
 */
public class Proveedor {

    private int id;
    private String nombre;
    private String telefono;
    private String email;
    private String direccion;
    private LocalDate fechaRegistro;

    public Proveedor() {
    }

    public Proveedor(int id, String nombre, String telefono, String email, String direccion, LocalDate fechaRegistro) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
        this.direccion = direccion;
        this.fechaRegistro = fechaRegistro;
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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
