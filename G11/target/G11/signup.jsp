<%@ page import="cn.edu.zucc.bean.User" %><%--
  Created by IntelliJ IDEA.
  User: vicky
  Date: 2018/7/12
  Time: 上午10:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>注册</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>注册</title>
    <link type="text/css" rel="stylesheet" href="css/all.css">
    <link type="text/css" rel="stylesheet" href="css/signup.css">
    <script src="js/jquery.js"></script>
    <link rel="stylesheet" href="//at.alicdn.com/t/font_700517_uope4g309t.css">
    <script>
        $(document).ready(function () {

            $("#userid").focus();

            $("#userid").focus(function () {
                $(this).next().text("");
            });
            $("#username").focus(function () {
                $(this).next().text("");
            });
            $("#userpwd").focus(function () {
                $(this).next().text("");
            });
            $("#email").focus(function () {
                $(this).next().text("");
            });

            $("#userid").blur(function () {
                if ($(this).val() == "") {
                    $("#warning1").html("<i class=\"iconfont icon-warnfill\"/> 用户名不能为空");
                }
                else {
                    if ($(this).val().length < 6 || $(this).val().length > 12) {
                        $("#warning1").html("<i class=\"iconfont icon-warnfill\"/> 长度应为6-12个字符");
                    }
                    else {
                        $.ajax({
                            url: "signup",
                            data: {"userid": $("#userid").val()},
                            type: "POST",
                            dataType: "json",

                            success: function (data) {
                                if (data.result == 1) {
                                    $("#warning1").html("<i class=\"iconfont icon-warnfill\"/> 用户名已存在");
                                }
                                else {

                                    $(this).next().text("");
                                }
                            }
                        });
                    }
                }
            });

            $("#username").blur(function () {
                if ($(this).val() == "") {
                    $("#warning2").html("<i class=\"iconfont icon-warnfill\"/> 昵称不能为空")
                }
                else {
                    $(this).next().text("")
                }
            });
            $("#email").blur(function () {
                if ($(this).val() == "") {
                    $("#warning3").html("<i class=\"iconfont icon-warnfill\"/> 邮箱不能为空")
                }
                else {
                    if (!$(this).val().match(/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/) && !$(this).val() == "") {
                        $("#warning3").html("<i class=\"iconfont icon-warnfill\"/> 请输入正确邮箱")
                    }
                    else {
                        $(this).next().text("");
                    }

                }
            });

            $("#userpwd").blur(function () {
                if ($(this).val() == "") {
                    $("#warning4").html("<i class=\"iconfont icon-warnfill\"/> 密码不能为空")
                }
                else {
                    if($(this).val().length < 6)
                        $("#warning4").html("<i class=\"iconfont icon-warnfill\"/> 密码不能少于6位");
                    else
                    $(this).next().text("");
                }
            });
        })


    </script>
</head>
<body>
<div id="signup">
    <div id="logo"><img src="img/logo.jpg"/></div>
    <div id="title"><h1>XX相册</h1></div>
    <div id="sign-up">
        <form action="adduserresult" method="post">
            <div>
                <p>用户名：</p>
                <input type="text" id="userid" name="userid" value=""/>
                <p class="red" id="warning1"></p>
            </div>
            <div>
                <p>昵称：</p>
                <input type="text" id="username" name="username" value=""/>
                <p class="red" id="warning2"></p>
            </div>
            <div>
                <p>电子邮件：</p>
                <input type="email" id="email" name="email" value=""/>
                <p class="red" id="warning3"></p>
            </div>
            <div>
                <p>密码：</p>
                <input type="password" id="userpwd" name="userpwd" placeholder="不得少于6位字符" value=""/>
                <p class="red" id="warning4"></p>
            </div>
            <input type="submit" class="btn" value="注 册"/>
        </form>
    </div>
</div>
</body>


</html>
