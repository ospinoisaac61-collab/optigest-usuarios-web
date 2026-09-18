<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.optigest.usuariosweb.modelo.Compra" %>
<%@ page import="com.optigest.usuariosweb.modelo.Proveedor" %>
<%@ page import="com.optigest.usuariosweb.modelo.Usuario" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>OptiGest ERP - Formulario de compra</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/estilos.css">
</head>
<body>
<%
    Compra compra = (Compra) request.getAttribute("compra");
    if (compra == null) compra = new Compra();
    boolean esEdicion = compra.getId() > 0;
    List<Proveedor> proveedores = (List<Proveedor>) request.getAttribute("proveedores");
    List<Usuario> usuarios = (List<Usuario>) request.getAttribute("usuarios");
    String[] estados = {"PENDIENTE", "COMPLETADA", "CANCELADA"};
%>
<div class="contenedor angosto">
    <div class="encabezado">
        <h1><%= esEdicion ? "Editar compra" : "Nueva compra" %></h1>
        <a class="boton secundario" href="<%= request.getContextPath() %>/compras">&larr; Volver al listado</a>
    </div>
    <form class="formulario" method="post" action="<%= request.getContextPath() %>/compras">
        <input type="hidden" name="id" value="<%= esEdicion ? compra.getId() : "" %>">

        <div class="campo">
            <label for="idProveedor">Proveedor</label>
            <select id="idProveedor" name="idProveedor" required>
                <% for (Proveedor p : proveedores) { %>
                <option value="<%= p.getId() %>" <%= p.getId() == compra.getIdProveedor() ? "selected" : "" %>><%= p.getNombre() %></option>
                <% } %>
            </select>
        </div>

        <div class="campo">
            <label for="idUsuario">Registrada por</label>
            <select id="idUsuario" name="idUsuario" required>
                <% for (Usuario u : usuarios) { %>
                <option value="<%= u.getId() %>" <%= u.getId() == compra.getIdUsuario() ? "selected" : "" %>><%= u.getNombre() %></option>
                <% } %>
            </select>
        </div>

        <div class="campo">
            <label for="total">Total (COP)</label>
            <input type="number" step="0.01" id="total" name="total" required value="<%= esEdicion ? compra.getTotal() : "" %>">
        </div>

        <div class="campo">
            <label for="estado">Estado</label>
            <select id="estado" name="estado" required>
                <% for (String estado : estados) { %>
                <option value="<%= estado %>" <%= estado.equals(compra.getEstado()) || (!esEdicion && estado.equals("PENDIENTE")) ? "selected" : "" %>><%= estado %></option>
                <% } %>
            </select>
        </div>

        <button type="submit" class="boton"><%= esEdicion ? "Actualizar compra" : "Registrar compra" %></button>
    </form>
</div>
</body>
</html>
