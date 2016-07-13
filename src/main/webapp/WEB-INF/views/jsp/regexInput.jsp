<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="/images/favicon.ico">
    <title>Dashboard Template for Bootstrap</title>
    <!-- Bootstrap core CSS -->
    <link href="/css/bootstrap.min.css" rel="stylesheet">
    <!-- Custom styles for this template -->
    <link href="/css/dashboard.css" rel="stylesheet">
    <!-- Just for debugging purposes. Don't actually copy these 2 lines! -->
    <!--[if lt IE 9]>
    <script src="/js/ie/ie8-responsive-file-warning.js"></script><![endif]-->
    <script src="/js/ie/ie-emulation-modes-warning.js"></script>
    <!--[if lt IE 9]>
    <script src="/js/html5shiv/html5shiv.min.js"></script>
    <script src="/js/respond/respond.min.js"></script>
    <![endif]-->
</head>
<body>
<jsp:directive.include file="common/nav.html"/>
<div class="container-fluid">
    <div class="row">
        <jsp:directive.include file="common/left.html"/>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <h1 class="page-header">Dashboard</h1>
            <form class="form-horizontal" action="/regex/save" method="post">
                <div class="form-group">
                    <label for="seed" class="col-sm-2 control-label">种子地址</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" value="${regex.seed}" name="seed" id="seed"
                               placeholder="seed">
                    </div>
                </div>
                <div class="form-group">
                    <label for="regex" class="col-sm-2 control-label">地址正则</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" value="${regex.regex}" name="regex" id="regex"
                               placeholder="regex">
                    </div>
                </div>
                <div class="form-group">
                    <label for="start" class="col-sm-2 control-label">爬取深度</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" value="${regex.start}" style="width: 200px"
                               name="start" id="start" placeholder="start">
                    </div>
                </div>
                <div class="form-group">
                    <label for="thread" class="col-sm-2 control-label">线程数量</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" value="${regex.thread}" style="width: 200px"
                               name="thread" id="thread" placeholder="thread">
                    </div>
                </div>
                <input type="hidden" name="data" id="data"/>
                <c:forEach var="item" items="${data.entrySet()}" varStatus="status">
                    <div class="form-group col-data">
                        <label for="${item.key}" class="col-sm-2 control-label">${item.key}</label>
                        <div class="col-sm-10">
                            <div class="col-md-3">
                                <input type="text" class="form-control col_select" id="${item.key}"
                                      value="${item.value.get('select')}" placeholder="${item.key}">
                            </div>
                            <div class="col-md-3">
                                <c:set var="type" value="${item.value.get('type')}"/>
                                <select class="form-control col_type">
                                    <option value="0" <c:if test="${type == '0'}">selected</c:if>>无需处理</option>
                                    <option value="1" <c:if test="${type == '1'}">selected</c:if>>分割处理</option>
                                </select>
                            </div>
                            <div class="col-md-3">
                                <input type="text" value="${item.value.get('split')}" class="form-control col_split"
                                       placeholder="分隔符"/>
                            </div>
                            <div class="col-md-3">
                                <input type="text" value="${item.value.get('index')}" class="form-control col_index"
                                       placeholder="分割后取第几位"/>
                            </div>
                        </div>
                    </div>
                </c:forEach>
                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <button class="btn btn-default" id="submit">保存</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
<script src="/js/jquery/jquery.min.js"></script>
<script src="/js/bootstrap/bootstrap.min.js"></script>
<script src="/js/ie/ie10-viewport-bug-workaround.js"></script>
<script>
    $('#submit').click(function () {
        var data = {};
        $.each($('.col-data'), function (i, o) {
            var key = $(o).find('label').text();
            var type = $(o).find('.col_type').val();
            var split = $(o).find('.col_split').val();
            var index = $(o).find('.col_index').val();
            var select = $(o).find('.col_select').val();
            var ele = {};
            ele["select"] = select;
            ele["type"] = type;
            ele["split"] = split;
            ele["index"] = index;
            data[key] = ele;
        });
        $('#data').val(JSON.stringify(data));
        $.ajax({
            cache: true,
            type: "POST",
            url: '/regex/save',
            data: $('.form-horizontal').serialize(),
            async: false,
            error: function () {
                alert("Connection error");
            },
            success: function () {
                alert("OK");
            }
        });
    });
</script>
</body>
</html>