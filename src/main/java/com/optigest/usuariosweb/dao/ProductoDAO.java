package com.optigest.usuariosweb.dao;

import com.optigest.usuariosweb.modelo.Producto;

import java.sql.SQLException;
import java.util.List;

/** Contrato de acceso a datos para la entidad Producto (CRUD). */
public interface ProductoDAO {

    void insertarProducto(Producto producto) throws SQLException;

    List<Producto> consultarProductos() throws SQLException;

    Producto consultarProductoPorId(int id) throws SQLException;

    void actualizarProducto(Producto producto) throws SQLException;

    void eliminarProducto(int id) throws SQLException;
}
