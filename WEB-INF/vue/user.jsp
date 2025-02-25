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
                <li><%= user.toString()%></li>
    <%
            }
        } else {
            User user = (User) request.getAttribute("user");
            if (user != null) {
    %>
                <li><%= user.toString()%></li>
    <%
            }
        }
    %>
    </ul>

    <h2>Ajouter un utilisateur</h2>
    <form action="users/" method="post">
        <input type="text" name="id_pseudo" placeholder="ID Pseudo" required>
        <input type="text" name="pseudo" placeholder="Pseudo" required>
        <input type="text" name="prenom" placeholder="Prénom" required>
        <input type="text" name="nom_user" placeholder="Nom" required>
        <input type="email" name="email" placeholder="Email" required>
        <input type="password" name="mdp" placeholder="Mot de passe" required>
        <input type="text" name="bio" placeholder="Bio">
        <input type="text" name="pdp" placeholder="Photo de profil">
        <input type="date" name="date_insc" placeholder="Date d'inscription" required>
        <input type="date" name="date_naiss" placeholder="Date de naissance" required>
        <input type="text" name="loca" placeholder="Localisation">
        <input type="text" name="sexe" placeholder="Sexe">
        <input type="text" name="num_tel" placeholder="Numéro de téléphone">
        <input type="text" name="langue" placeholder="Langue">
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
