<%
    if (session.getAttribute("error") != null) {
%>
        <p style="color:red;"><%= session.getAttribute("error").toString() %></p>
<%
    }
    if (session.getAttribute("success") != null) {
%>
        <p style="color:green;"><%= session.getAttribute("success").toString() %></p>
<%
    }
%>
<%
session.removeAttribute("error");
session.removeAttribute("success");
%>