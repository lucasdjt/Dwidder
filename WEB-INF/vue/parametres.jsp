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

<jsp:include page="include_header.jsp" />

<main class="container mt-4">
    <h2 class="text-primary text-center">Paramètres du compte</h2>
    <jsp:include page="include_popUp.jsp" />

    <form class="card bg-dark text-white p-4" action="${pageContext.request.contextPath}/user" method="post" enctype="multipart/form-data">
        <%
                User user = (User) session.getAttribute("me_user");
                if (user != null) {
        %>
        <div class="mb-3">
            <label for="idPseudo" class="form-label">Identifiant *</label>
            <input type="text" class="form-control bg-dark text-white" name="idPseudo" minlength="3" maxlength="15" value="<%= user.getIdPseudo() %>" pattern="[a-z0-9]*" required>
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
            <label for="tri" class="form-label">Trier les posts par :</label>
            <select class="form-select bg-dark text-white" id="tri" name="tri">
            <option value="reaction">Réaction</option>
            <option value="date">Date</option>
            </select>
        </div>
        <button type="submit" class="btn btn-primary w-100">Enregistrer les modifications</button>
        <%
            }
        %>
    </form>

    <div class="mt-3 text-center">
        <a href="${pageContext.request.contextPath}/connexion" class="btn btn-warning w-100" id="logout">Se déconnecter</a>
        <form action="${pageContext.request.contextPath}/user/delete" method="get" onsubmit="return confirm('Êtes-vous sûr de vouloir supprimer votre compte ?');">
            <button type="submit" class="btn btn-danger w-100 mt-2" id="deleteAccount">Supprimer mon compte</button>
        </form>
    </div>
</main>

<jsp:include page="include_footer.jsp" />

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script>
document.getElementById('togglePassword').addEventListener('click', function () {
    let passwordInput = document.getElementById('password');
    if (passwordInput.type === 'password') {
        passwordInput.type = 'text';
    } else {
        passwordInput.type = 'password';
    }
});
</script>
</body>
</html>
<input type="hidden" name="author" value="Lucas DE JESUS TEIXEIRA">
<input type="hidden" name="github" value="https://github.com/lucasdjt">