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

<%int uidSet = 1;%>
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
            String success = request.getParameter("success");
            if ("1".equals(success)) {
            %>
                <p style="color:green;">Vous avez ajouté un Post.</p>
            <%
            } else if ("0".equals(success)) {
            %>
                <p style="color:red;">Erreur lors de la création du Post.</p>
            <%
            }
            %>
            <form class="mb-3" action="${pageContext.request.contextPath}/posts" method="post" enctype="multipart/form-data">
                <textarea class="form-control bg-dark text-white mb-2" name="contenu" rows="3" maxlength="150" placeholder="Exprimez-vous..." required></textarea>
                <input type="file" class="form-control bg-dark text-white mb-2" name="image" accept="image/*">
                <label for="duration" class="form-label text-white">Durée du post</label>
                <div class="input-group mb-2">
                    <input type="number" class="form-control bg-dark text-white" name="duree" min="1" max="365" placeholder="Durée">
                    <select class="form-select bg-dark text-white" name="dureeUnite">
                        <option value="hours">Heures</option>
                        <option value="days">Jours</option>
                    </select>
                </div>
                <input type="hidden" name="uid" value="<%= uidSet %>">
                <button type="submit" class="btn btn-primary w-100">Publier</button>
            </form>
        <%
        List<PostDetails> posts = (ArrayList<PostDetails>) request.getAttribute("posts");
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
                <a href="${pageContext.request.contextPath}/addFavori?pid=<%= p.getPid() %>&uid=<%= uidSet %>" class="btn btn-outline-warning btn-sm">⭐ Favoris</a>
            </footer>
            </article>
        <%
            }
        }
        %>
        </section>
        </section>

        <aside class="col-md-4">
            <h2 class="text-primary">Utilisateurs</h2>
            <ul class="list-group">
        <%
        List<User> users = (ArrayList<User>) request.getAttribute("users");
        if (users != null) {
            for(User u : users){
                if (u.getUid() == uidSet) {
                    continue;
                }
        %>
                <li class="border list-group-item d-flex align-items-center">
                    <img src="${pageContext.request.contextPath}/<%= u.getPdp() %>" alt="<%= u.getPseudo() %>" class="rounded-circle me-2" width="40">
                    <a href="${pageContext.request.contextPath}/user/<%= u.getIdPseudo() %>" class="text-decoration-none text-white"><%= u.getPseudo() %></a>
                    <a href="${pageContext.request.contextPath}/addFollow?follow=<%= u.getUid() %>&follower=<%= uidSet %>" class="btn btn-sm btn-outline-success ms-auto">+ Suivre</a>
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
