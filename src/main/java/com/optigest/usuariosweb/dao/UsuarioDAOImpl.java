package com.optigest.usuariosweb.dao;

import com.optigest.usuariosweb.conexion.ConexionBD;
import com.optigest.usuariosweb.modelo.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación de UsuarioDAO que persiste los usuarios en PostgreSQL
 * usando JDBC con PreparedStatement (evita inyección SQL).
 *
 * Los nombres de tabla y columnas siguen el diseño oficial de base de
 * datos del proyecto (evidencia GA6-220501096-AA2-EV02/EV03): tabla
 * "usuario", columnas id_usuario, nombre, email, password, rol, estado
 * y fecha_creacion.
 */
public class UsuarioDAOImpl implements UsuarioDAO {

    private static final String SQL_INSERTAR =
            "INSERT INTO usuario (nombre, email, password, rol, estado) " +
            "VALUES (?, ?, ?, ?, ?)";

    private static final String SQL_CONSULTAR_TODOS =
            "SELECT id_usuario, nombre, email, password, rol, estado, fecha_creacion " +
            "FROM usuario ORDER BY id_usuario";

    private static final String SQL_CONSULTAR_POR_ID =
            "SELECT id_usuario, nombre, email, password, rol, estado, fecha_creacion " +
            "FROM usuario WHERE id_usuario = ?";

    private static final String SQL_ACTUALIZAR =
            "UPDATE usuario SET nombre = ?, email = ?, password = ?, rol = ?, estado = ? " +
            "WHERE id_usuario = ?";

    private static final String SQL_ELIMINAR =
            "DELETE FROM usuario WHERE id_usuario = ?";

    private static final String SQL_AUTENTICAR =
            "SELECT id_usuario, nombre, email, password, rol, estado, fecha_creacion " +
            "FROM usuario WHERE email = ? AND password = ? AND estado = TRUE";

    @Override
    public Usuario autenticar(String email, String passwordHash) throws SQLException {
        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(SQL_AUTENTICAR)) {
            sentencia.setString(1, email);
            sentencia.setString(2, passwordHash);
            try (ResultSet resultado = sentencia.executeQuery()) {
                return resultado.next() ? mapearUsuario(resultado) : null;
            }
        }
    }

    @Override
    public void insertarUsuario(Usuario usuario) throws SQLException {
        // fecha_creacion no se envía: la base de datos la asigna sola (DEFAULT CURRENT_TIMESTAMP).
        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(SQL_INSERTAR)) {

            asignarParametros(sentencia, usuario);
            sentencia.executeUpdate();
        }
    }

    @Override
    public List<Usuario> consultarUsuarios() throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();

        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(SQL_CONSULTAR_TODOS);
             ResultSet resultado = sentencia.executeQuery()) {

            while (resultado.next()) {
                usuarios.add(mapearUsuario(resultado));
            }
        }
        return usuarios;
    }

    @Override
    public Usuario consultarUsuarioPorId(int id) throws SQLException {
        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(SQL_CONSULTAR_POR_ID)) {

            sentencia.setInt(1, id);
            try (ResultSet resultado = sentencia.executeQuery()) {
                return resultado.next() ? mapearUsuario(resultado) : null;
            }
        }
    }

    @Override
    public void actualizarUsuario(Usuario usuario) throws SQLException {
        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(SQL_ACTUALIZAR)) {

            asignarParametros(sentencia, usuario);
            sentencia.setInt(6, usuario.getId());
            sentencia.executeUpdate();
        }
    }

    @Override
    public void eliminarUsuario(int id) throws SQLException {
        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(SQL_ELIMINAR)) {

            sentencia.setInt(1, id);
            sentencia.executeUpdate();
        }
    }

    // Copia los campos editables del Usuario a los "?" del PreparedStatement,
    // en el mismo orden en que aparecen en el SQL (nombre, email, password, rol, estado).
    private void asignarParametros(PreparedStatement sentencia, Usuario usuario) throws SQLException {
        sentencia.setString(1, usuario.getNombre());
        sentencia.setString(2, usuario.getEmail());
        sentencia.setString(3, usuario.getPassword());
        sentencia.setString(4, usuario.getRol());
        sentencia.setBoolean(5, usuario.isEstado());
    }

    // Convierte una fila del ResultSet en un objeto Usuario.
    private Usuario mapearUsuario(ResultSet resultado) throws SQLException {
        Timestamp fechaCreacion = resultado.getTimestamp("fecha_creacion");
        return new Usuario(
                resultado.getInt("id_usuario"),
                resultado.getString("nombre"),
                resultado.getString("email"),
                resultado.getString("password"),
                resultado.getString("rol"),
                resultado.getBoolean("estado"),
                fechaCreacion != null ? fechaCreacion.toLocalDateTime() : null
        );
    }
}
