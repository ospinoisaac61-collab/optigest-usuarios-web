package com.optigest.usuariosweb.dao;

import com.optigest.usuariosweb.modelo.Compra;

import java.sql.SQLException;
import java.util.List;

/** Contrato de acceso a datos para la entidad Compra (CRUD). */
public interface CompraDAO {

    void insertarCompra(Compra compra) throws SQLException;

    List<Compra> consultarCompras() throws SQLException;

    Compra consultarCompraPorId(int id) throws SQLException;

    void actualizarCompra(Compra compra) throws SQLException;

    void eliminarCompra(int id) throws SQLException;
}
