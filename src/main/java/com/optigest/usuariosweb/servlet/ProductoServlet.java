package com.optigest.usuariosweb.servlet;

import com.optigest.usuariosweb.dao.ProductoDAO;
import com.optigest.usuariosweb.dao.ProductoDAOImpl;
import com.optigest.usuariosweb.modelo.Producto;

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
 * Servlet del módulo de Productos. Mismo patrón que UsuarioServlet:
 * GET lista/muestra formulario/elimina, POST crea o actualiza.
 */
@WebServlet(name = "ProductoServlet", urlPatterns = {"/productos"})
public class ProductoServlet extends HttpServlet {

    private final ProductoDAO productoDAO = new ProductoDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");
        try {
            if ("nuevo".equals(accion)) {
                mostrarFormulario(request, response, new Producto());
            } else if ("editar".equals(accion)) {
                int id = Integer.parseInt(request.getParameter("id"));
                mostrarFormulario(request, response, productoDAO.consultarProductoPorId(id));
            } else if ("eliminar".equals(accion)) {
                int id = Integer.parseInt(request.getParameter("id"));
                productoDAO.eliminarProducto(id);
                response.sendRedirect(request.getContextPath() + "/productos");
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
        String tipo = request.getParameter("tipo");
        double precio = Double.parseDouble(request.getParameter("precioUnitario"));
        int stock = Integer.parseInt(request.getParameter("stockActual"));

        Producto producto = new Producto();
        producto.setNombre(nombre);
        producto.setTipo(tipo);
        producto.setPrecioUnitario(precio);
        producto.setStockActual(stock);

        try {
            if (idTexto == null || idTexto.isBlank()) {
                productoDAO.insertarProducto(producto);
            } else {
                producto.setId(Integer.parseInt(idTexto));
                productoDAO.actualizarProducto(producto);
            }
            response.sendRedirect(request.getContextPath() + "/productos");
        } catch (SQLException excepcion) {
            throw new ServletException("Error de base de datos.", excepcion);
        }
    }

    private void mostrarListado(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        List<Producto> productos = productoDAO.consultarProductos();
        request.setAttribute("productos", productos);
        RequestDispatcher despachador = request.getRequestDispatcher("/WEB-INF/views/productos/lista.jsp");
        despachador.forward(request, response);
    }

    private void mostrarFormulario(HttpServletRequest request, HttpServletResponse response, Producto producto)
            throws ServletException, IOException {
        request.setAttribute("producto", producto);
        RequestDispatcher despachador = request.getRequestDispatcher("/WEB-INF/views/productos/formulario.jsp");
        despachador.forward(request, response);
    }
}
