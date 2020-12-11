<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2020/12/10
  Time: 10:11
  To change this template use File | Settings | File Templates.
--%>
<%--page标签中必须添加isELIgnored=false，ModelAndView中的值才能取到--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <meta http-equiv="content-type" content="text/html,charset=utf-8"/>
    <title>我是myError异常页面</title>
</head>
<body>
    <h1>异常状态码：${status}</h1>
    <h1>异常信息：${reason}</h1>
</body>
</html>
