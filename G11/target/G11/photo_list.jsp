<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="java.util.List" %>
<%@ page import="cn.edu.zucc.bean.Photo" %>
<%@ page import="cn.edu.zucc.bean.Photoalbum" %>
<%@page contentType="text/html" %>
<%@page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>相册</title>
    <link type="text/css" rel="stylesheet" href="css/all.css">
    <link type="text/css" rel="stylesheet" href="css/photo_list.css">
    <link rel="stylesheet" href="//at.alicdn.com/t/font_700517_uqkqrwxbjjq.css">
    <link href="lib/bootstrap-fileinput/css/fileinput.css" media="all" rel="stylesheet" type="text/css">
</head>
<body>
<jsp:include page="head.jsp" flush="true"/>

<%
    String paname = (String) request.getSession().getAttribute("paname");
    int paid = Integer.valueOf(request.getAttribute("paid").toString());
    String url = (String) request.getAttribute("url");
    String username = (String) request.getSession().getAttribute("username");
%>
<div id="content">
    <div id="album" class="container">
        <img class="left" id="album-img" src="<%=url%>"/>
        <div class="left">
            <h2 id="album-name"><%=paname%>
            </h2>
            <button id="add-photo"><i class="iconfont icon-cameraaddfill"></i>上传照片</button>
            <button id="plgl-photo">批量管理</button>
        </div>
    </div>
    <div id="plgl" class="container no">
        <input type="checkbox"/>
        <p>全选</p>
        <button id="yd-btn" onclick="allmove()">移动到相册</button>
        <button id="bj-btn" onclick="alledit()">编辑照片信息</button>
        <button id="sc-btn" onclick="alldelete()">删除</button>
        <button id="wc-btn">完成</button>
    </div>
    <ul id="photo">
        <%
            List objlist = (List) request.getAttribute("objlist");
            if (objlist != null) {
                for (int i = 0; i < objlist.size(); i++) {
                    Photo photo = (Photo) objlist.get(i);
        %>
        <li>
            <div class="photo-modal">
                <img src="<%=photo.getPhotourl()%>"/>
                <input type="checkbox" class="no" name="box" value="<%=photo.getPhotoid()%>"/>
                <div class="photo-div no">
                    <button id="photo-mdf" title="编辑" onclick="edit_photo(<%=photo.getPhotoid()%>)"><i
                            class="iconfont icon-writefill"></i></button>
                    <button id="photo-move" title="移动" onclick="move_photo(<%=photo.getPhotoid()%>)"><i
                            class="iconfont icon-move"></i></button>
                    <button id="photo-del" title="删除" onclick="del_photo(<%=photo.getPhotoid()%>,<%=paid%>)"><i
                            class="iconfont icon-deletefill"></i></button>
                    <button id="photo-share" title="分享" onclick="share_photo(<%=photo.getPhotoid()%>,'<%=photo.getExphoto()%>','<%=photo.getPhotourl()%>','<%=username%>')"><i
                            class="iconfont icon-share"></i></button>
                    <button id="szfm" onclick="setting_page(<%=photo.getPhotoid()%>,<%=paid%>)">设置为封面</button>
                </div>
                <p><%=photo.getExphoto()%>
                </p>
            </div>
        </li>

        <%--查看照片--%>
        <div id="fd-photo" class="modal no">
            <div class="modal-content">
                <span class="close">&times;</span>
                <div class="fd-img">
                    <div><img class="img-src" src="<%=photo.getPhotourl()%>"/></div>
                    <p><%=photo.getPhotodetail()%>
                    </p>
                </div>
            </div>
        </div>
        <%
                }
            }
        %>
    </ul>
</div>

<div id="szfm-content" class="modal no">
    <div class="modal-content">
        <p><i class="iconfont icon-check"></i>设置封面成功</p>
    </div>
</div>


<%--修改照片--%>
<div id="mdf-photo" class="modal no">
    <div class="modal-content">
        <span class="close">&times;</span>
        <p>名称：</p>
        <input type="text" id="exphoto" name="exphoto" value=""/>
        <p>照片描述：</p>
        <textarea id="photodetail" rows="3" cols="30"></textarea>
        <input type="button" id="editphoto" class="btn" value="保 存"/>
    </div>
</div>

<%--移动照片--%>
<div id="move-photo" class="modal no">
    <div class="modal-content">
        <span class="close">&times;</span>
        <p>移动到相册：</p>
        <select id="paid" name="paid">
            <%
                List objlist1 = (List) request.getAttribute("allpa");
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
        <input type="button" class="btn" id="move" value="移 动"/>
    </div>
</div>

<%--删除照片--%>
<div id="del-photo" class="modal no">
    <div class="modal-content">
        <span class="close">&times;</span>
        <p>确定删除该照片？</p>
        <button class="btn" id="true">确定</button>
        <button class="btn" id="false">取消</button>
    </div>
</div>

<%--分享照片--%>
<div id="share-photo" class="modal no">
    <div class="modal-content">
        <span class="close">&times;</span>
        <p class="no"><i class="iconfont icon-check"></i>复制成功</p>
        <input type="text" id="link" name="link"
               value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()%>/link.jsp?"/>
        <button class="btn">复制链接</button>
    </div>
</div>

<%--上传照片--%>
<div id="photo-add" class="modal no">
    <div class="modal-content">
        <span class="close">&times;</span>
        <div id="pa-select">
            <p>上传到相册：<input type="text" class="no" id="paid1" value="<%=paid%>"><%=paname%>
            </p>
        </div>
        <div id="photo-select">
            <input id="input-b5" value="file" name="file" type="file" multiple>
        </div>
    </div>
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
        $(".photo-modal").mouseenter(function () {
            $(this).children(".photo-div").slideDown();
        });
        $(".photo-modal").mouseleave(function () {
            $(this).children(".photo-div").slideUp();
        });
        $("#plgl-photo").click(function () {
            $("#plgl").show();
            $("#photo input").show();
            $("#plgl input").click(function () {
                if ($("#plgl input").is(':checked'))
                    $("#photo input").prop("checked", true);
                else
                    $("#photo input").prop("checked", false);
            });
            $("#wc-btn").click(function () {
                $("#plgl").hide();
                $("#photo input").hide();
            });
        });
        $(".photo-modal img").click(function () {
            $(".img-src").attr("src", $(this).attr("src"));
            $("#fd-photo").show();
            $("#fd-photo .close").click(function () {
                $("#fd-photo").hide();
            });
        });

        $("#add-photo").click(function () {
            $("#photo-add").show();
            $("#photo-add .close").click(function () {
                $("#photo-add").hide();
            });
        });

        $("#input-b5").fileinput({
            language: 'zh',
            uploadUrl: "addphoto",
            showUpload: true,
            dropZoneEnabled: true,
            mainClass: "input-group-lg",
            autoReplaceBoolean: false,
            uploadAsync: true,
            uploadExtraData: {
                "paid": $("#paid1").val()

            }
        }).on("fileuploaded", function (event, data, previewId, index) {
            if (data.response) {
                $("#photo-add").hide();
                window.location.reload();
            }
        });
    });


    function share_photo(photoid,exphoto,photourl,username) {
        var link = $("#link").val();
        var link1 = link + photoid.toString()+","+exphoto.toString()+","+photourl.toString()+","+username.toString();
        document.getElementById("link").value = link1;
        $("#share-photo").show();
        $("#share-photo .close").click(function () {
            $("#share-photo").hide();
            document.getElementById("link").value = link;
        });
        $("#share-photo button").click(function () {
            var url = document.getElementById("link");
            url.select();
            document.execCommand("Copy");
            $("#share-photo p").show().delay(1000).fadeOut();
        });
    }

    function setting_page(photoid, paid) {
        $.ajax({
            url: "setting",
            data: {
                "photoid": photoid,
                "paid": paid
            },
            dataType: "json",
            Type: "POST",
            success: function (data) {
                if (data.result == 1) {
                    location.reload(true);
                    $("#szfm-content").show().delay(1000).fadeOut();

                }
            }
        })
    }

    function edit_photo(photoid) {
        $("#mdf-photo").show();
        $("#mdf-photo .close").click(function () {
            $("#mdf-photo").hide();
        });

        $("#editphoto").click(function () {

            $.ajax({
                url: "modifyphoto",
                data: {
                    "photoid": photoid,
                    "exphoto": $("#exphoto").val(),
                    "photodetail": $("#photodetail").val()
                },
                dataType: "json",
                Type: "POST",
                success: function (data) {
                    if (data.result == 1) {
                        location.reload(true);
                    }
                }
            })
        })
    }

    function del_photo(photoid, paid) {
        $("#del-photo").show();
        $("#del-photo .close").click(function () {
            $("#del-photo").hide();
        });
        $("#false").click(function () {
            $("#del-photo").hide();
        });

        $("#true").click(function () {
            $.ajax({
                url: "deletephoto",
                data: {
                    "photoid": photoid,
                    "paid": paid
                },
                dataType: "json",
                Type: "POST",
                success: function (data) {
                    if (data.result == 1) {
                        location.reload(true);
                    }
                }
            })
        })
    }

    function move_photo(photoid) {
        $("#move-photo").show();
        $("#move-photo .close").click(function () {
            $("#move-photo").hide();
        });

        $("#move").click(function () {
            $.ajax({
                url: "movephoto",
                data: {
                    "photoid": photoid,
                    "paid": $("#paid").val()
                },
                dataType: "json",
                Type: "POST",
                success: function (data) {
                    if (data.result == 1) {
                        location.reload(true);
                    }
                }
            })
        })
    }

    function alledit() {
        $("#mdf-photo").show();
        $("#mdf-photo .close").click(function () {
            $("#mdf-photo").hide();
        });
        var id_array = new Array();
        $('input[name="box"]:checked').each(function () {
            id_array.push($(this).val());
        });
        var idstr = id_array.join(',');
        $("#editphoto").click(function () {
            $.ajax({
                url: "allmodify",
                data: {
                    "allphotoid": idstr,
                    "exphoto": $("#exphoto").val(),
                    "photodetail": $("#photodetail").val()
                },
                dataType: "json",
                Type: "POST",
                success: function (data) {
                    if (data.result == 1) {
                        location.reload(true);
                    }
                }
            })
        })
    }

    function alldelete() {
        $("#del-photo").show();
        $("#del-photo .close").click(function () {
            $("#del-photo").hide();
        });
        $("#false").click(function () {
            $("#del-photo").hide();
        });

        var id_array = new Array();
        $('input[name="box"]:checked').each(function () {
            id_array.push($(this).val());
        });
        var idstr = id_array.join(',');
        $("#true").click(function () {
            $.ajax({
                url: "alldelete",
                data: {
                    "paid": $("#paid").val(),
                    "allphotoid": idstr
                },
                dataType: "json",
                Type: "POST",
                success: function (data) {
                    if (data.result == 1) {
                        location.reload(true);
                    }
                }
            })
        })
    }

    function allmove() {
        $("#move-photo").show();
        $("#move-photo .close").click(function () {
            $("#move-photo").hide();
        });
        var id_array = new Array();
        $('input[name="box"]:checked').each(function () {
            id_array.push($(this).val());
        });
        var idstr = id_array.join(',');

        $("#move").click(function () {
            $.ajax({
                url: "allmove",
                data: {
                    "paid": $("#paid").val(),
                    "allphotoid": idstr
                },
                Type: "POST",
                dataType: "json",
                success: function (data) {
                    if (data.result == 1) {
                        location.reload(true);
                    }
                }
            })
        })
    }
</script>
</html>