<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.ArrayList, java.util.List, modele.dto.PostDetails, modele.dto.User"%>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Profil de User - Dwidder</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="icon" href="${pageContext.request.contextPath}/img/logo.ico">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<% 
int User_ID = (int) session.getAttribute("me_uid");
%>

<jsp:include page="header.jsp" />


<main class="container mt-5 pt-4">
    <div class="row">
        <aside class="col-md-4">
            <div class="card text-center">
                <%
                User u = (User) session.getAttribute("userSearch");
                if (u != null) {
                %>
                <img src="${pageContext.request.contextPath}/<%= u.getPdp() %>" alt="<%= u.getPdp() %>" class="card-img-top rounded-circle mx-auto mt-3" style="width: 100px; height: 100px;">
                
                <div class="card-body">
                    <h3 class="card-title"><%= u.getPseudo() %></h3>
                    <p class="text-muted">@<%= u.getIdPseudo() %></p>
                    
                    <div class="card p-2 border">
                        <p class="mb-0"><%= u.getBio() %></p>
                    </div>
        
                    <ul class="list-group mt-3 text-start">
                        <li class="list-group-item"><strong>Nom : </strong><%= u.getNomUser() %> <%= u.getPrenom() %></li>
                        <li class="list-group-item"><strong>Date de naissance : </strong><%= u.getDnaiss() %></li>
                        <li class="list-group-item"><strong>Localisation : </strong><%= u.getLoca() %></li>
                        <li class="list-group-item"><strong>Date d'inscription : </strong><%= u.getDinscAsDate() %></li>
                    </ul>
                    <div class="d-flex flex-column align-items-stretch mt-3">
                        <a href="${pageContext.request.contextPath}/follow/follower/<%= u.getIdPseudo() %>" class="btn btn-outline-primary mb-2">Abonnés : <%= session.getAttribute("nombreFollowers") %></a>
                        <a href="${pageContext.request.contextPath}/follow/follow/<%=  u.getIdPseudo() %>" class="btn btn-outline-secondary mb-2">Abonnements : <%= session.getAttribute("nombreFollows") %></a>
                        <% if (User_ID == u.getUid()) { %>
                        <a href="${pageContext.request.contextPath}/favori" class="btn btn-outline-warning mb-2">Favoris</a>
                        <% } %>
                    </div>
                </div>
                <% } %>
            </div>
        </aside>

        <section class="col-md-8">
            <h2 class="text-primary">Profil de <%= u.getPseudo() %></h2>
            <jsp:include page="popUp.jsp" />
            <jsp:include page="post.jsp" />
        </section>
    </div>
</main>

<jsp:include page="footer.jsp" />

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
<%
session.removeAttribute("userSearch");
session.removeAttribute("nombreFollowers");
session.removeAttribute("nombreFollows");
%>