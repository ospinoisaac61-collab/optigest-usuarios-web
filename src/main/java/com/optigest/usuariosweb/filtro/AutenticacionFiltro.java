package com.optigest.usuariosweb.filtro;

import com.optigest.usuariosweb.modelo.Usuario;
import com.optigest.usuariosweb.servlet.LoginServlet;
import com.optigest.usuariosweb.util.Permisos;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Protege los modulos en dos pasos: (1) si no hay sesion iniciada, redirige al
 * login; (2) si el rol del usuario no tiene permiso sobre el modulo pedido,
 * responde 403 con una pagina de "sin permiso".
 * /login, /logout y los recursos estaticos (css) no pasan por este filtro.
 */
@WebFilter(filterName = "AutenticacionFiltro",
        urlPatterns = {"/inicio", "/usuarios", "/productos", "/proveedores", "/compras"})
public class AutenticacionFiltro implements Filter {

    // init y destroy se declaran vacios porque Jetty 9.4 usa Servlet 3.1,
    // donde no existen como metodos por defecto de la interfaz Filter.
    @Override
    public void init(FilterConfig configuracion) {
    }

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest solicitud, ServletResponse respuesta, FilterChain cadena)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) solicitud;
        HttpServletResponse response = (HttpServletResponse) respuesta;

        HttpSession sesion = request.getSession(false);
        Usuario usuario = sesion == null ? null : (Usuario) sesion.getAttribute(LoginServlet.ATRIBUTO_SESION);

        if (usuario == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // La ruta (por ejemplo "/compras") indica el modulo que se quiere abrir.
        String modulo = request.getServletPath().substring(1);
        if (!Permisos.puedeAcceder(usuario.getRol(), modulo)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            request.getRequestDispatcher("/WEB-INF/views/sin-permiso.jsp").forward(request, response);
            return;
        }

        cadena.doFilter(solicitud, respuesta);
    }
}
