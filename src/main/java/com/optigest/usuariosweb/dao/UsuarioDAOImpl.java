package com.optigest.usuariosweb.dao;

import com.optigest.usuariosweb.conexion.ConexionBD;
import com.optigest.usuariosweb.modelo.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementacion de UsuarioDAO que persiste los usuarios en PostgreSQL
 * usando JDBC con PreparedStatement (evita inyeccion SQL).
 */
public class UsuarioDAOImpl implements UsuarioDAO {

    private static final String SQL_INSERTAR =
            "INSERT INTO usuarios (nombre, usuario, correo, clave, celular, rol, estado) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_CONSULTAR_TODOS =
            "SELECT id, nombre, usuario, correo, clave, celular, rol, estado " +
            "FROM usuarios ORDER BY id";

    private static final String SQL_CONSULTAR_POR_ID =
            "SELECT id, nombre, usuario, correo, clave, celular, rol, estado " +
            "FROM usuarios WHERE id = ?";

    private static final String SQL_ACTUALIZAR =
            "UPDATE usuarios SET nombre = ?, usuario = ?, correo = ?, clave = ?, " +
            "celular = ?, rol = ?, estado = ? WHERE id = ?";

    private static final String SQL_ELIMINAR =
            "DELETE FROM usuarios WHERE id = ?";

    @Override
    public void insertarUsuario(Usuario usuario) throws SQLException {
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
            sentencia.setInt(8, usuario.getId());
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

    private void asignarParametros(PreparedStatement sentencia, Usuario usuario) throws SQLException {
        sentencia.setString(1, usuario.getNombre());
        sentencia.setString(2, usuario.getUsuario());
        sentencia.setString(3, usuario.getCorreo());
        sentencia.setString(4, usuario.getClave());
        sentencia.setString(5, usuario.getCelular());
        sentencia.setString(6, usuario.getRol());
        sentencia.setString(7, usuario.getEstado());
    }

    private Usuario mapearUsuario(ResultSet resultado) throws SQLException {
        return new Usuario(
                resultado.getInt("id"),
                resultado.getString("nombre"),
                resultado.getString("usuario"),
                resultado.getString("correo"),
                resultado.getString("clave"),
                resultado.getString("celular"),
                resultado.getString("rol"),
                resultado.getString("estado")
        );
    }
}
