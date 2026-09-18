package com.optigest.usuariosweb.dao;

import com.optigest.usuariosweb.modelo.Proveedor;

import java.sql.SQLException;
import java.util.List;

/** Contrato de acceso a datos para la entidad Proveedor (CRUD). */
public interface ProveedorDAO {

    void insertarProveedor(Proveedor proveedor) throws SQLException;

    List<Proveedor> consultarProveedores() throws SQLException;

    Proveedor consultarProveedorPorId(int id) throws SQLException;

    void actualizarProveedor(Proveedor proveedor) throws SQLException;

    void eliminarProveedor(int id) throws SQLException;
}
