<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.optigest.usuariosweb.modelo.Producto" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>OptiGest ERP - Productos</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/estilos.css">
</head>
<body>
<div class="contenedor">
    <div class="encabezado">
        <h1>OptiGest ERP &mdash; Productos</h1>
        <a class="boton" href="<%= request.getContextPath() %>/productos?accion=nuevo">+ Nuevo producto</a>
    </div>
    <% List<Producto> productos = (List<Producto>) request.getAttribute("productos"); %>
    <table class="tabla">
        <thead>
        <tr><th>ID</th><th>Nombre</th><th>Tipo</th><th>Precio unitario</th><th>Stock</th><th>Acciones</th></tr>
        </thead>
        <tbody>
        <% if (productos == null || productos.isEmpty()) { %>
        <tr><td colspan="6" class="vacio">No hay productos registrados todav&iacute;a.</td></tr>
        <% } else {
            for (Producto p : productos) {
        %>
        <tr>
            <td>#<%= p.getId() %></td>
            <td><%= p.getNombre() %></td>
            <td><span class="badge"><%= p.getTipo() %></span></td>
            <td>$<%= String.format("%,.2f", p.getPrecioUnitario()) %></td>
            <td><%= p.getStockActual() %></td>
            <td class="acciones">
                <a href="<%= request.getContextPath() %>/productos?accion=editar&id=<%= p.getId() %>">Editar</a>
                <a class="enlace-peligro" href="<%= request.getContextPath() %>/productos?accion=eliminar&id=<%= p.getId() %>"
                   onclick="return confirm('¿Eliminar este producto?');">Eliminar</a>
            </td>
        </tr>
        <% } } %>
        </tbody>
    </table>
</div>
</body>
</html>
