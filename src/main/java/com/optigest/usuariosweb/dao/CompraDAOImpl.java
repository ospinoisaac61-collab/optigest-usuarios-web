package com.optigest.usuariosweb.dao;

import com.optigest.usuariosweb.conexion.ConexionBD;
import com.optigest.usuariosweb.modelo.Compra;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación JDBC de CompraDAO usando PreparedStatement (evita inyección SQL).
 * Las consultas hacen JOIN con proveedor y usuario solo para traer sus nombres
 * y mostrarlos en el listado (la relación real se guarda por id_proveedor/id_usuario).
 */
public class CompraDAOImpl implements CompraDAO {

    private static final String SQL_INSERTAR =
            "INSERT INTO compra (total, estado, id_proveedor, id_usuario) VALUES (?, ?, ?, ?)";

    private static final String SQL_CONSULTAR_TODOS =
            "SELECT c.id_compra, c.fecha_compra, c.total, c.estado, c.id_proveedor, c.id_usuario, " +
            "       p.nombre AS nombre_proveedor, u.nombre AS nombre_usuario " +
            "FROM compra c " +
            "JOIN proveedor p ON p.id_proveedor = c.id_proveedor " +
            "JOIN usuario u ON u.id_usuario = c.id_usuario " +
            "ORDER BY c.id_compra";

    private static final String SQL_CONSULTAR_POR_ID =
            "SELECT id_compra, fecha_compra, total, estado, id_proveedor, id_usuario " +
            "FROM compra WHERE id_compra = ?";

    private static final String SQL_ACTUALIZAR =
            "UPDATE compra SET total = ?, estado = ?, id_proveedor = ?, id_usuario = ? WHERE id_compra = ?";

    private static final String SQL_ELIMINAR = "DELETE FROM compra WHERE id_compra = ?";

    @Override
    public void insertarCompra(Compra compra) throws SQLException {
        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(SQL_INSERTAR)) {
            asignarParametros(sentencia, compra);
            sentencia.executeUpdate();
        }
    }

    @Override
    public List<Compra> consultarCompras() throws SQLException {
        List<Compra> compras = new ArrayList<>();
        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(SQL_CONSULTAR_TODOS);
             ResultSet resultado = sentencia.executeQuery()) {
            while (resultado.next()) {
                Compra compra = mapearCompra(resultado);
                compra.setNombreProveedor(resultado.getString("nombre_proveedor"));
                compra.setNombreUsuario(resultado.getString("nombre_usuario"));
                compras.add(compra);
            }
        }
        return compras;
    }

    @Override
    public Compra consultarCompraPorId(int id) throws SQLException {
        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(SQL_CONSULTAR_POR_ID)) {
            sentencia.setInt(1, id);
            try (ResultSet resultado = sentencia.executeQuery()) {
                return resultado.next() ? mapearCompra(resultado) : null;
            }
        }
    }

    @Override
    public void actualizarCompra(Compra compra) throws SQLException {
        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(SQL_ACTUALIZAR)) {
            asignarParametros(sentencia, compra);
            sentencia.setInt(5, compra.getId());
            sentencia.executeUpdate();
        }
    }

    @Override
    public void eliminarCompra(int id) throws SQLException {
        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(SQL_ELIMINAR)) {
            sentencia.setInt(1, id);
            sentencia.executeUpdate();
        }
    }

    private void asignarParametros(PreparedStatement sentencia, Compra compra) throws SQLException {
        sentencia.setDouble(1, compra.getTotal());
        sentencia.setString(2, compra.getEstado());
        sentencia.setInt(3, compra.getIdProveedor());
        sentencia.setInt(4, compra.getIdUsuario());
    }

    private Compra mapearCompra(ResultSet resultado) throws SQLException {
        Compra compra = new Compra();
        compra.setId(resultado.getInt("id_compra"));
        Timestamp fecha = resultado.getTimestamp("fecha_compra");
        compra.setFechaCompra(fecha != null ? fecha.toLocalDateTime() : null);
        compra.setTotal(resultado.getDouble("total"));
        compra.setEstado(resultado.getString("estado"));
        compra.setIdProveedor(resultado.getInt("id_proveedor"));
        compra.setIdUsuario(resultado.getInt("id_usuario"));
        return compra;
    }
}
