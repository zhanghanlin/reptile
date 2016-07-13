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
            <button type="button" id="myButton" data-loading-text="Loading..." class="btn btn-primary"
                    autocomplete="off">
                获取58登陆发布数据
            </button>
            <div class="table-responsive">
                <table class="table table-striped">
                    <thead>
                    <tr>
                        <th>#</th>
                    </tr>
                    </thead>
                    <tbody></tbody>
                </table>
            </div>
        </div>
    </div>
</div>
<script src="/js/jquery/jquery.min.js"></script>
<script src="/js/bootstrap/bootstrap.min.js"></script>
<script src="/js/ie/ie10-viewport-bug-workaround.js"></script>
<script>
    $('#myButton').click(function () {
        var _this = $(this);
        if (_this.is(':disabled')) {
            return;
        }
        _this.button('loading');
        $.ajax({
            type: "GET",
            url: '/fetcher/car58',
            dataType: 'JSON',
            error: function () {
                alert("Connection error");
            },
            success: function (data) {
                var html = '';
                if (data) {
                    $.each(data, function (i, o) {
                        html += '<tr><td>' + o + '<td></tr>';
                    });
                    $('table tbody').html(html);
                }
                _this.button('reset');
            }
        });
    });
</script>
</body>
</html>