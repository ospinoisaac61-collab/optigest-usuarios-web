package com.optigest.usuariosweb.modelo;

import java.time.LocalDateTime;

/**
 * Entidad que representa una compra a un proveedor en OptiGest ERP.
 * Corresponde a la tabla "compra" del diseño oficial de base de datos.
 * Relaciona un proveedor (id_proveedor) y el usuario que registró la compra (id_usuario).
 */
public class Compra {

    private int id;
    private LocalDateTime fechaCompra;
    private double total;
    private String estado;
    private int idProveedor;
    private String nombreProveedor; // solo para mostrar en el listado (join)
    private int idUsuario;
    private String nombreUsuario;   // solo para mostrar en el listado (join)

    public Compra() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(LocalDateTime fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    public String getNombreProveedor() {
        return nombreProveedor;
    }

    public void setNombreProveedor(String nombreProveedor) {
        this.nombreProveedor = nombreProveedor;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
}
