<%--
  Created by IntelliJ IDEA.
  User: ye
  Date: 2018/4/23
  Time: 10:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
    <title>首页</title>
    <script type="text/javascript" src="/js/jquery/jquery-1.12.2.min.js"></script>
</head>
<body>
<h1>首页</h1>
<h2>用户${user.login_name}登录成功！</h2>
<shiro:hasPermission name="/test1">
    <h3>测试权限1</h3>
</shiro:hasPermission>
<shiro:hasPermission name="/test2">
    <h3>测试权限2</h3>
</shiro:hasPermission>
<shiro:hasPermission name="/test3">
    <h3>测试权限3</h3>
</shiro:hasPermission>
<shiro:hasPermission name="/solrUser/list">
    <a href="/test/solrUser/list" onclick="alertCtx()">搜索引擎</a>
</shiro:hasPermission>
</body>
</html>
