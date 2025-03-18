<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.ArrayList, java.util.List, modele.dto.PostDetails, java.util.Map" %>

<meta http-equiv="refresh" content="15">

<% 
    int User_ID = (int) session.getAttribute("me_uid");
    List<Integer> listFavoriUser = (List<Integer>) session.getAttribute("me_listFavori");
    List<Integer> listFollowUser = (List<Integer>) session.getAttribute("me_listFollow");
    List<Integer> listFollowersUser = (List<Integer>) session.getAttribute("me_listFollowers");
    Map<Integer, String> listReactionsUser = (Map<Integer, String>) session.getAttribute("me_listReactions");
    List<PostDetails> listeDesPosts = (List<PostDetails>) session.getAttribute("listeDesPosts");
    if (listeDesPosts != null) {
        for(PostDetails post : listeDesPosts){
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
                        <blockquote class="text-muted small">Ce post répond à ce <a href="${pageContext.request.contextPath}/posts/<%= post.getPidParent() %>">post</a></blockquote>
                    <% } else if(post.getNomGrp() != null){ %>
                        <blockquote class="text-muted small">Ce post vient du groupe <em><%= post.getNomGrp() %></em></blockquote>
                    <% } %>
                </div>
            <div class="ms-auto">
                <% if (post.getUid() != User_ID) { %>
                    <% if (!listFollowUser.contains(post.getUid())) { %>
                        <a href="${pageContext.request.contextPath}/addFollow?follow=<%= post.getUid() %>&follower=<%= User_ID %>" class="btn btn-sm btn-outline-success">+ Suivre</a>
                    <% } else { %>
                        <a href="${pageContext.request.contextPath}/addFollow?follow=<%= post.getUid() %>&follower=<%= User_ID %>" class="btn btn-sm btn-outline-danger">Ne plus suivre</a>
                    <% } %>
                <% } else { %>
                    <a href="${pageContext.request.contextPath}/reaction/<%= post.getPid() %>" class="btn btn-sm btn-outline-primary">Voir les réactions</a>
                <% } %>
                <% if (post.getUid() == User_ID  || post.getUidAdmin() == User_ID) { %>
                    <a href="${pageContext.request.contextPath}/delPost/<%= post.getPid() %>" class="btn btn-sm btn-outline-danger">Supprimer le post</a>
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
                <a href="${pageContext.request.contextPath}/posts/<%= post.getPid() %>" class="btn btn-outline-secondary btn-sm">💬 <%= post.getNbComm() %></a>
                <% if (!listFavoriUser.contains(post.getPid())) { %>
                    <a href="${pageContext.request.contextPath}/favori/change?pid=<%= post.getPid() %>" class="btn btn-outline-warning btn-sm">⭐ Favoris</a>
                <% } else { %>
                    <a href="${pageContext.request.contextPath}/favori/change?pid=<%= post.getPid() %>" class="btn btn-outline-danger btn-sm">❌ Retirer des favoris</a>
                <% } %>
            </footer>
    </article>
<%
}
}
%>
<%
session.removeAttribute("listeDesPosts");
%>