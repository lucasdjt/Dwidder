<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="modele.dto.User"%>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Paramètres - Dwidder</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="icon" href="${pageContext.request.contextPath}/img/logo.ico">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<jsp:include page="header.jsp" />

<main class="container mt-4">
    <h2 class="text-primary text-center">Paramètres du compte</h2>

    <form class="card bg-dark text-white p-4" action="${pageContext.request.contextPath}/parametres" method="post" enctype="multipart/form-data">
        <%
                User user = (User) request.getAttribute("user");
                if (user != null) {
        %>
        <div class="mb-3">
            <label for="idPseudo" class="form-label">Identifiant *</label>
            <input type="text" class="form-control bg-dark text-white" name="idPseudo" minlength="3" maxlength="15" value="<%= user.getIdPseudo() %>" required>
        </div>
        <div class="mb-3">
            <label for="pseudo" class="form-label">Pseudo *</label>
            <input type="text" class="form-control bg-dark text-white" name="pseudo" maxlength="20" value="<%= user.getPseudo() %>" required>
        </div>
        <div class="mb-3">
            <label for="prenom" class="form-label">Prénom</label>
            <input type="text" class="form-control bg-dark text-white" name="prenom" maxlength="20" value="<%= user.getPrenom() %>">
        </div>
        <div class="mb-3">
            <label for="nomUser" class="form-label">Nom</label>
            <input type="text" class="form-control bg-dark text-white" name="nomUser" maxlength="50" value="<%= user.getNomUser() %>">
        </div>
        <div class="mb-3">
            <label for="email" class="form-label">Email *</label>
            <input type="email" class="form-control bg-dark text-white" name="email" value="<%= user.getEmail() %>" required>
        </div>
        <div class="mb-3">
            <label for="password" class="form-label">Nouveau mot de passe</label>
            <div class="input-group">
                <input type="password" class="form-control bg-dark text-white" name="password" id="password">
                <button class="btn btn-outline-light" type="button" id="togglePassword">👁️</button>
            </div>
        </div>
        <div class="mb-3">
            <label for="bio" class="form-label">Bio (200 caractères max)</label>
            <textarea class="form-control bg-dark text-white" name="bio" maxlength="200"><%= user.getBio() %></textarea>
        </div>
        <div class="mb-3">
            <label for="pdp" class="form-label">Nouvelle Photo de profil</label>
            <input type="file" class="form-control bg-dark text-white" name="pdp">
        </div>
        <div class="mb-3">
            <label for="dnaiss" class="form-label">Date de naissance</label>
            <input type="date" class="form-control bg-dark text-white" name="dnaiss" value="<%= user.getDnaiss() %>">
        </div>
        <div class="mb-3">
            <label for="loca" class="form-label">Localisation</label>
            <input type="text" class="form-control bg-dark text-white" name="loca" maxlength="200" value="<%= user.getLoca() %>">
        </div>
        <div class="mb-3">
            <label class="form-label">Sexe *</label>
            <select class="form-select bg-dark text-white" name="sexe" required>
                <option value="M" selected>Homme</option>
                <option value="F">Femme</option>
                <option value="O">Autre</option>
            </select>
        </div>
        <div class="mb-3">
            <label for="tel" class="form-label">Téléphone</label>
            <input type="tel" class="form-control bg-dark text-white" name="tel" maxlength="30" value="<%= user.getTel() %>">
        </div>
        <div class="mb-3">
            <label class="form-label">Langue *</label>
            <select class="form-select bg-dark text-white" name="langue" required>
                <option value="FR" selected>Français</option>
                <option value="EN">Anglais</option>
            </select>
        </div>
        <button type="submit" class="btn btn-primary w-100">Enregistrer les modifications</button>
        <%
            }
        %>
    </form>

    <div class="mt-3 text-center">
        <a href="${pageContext.request.contextPath}/connexion" class="btn btn-warning w-100" id="logout">Se déconnecter</a>
        <form action="${pageContext.request.contextPath}/delAccount" method="get" onsubmit="return confirm('Êtes-vous sûr de vouloir supprimer votre compte ?');">
            <button type="submit" class="btn btn-danger w-100 mt-2" id="deleteAccount">Supprimer mon compte</button>
        </form>
    </div>
</main>

<jsp:include page="footer.jsp" />

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script src="script/main.js"></script>
</body>
</html>
