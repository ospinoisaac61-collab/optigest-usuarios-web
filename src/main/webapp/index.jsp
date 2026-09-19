<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    // Pagina de entrada: va al inicio; si no hay sesion, el filtro redirige al login.
    response.sendRedirect(request.getContextPath() + "/inicio");
%>
