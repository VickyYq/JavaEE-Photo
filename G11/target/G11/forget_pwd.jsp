<%@page contentType="text/html" %>
<%@page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>忘记密码</title>
    <link type="text/css" rel="stylesheet" href="css/all.css">
    <link type="text/css" rel="stylesheet" href="css/forget_pwd.css">
    <link rel="stylesheet" href="//at.alicdn.com/t/font_700517_uope4g309t.css">
    <script type="text/javascript" src="js/jquery.js"></script>
    <script type="text/javascript">
        $(document).ready(function () {
            $("#userid").focus();

            $("#userid").focus(function () {
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
                            url: "email",
                            data: {"userid": $("#userid").val()},
                            type: "POST",
                            dataType: "json",
                            success: function (data) {
                                if (data.result == 1) {
                                    $("#warning1").html("<i class=\"iconfont icon-warnfill\"/> 用户名不存在");
                                }
                                else {
                                    $(this).next().text("");
                                }
                            }
                        });
                    }
                }
            });

            $("#email").blur(function () {
                if ($(this).val() == "") {
                    $("#warning2").html("<i class=\"iconfont icon-warnfill\"/> 邮箱不能为空")
                }
                else {
                    if (!$(this).val().match(/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/) && !$(this).val() == "") {
                        $("#warning2").html("<i class=\"iconfont icon-warnfill\"/> 请输入正确邮箱")
                    }
                    else {
                        $.ajax({
                            url: "email",
                            data: {"userid": $("#userid").val(), "email": $("#email").val()},
                            type: "POST",
                            dataType: "json",

                            success: function (data) {
                                if (data.result == 2) {
                                    $("#warning2").html("<i class=\"iconfont icon-warnfill\"/> 邮箱不匹配");
                                }
                                else {
                                    $(this).next().text("");
                                }
                            }
                        });
                    }

                }
            });

        });

        function sendemail(schema,servername,serverport){
            alert($("#userid").val());
            $.ajax({
                url:"sendemail",
                data:{userid: $("#userid").val(),
                    email:$("#email").val(),
                    schema:schema,
                    servername:servername,
                    serverport:serverport},
                type: "POST",
                dataType: "json",

                success:function(data){
                    $(this).next().text("");
                }
            })
        }
    </script>
</head>
<body>
<div id="forget_pwd">
    <div id="logo"><img src="img/logo.jpg"/></div>
    <div id="title"><h1>重置密码</h1></div>
    <div id="warn" class="no">
        <p></p>
    </div>
    <div id="email_ad" class="sign-border">
        <p>输入您的电子邮箱，我们将向您发送重置密码的链接。</p>
        <input type="text" id="userid" name="userid" placeholder="用户名" value=""/>
        <p class="red" id="warning1"></p>
        <input type="email" id="email" name="email" placeholder="电子邮箱" value=""/>
        <p class="red" id="warning2"></p>
        <input type="button" class="btn" value="发 送" onclick="sendemail('<%=request.getScheme()%>','<%=request.getServerName()%>','<%=request.getServerPort()%>')"/>
    </div>
</div>
</body>

</html>