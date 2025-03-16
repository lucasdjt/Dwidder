<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="modele.dto.PostDetails, modele.dto.User, java.util.List, java.util.ArrayList" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Accueil - Dwidder</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="icon" href="${pageContext.request.contextPath}/img/logo.ico">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<% 
int User_ID = (int) request.getSession().getAttribute("uid");
%>

<jsp:include page="header.jsp" />

<main class="container mt-4">
    <div class="row">
        <section class="col-md-8">
            <h2 class="text-primary">Accueil</h2>
            <% 
            String groupeSuccess = request.getParameter("groupe");
            if ("1".equals(groupeSuccess)) {
            %>
                <p style="color:green;">Vous avez créer un groupe.</p>
            <% 
            }
            %>
            <jsp:include page="form.jsp" />
        </section>

        <aside class="col-md-4">
            <h2 class="text-primary">Utilisateurs</h2>
            <ul class="list-group">
        <%
        List<User> users = (ArrayList<User>) request.getAttribute("users");
        if (users != null) {
            for(User u : users){
                if (u.getUid() == User_ID) {
                    continue;
                }
        %>
                <li class="border list-group-item d-flex align-items-center">
                    <img src="${pageContext.request.contextPath}/<%= u.getPdp() %>" alt="<%= u.getPseudo() %>" class="rounded-circle me-2" width="40">
                    <a href="${pageContext.request.contextPath}/user/<%= u.getIdPseudo() %>" class="text-decoration-none text-white"><%= u.getPseudo() %></a>
                    <a href="${pageContext.request.contextPath}/addFollow?follow=<%= u.getUid() %>&follower=<%= User_ID %>" class="btn btn-sm btn-outline-success ms-auto">+ Suivre</a>
                </li>
        <%}}%>
            </ul>
            <a href="user" class="btn btn-outline-primary mt-2 w-100">Afficher Plus</a>
        </aside>
    </div>
</main>

<jsp:include page="footer.jsp" />

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
