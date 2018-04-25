<%--
  Created by IntelliJ IDEA.
  User: ye
  Date: 2018/4/24
  Time: 10:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>solrOpt</title>
    <script type="text/javascript" src="/test/js/jquery/jquery-1.12.2.min.js"></script>
    <script type="text/javascript">
        //创建
        function addUser(){
            var url = "/test/solrUser/addUser";
            var id = $("#id").val();
            var login_name = $("#login_name").val();
            var password = $("#password").val();
            var role_id = $("#role_id").val();
            if($.isEmptyObject(id)||$.isEmptyObject(login_name)||$.isEmptyObject(password)||$.isEmptyObject(role_id)){
                alert("字段不能为空！")
                return false;
            }
            var params={
                "id":id,
                "login_name": login_name,
                "password":password,
                "role_id":role_id
            };
            $.get(url,params,function(data){
                alert(data);
            });
        };

        //查询
        function selectUser(){
            var url = "/test/solrUser/selectUser";
            var loginName = $("#loginNameForSel").val();
            var params={
                "loginName": loginName
            };
            $.get(url,params,
                function(result){
                    if (!result.success) {
                        alert(result.msg);
                    }else{
                        //alert(result.object);
                        $.each(result.obj, function(i, value) {
                            //alert(value);
                            //alert(value.id);
                            $("#remark").append(" <tr><td>" + value.id + "</td><td>"
                                                            + value.login_name + "</td><td>"
                                                            + value.password + "</td><td>"
                                                            + value.role_id + "</td></tr>");
                        });
                    }

            });
        };

    </script>
</head>
<body>
<form action="">
    ID:<input type="text" id="id"/>
    用户名:<input type="text" id="login_name"/>
    密码:<input type="text" id="password"/>
    角色ID<input type="text" id="role_id"/>
    <input type="button" value="新增" onclick="addUser()"/>
</form>

<form action="">
    用户名：<input type="text" id="loginNameForSel"/>
    <input type="button" value="查询" onclick="selectUser()"/>
    <table id="remark">
        <tr>
            <th>id</th>
            <th>用户名</th>
            <th>密码</th>
            <th>角色ID</th>
        </tr>
    </table>
</form>

</body>
</html>
