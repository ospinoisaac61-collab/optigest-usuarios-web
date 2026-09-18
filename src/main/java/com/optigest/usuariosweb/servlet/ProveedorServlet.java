package com.optigest.usuariosweb.servlet;

import com.optigest.usuariosweb.dao.ProveedorDAO;
import com.optigest.usuariosweb.dao.ProveedorDAOImpl;
import com.optigest.usuariosweb.modelo.Proveedor;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/** Servlet del módulo de Proveedores. Mismo patrón que UsuarioServlet. */
@WebServlet(name = "ProveedorServlet", urlPatterns = {"/proveedores"})
public class ProveedorServlet extends HttpServlet {

    private final ProveedorDAO proveedorDAO = new ProveedorDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");
        try {
            if ("nuevo".equals(accion)) {
                mostrarFormulario(request, response, new Proveedor());
            } else if ("editar".equals(accion)) {
                int id = Integer.parseInt(request.getParameter("id"));
                mostrarFormulario(request, response, proveedorDAO.consultarProveedorPorId(id));
            } else if ("eliminar".equals(accion)) {
                int id = Integer.parseInt(request.getParameter("id"));
                proveedorDAO.eliminarProveedor(id);
                response.sendRedirect(request.getContextPath() + "/proveedores");
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

        Proveedor proveedor = new Proveedor();
        proveedor.setNombre(request.getParameter("nombre"));
        proveedor.setTelefono(request.getParameter("telefono"));
        proveedor.setEmail(request.getParameter("email"));
        proveedor.setDireccion(request.getParameter("direccion"));

        try {
            if (idTexto == null || idTexto.isBlank()) {
                proveedorDAO.insertarProveedor(proveedor);
            } else {
                proveedor.setId(Integer.parseInt(idTexto));
                proveedorDAO.actualizarProveedor(proveedor);
            }
            response.sendRedirect(request.getContextPath() + "/proveedores");
        } catch (SQLException excepcion) {
            throw new ServletException("Error de base de datos.", excepcion);
        }
    }

    private void mostrarListado(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        List<Proveedor> proveedores = proveedorDAO.consultarProveedores();
        request.setAttribute("proveedores", proveedores);
        RequestDispatcher despachador = request.getRequestDispatcher("/WEB-INF/views/proveedores/lista.jsp");
        despachador.forward(request, response);
    }

    private void mostrarFormulario(HttpServletRequest request, HttpServletResponse response, Proveedor proveedor)
            throws ServletException, IOException {
        request.setAttribute("proveedor", proveedor);
        RequestDispatcher despachador = request.getRequestDispatcher("/WEB-INF/views/proveedores/formulario.jsp");
        despachador.forward(request, response);
    }
}
