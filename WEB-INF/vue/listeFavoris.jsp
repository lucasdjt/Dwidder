<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="modele.dto.PostDetails, java.util.List, java.util.ArrayList" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Liste des Favoris - Dwidder</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="icon" href="${pageContext.request.contextPath}/img/logo.ico">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<% 
int User_ID = (int) session.getAttribute("me_uid");
%>

<jsp:include page="include_header.jsp" />

<main class="container mt-5 pt-4">
    <section class="col-md-8 offset-md-2">
        <jsp:include page="include_popUp.jsp" />
        <jsp:include page="include_post.jsp" />
    </section>
</main>

<jsp:include page="include_footer.jsp" />

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
<input type="hidden" name="author" value="Lucas DE JESUS TEIXEIRA">
<input type="hidden" name="github" value="https://github.com/lucasdjt">