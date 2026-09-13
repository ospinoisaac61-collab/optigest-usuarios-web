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
<%
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
            <label for="usuario">Nombre de usuario</label>
            <input type="text" id="usuario" name="usuario" required
                   value="<%= usuario.getUsuario() != null ? usuario.getUsuario() : "" %>">
        </div>

        <div class="campo">
            <label for="correo">Correo electr&oacute;nico</label>
            <input type="email" id="correo" name="correo" required
                   value="<%= usuario.getCorreo() != null ? usuario.getCorreo() : "" %>">
        </div>

        <div class="campo">
            <label for="clave">Contrase&ntilde;a<%= esEdicion ? " (dejar vacío para no cambiarla)" : "" %></label>
            <input type="password" id="clave" name="clave" <%= esEdicion ? "" : "required" %>>
        </div>

        <div class="campo">
            <label for="celular">Celular</label>
            <input type="text" id="celular" name="celular"
                   value="<%= usuario.getCelular() != null ? usuario.getCelular() : "" %>">
        </div>

        <div class="campo">
            <label for="rol">Rol</label>
            <select id="rol" name="rol" required>
                <% for (String rol : roles) { %>
                <option value="<%= rol %>" <%= rol.equals(usuario.getRol()) ? "selected" : "" %>><%= rol %></option>
                <% } %>
            </select>
        </div>

        <div class="campo">
            <label for="estado">Estado</label>
            <select id="estado" name="estado" required>
                <option value="Activo" <%= "Activo".equals(usuario.getEstado()) || !esEdicion ? "selected" : "" %>>Activo</option>
                <option value="Inactivo" <%= "Inactivo".equals(usuario.getEstado()) ? "selected" : "" %>>Inactivo</option>
            </select>
        </div>

        <button type="submit" class="boton">
            <%= esEdicion ? "Actualizar usuario" : "Crear usuario" %>
        </button>
    </form>
</div>
</body>
</html>
