<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.optigest.usuariosweb.modelo.Usuario" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>OptiGest ERP - Formulario de usuario</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/estilos.css">
</head>
<body>
<%@ include file="/WEB-INF/views/nav.jspf" %>
<%
    // El servlet deja aqui el usuario a editar, o un Usuario vacio si es "nuevo".
    Usuario usuario = (Usuario) request.getAttribute("usuario");
    if (usuario == null) usuario = new Usuario();
    boolean esEdicion = usuario.getId() > 0;

    String[] roles = {"Administrador", "Vendedor", "Jefe de taller", "Jefe de compras", "Contador", "Bodega"};
%>
<div class="contenedor angosto">
    <div class="encabezado">
        <h1><%= esEdicion ? "Editar usuario" : "Nuevo usuario" %></h1>
        <a class="boton secundario" href="<%= request.getContextPath() %>/usuarios">&larr; Volver al listado</a>
    </div>

    <!-- Formulario HTML procesado por UsuarioServlet.doPost (metodo POST) -->
    <form class="formulario" method="post" action="<%= request.getContextPath() %>/usuarios">

        <input type="hidden" name="id" value="<%= esEdicion ? usuario.getId() : "" %>">

        <div class="campo">
            <label for="nombre">Nombre completo</label>
            <input type="text" id="nombre" name="nombre" required
                   value="<%= usuario.getNombre() != null ? usuario.getNombre() : "" %>">
        </div>

        <div class="campo">
            <label for="email">Correo electr&oacute;nico</label>
            <input type="email" id="email" name="email" required
                   value="<%= usuario.getEmail() != null ? usuario.getEmail() : "" %>">
        </div>

        <div class="campo">
            <label for="password">Contrase&ntilde;a<%= esEdicion ? " (dejar vacío para no cambiarla)" : "" %></label>
            <input type="password" id="password" name="password" <%= esEdicion ? "" : "required" %>>
        </div>

        <div class="campo">
            <label for="rol">Rol</label>
            <select id="rol" name="rol" required>
                <% for (String rol : roles) { %>
                <option value="<%= rol %>" <%= rol.equals(usuario.getRol()) ? "selected" : "" %>><%= rol %></option>
                <% } %>
            </select>
        </div>

        <div class="campo campo-checkbox">
            <label>
                <input type="checkbox" name="estado" <%= usuario.isEstado() || !esEdicion ? "checked" : "" %>>
                Usuario activo
            </label>
        </div>

        <button type="submit" class="boton">
            <%= esEdicion ? "Actualizar usuario" : "Crear usuario" %>
        </button>
    </form>
</div>
</body>
</html>
