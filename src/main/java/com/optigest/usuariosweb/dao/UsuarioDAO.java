package com.optigest.usuariosweb.dao;

import com.optigest.usuariosweb.modelo.Usuario;

import java.sql.SQLException;
import java.util.List;

/**
 * Contrato de acceso a datos para la entidad Usuario.
 * Define las cuatro operaciones basicas de persistencia (CRUD).
 */
public interface UsuarioDAO {

    void insertarUsuario(Usuario usuario) throws SQLException;

    List<Usuario> consultarUsuarios() throws SQLException;

    Usuario consultarUsuarioPorId(int id) throws SQLException;

    void actualizarUsuario(Usuario usuario) throws SQLException;

    void eliminarUsuario(int id) throws SQLException;

    /**
     * Valida las credenciales de inicio de sesion. Retorna el usuario si el email y
     * el hash de la contrasenia coinciden y el usuario esta activo; si no, retorna null.
     */
    Usuario autenticar(String email, String passwordHash) throws SQLException;
}
