package com.optigest.usuariosweb.dao;

import com.optigest.usuariosweb.conexion.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Consultas de conteo para el dashboard de inicio. Cada metodo usa una
 * sentencia fija (sin concatenar datos externos), por lo que es seguro.
 */
public class EstadisticasDAO {

    public int contarUsuarios() throws SQLException {
        return contar("SELECT COUNT(*) FROM usuario");
    }

    public int contarProductos() throws SQLException {
        return contar("SELECT COUNT(*) FROM producto");
    }

    public int contarProveedores() throws SQLException {
        return contar("SELECT COUNT(*) FROM proveedor");
    }

    public int contarCompras() throws SQLException {
        return contar("SELECT COUNT(*) FROM compra");
    }

    public double sumarTotalCompras() throws SQLException {
        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement("SELECT COALESCE(SUM(total), 0) FROM compra");
             ResultSet resultado = sentencia.executeQuery()) {
            return resultado.next() ? resultado.getDouble(1) : 0;
        }
    }

    private int contar(String sql) throws SQLException {
        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(sql);
             ResultSet resultado = sentencia.executeQuery()) {
            return resultado.next() ? resultado.getInt(1) : 0;
        }
    }
}
