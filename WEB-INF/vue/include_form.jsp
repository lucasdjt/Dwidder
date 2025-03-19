<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="modele.dto.Groupe, modele.dto.PostDetails" %>

<% 
    int User_ID = (int) session.getAttribute("me_uid");
%>

<form class="mb-3" action="${pageContext.request.contextPath}/post" method="post" enctype="multipart/form-data">
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
    <% if(session.getAttribute("post") != null) { %>
    <input type="hidden" name="pidParent" value="<%= ((PostDetails)session.getAttribute("post")).getPid() %>">
    <input type="hidden" name="gid" value="<%= ((PostDetails)session.getAttribute("post")).getGid() %>">
    <% } else if(session.getAttribute("groupe") != null) { %>
    <input type="hidden" name="gid" value="<%= ((Groupe)session.getAttribute("groupe")).getGid() %>">
    <% } %>
    <button type="submit" class="btn btn-primary w-100">Publier</button>
</form>
<jsp:include page="include_post.jsp" />
<%
session.removeAttribute("post");
session.removeAttribute("groupe");
%>