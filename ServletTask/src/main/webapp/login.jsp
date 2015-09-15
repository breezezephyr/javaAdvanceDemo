<%--
  Created by IntelliJ IDEA.
  User: xiaopeng.cai
  Date: 14-5-19
  Time: 上午10:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<h1>登录页面</h1>
已存在的用户名
<br>sean
<br>test<br>
${message}
<form action="login" method="post">
    <table>
        <tr>
            <td><label>登录名称</label></td>
            <td><input type="text" name="username"/></td>
        </tr>
        <tr>
            <td></td>
            <td><input type="submit" value="提交"></td>
        </tr>
    </table>
</form>
</body>
</html>
