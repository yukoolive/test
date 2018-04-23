
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>登录页面</title>
    <script type="text/javascript" src="/js/jquery/jquery-1.12.2.min.js"></script>
</head>
<body>
<h1>login page</h1>
<h2>欢迎登录</h2>
<form action="/common/login" method="post">
    <div>
        <label>用户名:<input type="text" id="name" name="name"/></label>
    </div>
    <div>
        <label>密码:<input type="password"/></label>
    </div>
    <div>
        <input type="submit" value="提交"/>
        <input type="reset" value="重置" />
    </div>
</form>
</body>
</html>
