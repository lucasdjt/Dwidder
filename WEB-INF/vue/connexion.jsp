<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Connexion - Dwidder</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="icon" href="${pageContext.request.contextPath}/img/logo.ico">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<header class="d-flex justify-content-between align-items-center p-2 bg-dark">
    <img src="${pageContext.request.contextPath}/img/TexteLogo.png" alt="Dwidder" class="mx-auto d-block" width="140">
</header>

<main class="container d-flex justify-content-center align-items-center vh-100">
    <div class="card bg-dark text-white p-4" style="max-width: 400px; width: 100%;">
        <h2 class="text-center text-white">Connexion</h2>
        <jsp:include page="popUp.jsp" />
        <form action="${pageContext.request.contextPath}/connexion" method="post">
            <div class="mb-3">
                <label for="identifier" class="form-label">Email ou Pseudo</label>
                <input type="text" class="form-control bg-dark text-white" name="identifier" required>
            </div>
            <div class="mb-3">
                <label for="password" class="form-label">Mot de passe</label>
                <div class="input-group">
                    <input type="password" class="form-control bg-dark text-white" name="password" id="password" required>
                    <button class="btn btn-outline-light" type="button" id="togglePassword">👁️</button>
                </div>
            </div>
            <button type="submit" class="btn btn-primary w-100">Se connecter</button>
        </form>
        <div class="text-center mt-3">
            <a href="${pageContext.request.contextPath}/inscription" class="text-decoration-none text-light">Créer un compte</a>
        </div>
    </div>
</main>

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
