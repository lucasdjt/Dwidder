<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="modele.dto.PostDetails, java.util.List, java.util.ArrayList" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Liste des Favoris - Dwidder</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="icon" href="${pageContext.request.contextPath}/img/logo.ico">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<% 
int User_ID = (int) request.getSession().getAttribute("uid");
%>

<jsp:include page="header.jsp" />

<main class="container mt-5 pt-4">
    <section class="col-md-8 offset-md-2">
        <%
        List<PostDetails> posts = (ArrayList<PostDetails>) request.getAttribute("favoris");
        %>
        <h3 class="title">Liste des posts favoris (<%= posts.size() %>)</h3>
        <%
        if (posts != null) {
            for(PostDetails p : posts){
        %>
            <article class="card mb-3">
            <header class="card-header d-flex align-items-center">
                <img src="${pageContext.request.contextPath}/<%= p.getPdp() %>" alt="<%= p.getPdp() %>" class="rounded-circle me-2" width="40">
                <div>
                <a href="${pageContext.request.contextPath}/user/<%= p.getIdPseudo() %>" class="text-decoration-none text-white"><h6 class="mb-0"><%= p.getPseudo() %></h6></a>
                <small class="text-muted">@<%= p.getIdPseudo() %> - <%= p.getDpubAsDate() %></small>
                </div>
            </header>
            <main class="card-body">
                <p><%= p.getContenu() %></p>
                <% if(p.getMedia() != null) { %>
                <img src="${pageContext.request.contextPath}/<%= p.getMedia() %>" alt="<%= p.getMedia() %>" class="rounded w-100">
                <% } %>
                <% if(p.getDuree() < 700) { %>
                <blockquote class="text-muted small">Il reste <%= p.getDuree() %>h à ce post avant d'être supprimé</blockquote>
                <% } %>
            </main>
            <footer class="card-footer d-flex justify-content-around">
                <a href="${pageContext.request.contextPath}/addLike?pid=<%= p.getPid() %>&uid=<%= p.getUid() %>" class="btn btn-outline-primary btn-sm">👍 <%= p.getNbLikes() %></a>
                <a href="${pageContext.request.contextPath}/posts/<%= p.getPid() %>" class="btn btn-outline-secondary btn-sm">💬 <%= p.getNbComm() %></a>
                <a href="${pageContext.request.contextPath}/addFavori?pid=<%= p.getPid() %>&uid=<%= User_ID %>" class="btn btn-outline-warning btn-sm">⭐ Retirer des favoris</a>
            </footer>
            </article>
        <%
            }
        }
        %>
    </section>
</main>

<jsp:include page="footer.jsp" />

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>