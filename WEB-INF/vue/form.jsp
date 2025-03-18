<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="modele.dto.Groupe, modele.dto.PostDetails" %>

<% 
    int User_ID = (int) request.getSession().getAttribute("uid");
%>
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
<form class="mb-3" action="${pageContext.request.contextPath}/posts" method="post" enctype="multipart/form-data">
    <textarea class="form-control bg-dark text-white mb-2" name="contenu" rows="3" maxlength="150" placeholder="Exprimez-vous..."></textarea>
    <input type="file" class="form-control bg-dark text-white mb-2" name="image" accept="image/*">
    <label for="duration" class="form-label text-white">Durée du post</label>
        <div class="input-group mb-2">
            <input type="number" class="form-control bg-dark text-white" name="duree" min="1" max="365" placeholder="Durée">
            <select class="form-select bg-dark text-white" name="dureeUnite">
                <option value="hours">Heures</option>
                <option value="days">Jours</option>
            </select>
        </div>
    <input type="hidden" name="uid" value="<%= User_ID %>">
    <% if(request.getAttribute("post") != null) { %>
    <input type="hidden" name="pidParent" value="<%= ((PostDetails)request.getAttribute("post")).getPid() %>">
    <input type="hidden" name="gid" value="<%= ((PostDetails)request.getAttribute("post")).getGid() %>">
    <% } else if(request.getAttribute("groupe") != null) { %>
    <input type="hidden" name="gid" value="<%= ((Groupe)request.getAttribute("groupe")).getGid() %>">
    <% } %>
    <button type="submit" class="btn btn-primary w-100">Publier</button>
</form>
<jsp:include page="post.jsp" />