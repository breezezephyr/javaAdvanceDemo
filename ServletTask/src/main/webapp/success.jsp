<%--
  Created by IntelliJ IDEA.
  User: xiaopeng.cai
  Date: 14-5-18
  Time: 下午9:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>login Success</title>
</head>
<body>
<h1>Welcome <%=request.getAttribute("username") %>
</h1>
可以测试一下我们的功能了！在地址栏后输入：<br>
insert.do?name=XXX<br>
del.do?id=XXX<br>
update.do?id=XXX&name=YYY<br>
query.do<br>
query.do?id=XXX<br>
<a href="logout">Logout</a>
</body>
<footer>
    <h4>CookieValue:<%=request.getAttribute("cookieValue")%>
    </h4>
</footer>
</html>
