<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.ArrayList, java.util.List, modele.dto.PostDetails, java.util.Map" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Liste des Réponses au post - Dwidder</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="icon" href="${pageContext.request.contextPath}/img/logo.ico">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<% 
int User_ID = (int) session.getAttribute("me_uid");
List<Integer> listFavoriUser = (List<Integer>) session.getAttribute("me_listFavori");
List<Integer> listFollowUser = (List<Integer>) session.getAttribute("me_listFollow");
List<Integer> listFollowersUser = (List<Integer>) session.getAttribute("me_listFollowers");
Map<Integer, String> listReactionsUser = (Map<Integer, String>) session.getAttribute("me_listReactions");
%>

<jsp:include page="header.jsp" />

<main class="container mt-5 pt-4">
    <div class="row">
        <aside class="col-md-4">
            <%
            PostDetails post = (PostDetails) session.getAttribute("post");
            if (post != null) {
            %>
            <article class="card mb-3">
                <header class="card-header d-flex align-items-center">
                    <img src="${pageContext.request.contextPath}/<%= post.getPdp() %>" alt="Pdp de <%= post.getIdPseudo() %>" class="rounded-circle me-2" width="40">
                        <div>
                            <a href="${pageContext.request.contextPath}/user/<%= post.getIdPseudo() %>" class="text-decoration-none text-white"><h6 class="mb-0"><%= post.getPseudo() %></h6></a>
                            <small class="text-muted">@<%= post.getIdPseudo() %> - <%= post.getDpubAsDate() %>
                                <% if (listFollowersUser.contains(post.getUid())) { %>
                                    <span class="text-success"> - Cette personne vous suit</span>
                                <% } %>
                            </small>
                            <% if(post.getPidParent() != null && post.getPidParent() != 0){ %>
                                <blockquote class="text-muted small">Ce post répond à ce <a href="${pageContext.request.contextPath}/post/<%= post.getPidParent() %>">post</a></blockquote>
                            <% } else if(post.getNomGrp() != null){ %>
                                <blockquote class="text-muted small">Ce post vient du groupe <em><%= post.getNomGrp() %></em></blockquote>
                            <% } %>
                        </div>
                    <div class="ms-auto">
                        <% if (post.getUid() != User_ID) { %>
                            <% if (!listFollowUser.contains(post.getUid())) { %>
                                <a href="${pageContext.request.contextPath}/follow/addFollow/<%= post.getUid() %>" class="btn btn-sm btn-outline-success">+ Suivre</a>
                            <% } else { %>
                                <a href="${pageContext.request.contextPath}/follow/addFollow/<%= post.getUid() %>" class="btn btn-sm btn-outline-danger">Ne plus suivre</a>
                            <% } %>
                        <% } else { %>
                            <a href="${pageContext.request.contextPath}/reaction/<%= post.getPid() %>" class="btn btn-sm btn-outline-primary">Voir les réactions</a>
                        <% } %>
                    </div>
                </header>
                    <main class="card-body">
                            <p><%= post.getContenu() %></p>
                        <% if(post.getMedia() != null) { %>
                            <img src="${pageContext.request.contextPath}/<%= post.getMedia() %>" alt="Image accompagnant le post" class="rounded w-100">
                        <% } %>
                        <% if(post.getDuree() < 336) { %>
                            <blockquote class="text-muted small">Il reste <%= post.getDuree() %>h à ce post avant d'être retiré de l'affichage</blockquote>
                        <% } %>
                    </main>
                    <footer class="card-footer d-flex justify-content-around">
                        <div class="btn-group">
                            <button type="button" class="btn btn-outline-primary btn-sm dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false">
                                <% if(listReactionsUser.containsKey(post.getPid())) { %>
                                    <%= listReactionsUser.get(post.getPid()) %> <%= post.getNbLikes() %> 
                                <% } else { %>
                                    Ajouter une Réaction <%= post.getNbLikes() %>
                                <% } %>
                            </button>
                            <ul class="dropdown-menu" style="min-width: auto;">
                                <li><a class="dropdown-item d-flex align-items-center" href="${pageContext.request.contextPath}/reaction/add?pid=<%= post.getPid() %>&uid=<%= User_ID %>&type=LIKES">👍</a></li>
                                <li><a class="dropdown-item d-flex align-items-center" href="${pageContext.request.contextPath}/reaction/add?pid=<%= post.getPid() %>&uid=<%= User_ID %>&type=LOVES">❤️</a></li>
                                <li><a class="dropdown-item d-flex align-items-center" href="${pageContext.request.contextPath}/reaction/add?pid=<%= post.getPid() %>&uid=<%= User_ID %>&type=FIRES">🔥</a></li>
                                <li><a class="dropdown-item d-flex align-items-center" href="${pageContext.request.contextPath}/reaction/add?pid=<%= post.getPid() %>&uid=<%= User_ID %>&type=JOYYY">😂</a></li>
                                <li><a class="dropdown-item d-flex align-items-center" href="${pageContext.request.contextPath}/reaction/add?pid=<%= post.getPid() %>&uid=<%= User_ID %>&type=SADDD">😢</a></li>
                                <li><a class="dropdown-item d-flex align-items-center" href="${pageContext.request.contextPath}/reaction/add?pid=<%= post.getPid() %>&uid=<%= User_ID %>&type=ANGER">😡</a></li>
                                <li><a class="dropdown-item d-flex align-items-center" href="${pageContext.request.contextPath}/reaction/add?pid=<%= post.getPid() %>&uid=<%= User_ID %>&type=THIFT">🤔</a></li>
                                <li><a class="dropdown-item d-flex align-items-center" href="${pageContext.request.contextPath}/reaction/delete?pid=<%= post.getPid() %>&uid=<%= User_ID %>">❌</a></li>
                            </ul>
                        </div>
                        <a href="${pageContext.request.contextPath}/post/<%= post.getPid() %>" class="btn btn-outline-secondary btn-sm">💬 <%= post.getNbComm() %></a>
                        <% if (!listFavoriUser.contains(post.getPid())) { %>
                            <a href="${pageContext.request.contextPath}/addFavori?pid=<%= post.getPid() %>&uid=<%= User_ID %>" class="btn btn-outline-warning btn-sm">⭐ Favoris</a>
                        <% } else { %>
                            <a href="${pageContext.request.contextPath}/addFavori?pid=<%= post.getPid() %>&uid=<%= User_ID %>" class="btn btn-outline-danger btn-sm">❌ Retirer des favoris</a>
                        <% } %>
                    </footer>
            </article>
            <%
            }
            %>
        </aside>
        
        <section class="col-md-8">
            <h3>Ajouter un post réponse</h3>
            <jsp:include page="popUp.jsp" />
            <jsp:include page="form.jsp" />
        </section>

    </div>
</main>

<jsp:include page="footer.jsp" />

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
<%
session.removeAttribute("post");
%>