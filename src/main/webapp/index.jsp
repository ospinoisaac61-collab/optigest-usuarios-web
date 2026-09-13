<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    // Pagina de entrada: redirige al listado de usuarios (servlet, metodo GET).
    response.sendRedirect(request.getContextPath() + "/usuarios");
%>
