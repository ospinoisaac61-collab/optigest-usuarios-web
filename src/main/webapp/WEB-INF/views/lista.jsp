<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.optigest.usuariosweb.modelo.Usuario" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>OptiGest ERP - Usuarios</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/estilos.css">
</head>
<body>
<div class="contenedor">
    <div class="encabezado">
        <h1>OptiGest ERP &mdash; Gesti&oacute;n de Usuarios</h1>
        <a class="boton" href="<%= request.getContextPath() %>/usuarios?accion=nuevo">+ Nuevo usuario</a>
    </div>

    <%
        // La lista de usuarios la deja aqui el servlet (metodo doGet) antes de reenviar a esta vista.
        List<Usuario> usuarios = (List<Usuario>) request.getAttribute("usuarios");
    %>

    <table class="tabla">
        <thead>
        <tr>
            <th>ID</th>
            <th>Nombre</th>
            <th>Email</th>
            <th>Rol</th>
            <th>Estado</th>
            <th>Acciones</th>
        </tr>
        </thead>
        <tbody>
        <% if (usuarios == null || usuarios.isEmpty()) { %>
        <tr>
            <td colspan="6" class="vacio">No hay usuarios registrados todav&iacute;a.</td>
        </tr>
        <% } else {
            for (Usuario usuario : usuarios) {
        %>
        <tr>
            <td>#<%= usuario.getId() %></td>
            <td><%= usuario.getNombre() %></td>
            <td><%= usuario.getEmail() %></td>
            <td><span class="badge"><%= usuario.getRol() %></span></td>
            <td>
                <span class="estado <%= usuario.isEstado() ? "activo" : "inactivo" %>">
                    <%= usuario.isEstado() ? "Activo" : "Inactivo" %>
                </span>
            </td>
            <td class="acciones">
                <a href="<%= request.getContextPath() %>/usuarios?accion=editar&id=<%= usuario.getId() %>">Editar</a>
                <a class="enlace-peligro"
                   href="<%= request.getContextPath() %>/usuarios?accion=eliminar&id=<%= usuario.getId() %>"
                   onclick="return confirm('¿Eliminar el usuario <%= usuario.getNombre() %>? Esta acción no se puede deshacer.');">Eliminar</a>
            </td>
        </tr>
        <% }
        } %>
        </tbody>
    </table>
</div>
</body>
</html>
