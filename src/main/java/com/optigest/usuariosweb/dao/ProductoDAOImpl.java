package com.optigest.usuariosweb.dao;

import com.optigest.usuariosweb.conexion.ConexionBD;
import com.optigest.usuariosweb.modelo.Producto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/** Implementación JDBC de ProductoDAO usando PreparedStatement (evita inyección SQL). */
public class ProductoDAOImpl implements ProductoDAO {

    private static final String SQL_INSERTAR =
            "INSERT INTO producto (nombre, tipo, precio_unitario, stock_actual) VALUES (?, ?, ?, ?)";

    private static final String SQL_CONSULTAR_TODOS =
            "SELECT id_producto, nombre, tipo, precio_unitario, stock_actual FROM producto ORDER BY id_producto";

    private static final String SQL_CONSULTAR_POR_ID =
            "SELECT id_producto, nombre, tipo, precio_unitario, stock_actual FROM producto WHERE id_producto = ?";

    private static final String SQL_ACTUALIZAR =
            "UPDATE producto SET nombre = ?, tipo = ?, precio_unitario = ?, stock_actual = ? WHERE id_producto = ?";

    private static final String SQL_ELIMINAR = "DELETE FROM producto WHERE id_producto = ?";

    @Override
    public void insertarProducto(Producto producto) throws SQLException {
        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(SQL_INSERTAR)) {
            asignarParametros(sentencia, producto);
            sentencia.executeUpdate();
        }
    }

    @Override
    public List<Producto> consultarProductos() throws SQLException {
        List<Producto> productos = new ArrayList<>();
        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(SQL_CONSULTAR_TODOS);
             ResultSet resultado = sentencia.executeQuery()) {
            while (resultado.next()) {
                productos.add(mapearProducto(resultado));
            }
        }
        return productos;
    }

    @Override
    public Producto consultarProductoPorId(int id) throws SQLException {
        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(SQL_CONSULTAR_POR_ID)) {
            sentencia.setInt(1, id);
            try (ResultSet resultado = sentencia.executeQuery()) {
                return resultado.next() ? mapearProducto(resultado) : null;
            }
        }
    }

    @Override
    public void actualizarProducto(Producto producto) throws SQLException {
        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(SQL_ACTUALIZAR)) {
            asignarParametros(sentencia, producto);
            sentencia.setInt(5, producto.getId());
            sentencia.executeUpdate();
        }
    }

    @Override
    public void eliminarProducto(int id) throws SQLException {
        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(SQL_ELIMINAR)) {
            sentencia.setInt(1, id);
            sentencia.executeUpdate();
        }
    }

    private void asignarParametros(PreparedStatement sentencia, Producto producto) throws SQLException {
        sentencia.setString(1, producto.getNombre());
        sentencia.setString(2, producto.getTipo());
        sentencia.setDouble(3, producto.getPrecioUnitario());
        sentencia.setInt(4, producto.getStockActual());
    }

    private Producto mapearProducto(ResultSet resultado) throws SQLException {
        return new Producto(
                resultado.getInt("id_producto"),
                resultado.getString("nombre"),
                resultado.getString("tipo"),
                resultado.getDouble("precio_unitario"),
                resultado.getInt("stock_actual")
        );
    }
}
