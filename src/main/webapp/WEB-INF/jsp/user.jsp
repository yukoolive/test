<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>首页</title>
    <script type="text/javascript" src="/js/jquery/jquery-1.12.2.min.js"></script>
    <script type="text/javascript">
        //查询的内容
        function query(){
            var url = "/user/findUser";
            var loginName = $("#loginName").val();
            var params={ loginName: loginName};
            $.get(url,params,function(data){
                $.each(data, function(i, value) {
                    $("#remark").append(" <tr><td>" + value.id + "</td><td>"+ value.login_name + "</td></tr>");
                });
            });
        };
    </script>
</head>
<body>
<form action="">
    <input type="text" id="loginName"/>
    <input type="button" value="查询" onclick="query()"/>
    <table id="remark">
        <tr>
            <td>id</td>
            <td>登录名</td>
            <td>角色</td>
        </tr>
    </table>
</form>
</body>
</html>