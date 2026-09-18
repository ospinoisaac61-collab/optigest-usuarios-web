package com.optigest.usuariosweb.dao;

import com.optigest.usuariosweb.conexion.ConexionBD;
import com.optigest.usuariosweb.modelo.Proveedor;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/** Implementación JDBC de ProveedorDAO usando PreparedStatement (evita inyección SQL). */
public class ProveedorDAOImpl implements ProveedorDAO {

    private static final String SQL_INSERTAR =
            "INSERT INTO proveedor (nombre, telefono, email, direccion) VALUES (?, ?, ?, ?)";

    private static final String SQL_CONSULTAR_TODOS =
            "SELECT id_proveedor, nombre, telefono, email, direccion, fecha_registro FROM proveedor ORDER BY id_proveedor";

    private static final String SQL_CONSULTAR_POR_ID =
            "SELECT id_proveedor, nombre, telefono, email, direccion, fecha_registro FROM proveedor WHERE id_proveedor = ?";

    private static final String SQL_ACTUALIZAR =
            "UPDATE proveedor SET nombre = ?, telefono = ?, email = ?, direccion = ? WHERE id_proveedor = ?";

    private static final String SQL_ELIMINAR = "DELETE FROM proveedor WHERE id_proveedor = ?";

    @Override
    public void insertarProveedor(Proveedor proveedor) throws SQLException {
        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(SQL_INSERTAR)) {
            asignarParametros(sentencia, proveedor);
            sentencia.executeUpdate();
        }
    }

    @Override
    public List<Proveedor> consultarProveedores() throws SQLException {
        List<Proveedor> proveedores = new ArrayList<>();
        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(SQL_CONSULTAR_TODOS);
             ResultSet resultado = sentencia.executeQuery()) {
            while (resultado.next()) {
                proveedores.add(mapearProveedor(resultado));
            }
        }
        return proveedores;
    }

    @Override
    public Proveedor consultarProveedorPorId(int id) throws SQLException {
        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(SQL_CONSULTAR_POR_ID)) {
            sentencia.setInt(1, id);
            try (ResultSet resultado = sentencia.executeQuery()) {
                return resultado.next() ? mapearProveedor(resultado) : null;
            }
        }
    }

    @Override
    public void actualizarProveedor(Proveedor proveedor) throws SQLException {
        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(SQL_ACTUALIZAR)) {
            asignarParametros(sentencia, proveedor);
            sentencia.setInt(5, proveedor.getId());
            sentencia.executeUpdate();
        }
    }

    @Override
    public void eliminarProveedor(int id) throws SQLException {
        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(SQL_ELIMINAR)) {
            sentencia.setInt(1, id);
            sentencia.executeUpdate();
        }
    }

    private void asignarParametros(PreparedStatement sentencia, Proveedor proveedor) throws SQLException {
        sentencia.setString(1, proveedor.getNombre());
        sentencia.setString(2, proveedor.getTelefono());
        sentencia.setString(3, proveedor.getEmail());
        sentencia.setString(4, proveedor.getDireccion());
    }

    private Proveedor mapearProveedor(ResultSet resultado) throws SQLException {
        Date fecha = resultado.getDate("fecha_registro");
        return new Proveedor(
                resultado.getInt("id_proveedor"),
                resultado.getString("nombre"),
                resultado.getString("telefono"),
                resultado.getString("email"),
                resultado.getString("direccion"),
                fecha != null ? fecha.toLocalDate() : null
        );
    }
}
