<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.ArrayList, java.util.List, modele.dto.PostDetails, modele.dto.Groupe" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Groupes - Dwidder</title>
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
    <div class="row">
        <aside class="col-md-4">
            <h2 class="text-primary">Groupes</h2>
            <a href="${pageContext.request.contextPath}/addGroupe" class="btn btn-outline-success mt-2 w-100">Créer un Groupe</a>
            <ul class="list-group">
            <%
            List<Groupe> listGrp = (ArrayList<Groupe>) request.getAttribute("listGrp");
            if (listGrp != null) {
                for(Groupe g : listGrp){
            %>
                <li class="border list-group-item d-flex align-items-center btn-outline-success">
                    <img src="${pageContext.request.contextPath}/<%= g.getPdpGrp() %>" alt="<%= g.getPdpGrp() %>" class="rounded-circle me-2" width="40">
                    <a href="${pageContext.request.contextPath}/groupes/<%= g.getGid() %>" class="stretched-link text-decoration-none text-white"><%= g.getNomGrp() %></a>
                </li>
            <% }}
            %>
            </ul>
        </aside>
        
        <section class="col-md-8">
            <div class="card text-center">
            <%
            Groupe gSelect = (Groupe) request.getAttribute("groupe");
            if (gSelect != null) {
            %>
                <img src="${pageContext.request.contextPath}/<%= gSelect.getPdpGrp() %>" alt="<%= gSelect.getPdpGrp() %>" class="card-img-top rounded-circle mx-auto mt-3" style="width: 100px; height: 100px;">
                
                <div class="card-body">
                    <h3 class="card-title"><%= gSelect.getNomGrp() %></h3>
                    <p class="text-muted">Crée le <%= gSelect.getDcreatAsDate() %></p>
                    <div class="card p-2 border">
                        <p class="mb-0"><%= gSelect.getDescription() %></p>
                    </div>
                </div>
            <% } %>
            </div>

            <% if (gSelect != null) { %>
            <h3>Ajouter un Post au groupe</h3>
            <% 
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
                <input type="hidden" name="uid" value="<%= User_ID %>">
                <input type="hidden" name="gid" value="<%= gSelect.getGid() %>">
                <button type="submit" class="btn btn-primary w-100">Publier</button>
            </form>
            <jsp:include page="post.jsp" />
            <% } %>
        </section>

    </div>
</main>

<jsp:include page="footer.jsp" />

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>