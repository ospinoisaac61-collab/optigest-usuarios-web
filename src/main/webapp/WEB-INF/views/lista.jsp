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
        List<Usuario> usuarios = (List<Usuario>) request.getAttribute("usuarios");
    %>

    <table class="tabla">
        <thead>
        <tr>
            <th>ID</th>
            <th>Nombre</th>
            <th>Usuario</th>
            <th>Correo</th>
            <th>Celular</th>
            <th>Rol</th>
            <th>Estado</th>
            <th>Acciones</th>
        </tr>
        </thead>
        <tbody>
        <% if (usuarios == null || usuarios.isEmpty()) { %>
        <tr>
            <td colspan="8" class="vacio">No hay usuarios registrados todav&iacute;a.</td>
        </tr>
        <% } else {
            for (Usuario usuario : usuarios) {
        %>
        <tr>
            <td>#<%= usuario.getId() %></td>
            <td><%= usuario.getNombre() %></td>
            <td><code><%= usuario.getUsuario() %></code></td>
            <td><%= usuario.getCorreo() %></td>
            <td><%= usuario.getCelular() %></td>
            <td><span class="badge"><%= usuario.getRol() %></span></td>
            <td>
                <span class="estado <%= "Activo".equals(usuario.getEstado()) ? "activo" : "inactivo" %>">
                    <%= usuario.getEstado() %>
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
