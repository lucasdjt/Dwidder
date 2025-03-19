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
int User_ID = (int) session.getAttribute("me_uid");
List<Integer> listFollowUser = (List<Integer>) session.getAttribute("me_listFollow");
List<Integer> listFollowersUser = (List<Integer>) session.getAttribute("me_listFollowers");
%>

<jsp:include page="include_header.jsp" />

<main class="container mt-4">
    <div class="row">
        <section class="col-md-8">
            <h2 class="text-primary">Accueil</h2>
            <jsp:include page="include_popUp.jsp" />
            <jsp:include page="include_form.jsp" />
        </section>

        <aside class="col-md-4">
            <h2 class="text-primary">Utilisateurs</h2>
            <ul class="list-group">
        <%
        List<User> listeDesUsers = (List<User>) session.getAttribute("listeDesUsers");
        if (listeDesUsers != null) {
            int limite = 5;
            for (int i = 0; i < listeDesUsers.size() && i < limite; i++) {
                User u = listeDesUsers.get(i);
                if (u.getUid() == User_ID) {
                    limite++;
                    continue;
                }
        %>
                <li class="border list-group-item d-flex align-items-center" id="user_<%= u.getIdPseudo()%>">
                    <img src="${pageContext.request.contextPath}/<%= u.getPdp() %>" alt="<%= u.getPseudo() %>" class="rounded-circle me-2" width="40">
                    <div>
                        <a href="${pageContext.request.contextPath}/user/<%= u.getIdPseudo() %>" class="text-decoration-none text-white"><%= u.getPseudo() %></a>
                        <br>
                        <% if (listFollowersUser.contains(u.getUid())) { %>
                            <small class="text-muted"><span class="text-success">Cette personne vous suit</span></small>
                        <% } %>
                    </div>
                    <div class="ms-auto">
                        <% if (!listFollowUser.contains(u.getUid())) { %>
                            <a href="${pageContext.request.contextPath}/follow/addFollow/<%= u.getUid() %>" class="btn btn-sm btn-outline-success">+ Suivre</a>
                        <% } else { %>
                            <a href="${pageContext.request.contextPath}/follow/addFollow/<%= u.getUid() %>" class="btn btn-sm btn-outline-danger">Ne plus suivre</a>
                        <% } %>
                    </div>
                </li>
        <% }} %>
            </ul>
            <a href="user" class="btn btn-outline-primary mt-2 w-100">Afficher Plus</a>
        </aside>
    </div>
</main>

<jsp:include page="include_footer.jsp" />

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
<%
session.removeAttribute("listeDesUsers");
%>