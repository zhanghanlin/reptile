<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
    <style>
        td {
            border: 1px solid;
        }
    </style>
</head>
<body>
<table style="border: 1px solid">
    <thead>
    <th>任务名称</th>
    <th>任务分组</th>
    <th>任务状态</th>
    <th>Cron表达式</th>
    <th>描述</th>
    <th>操作</th>
    </thead>
    <tbody>
    <c:forEach var="item" items="${list}" varStatus="status">
        <tr>
            <td>${item.name}</td>
            <td>${item.group}</td>
            <td>${item.status}</td>
            <td>${item.cronExpression}</td>
            <td>${item.description}</td>
            <td qname="${item.name}" qgroup="${item.group}">
                <button class="pause">暂停</button>
                <button class="delete">删除</button>
                <button class="resume">恢复</button>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<script src="/js/jquery.min.js"></script>
<script>
    $('td button').click(function () {
        var clz = $(this).attr('class');
        var name = $(this).parent().attr('qname');
        var group = $(this).parent().attr('qgroup');
        ex(clz, name, group);
    });
    function ex(action, name, group) {
        var url = '/quartz/' + action + '/' + name + '/' + group;
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
