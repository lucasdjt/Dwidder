<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Liste des Likes - Dwidder</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="icon" href="img/logo.ico">
    <link rel="stylesheet" href="css/style.css">
</head>
<body>

<jsp:include page="header.jsp" />

<main class="container mt-4">
        <h2 class="text-primary">Utilisateurs qui ont likés / Gérer les Utilisateurs qui ont likés</h2>
        <ul class="list-group">
            <li class="border list-group-item d-flex align-items-center">
                <img src="img/pdp.png" alt="Utilisateur1" class="rounded-circle me-2" width="40">
                <div>
                    <a href="user.html" class="text-decoration-none text-white"><h6 class="mb-0">Utilisateur 1</h6></a>
                    <small class="text-muted">@utilisateur1</small>
                    <p class="mb-1">Description de l'utilisateur 1</p>
                </div>
                <button class="btn btn-sm btn-outline-light ms-auto me-2">👍</button>
                <button class="btn btn-sm btn-outline-success me-2">+ Suivre</button>
                <a href="user.html" class="btn btn-sm btn-outline-primary">Consulter le profil</a>
            </li>
            <li class="border list-group-item d-flex align-items-center">
                <img src="img/pdp.png" alt="Utilisateur2" class="rounded-circle me-2" width="40">
                <div>
                    <a href="user.html" class="text-decoration-none text-white"><h6 class="mb-0">Utilisateur 2</h6></a>
                    <small class="text-muted">@utilisateur2</small>
                    <p class="mb-1">Description de l'utilisateur 2</p>
                </div>
                <button class="btn btn-sm btn-outline-light ms-auto me-2">👍</button>
                <button class="btn btn-sm btn-outline-success me-2">- Supprimer</button>
                <a href="user.html" class="btn btn-sm btn-outline-primary">Consulter le profil</a>
            </li>
            <li class="border list-group-item d-flex align-items-center">
                <img src="img/pdp.png" alt="Utilisateur3" class="rounded-circle me-2" width="40">
                <div>
                    <a href="user.html" class="text-decoration-none text-white"><h6 class="mb-0">Utilisateur 3</h6></a>
                    <small class="text-muted">@utilisateur3</small>
                    <p class="mb-1">Description de l'utilisateur 3</p>
                </div>
                <button class="btn btn-sm btn-outline-light ms-auto me-2">👍</button>
                <button class="btn btn-sm btn-outline-success me-2">+ Suivre</button>
                <a href="user.html" class="btn btn-sm btn-outline-primary me-2">Consulter le profil</a>
                <button class="btn btn-sm btn-outline-danger">Supprimer le like</button>
            </li>
        </ul>
</main>

<jsp:include page="footer.jsp" />

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>