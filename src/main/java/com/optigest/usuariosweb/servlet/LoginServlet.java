package com.optigest.usuariosweb.servlet;

import com.optigest.usuariosweb.dao.UsuarioDAO;
import com.optigest.usuariosweb.dao.UsuarioDAOImpl;
import com.optigest.usuariosweb.modelo.Usuario;
import com.optigest.usuariosweb.util.Seguridad;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Inicio de sesion. GET muestra el formulario; POST valida email y contrasenia
 * contra la tabla usuario (la contrasenia se compara por su hash SHA-256).
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    public static final String ATRIBUTO_SESION = "usuarioSesion";

    private final UsuarioDAO usuarioDAO = new UsuarioDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            Usuario usuario = null;
            if (email != null && password != null && !email.isBlank() && !password.isEmpty()) {
                usuario = usuarioDAO.autenticar(email.trim(), Seguridad.hashearClave(password));
            }

            if (usuario == null) {
                request.setAttribute("error", "Correo o contraseña incorrectos, o el usuario está inactivo.");
                request.setAttribute("emailIngresado", email);
                request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
                return;
            }

            // Se renueva el id de sesion al autenticarse (evita fijacion de sesion).
            request.getSession(true);
            request.changeSessionId();
            HttpSession sesion = request.getSession();
            sesion.setAttribute(ATRIBUTO_SESION, usuario);
            response.sendRedirect(request.getContextPath() + "/inicio");
        } catch (SQLException excepcion) {
            throw new ServletException("Error de base de datos.", excepcion);
        }
    }
}
