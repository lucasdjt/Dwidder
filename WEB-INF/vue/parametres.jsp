<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

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

    <form class="card bg-dark text-white p-4">
        <div class="mb-3">
            <label for="idPseudo" class="form-label">Identifiant *</label>
            <input type="text" class="form-control bg-dark text-white" id="idPseudo" minlength="3" maxlength="15" value="MonID" required>
        </div>
        <div class="mb-3">
            <label for="pseudo" class="form-label">Pseudo *</label>
            <input type="text" class="form-control bg-dark text-white" id="pseudo" maxlength="20" value="MonPseudo" required>
        </div>
        <div class="mb-3">
            <label for="prenom" class="form-label">Prénom</label>
            <input type="text" class="form-control bg-dark text-white" id="prenom" maxlength="20" value="Jean">
        </div>
        <div class="mb-3">
            <label for="nomUser" class="form-label">Nom</label>
            <input type="text" class="form-control bg-dark text-white" id="nomUser" maxlength="50" value="Dupont">
        </div>
        <div class="mb-3">
            <label for="email" class="form-label">Email *</label>
            <input type="email" class="form-control bg-dark text-white" id="email" value="email@example.com" required>
        </div>
        <div class="mb-3">
            <label for="password" class="form-label">Nouveau mot de passe</label>
            <div class="input-group">
                <input type="password" class="form-control bg-dark text-white" id="password">
                <button class="btn btn-outline-light" type="button" id="togglePassword">👁️</button>
            </div>
        </div>
        <div class="mb-3">
            <label for="bio" class="form-label">Bio (200 caractères max)</label>
            <textarea class="form-control bg-dark text-white" id="bio" maxlength="200">Ma bio actuelle...</textarea>
        </div>
        <div class="mb-3">
            <label for="pdp" class="form-label">Photo de profil</label>
            <input type="file" class="form-control bg-dark text-white" id="pdp">
        </div>
        <div class="mb-3">
            <label for="dnaiss" class="form-label">Date de naissance</label>
            <input type="date" class="form-control bg-dark text-white" id="dnaiss" value="2000-01-01">
        </div>
        <div class="mb-3">
            <label for="loca" class="form-label">Localisation</label>
            <input type="text" class="form-control bg-dark text-white" id="loca" maxlength="200" value="Paris">
        </div>
        <div class="mb-3">
            <label class="form-label">Sexe *</label>
            <select class="form-select bg-dark text-white" id="sexe" required>
                <option value="M" selected>Homme</option>
                <option value="F">Femme</option>
                <option value="O">Autre</option>
            </select>
        </div>
        <div class="mb-3">
            <label for="tel" class="form-label">Téléphone</label>
            <input type="tel" class="form-control bg-dark text-white" id="tel" maxlength="30" value="+33 6 12 34 56 78">
        </div>
        <div class="mb-3">
            <label class="form-label">Langue *</label>
            <select class="form-select bg-dark text-white" id="langue" required>
                <option value="FR" selected>Français</option>
                <option value="EN">Anglais</option>
            </select>
        </div>

        <button type="submit" class="btn btn-primary w-100">Enregistrer les modifications</button>
    </form>

    <div class="mt-3 text-center">
        <a href="connexion.html" class="btn btn-warning w-100" id="logout">Se déconnecter</a>
        <a href="connexion.html" class="btn btn-danger w-100 mt-2" id="deleteAccount">Supprimer mon compte</a>
    </div>
</main>

<jsp:include page="footer.jsp" />

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script src="script/main.js"></script>
</body>
</html>
