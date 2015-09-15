<%@page contentType="text/html;charset=UTF-8" isErrorPage="true"%>
<!DOCTYPE html>
<html>
  <head>
    <META http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>错误页面</title>
  </head>
  <body>
  <span><%=request.getAttribute("javax.servlet.error.status_code")%> : 发生了错误</span>
  <br>
  <span><%=request.getAttribute("javax.servlet.error.exception_type")%></span>
  </body>
  <footer>
      <h4>CookieValue:<%=request.getAttribute("cookieValue")%></h4>
  </footer>
</html>