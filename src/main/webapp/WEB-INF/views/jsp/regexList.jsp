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
    <th>种子URL</th>
    <th>URL正则</th>
    <th>爬取深度</th>
    <th>爬取线程</th>
    <th>字段</th>
    <th>操作</th>
    </thead>
    <tbody>
    <c:forEach var="item" items="${list}" varStatus="status">
        <tr id="${item.id}">
            <td>${item.seed}</td>
            <td>${item.regex}</td>
            <td>${item.start}</td>
            <td>${item.thread}</td>
            <td>
                选择器:${item.getJSONData().getString('select')}<br/>
                列信息:<br/>
                <c:forEach var="col" items="${item.getJSONData().getJSONObject('data').entrySet()}">
                    ${col.key}:<c:out value="${col.value}"/><br/>
                </c:forEach>
            </td>
            <td>
                <button class="fetcher">爬取</button>
                <button class="fetcher_cron"><a href="/quartz/input?regexId=${item.id}">部署定时</a></button>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<script src="/js/jquery.min.js"></script>
<script>
    $('.fetcher').click(function () {
        var id = $(this).parents('tr').attr('id');
        $.ajax({
            url: '/fetcher/' + id,
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
