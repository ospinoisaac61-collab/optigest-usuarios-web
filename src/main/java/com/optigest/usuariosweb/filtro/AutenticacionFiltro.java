package com.optigest.usuariosweb.filtro;

import com.optigest.usuariosweb.servlet.LoginServlet;

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
 * Protege los modulos: si no hay una sesion iniciada, redirige al login.
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
        boolean autenticado = sesion != null && sesion.getAttribute(LoginServlet.ATRIBUTO_SESION) != null;

        if (autenticado) {
            cadena.doFilter(solicitud, respuesta);
        } else {
            response.sendRedirect(request.getContextPath() + "/login");
        }
    }
}
