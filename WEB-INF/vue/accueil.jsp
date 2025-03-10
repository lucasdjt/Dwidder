<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Accueil - Dwidder</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="icon" href="Exemple/img/logo.ico">
    <link rel="stylesheet" href="Exemple/css/style.css">
</head>
<body>

<jsp:include page="header.jsp" />

<main class="container mt-4">
    <div class="row">
        <section class="col-md-8">
            <h2 class="text-primary">Accueil</h2>
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
            <form class="mb-3" action="posts" method="post" enctype="multipart/form-data">
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
                <input type="hidden" name="uid" value="1">
                <button type="submit" class="btn btn-primary w-100">Publier</button>
            </form>
            <article class="card mb-3">
                <header class="card-header d-flex align-items-center">
                    <img src="img/pdp.png" alt="Pdp" class="rounded-circle me-2" width="40">
                    <div>
                        <a href="user.html" class="text-decoration-none text-white"><h6 class="mb-0">Draggas</h6></a>
                        <small class="text-muted">@draggas - 12/10/2024</small>
                    </div>
                </header>
                <main class="card-body">
                    <p>C'est le 1er post du réseau social, on est tous heureux !!</p>
                    <img src="img/pdp.png" alt="Image" class="rounded w-100">
                </main>
                <footer class="card-footer d-flex justify-content-around">
                    <a href="listeLike.html" class="btn btn-outline-primary btn-sm">👍 25k</a>
                    <a href="listeReponse.html" class="btn btn-outline-secondary btn-sm">💬 192</a>
                    <button class="btn btn-outline-warning btn-sm">⭐ Favoris</button>
                </footer>
            </article>

            <article class="card mb-3">
                <header class="card-header d-flex align-items-center">
                    <img src="img/pdp.png" alt="Pdp" class="rounded-circle me-2" width="40">
                    <div>
                        <a href="user.html" class="text-decoration-none text-white"><h6 class="mb-0">OMG</h6></a>
                        <small class="text-muted">@omg - 10h</small>
                    </div>
                </header>
                <main class="card-body">
                    <p>Je suis là</p>
                    <blockquote class="text-muted small">Dure plus que 2h</blockquote>
                </main>
                <footer class="card-footer d-flex justify-content-around">
                    <a href="listeLike.html" class="btn btn-outline-primary btn-sm">👍 10</a>
                    <a href="listeReponse.html" class="btn btn-outline-secondary btn-sm">💬 0</a>
                    <button class="btn btn-outline-warning btn-sm">⭐ Favoris</button>
                </footer>
            </article>
        </section>

        <aside class="col-md-4">
            <h2 class="text-primary">Utilisateurs</h2>
            <ul class="list-group">
                <li class="border list-group-item d-flex align-items-center">
                    <img src="img/pdp.png" alt="Utilisateur1" class="rounded-circle me-2" width="40">
                    <a href="user.html" class="text-decoration-none text-white">Utilisateur 1</a>
                    <button class="btn btn-sm btn-outline-success ms-auto">+ Suivre</button>
                </li>
                <li class="border list-group-item d-flex align-items-center">
                    <img src="img/pdp.png" alt="Utilisateur2" class="rounded-circle me-2" width="40">
                    <a href="user.html" class="text-decoration-none text-white">Utilisateur 2</a>
                    <button class="btn btn-sm btn-outline-success ms-auto">+ Suivre</button>
                </li>
                <li class="border list-group-item d-flex align-items-center">
                    <img src="img/pdp.png" alt="Utilisateur3" class="rounded-circle me-2" width="40">
                    <a href="user.html" class="text-decoration-none text-white">Utilisateur 3</a>
                    <button class="btn btn-sm btn-outline-success ms-auto">- Supprimer</button>
                </li>
                <li class="border list-group-item d-flex align-items-center">
                    <img src="img/pdp.png" alt="Utilisateur4" class="rounded-circle me-2" width="40">
                    <a href="user.html" class="text-decoration-none text-white">Utilisateur 4</a>
                    <button class="btn btn-sm btn-outline-success ms-auto">+ Suivre</button>
                </li>
                <li class="border list-group-item d-flex align-items-center">
                    <img src="img/pdp.png" alt="Utilisateur5" class="rounded-circle me-2" width="40">
                    <a href="user.html" class="text-decoration-none text-white">Utilisateur 5</a>
                    <button class="btn btn-sm btn-outline-success ms-auto">+ Suivre</button>
                </li>
            </ul>
            <a href="listeUser.html" class="btn btn-outline-primary mt-2 w-100">Afficher Plus</a>
        </aside>
    </div>
</main>

<jsp:include page="footer.jsp" />

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
