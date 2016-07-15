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
            <h1 class="page-header">抓取任务列表</h1>
            <a class="btn btn-default btn-xs"
               role="button" href="/regex/input/0">新增抓取任务</a>
            <div class="table-responsive">
                <table class="table table-striped">
                    <thead>
                    <tr>
                        <th>名称</th>
                        <th>种子</th>
                        <th>正则</th>
                        <th style="width: 50px;">深度</th>
                        <th style="width: 60px;">线程数</th>
                        <th style="width: 160px;">更新时间</th>
                        <th>#</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="item" items="${list}" varStatus="status">
                        <tr id="${item.id}">
                            <td>${item.name}</td>
                            <td><a href="${item.seed}" target="_blank">查看</a></td>
                            <td>${item.regex}</td>
                            <td>${item.start}</td>
                            <td>${item.thread}</td>
                            <td>${item.updateTime}</td>
                            <td>
                                <a class="btn btn-default btn-xs"
                                   role="button" href="/regex/input/${item.id}">编辑</a>
                                <button class="btn btn-default btn-xs fetcher" action="start">爬取</button>
                                <a class="btn btn-default btn-xs"
                                   role="button" href="/regex/delete/${item.id}">删除</a>
                                <a class="btn btn-default btn-xs"
                                   role="button" href="/quartz/input?regexId=${item.id}">部署定时</a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
<script src="/js/jquery/jquery.min.js"></script>
<script src="/js/bootstrap/bootstrap.min.js"></script>
<script src="/js/ie/ie10-viewport-bug-workaround.js"></script>
<script>
    $('.fetcher').click(function () {
        var id = $(this).parents('tr').attr('id');
        var action = $(this).attr('action');
        $.ajax({
            url: '/fetcher/' + action + '/' + id,
            type: 'GET',
            dataType: 'text',
            success: function (msg) {
                alert(msg);
            }
        })
    });
</script>
</body>
</html>