<%--
  Created by IntelliJ IDEA.
  User: xiaopeng.cai
  Date: 14-5-18
  Time: 下午10:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Delete Result</title>
</head>
<body>
<h1><%=request.getAttribute("result")%>
</h1>
</body>
<footer>
    <h4>CookieValue:<%=request.getAttribute("cookieValue")%></h4>
</footer>
</html>
