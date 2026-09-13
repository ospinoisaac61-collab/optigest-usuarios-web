package com.optigest.usuariosweb.servlet;

import com.optigest.usuariosweb.dao.UsuarioDAO;
import com.optigest.usuariosweb.dao.UsuarioDAOImpl;
import com.optigest.usuariosweb.modelo.Usuario;
import com.optigest.usuariosweb.util.Seguridad;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Servlet principal del modulo de Usuarios.
 *
 * GET  /usuarios                    -> lista todos los usuarios (vista JSP)
 * GET  /usuarios?accion=nuevo       -> muestra el formulario vacio
 * GET  /usuarios?accion=editar&id=X -> muestra el formulario con los datos del usuario X
 * GET  /usuarios?accion=eliminar&id=X -> elimina el usuario X y redirige a la lista
 * POST /usuarios                    -> procesa el formulario (inserta o actualiza)
 */
@WebServlet(name = "UsuarioServlet", urlPatterns = {"/usuarios"})
public class UsuarioServlet extends HttpServlet {

    private final UsuarioDAO usuarioDAO = new UsuarioDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");
        try {
            if ("nuevo".equals(accion)) {
                mostrarFormulario(request, response, new Usuario());
            } else if ("editar".equals(accion)) {
                int id = Integer.parseInt(request.getParameter("id"));
                Usuario usuario = usuarioDAO.consultarUsuarioPorId(id);
                mostrarFormulario(request, response, usuario);
            } else if ("eliminar".equals(accion)) {
                int id = Integer.parseInt(request.getParameter("id"));
                usuarioDAO.eliminarUsuario(id);
                response.sendRedirect(request.getContextPath() + "/usuarios");
            } else {
                mostrarListado(request, response);
            }
        } catch (SQLException excepcion) {
            throw new ServletException("Error de base de datos.", excepcion);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String idTexto = request.getParameter("id");
        String nombre = request.getParameter("nombre");
        String nombreUsuario = request.getParameter("usuario");
        String correo = request.getParameter("correo");
        String clave = request.getParameter("clave");
        String celular = request.getParameter("celular");
        String rol = request.getParameter("rol");
        String estado = request.getParameter("estado");

        try {
            boolean esNuevo = (idTexto == null || idTexto.isBlank());

            Usuario usuario = new Usuario();
            usuario.setNombre(nombre);
            usuario.setUsuario(nombreUsuario);
            usuario.setCorreo(correo);
            usuario.setCelular(celular);
            usuario.setRol(rol);
            usuario.setEstado(estado);

            if (esNuevo) {
                usuario.setClave(Seguridad.hashearClave(clave));
                usuarioDAO.insertarUsuario(usuario);
            } else {
                int id = Integer.parseInt(idTexto);
                usuario.setId(id);
                if (clave == null || clave.isBlank()) {
                    Usuario usuarioActual = usuarioDAO.consultarUsuarioPorId(id);
                    usuario.setClave(usuarioActual != null ? usuarioActual.getClave() : "");
                } else {
                    usuario.setClave(Seguridad.hashearClave(clave));
                }
                usuarioDAO.actualizarUsuario(usuario);
            }

            // Patron Post/Redirect/Get: evita reenvios del formulario al recargar.
            response.sendRedirect(request.getContextPath() + "/usuarios");

        } catch (SQLException excepcion) {
            throw new ServletException("Error de base de datos.", excepcion);
        }
    }

    private void mostrarListado(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {

        List<Usuario> usuarios = usuarioDAO.consultarUsuarios();
        request.setAttribute("usuarios", usuarios);

        RequestDispatcher despachador =
                request.getRequestDispatcher("/WEB-INF/views/lista.jsp");
        despachador.forward(request, response);
    }

    private void mostrarFormulario(HttpServletRequest request, HttpServletResponse response,
                                    Usuario usuario) throws ServletException, IOException {

        request.setAttribute("usuario", usuario);

        RequestDispatcher despachador =
                request.getRequestDispatcher("/WEB-INF/views/formulario.jsp");
        despachador.forward(request, response);
    }
}
