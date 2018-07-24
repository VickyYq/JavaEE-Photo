<%@ page import="cn.edu.zucc.bean.User" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Arrays" %>
<%@ page import="cn.edu.zucc.bean.Photoalbum" %>
<%@page contentType="text/html" %>
<%@page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>链接</title>
    <link type="text/css" rel="stylesheet" href="css/all.css">
    <link type="text/css" rel="stylesheet" href="css/link.css">
    <link rel="stylesheet" href="//at.alicdn.com/t/font_700517_kn066a8vld.css">

</head>
<body>
<div id="nav" class="container">
    <div id="logo">
        <a href="listpa">
            <img src="img/logo.jpg"/>
            <h1>XX相册</h1>
        </a>
    </div>
</div>


<div class="container">
    <%
        User user = (User) request.getSession().getAttribute("obj");
        if (user == null) {
            request.getRequestDispatcher("share_login.jsp").forward(request,response);
        } else {
            String url = request.getQueryString();
            List<String> result = Arrays.asList(url.split(","));
            int photoid = Integer.valueOf(result.get(0));
            String exphoto = result.get(1);
            String photourl = result.get(2);
            String username = result.get(3);
    %>
    <div id="link">
        <img src="<%=photourl%>"/>
        <p><%=exphoto%></p>
        <button onclick="download(<%=photoid%>)"><i class="iconfont icon-down"></i>下载</button>
        <button  onclick="movephoto(<%=photoid%>)"><i class="iconfont icon-exit"></i>保存到相册</button>
    </div>
    <div id="user">
        <img src="img/user_img.jpg"/>
        <p><%=username%></p>
    </div>
    <%
        }
    %>
</div>


<div id="move-photo" class="modal no">
    <div class="modal-content">
        <span class="close">&times;</span>
        <p>保存到相册：</p>
        <select id="paid" name="paid">
            <%
                List objlist = (List) request.getSession().getAttribute("photoalbum");
                if (objlist != null) {
                    for (int i = 0; i < objlist.size(); i++) {
                        Photoalbum pab = (Photoalbum) objlist.get(i);
            %>
            <option value="<%=pab.getPaid()%>"><%=pab.getPaname()%>
            </option>
            <%
                    }
                }
            %>

        </select>
        <input type="button" class="btn" id="savep" value="保 存"/>
    </div>
</div>
</body>
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript">
    function movephoto(photoid){

        $("#move-photo").show();

        $("#move-photo .close").click(function () {
            $("#move-photo").hide();
        });
        $("#savep").click(function () {
            $.ajax({
                url:"movesharephoto",
                data:{
                    "photoid":photoid,
                    "paid":$("#paid").val()
                },
                dataType:"json",
                Type:"POST",
                success:function (data) {
                    if(data.result == 1){
                        window.location.href = "logincheck.jsp";
                    }
                },
                error:function (data) {
                    alert("添加失败")
                }
            })
        })
    }


    function download(photoid) {
        $.ajax({
            url:"downloadphoto",
            data:{
                "photoid":photoid
            },
            type: "POST",
            dataType: "json",
            success:function(data){
                $(this).next().text("");
            }
        })
    }
</script>
</html>