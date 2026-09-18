package com.optigest.usuariosweb.servlet;

import com.optigest.usuariosweb.dao.CompraDAO;
import com.optigest.usuariosweb.dao.CompraDAOImpl;
import com.optigest.usuariosweb.dao.ProveedorDAO;
import com.optigest.usuariosweb.dao.ProveedorDAOImpl;
import com.optigest.usuariosweb.dao.UsuarioDAO;
import com.optigest.usuariosweb.dao.UsuarioDAOImpl;
import com.optigest.usuariosweb.modelo.Compra;

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
 * Servlet del módulo de Compras. Además del CRUD, el formulario necesita
 * las listas de proveedores y usuarios para mostrarlas como listas desplegables
 * (una compra pertenece a un proveedor y fue registrada por un usuario).
 */
@WebServlet(name = "CompraServlet", urlPatterns = {"/compras"})
public class CompraServlet extends HttpServlet {

    private final CompraDAO compraDAO = new CompraDAOImpl();
    private final ProveedorDAO proveedorDAO = new ProveedorDAOImpl();
    private final UsuarioDAO usuarioDAO = new UsuarioDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");
        try {
            if ("nuevo".equals(accion)) {
                mostrarFormulario(request, response, new Compra());
            } else if ("editar".equals(accion)) {
                int id = Integer.parseInt(request.getParameter("id"));
                mostrarFormulario(request, response, compraDAO.consultarCompraPorId(id));
            } else if ("eliminar".equals(accion)) {
                int id = Integer.parseInt(request.getParameter("id"));
                compraDAO.eliminarCompra(id);
                response.sendRedirect(request.getContextPath() + "/compras");
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

        Compra compra = new Compra();
        compra.setTotal(Double.parseDouble(request.getParameter("total")));
        compra.setEstado(request.getParameter("estado"));
        compra.setIdProveedor(Integer.parseInt(request.getParameter("idProveedor")));
        compra.setIdUsuario(Integer.parseInt(request.getParameter("idUsuario")));

        try {
            if (idTexto == null || idTexto.isBlank()) {
                compraDAO.insertarCompra(compra);
            } else {
                compra.setId(Integer.parseInt(idTexto));
                compraDAO.actualizarCompra(compra);
            }
            response.sendRedirect(request.getContextPath() + "/compras");
        } catch (SQLException excepcion) {
            throw new ServletException("Error de base de datos.", excepcion);
        }
    }

    private void mostrarListado(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        List<Compra> compras = compraDAO.consultarCompras();
        request.setAttribute("compras", compras);
        RequestDispatcher despachador = request.getRequestDispatcher("/WEB-INF/views/compras/lista.jsp");
        despachador.forward(request, response);
    }

    private void mostrarFormulario(HttpServletRequest request, HttpServletResponse response, Compra compra)
            throws ServletException, IOException, SQLException {
        request.setAttribute("compra", compra);
        request.setAttribute("proveedores", proveedorDAO.consultarProveedores());
        request.setAttribute("usuarios", usuarioDAO.consultarUsuarios());
        RequestDispatcher despachador = request.getRequestDispatcher("/WEB-INF/views/compras/formulario.jsp");
        despachador.forward(request, response);
    }
}
