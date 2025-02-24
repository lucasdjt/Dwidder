<%@ page import="java.util.List" %>
<%@ page import="modele.dto.User" %>

<html>
<head>
    <title>User Management</title>
</head>
<body>
    <h1>Gestion des utilisateurs</h1>

    <h2>Liste des utilisateurs</h2>
    <ul>
    <%
        List<User> users = (List<User>) request.getAttribute("users");
        if(users != null) {
            for (User user : users) {
    %>
                <li><%= user.getId_pseudo() %> - <%= user.getName() %></li>
    <%
            }
        } else {
            User user = (User) request.getAttribute("user");
            if (user != null) {
    %>
                <li><%= user.getId_pseudo() %> - <%= user.getName() %></li>
    <%
            }
        }
    %>
    </ul>

    <h2>Ajouter un utilisateur</h2>
    <form action="users/" method="post">
        <input type="text" name="id_pseudo" placeholder="ID Pseudo" required>
        <input type="text" name="name" placeholder="Nom" required>
        <button type="submit">Ajouter</button>
    </form>

    <%
        String message = (String) request.getAttribute("add");
        if (message != null) {
    %>
        <p><%= message %></p>
    <%
        }
    %>
</body>
</html>
