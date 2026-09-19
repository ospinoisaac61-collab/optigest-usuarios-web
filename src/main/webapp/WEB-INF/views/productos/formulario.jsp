<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.optigest.usuariosweb.modelo.Producto" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>OptiGest ERP - Formulario de producto</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/estilos.css">
</head>
<body>
<%@ include file="/WEB-INF/views/nav.jspf" %>
<%
    Producto producto = (Producto) request.getAttribute("producto");
    if (producto == null) producto = new Producto();
    boolean esEdicion = producto.getId() > 0;
%>
<div class="contenedor angosto">
    <div class="encabezado">
        <h1><%= esEdicion ? "Editar producto" : "Nuevo producto" %></h1>
        <a class="boton secundario" href="<%= request.getContextPath() %>/productos">&larr; Volver al listado</a>
    </div>
    <form class="formulario" method="post" action="<%= request.getContextPath() %>/productos">
        <input type="hidden" name="id" value="<%= esEdicion ? producto.getId() : "" %>">
        <div class="campo">
            <label for="nombre">Nombre</label>
            <input type="text" id="nombre" name="nombre" required value="<%= producto.getNombre() != null ? producto.getNombre() : "" %>">
        </div>
        <div class="campo">
            <label for="tipo">Tipo</label>
            <input type="text" id="tipo" name="tipo" required value="<%= producto.getTipo() != null ? producto.getTipo() : "" %>">
        </div>
        <div class="campo">
            <label for="precioUnitario">Precio unitario (COP)</label>
            <input type="number" step="0.01" id="precioUnitario" name="precioUnitario" required value="<%= esEdicion ? producto.getPrecioUnitario() : "" %>">
        </div>
        <div class="campo">
            <label for="stockActual">Stock actual</label>
            <input type="number" id="stockActual" name="stockActual" required value="<%= esEdicion ? producto.getStockActual() : "0" %>">
        </div>
        <button type="submit" class="boton"><%= esEdicion ? "Actualizar producto" : "Crear producto" %></button>
    </form>
</div>
</body>
</html>
