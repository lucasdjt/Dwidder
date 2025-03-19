<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.ArrayList, java.util.List, java.util.Map, java.util.HashMap, java.util.Map.Entry, modele.dto.User, modele.dto.Message" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="refresh" content="15">
    <title>Messages - Dwidder</title>
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
            <h2 class="text-primary">Messages</h2>
            <jsp:include page="popUp.jsp" />
            <form class="mb-4" method="get" action="${pageContext.request.contextPath}/message">
                <div class="input-group">
                    <input type="text" name="query" class="form-control" placeholder="Rechercher un utilisateur...">
                    <button class="btn btn-primary" type="submit">Rechercher</button>
                </div>
            </form>
            <ul class="list-group">
                <%
                List<User> list = (List<User>) session.getAttribute("listDesUtilisateurs");
                if (list != null) {
                    for(User u : list){
                        if(u.getUid() == User_ID){
                            continue;
                        }
                %>
                <li class="border list-group-item d-flex align-items-center btn-outline-success">
                    <img src="${pageContext.request.contextPath}/<%= u.getPdp() %>" alt="<%= u.getPdp() %>" class="rounded-circle me-2" width="40">
                    <a href="${pageContext.request.contextPath}/message/<%= u.getIdPseudo() %>" class="stretched-link text-decoration-none text-white"><%= u.getPseudo() %></a>
                </li>
                <% }} %>
            </ul>
        </aside>

        <section class="col-md-8 d-flex flex-column">
                <%
                User user = (User) session.getAttribute("userMess");
                session.removeAttribute("userMess");
                if (user != null) {
                %>
            <header class="card d-flex align-items-center p-3 border">
                <img src="${pageContext.request.contextPath}/<%= user.getPdp() %>" alt="<%= user.getPdp() %>" class="rounded-circle me-3" width="50">
                <div>
                    <h4><a href="${pageContext.request.contextPath}/user/<%= user.getIdPseudo() %>" class="text-decoration-none text-white"><%= user.getPseudo() %></a></h4>
                    <p class="text-muted mb-0"><%= user.getBio() %></p>
                    <small class="text-secondary">Utilisateur créée le <%= user.getDinscAsDate() %></small>
                </div>
            </header>
            <div class="card flex-grow-1 d-flex flex-column">
                <div class="card-body overflow-auto" id="conversation" style="height: 400px;">
                    <div class="d-flex flex-column">
                        <%
                        List<Message> listMess = (List<Message>) session.getAttribute("listeDesMessages");
                        session.removeAttribute("listeDesMessages");
                        if (listMess != null) {
                            for(Message m : listMess){
                                if(m.getUidEnvoyeur() != User_ID){
                        %>
                        <small class="text-muted align-self-start"><%= m.getDmessAsDate() %></small>
                        <p class="bg-secondary p-2 rounded align-self-start"><%= m.getCorps() %>
                        <% if (m.getImgMess() != null) { %>
                        <br><img src="${pageContext.request.contextPath}/<%= m.getImgMess() %>" alt="Image" class="img-fluid mt-2 align-self-start">
                        <% } %>
                        </p>
                        <% } else {
                        %>
                        <small class="text-muted align-self-end"><%= m.getDmessAsDate() %></small>
                        <p class="bg-primary text-white p-2 rounded align-self-end"><%= m.getCorps() %>
                        <% if (m.getImgMess() != null) { %>
                        <br><img src="${pageContext.request.contextPath}/<%= m.getImgMess() %>" alt="Image" class="img-fluid mt-2 align-self-end">
                        <% } %>
                        </p>
                        <% }}} %>
                    </div>
                </div>
                <div class="card-footer">
                    <form class="d-flex" action="message" method="post" enctype="multipart/form-data">
                        <input type="text" class="form-control me-2" name="corps" placeholder="Écrire un message...">
                        <input type="file" class="form-control bg-dark text-white mb-2" name="imgMess" accept="image/*">
                        <input type="hidden" name="uidReceveur" value="<%= user.getUid() %>">
                        <button class="btn btn-primary">Envoyer</button>
                    </form>
                </div>
            </div>
                <% } %>
        </section>

    </div>
</main>

<jsp:include page="footer.jsp" />

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script>
document.addEventListener("DOMContentLoaded", function() {
    const conversation = document.getElementById("conversation");
    conversation.scrollTop = conversation.scrollHeight;
});
</script>
</body>
</html>
<%
session.removeAttribute("listDesUtilisateurs");
%>