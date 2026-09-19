package com.optigest.usuariosweb.servlet;

import com.optigest.usuariosweb.dao.EstadisticasDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/** Dashboard de inicio: muestra conteos reales de cada modulo desde la base de datos. */
@WebServlet(name = "InicioServlet", urlPatterns = {"/inicio"})
public class InicioServlet extends HttpServlet {

    private final EstadisticasDAO estadisticasDAO = new EstadisticasDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            request.setAttribute("totalUsuarios", estadisticasDAO.contarUsuarios());
            request.setAttribute("totalProductos", estadisticasDAO.contarProductos());
            request.setAttribute("totalProveedores", estadisticasDAO.contarProveedores());
            request.setAttribute("totalCompras", estadisticasDAO.contarCompras());
            request.setAttribute("montoCompras", estadisticasDAO.sumarTotalCompras());
        } catch (SQLException excepcion) {
            throw new ServletException("Error de base de datos.", excepcion);
        }
        request.getRequestDispatcher("/WEB-INF/views/inicio.jsp").forward(request, response);
    }
}
