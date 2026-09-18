package com.optigest.usuariosweb.modelo;

/**
 * Entidad que representa un producto del catálogo de OptiGest ERP.
 * Corresponde a la tabla "producto" del diseño oficial de base de datos.
 */
public class Producto {

    private int id;
    private String nombre;
    private String tipo;
    private double precioUnitario;
    private int stockActual;

    public Producto() {
    }

    public Producto(int id, String nombre, String tipo, double precioUnitario, int stockActual) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.precioUnitario = precioUnitario;
        this.stockActual = stockActual;
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public int getStockActual() {
        return stockActual;
    }

    public void setStockActual(int stockActual) {
        this.stockActual = stockActual;
    }
}
