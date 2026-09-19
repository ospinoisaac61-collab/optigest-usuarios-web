<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.optigest.usuariosweb.modelo.Compra" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>OptiGest ERP - Compras</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/estilos.css">
</head>
<body>
<%@ include file="/WEB-INF/views/nav.jspf" %>
<div class="contenedor">
    <div class="encabezado">
        <h1>OptiGest ERP &mdash; Compras</h1>
        <a class="boton" href="<%= request.getContextPath() %>/compras?accion=nuevo">+ Nueva compra</a>
    </div>
    <% List<Compra> compras = (List<Compra>) request.getAttribute("compras"); %>
    <table class="tabla">
        <thead>
        <tr><th>ID</th><th>Fecha</th><th>Proveedor</th><th>Registrada por</th><th>Total</th><th>Estado</th><th>Acciones</th></tr>
        </thead>
        <tbody>
        <% if (compras == null || compras.isEmpty()) { %>
        <tr><td colspan="7" class="vacio">No hay compras registradas todav&iacute;a.</td></tr>
        <% } else {
            for (Compra c : compras) {
        %>
        <tr>
            <td>#<%= c.getId() %></td>
            <td><%= c.getFechaCompra() %></td>
            <td><%= c.getNombreProveedor() %></td>
            <td><%= c.getNombreUsuario() %></td>
            <td>$<%= String.format("%,.2f", c.getTotal()) %></td>
            <td><span class="badge"><%= c.getEstado() %></span></td>
            <td class="acciones">
                <a href="<%= request.getContextPath() %>/compras?accion=editar&id=<%= c.getId() %>">Editar</a>
                <a class="enlace-peligro" href="<%= request.getContextPath() %>/compras?accion=eliminar&id=<%= c.getId() %>"
                   onclick="return confirm('¿Eliminar esta compra?');">Eliminar</a>
            </td>
        </tr>
        <% } } %>
        </tbody>
    </table>
</div>
</body>
</html>
