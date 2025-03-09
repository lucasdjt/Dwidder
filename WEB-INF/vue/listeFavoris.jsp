<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Liste des Favoris - Dwidder</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="icon" href="img/logo.ico">
    <link rel="stylesheet" href="css/style.css">
</head>
<body>

<jsp:include page="header.jsp" />

<main class="container mt-5 pt-4">
    <section class="col-md-8 offset-md-2">
        <h3 class="title">Liste des posts favoris (50)</h3>
        <article class="card mb-3">
            <header class="card-header d-flex align-items-center">
                <img src="img/pdp.png" alt="Pdp" class="rounded-circle me-2" width="40">
                <div>
                    <a href="user.html" class="text-decoration-none text-white"><h6 class="mb-0">User</h6></a>
                    <small class="text-muted">@user - 2h</small>
                </div>
            </header>
            <main class="card-body">
                <p>Incroyable ! 🔥</p>
                <img src="img/pdp.png" alt="Image" class="rounded w-100">
                <blockquote class="text-muted small">Il y a 2 heures</blockquote>
            </main>
            <footer class="card-footer d-flex justify-content-around">
                <a href="listeLike.html" class="btn btn-outline-primary btn-sm">👍 132</a>
                <a href="listeReponse.html" class="btn btn-outline-secondary btn-sm">💬 24</a>
                <button class="btn btn-outline-warning btn-sm">⭐ Retirer des Favoris</button>
            </footer>
        </article>

        <article class="card mb-3">
            <header class="card-header d-flex align-items-center">
                <img src="img/pdp.png" alt="Pdp" class="rounded-circle me-2" width="40">
                <div>
                    <a href="user.html" class="text-decoration-none text-white"><h6 class="mb-0">User</h6></a>
                    <small class="text-muted">@user - 2h</small>
                </div>
            </header>
            <main class="card-body">
                <p>OMG ? 👀</p>
                <blockquote class="text-muted small">Il y a 5 heures</blockquote>
            </main>
            <footer class="card-footer d-flex justify-content-around">
                <a href="listeLike.html" class="btn btn-outline-primary btn-sm">👍 89</a>
                <a href="listeReponse.html" class="btn btn-outline-secondary btn-sm">💬 17</a>
                <button class="btn btn-outline-warning btn-sm">⭐ Retirer des Favoris</button>
            </footer>
        </article>
    </section>
</main>

<jsp:include page="footer.jsp" />

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>