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
            <form class="form-horizontal" action="/quartz/save" method="post">
                <div class="form-group">
                    <label for="name" class="col-sm-2 control-label">名称</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" name="name" id="name" placeholder="name">
                    </div>
                </div>
                <div class="form-group">
                    <label for="group" class="col-sm-2 control-label">组</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" name="group" id="group" placeholder="group">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">是否启动任务</label>
                    <div class="col-sm-10">
                        <select class="form-control" name="status">
                            <option value="0">不启动</option>
                            <option value="1">启动</option>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">执行类</label>
                    <div class="col-sm-10">
                        <select class="form-control" name="beanClass">
                            <option value="com.demo.java.common.quartz.FetcherQuartz">
                                com.demo.java.common.quartz.FetcherQuartz
                            </option>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">执行方法</label>
                    <div class="col-sm-10">
                        <select class="form-control" name="methodName">
                            <option value="fetcher">
                                fetcher(java.lang.String)
                            </option>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <label for="methodParam" class="col-sm-2 control-label">方法参数</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" value="${regexId}" name="methodParam" id="methodParam"
                               placeholder="methodParam">
                    </div>
                </div>
                <div class="form-group">
                    <label for="description" class="col-sm-2 control-label">描述</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" name="description" id="description"
                               placeholder="description">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">cron表达式</label>
                    <input type="hidden" id="cronExpression" name="cronExpression"/>
                    <div class="col-sm-10">
                        <div class="col-md-2">
                            <input type="text" class="form-control cron" value="*"/>
                        </div>
                        <div class="col-md-2">
                            <input type="text" class="form-control cron" value="*"/>
                        </div>
                        <div class="col-md-2">
                            <input type="text" class="form-control cron" value="*"/>
                        </div>
                        <div class="col-md-2">
                            <input type="text" class="form-control cron" value="*"/>
                        </div>
                        <div class="col-md-2">
                            <input type="text" class="form-control cron" value="*"/>
                        </div>
                        <div class="col-md-2">
                            <input type="text" class="form-control cron" value="?"/>
                        </div>
                    </div>
                </div>
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
    $(".form-horizontal").submit(function () {
        $('#cronExpression').val($('input.cron').map(function () {
            return $(this).val();
        }).get().join(" "));
    });
</script>
</body>
</html>