<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="modele.dto.Logs, modele.dto.User, java.util.List" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Panel - Dwidder</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

<% 
int User_ID = (int) session.getAttribute("me_uid");
%>

<main class="container mt-4">
    <h2 class="text-primary">Panneau d'Administration</h2>
    <jsp:include page="include_popUp.jsp" />

    <section class="mb-4">
        <h3 class="text-secondary">Gérer les utilisateurs</h3>
        <form action="${pageContext.request.contextPath}/admin" method="post">
            <div class="mb-3">
                <label for="uid" class="form-label">Sélectionner un utilisateur :</label>
                <select class="form-select" name="uid" id="uid" required>
                    <%
                    List<User> listeDesUsers = (List<User>) session.getAttribute("listeDesUsers");
                    if (listeDesUsers != null) {
                        for (User user : listeDesUsers) {
                            if (user.getUid() != User_ID) {
                    %>
                            <option value="<%= user.getIdPseudo() %>"><%= user.getPseudo() %> (<%= user.getIdPseudo() %>)</option>
                    <%
                            }
                        }
                    }
                    %>
                </select>
            </div>
            <button type="submit" class="btn btn-danger">Supprimer l'utilisateur</button>
        </form>
    </section>

    <section>
        <h3 class="text-secondary">Historique des actions</h3>
        <table class="table table-bordered table-striped">
            <thead>
                <tr>
                    <th>lid</th>
                    <th>Date de l'action</th>
                    <th>Utilisateur</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
                <%
                List<Logs> listeDesLogs = (List<Logs>) session.getAttribute("listeDesLogs");
                if (listeDesLogs != null) {
                    for (Logs log : listeDesLogs){
                %>
                        <tr>
                            <td><%= log.getLid() %></td>
                            <td><%= log.getDaction() %></td>
                            <td><%= log.getPseudoLog() %></td>
                            <td><%= log.getTextLog() %></td>
                        </tr>
                <%
                    }
                }
                %>
            </tbody>
        </table>
    </section>
</main>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
<%
session.removeAttribute("listeDesUsers");
session.removeAttribute("listeDesLogs");
%>