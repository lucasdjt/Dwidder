<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.ArrayList, java.util.List, modele.dto.User, modele.dto.Groupe" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Modifier un Groupe - Dwidder</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="icon" href="${pageContext.request.contextPath}/img/logo.ico">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<% 
int User_ID = (int) session.getAttribute("me_uid");
List<Integer> listFollowersUser = (List<Integer>) session.getAttribute("me_listFollowers");
Groupe gSelect = (Groupe) session.getAttribute("groupeSelect");
%>

<jsp:include page="header.jsp" />

<main class="container mt-5 pt-4">
    <div class="row">
        <aside class="col-md-4">
            <h2 class="text-primary">Ajouter un utilisateur</h2>
            <jsp:include page="popUp.jsp" />
            <form action="${pageContext.request.contextPath}/groupe/member" method="post" class="mt-2">
                <div class="input-group">
                    <input type="hidden" name="gid" value="<%= gSelect.getGid() %>">
                    <input type="text" name="idPseudo" class="form-control" placeholder="@ de l'utilisateur" required>
                    <button type="submit" class="btn btn-success">Ajouter</button>
                </div>
            </form>
            <h2 class="text-primary">Membres du groupe</h2>
            <ul class="list-group">
            <%
            List<User> listUsers = (ArrayList<User>) session.getAttribute("membreGrp");
            if (listUsers != null) {
                for(User u : listUsers){
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
                    <% if (u.getUid() != User_ID) { %>
                    <a href="${pageContext.request.contextPath}/groupe/member?gid=<%= gSelect.getGid() %>&uid=<%= u.getUid() %>" class="btn btn-sm btn-outline-danger ms-auto">Supprimer</a>
                    <% } %>
                </li>
            <% }} %>
            </ul>
        </aside>
        
        <section class="col-md-8">
            <div class="card text-center">
                <form action="${pageContext.request.contextPath}/groupe/edit" method="post" enctype="multipart/form-data">
                    <input type="hidden" name="gid" value="<%= gSelect.getGid() %>">
                    <div class="card-body">
                        <div class="mb-3">
                            <label for="pdpGrp" class="form-label">Photo du groupe</label>
                            <input type="file" name="pdpGrp" id="pdpGrp" class="form-control">
                        </div>
                        <div class="mb-3">
                            <label for="nomGrp" class="form-label">Nom du groupe</label>
                            <input type="text" name="nomGrp" id="nomGrp" class="form-control" value="<%= gSelect.getNomGrp() %>" required>
                        </div>
                        <div class="mb-3">
                            <label for="description" class="form-label">Description</label>
                            <textarea name="description" id="description" class="form-control" rows="3"><%= gSelect.getDescription() %></textarea>
                        </div>
                        <div class="mb-3 bg-danger p-3 rounded">
                            <label for="newAdmin" class="form-label text-white">Changer d'administrateur du groupe</label>
                            <select name="newAdmin" id="newAdmin" class="form-select">
                                <option value="" disabled selected>Choisir un nouvel administrateur</option>
                                <% 
                                if (listUsers != null) {
                                    for (User u : listUsers) {
                                        if (u.getUid() != User_ID) { 
                                %>
                                <option value="<%= u.getUid() %>"><%= u.getPseudo() %></option>
                                <% 
                                        }
                                    }
                                } 
                                %>
                            </select>
                        </div>
                        <button type="submit" class="btn btn-primary">Enregistrer les modifications</button>
                    </div>
                </form>
                <a href="${pageContext.request.contextPath}/groupe/delete?gid=<%= gSelect.getGid() %>" class="btn btn-danger mt-3">Supprimer le groupe</a>
            </div>
        </section>
    </div>
</main>

<jsp:include page="footer.jsp" />

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
<%
session.removeAttribute("groupeSelect");
session.removeAttribute("membreGrp");
%>