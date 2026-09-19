<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.optigest.usuariosweb.util.Html" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>OptiGest ERP - Iniciar sesión</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/estilos.css">
</head>
<body class="fondo-login">
<div class="tarjeta-login">
    <h1>OptiGest ERP</h1>
    <p class="subtitulo">Sistema ERP &middot; Aluminio &amp; Vidrio</p>

    <% String error = (String) request.getAttribute("error"); %>
    <% if (error != null) { %>
    <div class="alerta-error"><%= Html.escapar(error) %></div>
    <% } %>

    <!-- Formulario procesado por LoginServlet.doPost (metodo POST) -->
    <form method="post" action="<%= request.getContextPath() %>/login" class="formulario-login">
        <div class="campo">
            <label for="email">Correo electr&oacute;nico</label>
            <input type="email" id="email" name="email" required autofocus
                   value="<%= Html.escapar((String) request.getAttribute("emailIngresado")) %>">
        </div>
        <div class="campo">
            <label for="password">Contrase&ntilde;a</label>
            <input type="password" id="password" name="password" required>
        </div>
        <button type="submit" class="boton boton-ancho">Ingresar al sistema</button>
    </form>

    <p class="pie-login">OptiGest ERP v1.0 &mdash; SENA &middot; Ficha 3186674</p>
</div>
</body>
</html>
