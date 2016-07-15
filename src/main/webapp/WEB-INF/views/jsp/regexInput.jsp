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
                <input type="hidden" name="id" value="${regex.id}"/>
                <div class="form-group">
                    <label for="name" class="col-sm-2 control-label">名称</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" value="${regex.name}" name="name" id="name"
                               placeholder="name">
                    </div>
                </div>
                <div class="form-group">
                    <label for="taskKey" class="col-sm-2 control-label">任务标识(需唯一)</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" value="${regex.taskKey}" name="taskKey" id="taskKey"
                               placeholder="taskKey">
                    </div>
                </div>
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
                <div class="form-group">
                    <label for="ignoreKey" class="col-sm-2 control-label">存在元素则忽略</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" value="${regex.ignoreKey}" style="width: 200px"
                               name="ignoreKey" id="ignoreKey" placeholder="ignoreKey">
                    </div>
                </div>
                <input type="hidden" name="data" id="data"/>
                <div class="form-group">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <h3 class="panel-title">
                                字段CSS选择器设置
                            </h3>
                        </div>
                        <div class="panel-body">
                            <div class="form-group">
                                <div class="col-md-2" style="text-align: right;">
                                    <div class="col-md-12">字段名</div>
                                </div>
                                <div class="col-sm-10">
                                    <div class="col-md-4">CSS选择器</div>
                                    <div class="col-md-2">获取数据类型</div>
                                    <div class="col-md-2">是否处理</div>
                                    <div class="col-md-3">处理值</div>
                                </div>
                            </div>
                            <c:forEach var="item" items="${data.entrySet()}" varStatus="status">
                                <div class="form-group col-data">
                                    <label for="${item.key}"
                                           class="col-sm-2 control-label">${item.value.get('name')}</label>
                                    <div class="col-sm-10">
                                        <div class="col-md-4">
                                            <input type="text" class="form-control col_dom" id="${item.key}"
                                                   value="${item.value.get('dom')}"
                                                   placeholder="${item.value.get('name')}">
                                        </div>
                                        <div class="col-md-2">
                                            <select class="form-control col_type" select="${item.value.get('type')}">
                                                <option value="String">字符串</option>
                                                <option value="Integer">数字</option>
                                            </select>
                                        </div>
                                        <div class="col-md-2">
                                            <select class="form-control col_handle"
                                                    select="${item.value.get('handle')}">
                                                <option value="none">不处理</option>
                                                <option value="replace">替换</option>
                                            </select>
                                        </div>
                                        <div class="col-md-3">
                                            <input type="text" class="form-control col_handle_p"
                                                   value="${item.value.get('handle_p')}"
                                                   placeholder="${item.value.get('handle_p')}">
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
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
        var data = {};
        $.each($('.col-data'), function (i, o) {
            var key = $(o).find('label').attr('for');
            var handle = $(o).find('.col_handle').val();
            var handle_p = $(o).find('.col_handle_p').val();
            var dom = $(o).find('.col_dom').val();
            var type = $(o).find('.col_type').val();
            var ele = {};
            ele["dom"] = dom;
            ele["handle"] = handle;
            ele["handle_p"] = handle_p;
            ele["type"] = type;
            data[key] = ele;
        });
        $('#data').val(JSON.stringify(data));
        return true;
    });
    $.each($('.panel-body select'), function (i, o) {
        var val = $(o).attr('select');
        $(o).find('option[value="' + val + '"]').prop('selected', true)
    });
</script>
</body>
</html>