<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title></title>
        <link type="text/css" rel="stylesheet" href="css/all.css">
		<link type="text/css" rel="stylesheet" href="css/head.css">
		<link rel="stylesheet" href="//at.alicdn.com/t/font_700517_ym62qftvxmm.css">
	</head>
	<body>
		<%
		    String username = (String)request.getSession().getAttribute("username");
			String userid = (String)request.getSession().getAttribute("userid");
		%>
		<div id="nav" class="container">
			<div id="logo">
				<a href="listpa">
					<img src="img/logo.jpg" />
					<h1> 个人相册</h1></a>
			</div>
			<div id="settings">
				<div id="set">
					<img src="img/user_img.jpg" />
					<i class="iconfont icon-triangledownfill"></i>
				</div>
				<ul id="setting" class="no">
					<li class="set-line">欢迎<%=username%></li>
					<li class="set-line"><a href="modifyuser?userid=<%=userid%>">修改密码</a></li>
					<li><a href="login.jsp">退出</a></li>
				</ul>
			</div>
		</div>
		<div id="user">
			<div id="user-content" class="container">
				<div id="user-img">
					<img src="img/user_img.jpg" />
				</div>
				<div id="user-name">
					<h2><%=username%></h2><i class="iconfont icon-writefill" onclick="name_mdf(<%=userid%>)"></i>
				</div>
				<button id="vip"><i class="iconfont icon-zuanshi"></i>开通会员</button>
			</div>
		</div>

		<div id="name-mdf" class="modal no">
			<div class="modal-content">
				<span class="close">&times;</span>
				<p>昵称：</p>
				<input type="text" id="username" name="username" value="" />
				<p class="red no"></p>
				<input type="button" id="save" class="btn" value="保 存" />
			</div>
		</div>
	</body>
	<script type="text/javascript" src="js/jquery.js"></script>
	<script type="text/javascript">
		$(document).ready(function(){
			$("#set").on("click", function(e){
			    $("#setting").toggle();
			    $(document).one("click", function(){
			        $("#setting").hide();
			    });
			    e.stopPropagation();
			});
			$("#setting").on("click", function(e){
			    e.stopPropagation();
			});
			$("#user-img").mouseenter(function(){
				$("#user-img button").slideDown();
			});
			$("#user-img").mouseleave(function(){
				$("#user-img button").slideUp();
			});
		});

		function  name_mdf(userid) {
            $("#name-mdf").show();
            $("#name-mdf .close").click(function(){
                $("#name-mdf").hide();
            });
            $("#username").focus();
            $("#username").blur(function(){
                if($("#username").val()==""){
                    $(this).next().html("<i class='iconfont icon-warnfill'></i>昵称不能为空");
                    $(this).next().show();
                    $(this).focus();
                }
            });

            $("#save").click(function () {
				$.ajax({
					url:"modifyusername",
					data:{"userid":userid,
						"username":$("#username").val()},
					Type:"POST",
					dataType:"json",
					success:function (data) {
					    if(data.result == 1){
						location.reload(true);
                        }
                    }
				})
            })
        }
	</script>
</html>