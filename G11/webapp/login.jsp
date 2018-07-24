<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>登录</title>
		<link type="text/css" rel="stylesheet" href="css/all.css">
		<link type="text/css" rel="stylesheet" href="css/login.css">
		<link rel="stylesheet" href="//at.alicdn.com/t/font_700517_uope4g309t.css">
        <script type="text/javascript" src="js/jquery.js"></script>
        <script type="text/javascript">

               function login() {
                   if($("#userid").val()==""||$("#userpwd").val()==""){
                       $("#warn>p").html("<i class='iconfont icon-warnfill'></i>用户名或密码不能为空");
                       $("#warn").show();
                       return false;
                   }
                   else{
                       $.ajax({
                           url: "logincheck",
                           data: {"userid": $("#userid").val(),"userpwd":$("#userpwd").val()},
                           type: "POST",
                           dataType: "json",
                           success: function (data) {
                               if (data.result == 1) {

                                   $("#warn").html("<i class=\"iconfont icon-warnfill\"/> 用户不存在");
                                   $("#warn").show();
                               }
                               else if (data.result == 2){

                                   $("#warn").html("<i class=\"iconfont icon-warnfill\"/> 密码错误");
                                   $("#warn").show();
                               }
                               else {
                                           window.location.href = "logincheck.jsp";
                               }
                           },

                       });
                   }
               }



        </script>
	</head>
	<body>
		<div id="login">
			<div id="logo"><img src="img/logo.jpg" /></div>
			<div id="title"><h1>个人相册</h1></div>
			<div id="warn" class="no">
				<p></p>
			</div>
			<div id="signin" class="sign-border">
					<p>用户名：</p>
					<input type="text" id="userid" name="userid"  value="" />
					<div id="signin-pwd">
						<p>密码：</p><a href="forget_pwd.jsp">忘记密码？</a>
					</div>
					<input type="password" id="userpwd" name="userpwd"  value="" />
					<input type="button" class="btn" value="登 录" onclick="login()"/>
			</div>
			<div id="signup" class="sign-border">
				<p>没有账户？<a class="signup-a" href="signup.jsp">立即注册</a></p>
			</div>
		</div>
	</body>

</html>