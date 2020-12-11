<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2020/12/11
  Time: 10:55
  To change this template use File | Settings | File Templates.
--%>
<%--page标签中必须添加isELIgnored=false，ModelAndView中的值才能取到--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"  isELIgnored="false" %>
<html>
<head>
    <title>我是hls页面</title>
</head>
<body>
    <h1>姓名：${name}</h1>
    <h1>年龄：${age}</h1>
</body>
</html>
