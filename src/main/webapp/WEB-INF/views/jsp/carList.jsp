<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
    <link rel="stylesheet" href="/css/dataTables.bootstrap.css">
    <style>
        td {
            overflow: hidden;
            white-space: nowrap;
            text-overflow: ellipsis;
        }
    </style>
</head>
<body>
<jsp:directive.include file="common/nav.html"/>
<div class="container-fluid">
    <div class="row">
        <jsp:directive.include file="common/left.html"/>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <h1 class="page-header">二手车数据</h1>
            <div class="table-responsive">
                <table id="search" class="table table-striped" style="table-layout:fixed">
                    <thead>
                    <tr>
                        <th>名称</th>
                        <th style="width: 50px;">价格/万</th>
                        <th style="width: 60px;">上牌时间</th>
                        <th style="width: 70px;">里程/万km</th>
                        <th style="width: 60px;">年检到期</th>
                        <th style="width: 60px;">保险到期</th>
                        <th>事故</th>
                        <th style="width: 50px;">发布人</th>
                        <th>电话</th>
                        <th>地址</th>
                        <th style="width: 30px;">来源</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td colspan="11">Loading data from server</td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
<script src="/js/jquery/jquery.min.js"></script>
<script src="/js/bootstrap/bootstrap.min.js"></script>
<script src="/js/jquery/jquery.dataTables.min.js"></script>
<script src="/js/bootstrap/dataTables.bootstrap.min.js"></script>
<script>
    $('#search').DataTable({
        bPaginate: true,// 分页按钮
        lengthChange: true,  //修改每页数量
        searching: false,
        sort: false,
        info: true,   //显示数据信息 第几页,总共几页等等
        autoWidth: false, //是否固定宽度
        bProcessing: false,    //服务器等等提示
        bServerSide: true, //表示从服务器获取
        lengthMenu: [[10, 15, 25, 50], [10, 15, 25, 50]],    //每页数据管理
        bDestroy: true,
        bSortCellsTop: true,
        bSortClasses: true,
        ajax: {
            url: '/car/api/list'
        },
        columns: [
            {data: 'carName'},
            {data: 'price'},
            {data: 'onTime'},
            {data: 'mileage'},
            {data: 'inspectExpire'},
            {data: 'safeExpire'},
            {data: 'accident'},
            {data: 'userName'},
            {data: 'phone'},
            {data: 'address'},
            {data: 'url'}
        ],
        select: true,
        language: {
            url: '/json/dataTable.oLanguage.json'
        },
        fnRowCallback: function (nRow, aData) {
            $(nRow).attr('id', aData['id']);
            $('td:eq(0)', nRow).attr("title", aData['carName']);
            $('td:eq(8)', nRow).attr("title", aData['phone']);
            $('td:eq(9)', nRow).attr("title", aData['address']);
            $('td:eq(10)', nRow).html('<a href="' + aData['url'] + '" target="_blank">查看</a>')
        }
    });

    function formatDate(timestamp) {
        var now = new Date(timestamp);
        var year = now.getFullYear();
        var month = now.getMonth() + 1;
        var date = now.getDate();
        var hour = now.getHours();
        var minute = now.getMinutes();
        var second = now.getSeconds();
        if (month < 10) {
            month = "0" + month;
        }
        if (second < 10) {
            second = "0" + second;
        }
        return year + "/" + month + "/" + date + " " + hour + ":" + minute + ":" + second;
    }
</script>
</body>
</html>