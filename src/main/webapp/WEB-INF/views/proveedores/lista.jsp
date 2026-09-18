<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.optigest.usuariosweb.modelo.Proveedor" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>OptiGest ERP - Proveedores</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/estilos.css">
</head>
<body>
<div class="contenedor">
    <div class="encabezado">
        <h1>OptiGest ERP &mdash; Proveedores</h1>
        <a class="boton" href="<%= request.getContextPath() %>/proveedores?accion=nuevo">+ Nuevo proveedor</a>
    </div>
    <% List<Proveedor> proveedores = (List<Proveedor>) request.getAttribute("proveedores"); %>
    <table class="tabla">
        <thead>
        <tr><th>ID</th><th>Nombre</th><th>Tel&eacute;fono</th><th>Email</th><th>Direcci&oacute;n</th><th>Acciones</th></tr>
        </thead>
        <tbody>
        <% if (proveedores == null || proveedores.isEmpty()) { %>
        <tr><td colspan="6" class="vacio">No hay proveedores registrados todav&iacute;a.</td></tr>
        <% } else {
            for (Proveedor p : proveedores) {
        %>
        <tr>
            <td>#<%= p.getId() %></td>
            <td><%= p.getNombre() %></td>
            <td><%= p.getTelefono() != null ? p.getTelefono() : "" %></td>
            <td><%= p.getEmail() != null ? p.getEmail() : "" %></td>
            <td><%= p.getDireccion() != null ? p.getDireccion() : "" %></td>
            <td class="acciones">
                <a href="<%= request.getContextPath() %>/proveedores?accion=editar&id=<%= p.getId() %>">Editar</a>
                <a class="enlace-peligro" href="<%= request.getContextPath() %>/proveedores?accion=eliminar&id=<%= p.getId() %>"
                   onclick="return confirm('¿Eliminar este proveedor?');">Eliminar</a>
            </td>
        </tr>
        <% } } %>
        </tbody>
    </table>
</div>
</body>
</html>
