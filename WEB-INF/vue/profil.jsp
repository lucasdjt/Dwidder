<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Profil de Draggas - Dwidder</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="icon" href="img/logo.ico">
    <link rel="stylesheet" href="css/style.css">
</head>
<body>

<jsp:include page="header.jsp" />

<main class="container mt-5 pt-4">
    <div class="row">
        <aside class="col-md-4">
            <div class="card text-center">
                <img src="img/pdp.png" alt="Photo de profil de l'utilisateur" class="card-img-top rounded-circle mx-auto mt-3" style="width: 100px; height: 100px;">
                
                <div class="card-body">
                    <h3 class="card-title">Draggas</h3></a>
                    <p class="text-muted">@draggas</p>
                    
                    <div class="card p-2 border">
                        <p class="mb-0">Bio de l'utilisateur : Développeur passionné, amateur de café et de musique 🎵</p>
                    </div>
        
                    <ul class="list-group mt-3 text-start">
                        <li class="list-group-item"><strong>Nom : </strong>Lucas DJT</li>
                        <li class="list-group-item"><strong>Date de naissance : </strong>10/05/2005</li>
                        <li class="list-group-item"><strong>Localisation : </strong>Lille, France</li>
                        <li class="list-group-item"><strong>Sexe : </strong>Homme</li>
                        <li class="list-group-item"><strong>Langue : </strong>Français (FR)</li>
                        <li class="list-group-item"><strong>Date d'inscription : </strong>01/03/2025</li>
                    </ul>
                    <div class="d-flex flex-column align-items-stretch mt-3">
                        <a href="listeUser.html" class="btn btn-outline-primary mb-2">Abonnés : 150</a>
                        <a href="listeUser.html" class="btn btn-outline-secondary mb-2">Abonnements : 200</a>
                        <a href="listeFavoris.html" class="btn btn-outline-warning mb-2">⭐ Ajouter aux favoris</a>
                    </div>
                </div>
            </div>
        </aside>

        <section class="col-md-8">
            <h2 class="text-primary">Profil de Draggas</h2>
            <article class="card mb-3">
                <header class="card-header d-flex align-items-center">
                    <img src="img/pdp.png" alt="Pdp" class="rounded-circle me-2" width="40">
                    <div>
                        <a href="user.html" class="text-decoration-none text-white"><h6 class="mb-0">Draggas</h6></a>
                        <small class="text-muted">@draggas - 12/10/2024</small>
                    </div>
                    <button class="btn btn-outline-secondary btn-sm ms-auto">⚙️</button>
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
                        <a href="user.html" class="text-decoration-none text-white"><h6 class="mb-0">Draggas</h6></a>
                        <small class="text-muted">@draggas - 2min</small>
                    </div>
                    <button class="btn btn-outline-secondary btn-sm ms-auto">⚙️</button>
                </header>
                <main class="card-body">
                    <p>Ce tweet dure 2s</p>
                    <blockquote class="text-muted small">Dure plus que 2s</blockquote>
                </main>
                <footer class="card-footer d-flex justify-content-around">
                    <a href="listeLike.html" class="btn btn-outline-primary btn-sm">👍 0</a>
                    <a href="listeReponse.html" class="btn btn-outline-secondary btn-sm">💬 0</a>
                    <button class="btn btn-outline-warning btn-sm">⭐ Favoris</button>
                </footer>
            </article>
        </section>
    </div>
</main>

<jsp:include page="footer.jsp" />

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
