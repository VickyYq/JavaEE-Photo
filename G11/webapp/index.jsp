<%@ page import="java.util.List" %>
<%@ page import="cn.edu.zucc.bean.Photoalbum" %>
<%@page contentType="text/html" %>
<%@page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>首页</title>
    <link type="text/css" rel="stylesheet" href="css/all.css">
    <link type="text/css" rel="stylesheet" href="css/index.css">
    <link rel="stylesheet" href="//at.alicdn.com/t/font_700517_ym62qftvxmm.css">
    <link href="lib/bootstrap-fileinput/css/fileinput.css" media="all" rel="stylesheet" type="text/css">
</head>
<body>
<%

    double usercapacity = (double) request.getSession().getAttribute("usercapacity");
    String userid = (String) request.getSession().getAttribute("userid");
%>
<jsp:include page="head.jsp" flush="true"/>
<div id="album-add" class="modal no">
    <div class="modal-content">
        <span class="close">&times;</span>
        <p>相册名称：</p>
        <input type="text" id="paname" name="paname" value=""/>
        <p class="red no"></p>
        <p>相册描述：</p>
        <textarea id="padetail" name="padetail" rows="3" cols="30"></textarea>
        <p>权限：</p>
        <select id="palimit" name="palimit">
            <option value="所有人可见">所有人可见</option>
            <option value="好友可见">好友可见</option>
            <option value="部分好友可见">部分好友可见</option>
            <option value="仅自己可见">仅自己可见</option>
        </select>
        <input type="button" id="save" class="btn" value="保 存" onclick="add_album(<%=userid%>)"/>
    </div>
</div>

<div id="album-add1" class="modal no">
    <div class="modal-content">
        <span class="close">&times;</span>
        <p>相册名称：</p>
        <input type="text" id="paname1" name="paname" value=""/>
        <p class="red no"></p>
        <p>相册描述：</p>
        <textarea id="padetail1" name="padetail" rows="3" cols="30"></textarea>
        <p>权限：</p>
        <select id="palimit1" name="palimit1">
            <option value="所有人可见">所有人可见</option>
            <option value="好友可见">好友可见</option>
            <option value="部分好友可见">部分好友可见</option>
            <option value="仅自己可见">仅自己可见</option>
        </select>
        <input type="button" id="save1" class="btn" value="保 存"/>
    </div>
</div>

<div id="photo-add" class="modal no">
    <div class="modal-content">
        <span class="close">&times;</span>
        <div id="pa-select">
            <p>上传到相册：</p>
            <select id="paid" name="paid" onchange="add_photo()">
                <option value="">请选择相册</option>
                <%
                    List objlist1 = (List) request.getAttribute("objlist");
                    if (objlist1 != null) {
                        for (int i = 0; i < objlist1.size(); i++) {
                            Photoalbum pab = (Photoalbum) objlist1.get(i);
                %>
                <option value="<%=pab.getPaid()%>"><%=pab.getPaname()%>
                </option>
                <%
                        }
                    }
                %>
            </select>
        </div>
        <div id="photo-select">
            <div id="input"><input id="input1" type="file"></div>
            <input id="input-b5" value="file" name="file" type="file" multiple>
        </div>
    </div>
</div>

<div id="mdf-photo" class="modal no">
    <div class="modal-content">
        <span class="close">&times;</span>
        <p>名称：</p>
        <input type="text" id="exphoto" name="exphoto" value=""/>
        <p>照片描述：</p>
        <textarea id="photodetail" rows="3" cols="30"></textarea>
        <input type="button" class="btn" value="保 存"/>
    </div>
</div>

<div id="content">
    <div class="container">
        <button id="add-photo"><i class="iconfont icon-cameraaddfill"></i>上传照片</button>
        <button id="add-album" onclick="edit_album()">创建相册</button>
        <button id="vip"><i class="iconfont icon-zuanshi"></i>开通会员</button>
        <p id="vip-p">已用<%=usercapacity%>M容量，开通会员获得更大容量</p>
    </div>

    <%--删除相册--%>
    <div id="album-del" class="modal no">
        <div class="modal-content">
            <span class="close">&times;</span>
            <p>确定删除该相册</p>
            <button class="btn" id="btdel">确定</button>
            <button class="btn" id="btcloce">取消</button>
        </div>
    </div>

    <%--添加相册--%>
    <ul id="album">
        <%
            List objlist = (List) request.getAttribute("objlist");
            if (objlist != null) {
                for (int i = 0; i < objlist.size(); i++) {
                    Photoalbum pab = (Photoalbum) objlist.get(i);
                    int paid = pab.getPaid();
        %>
        <li>
            <div class="album-modal">
                <a href="listphoto?paid=<%=paid%>&paname=<%=pab.getPaname()%>"><img src="<%=pab.getPaurl()%>"></a>
                <h1><%=pab.getCount()%></h1>
                <div class="pa-div no">
                    <button class="pa-mdf" onclick="edit_album1(<%=paid%>)">编辑</button>
                    <button class="pa-del" onclick="delete_album(<%=paid%>)">删除</button>
                </div>
            </div>

            <div class="album-txt">
                <p><%=pab.getPaname()%>
                </p>
                <i class="iconfont icon-lock"></i>
            </div>
        </li>
        <%
                }
            }
        %>
    </ul>
</div>
</body>
<script type="text/javascript" src="js/jquery.js"></script>
<script src="lib/bootstrap-fileinput/js/plugins/piexif.min.js" type="text/javascript"></script>
<script src="lib/bootstrap-fileinput/js/plugins/sortable.min.js" type="text/javascript"></script>
<script src="lib/bootstrap-fileinput/js/plugins/purify.min.js" type="text/javascript"></script>
<script src="lib/bootstrap-fileinput/js/fileinput.js"></script>
<script src="lib/bootstrap-fileinput/js/locales/zh.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
        $(".album-modal").mouseenter(function () {
            $(this).children(".pa-div").slideDown();
        });
        $(".album-modal").mouseleave(function () {
            $(this).children(".pa-div").slideUp();
        });
        $("#add-photo").click(function () {
            $("#photo-add").show();
            $("#photo-add .close").click(function () {
                $("#photo-add").hide();
            });
        });
        $("#input-b5").hide();
        $("#input1").fileinput({
            language: 'zh',
            uploadUrl: "addphoto",
            showUpload: false,
            dropZoneEnabled: true,
            mainClass: "input-group-lg",
            autoReplaceBoolean: false
        })
    });

    function add_photo() {

        $("#input").hide();

        $("#input-b5").show();

        $("#input-b5").fileinput({
            language: 'zh',
            uploadUrl: "addphoto",
            showUpload: true,
            dropZoneEnabled: true,
            mainClass: "input-group-lg",
            autoReplaceBoolean: false,
            uploadAsync: true,
            uploadExtraData: {
                "paid": $("#paid").val()

            }
        }).on("fileuploaded", function (event, data, previewId, index) {
            if (data.response) {
                $("#photo-add").hide();
                window.location.reload();
            }
        })
    }


    function edit_album() {
        $("#album-add").show();
        $("#album-add .close").click(function () {
            $("#album-add").hide();
        });
        $("#paname").focus();
        $("#paname").blur(function () {
            if ($("#paname").val() == "") {
                $(this).next().html("<i class='iconfont icon-warnfill'></i>相册名称不能为空");
                $(this).next().show();
                $(this).focus(function () {
                    $(this).next().hide();
                });
            }
        });
    }

    function edit_album1(paid) {
        $("#album-add1").show();
        $("#album-add1 .close").click(function () {
            $("#album-add1").hide();
        });
        $("#paname1").focus();
        $("#paname1").blur(function () {
            if ($("#paname1").val() == "") {
                $(this).next().html("<i class='iconfont icon-warnfill'></i>相册名称不能为空");
                $(this).next().show();
                $(this).focus(function () {
                    $(this).next().hide();
                });
            }
        });
        $("#save1").click(function () {
            $("#album-add1").hide();

            $.ajax({
                url: "modifyphotoalbum",
                data: {
                    "paname": $("#paname1").val(),
                    "padetail": $("#padetail1").val(),
                    "palimit": $("#palimit1").val(),
                    "paid": paid
                },
                type: "POST",
                dataType: "json",
                success: function (data) {
                    if (data.result == 1) {
                        location.reload(true);
                    }
                }
            })
        })
    }

    function delete_album(paid) {
        $("#album-del").show();
        $("#album-del .close").click(function () {
            $("#album-del").hide();
        });
        $("#btcloce").click(function () {
            $("#album-del").hide();
        });

        $("#btdel").click(function () {
            $("#album-del").hide();
            $.ajax({
                url: "deletephotoalbum",
                data: {"paid": paid},
                type: "POST",
                dataType: "json",
                success: function (data) {
                    if (data.result == 1) {
                        location.reload(true);
                    }
                }
            })
        })
    }

    function add_album(userid) {
        $("#save").click(function () {
            $("#album-add").hide();
        });

        $.ajax({
            url: "addphotoalbum",
            data: {
                "paname": $("#paname").val(),
                "padetail": $("#padetail").val(),
                "palimit": $("#palimit").val(),
                "userid": userid
            },
            type: "POST",
            dataType: "json",
            success: function (data) {
                if (data.result == 1) {
                    location.reload(true);
                }
            }
        })
    }
</script>
</html>