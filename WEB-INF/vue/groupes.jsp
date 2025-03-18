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
int User_ID = (int) session.getAttribute("me_uid");
%>

<jsp:include page="header.jsp" />

<main class="container mt-5 pt-4">
    <div class="row">
        <aside class="col-md-4">
            <h2 class="text-primary">Groupes</h2>
            <a href="${pageContext.request.contextPath}/addGroupe" class="btn btn-outline-success mt-2 w-100">Créer un Groupe</a>
            <ul class="list-group">
            <%
            List<Groupe> listGrp = (ArrayList<Groupe>) session.getAttribute("listeDesGroupes");
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
            Groupe gSelect = (Groupe) session.getAttribute("groupe");
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
                <% if (gSelect.getUid() == User_ID) { %>
                    <a href="${pageContext.request.contextPath}/chgGroupe/<%= gSelect.getGid() %>" class="btn btn-primary">Modifier le groupe</a>
                <% } else { %>

                    <a href="${pageContext.request.contextPath}/member?gid=<%= gSelect.getGid() %>&uid=<%= User_ID %>" class="btn btn-sm btn-outline-danger">Se retirer du groupe</a>
                <% } %>
            <% } %>
            </div>

            <% if (gSelect != null) { %>
            <h3>Ajouter un Post au groupe</h3>
            <jsp:include page="form.jsp" />
            <% } %>
        </section>

    </div>
</main>

<jsp:include page="footer.jsp" />

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>