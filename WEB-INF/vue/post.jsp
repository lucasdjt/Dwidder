<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.ArrayList, java.util.List, modele.dto.PostDetails" %>

<% 
    int User_ID = (int) request.getSession().getAttribute("uid");
    List<PostDetails> list = (ArrayList<PostDetails>) request.getAttribute("listePosts");
    if (list != null) {
        for(PostDetails post : list){
%>
    <article class="card mb-3">
        <header class="card-header d-flex align-items-center">
            <img src="${pageContext.request.contextPath}/<%= post.getPdp() %>" alt="Pdp de <%= post.getIdPseudo() %>" class="rounded-circle me-2" width="40">
                <div>
                    <a href="${pageContext.request.contextPath}/user/<%= post.getIdPseudo() %>" class="text-decoration-none text-white"><h6 class="mb-0"><%= post.getPseudo() %></h6></a>
                    <small class="text-muted">@<%= post.getIdPseudo() %> - <%= post.getDpubAsDate() %></small>
                    <% if(post.getPidParent() != null && post.getPidParent() != 0){ %>
                        <blockquote class="text-muted small">Ce post répond à ce <a href="${pageContext.request.contextPath}/posts/<%= post.getPidParent() %>">post</a></blockquote>
                    <% } else if(post.getNomGrp() != null){ %>
                        <blockquote class="text-muted small">Ce post vient du groupe <em><%= post.getNomGrp() %></em></blockquote>
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
                <a href="${pageContext.request.contextPath}/addLike?pid=<%= post.getPid() %>&uid=<%= User_ID %>" class="btn btn-outline-primary btn-sm">👍 <%= post.getNbLikes() %></a>
                <a href="${pageContext.request.contextPath}/posts/<%= post.getPid() %>" class="btn btn-outline-secondary btn-sm">💬 <%= post.getNbComm() %></a>
                <a href="${pageContext.request.contextPath}/addFavori?pid=<%= post.getPid() %>&uid=<%= User_ID %>" class="btn btn-outline-warning btn-sm">⭐ Favoris</a>
            </footer>
    </article>
<%
}
}
%>