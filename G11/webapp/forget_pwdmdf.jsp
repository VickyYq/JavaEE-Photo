<%@ page import="cn.edu.zucc.bean.User" %>
<%@page contentType="text/html" %>
<%@page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>重置密码</title>
    <link type="text/css" rel="stylesheet" href="css/all.css">
    <link type="text/css" rel="stylesheet" href="css/user_mdf.css">
    <link rel="stylesheet" href="//at.alicdn.com/t/font_700517_18aebw6uord.css">
</head>
<body>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    String userid = request.getParameter("userid");
    System.out.println(userid);
%>
<div id="setting">
    <div id="logo"><img src="img/logo.jpg"/></div>
    <div id="title"><h1>重置密码</h1></div>
    <div id="set">
        <form action="modifyuserresult?userid=<%=userid%>" method="post">
            <div>
                <p>新密码：</p>
                <input type="password" id="userpwd" name="userpwd" placeholder="不得少于6位字符" value=""/>
                <p class="red no"></p>
            </div>
            <div>
                <p>确认密码：</p>
                <input type="password" id="pwd" name="pwd" value=""/>
                <p class="red no"></p>
            </div>
            <input type="submit" class="btn" value="保 存"/>
        </form>
    </div>
</div>
</body>
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
        $("#userpwd").focus();
        $("#userpwd").blur(function () {
            if ($("#userpwd").val() == "") {
                $(this).next().html("<i class='iconfont icon-warnfill'></i>密码不能为空");
                $(this).next().show();
                $(this).focus();
            } else if ($("#userpwd").val().length < 6) {
                $(this).next().html("<i class='iconfont icon-warnfill'></i>密码不得少于6位字符");
                $(this).next().show();
                $(this).focus();
            } else {
                $(this).next().hide();
                $("#pwd").focus();
            }
        });
        $("#pwd").blur(function () {
            if ($("#pwd").val() != $("#userpwd").val()) {
                $(this).next().html("<i class='iconfont icon-warnfill'></i>两次填写的密码不一致");
                $(this).next().show();
                $(this).focus(function () {
                    $(this).next().hide();
                });
            } else {
                $(this).next().hide();
            }
        });
        $("form").submit(function () {
            for (var i = 0; i < 2; i++) {
                if ($("input").eq(i).val() == "") {
                    return false;
                }
            }
            if ($("#pwd").val() != $("#userpwd").val()) {
                return false;
            }
        });
    });

    function getUrlParam(name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); // 构造一个含有目标参数的正则表达式对象
        var r = window.location.search.substr(1).match(reg);  // 匹配目标参数
        if (r != null) return unescape(r[2]); return null; // 返回参数值
    }
    var userid = getUrlParam('userid');
</script>
</html>