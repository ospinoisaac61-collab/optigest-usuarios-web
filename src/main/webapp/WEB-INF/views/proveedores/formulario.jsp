<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.optigest.usuariosweb.modelo.Proveedor" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>OptiGest ERP - Formulario de proveedor</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/estilos.css">
</head>
<body>
<%
    Proveedor proveedor = (Proveedor) request.getAttribute("proveedor");
    if (proveedor == null) proveedor = new Proveedor();
    boolean esEdicion = proveedor.getId() > 0;
%>
<div class="contenedor angosto">
    <div class="encabezado">
        <h1><%= esEdicion ? "Editar proveedor" : "Nuevo proveedor" %></h1>
        <a class="boton secundario" href="<%= request.getContextPath() %>/proveedores">&larr; Volver al listado</a>
    </div>
    <form class="formulario" method="post" action="<%= request.getContextPath() %>/proveedores">
        <input type="hidden" name="id" value="<%= esEdicion ? proveedor.getId() : "" %>">
        <div class="campo">
            <label for="nombre">Nombre</label>
            <input type="text" id="nombre" name="nombre" required value="<%= proveedor.getNombre() != null ? proveedor.getNombre() : "" %>">
        </div>
        <div class="campo">
            <label for="telefono">Tel&eacute;fono</label>
            <input type="text" id="telefono" name="telefono" value="<%= proveedor.getTelefono() != null ? proveedor.getTelefono() : "" %>">
        </div>
        <div class="campo">
            <label for="email">Correo electr&oacute;nico</label>
            <input type="email" id="email" name="email" value="<%= proveedor.getEmail() != null ? proveedor.getEmail() : "" %>">
        </div>
        <div class="campo">
            <label for="direccion">Direcci&oacute;n</label>
            <input type="text" id="direccion" name="direccion" value="<%= proveedor.getDireccion() != null ? proveedor.getDireccion() : "" %>">
        </div>
        <button type="submit" class="boton"><%= esEdicion ? "Actualizar proveedor" : "Crear proveedor" %></button>
    </form>
</div>
</body>
</html>
