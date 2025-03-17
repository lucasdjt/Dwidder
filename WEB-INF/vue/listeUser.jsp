<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.ArrayList, java.util.List, modele.dto.User"%>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Liste des Users - Dwidder</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="icon" href="${pageContext.request.contextPath}/img/logo.ico">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<% 
int User_ID = (int) request.getSession().getAttribute("uid");
List<Integer> listFollowUser = (List<Integer>) request.getSession().getAttribute("listFollowUser");
List<Integer> listFollowersUser = (List<Integer>) request.getSession().getAttribute("listFollowersUser");
%>

<jsp:include page="header.jsp" />

<main class="container mt-4">
        <h2 class="text-primary">Liste de compte</h2>
        <ul class="list-group">
            <%
                List<User> list = (ArrayList<User>) request.getAttribute("listFollow");
                if (list != null) {
                    for(User u : list){
            %>
            <li class="border list-group-item d-flex align-items-center">
                <img src="${pageContext.request.contextPath}/<%= u.getPdp() %>" alt="<%= u.getPdp() %>" class="rounded-circle me-2" width="40">
                <div>
                    <a href="${pageContext.request.contextPath}/user/<%= u.getIdPseudo() %>" class="text-decoration-none text-white"><h6 class="mb-0"><%= u.getPseudo() %></h6></a>
                    <small class="text-muted">@<%= u.getIdPseudo() %>
                    <% if (listFollowersUser.contains(u.getUid())) { %>
                            <span class="text-success"> - Cette personne vous suit</span>
                    <% } %>
                    </small>
                    <p class="mb-1"><%= u.getBio() %></p>
                </div>
                <% if(u.getUid() != User_ID){ %>
                <div class="ms-auto">
                        <% if (!listFollowUser.contains(u.getUid())) { %>
                            <a href="${pageContext.request.contextPath}/addFollow?follow=<%= u.getUid() %>&follower=<%= User_ID %>" class="btn btn-sm btn-outline-success">+ Suivre</a>
                        <% } else { %>
                            <a href="${pageContext.request.contextPath}/addFollow?follow=<%= u.getUid() %>&follower=<%= User_ID %>" class="btn btn-sm btn-outline-danger">Ne plus suivre</a>
                        <% } %>
                </div>
                <% } %>
            </li>
            <% }} %>
        </ul>
</main>

<jsp:include page="footer.jsp" />

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
