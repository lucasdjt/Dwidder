<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<% 
String User_PSEUDO = String.valueOf(request.getSession().getAttribute("pseudo"));
%>

<header class="d-flex justify-content-between align-items-center p-2 bg-dark">
    <a class="btn btn-outline-light" id="parametres" href="${pageContext.request.contextPath}/parametres">⚙️</a>
    <img src="${pageContext.request.contextPath}/img/TexteLogo.png" alt="Dwidder" class="mx-auto d-block" width="140">
    <a class="btn btn-outline-light" id="profil" href="${pageContext.request.contextPath}/user/<%= User_PSEUDO %>">Profil</a>
</header>