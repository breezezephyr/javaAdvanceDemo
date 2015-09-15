<%--
  Created by IntelliJ IDEA.
  User: xiaopeng.cai
  Date: 14-5-18
  Time: 下午10:24
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Query Result</title>
</head>
<body>
<h1><%=request.getAttribute("result")%>
</h1>
<c:forEach var="user" items="${users}">
    id:<c:out value="${user.id}"></c:out>
    name:<c:out value="${user.username}"></c:out>
    </br>
</c:forEach>
</body>
<footer>
    <h4>CookieValue:<%=request.getAttribute("cookieValue")%></h4>
</footer>
</html>