<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="false" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>OptiGest ERP - Sin permiso</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/estilos.css">
</head>
<body>
<%@ include file="/WEB-INF/views/nav.jspf" %>
<div class="contenedor angosto">
    <div class="alerta-error">
        <strong>Acceso denegado.</strong> Tu rol no tiene permiso para abrir este m&oacute;dulo.
    </div>
    <a class="boton" href="<%= request.getContextPath() %>/inicio">&larr; Volver al inicio</a>
</div>
</body>
</html>
