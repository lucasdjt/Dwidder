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

<%int uidSet = 1;%>
<jsp:include page="header.jsp" />


<main class="container mt-5 pt-4">
    <div class="row">
        <aside class="col-md-4">
            <div class="card text-center">
                <%
                User u = (User) request.getAttribute("user");
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
                        <li class="list-group-item"><strong>Sexe : </strong><%= u.getSexe() %></li>
                        <li class="list-group-item"><strong>Langue : </strong><%= u.getLangue() %></li>
                        <li class="list-group-item"><strong>Date d'inscription : </strong><%= u.getDinscAsDate() %></li>
                    </ul>
                    <div class="d-flex flex-column align-items-stretch mt-3">
                        <a href="${pageContext.request.contextPath}/followers/<%= u.getIdPseudo() %>" class="btn btn-outline-primary mb-2">Abonnés : <%= request.getAttribute("followers") %></a>
                        <a href="${pageContext.request.contextPath}/follows/<%=  u.getIdPseudo() %>" class="btn btn-outline-secondary mb-2">Abonnements : <%= request.getAttribute("follows") %></a>
                        <a href="${pageContext.request.contextPath}/favoris" class="btn btn-outline-warning mb-2">Favoris</a>
                    </div>
                </div>
                <%}%>
            </div>
        </aside>

        <section class="col-md-8">
            <h2 class="text-primary">Profil de <%= u.getPseudo() %></h2>
            <%
            List<PostDetails> list = (ArrayList<PostDetails>) request.getAttribute("posts");
            if (list != null) {
                for(PostDetails rep : list){
            %>
                <article class="card mb-3">
                <header class="card-header d-flex align-items-center">
                    <img src="${pageContext.request.contextPath}/<%= rep.getPdp() %>" alt="<%= rep.getPdp() %>" class="rounded-circle me-2" width="40">
                    <div>
                    <a href="${pageContext.request.contextPath}/user/<%= rep.getIdPseudo() %>" class="text-decoration-none text-white"><h6 class="mb-0"><%= rep.getPseudo() %></h6></a>
                    <small class="text-muted">@<%= rep.getIdPseudo() %> - <%= rep.getDpubAsDate() %></small>
                    </div>
                </header>
                <main class="card-body">
                    <p><%= rep.getContenu() %></p>
                    <% if(rep.getMedia() != null) { %>
                    <img src="${pageContext.request.contextPath}/<%= rep.getMedia() %>" alt="<%= rep.getMedia() %>" class="rounded w-100">
                    <% } %>
                    <% if(rep.getDuree() < 700) { %>
                    <blockquote class="text-muted small">Il reste <%= rep.getDuree() %>h à ce post avant d'être supprimé</blockquote>
                    <% } %>
                </main>
                <footer class="card-footer d-flex justify-content-around">
                    <a href="${pageContext.request.contextPath}/addLike?pid=<%= rep.getPid() %>&uid=<%= rep.getUid() %>" class="btn btn-outline-primary btn-sm">👍 <%= rep.getNbLikes() %></a>
                    <a href="${pageContext.request.contextPath}/posts/<%= rep.getPid() %>" class="btn btn-outline-secondary btn-sm">💬 <%= rep.getNbComm() %></a>
                    <a href="${pageContext.request.contextPath}/addFavori?pid=<%= rep.getPid() %>&uid=<%= uidSet %>" class="btn btn-outline-warning btn-sm">⭐ Favoris</a>
                </footer>
                </article>
            <%
                }
            }
            %>
        </section>
    </div>
</main>

<jsp:include page="footer.jsp" />

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>