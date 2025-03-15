<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Créer un Groupe - Dwidder</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="icon" href="${pageContext.request.contextPath}/img/logo.ico">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<% 
int User_ID = (int) request.getSession().getAttribute("uid");
%>

<jsp:include page="header.jsp" />

<main class="container mt-5 pt-4">
    <h2 class="text-primary text-center">Créer un Groupe</h2>
    <div class="row justify-content-center">
        <div class="col-md-6">
            <%
            String success = request.getParameter("success");
            String existant = request.getParameter("existant");
            if ("0".equals(success)) {
            %>
                <p style="color:red;">Erreur lors de la création du groupe.</p>
            <%
            }
            if ("0".equals(existant)) {
            %>
                <p style="color:red;">Nom déjà utilisé.</p>
            <%
            }
            %>
            <form action="addGroupe" method="POST">
                <div class="mb-3">
                    <label for="nomGrp" class="form-label">Nom du Groupe</label>
                    <input type="text" class="form-control" id="nomGrp" name="nomGrp" placeholder="Nom unique du groupe" required>
                </div>

                <div class="mb-3">
                    <label for="description" class="form-label">Description</label>
                    <textarea class="form-control" id="description" name="description" rows="3" placeholder="Décrivez votre groupe"></textarea>
                </div>
                <input type="hidden" name="uid" value="<%= User_ID %>">
                <button type="submit" class="btn btn-primary w-100">Créer</button>
            </form>
        </div>
    </div>
</main>

<jsp:include page="footer.jsp" />

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>