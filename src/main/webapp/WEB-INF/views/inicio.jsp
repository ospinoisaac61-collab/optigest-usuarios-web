<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.optigest.usuariosweb.modelo.Usuario" %>
<%@ page import="com.optigest.usuariosweb.servlet.LoginServlet" %>
<%@ page import="com.optigest.usuariosweb.util.Html" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>OptiGest ERP - Inicio</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/estilos.css">
</head>
<body>
<%@ include file="/WEB-INF/views/nav.jspf" %>
<%
    Usuario sesionActual = (Usuario) session.getAttribute(LoginServlet.ATRIBUTO_SESION);
    String ctx = request.getContextPath();
%>
<div class="contenedor">
    <div class="encabezado">
        <div>
            <h1>Bienvenido, <%= Html.escapar(sesionActual.getNombre()) %></h1>
            <p class="subtitulo">Resumen del sistema &middot; rol: <%= Html.escapar(sesionActual.getRol()) %></p>
        </div>
    </div>

    <!-- Los totales los calcula InicioServlet consultando la base de datos -->
    <div class="tarjetas">
        <a class="tarjeta-kpi" href="<%= ctx %>/usuarios">
            <span class="kpi-etiqueta">Usuarios</span>
            <span class="kpi-valor"><%= request.getAttribute("totalUsuarios") %></span>
        </a>
        <a class="tarjeta-kpi" href="<%= ctx %>/productos">
            <span class="kpi-etiqueta">Productos</span>
            <span class="kpi-valor"><%= request.getAttribute("totalProductos") %></span>
        </a>
        <a class="tarjeta-kpi" href="<%= ctx %>/proveedores">
            <span class="kpi-etiqueta">Proveedores</span>
            <span class="kpi-valor"><%= request.getAttribute("totalProveedores") %></span>
        </a>
        <a class="tarjeta-kpi" href="<%= ctx %>/compras">
            <span class="kpi-etiqueta">Compras</span>
            <span class="kpi-valor"><%= request.getAttribute("totalCompras") %></span>
            <span class="kpi-nota">$<%= String.format("%,.2f", (Double) request.getAttribute("montoCompras")) %> en total</span>
        </a>
    </div>
</div>
</body>
</html>
