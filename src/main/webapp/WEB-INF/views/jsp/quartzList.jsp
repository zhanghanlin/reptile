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
            <h1 class="page-header">定时任务列表</h1>
            <a class="btn btn-default btn-xs"
               role="button" href="/quartz/input/0">新增定时任务</a>
            <div class="table-responsive">
                <table class="table table-striped">
                    <thead>
                    <tr>
                        <th>名称</th>
                        <th>组</th>
                        <th>状态</th>
                        <th>CRON</th>
                        <th>描述</th>
                        <th>#</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="item" items="${list}" varStatus="status">
                        <tr id="${item.id}">
                            <td>${item.name}</td>
                            <td>${item.taskGroup}</td>
                            <td>${item.status}</td>
                            <td>${item.cronExpression}</td>
                            <td>${item.description}</td>
                            <td>
                                <button class="btn btn-default btn-xs" action="run">执行</button>
                                <button class="btn btn-default btn-xs" action="pause">暂停</button>
                                <button class="btn btn-default btn-xs" action="resume">恢复</button>
                                <button class="btn btn-default btn-xs" action="delete">删除</button>
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
    $('td button').click(function () {
        var action = $(this).attr('action');
        var id = $(this).parents('tr').attr('id');
        ex(action, id);
    });
    function ex(action, id) {
        var url = '/quartz/' + action + '/' + id;
        $.ajax({
            url: url,
            cache: true,
            type: "POST",
            async: false,
            error: function () {
                alert("Connection error");
            },
            success: function () {
                alert("OK");
            }
        })
    }
</script>
</body>
</html>