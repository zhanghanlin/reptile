<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="/quartz/add" id="form">
    <div>任务名称: <input name="name"/></div>
    <div>任务分组: <input name="group"/></div>
    <div>任务状态(是否启动任务):
        <select name="status">
            <option value="0">不启动</option>
            <option value="1">启动</option>
        </select>
    </div>
    <div class="cronExpression">cron表达式 :
        <input name="cronExpression" id="cronExpression" type="hidden"/>
        s<input style="width:40px" value="*"/>
        m<input style="width:40px" value="*"/>
        h<input style="width:40px" value="*"/>
        d<input style="width:40px" value="*"/>
        M<input style="width:40px" value="*"/>
        w<input style="width:40px" value="?"/>
    </div>
    <div>执行类 :
        <select name="beanClass">
            <option value="com.demo.java.common.quartz.FetcherQuartz">
                com.demo.java.common.quartz.FetcherQuartz
            </option>
        </select>
    </div>
    <div>执行方法 :
        <select name="methodName">
            <option value="fetcher">
                fetcher(java.lang.String)
            </option>
        </select>
    </div>
    <div>方法参数 : <input name="methodParam" value="${regexId}"/><br/></div>
    <div>描述 : <input name="description"/><br/></div>
    <div><input id="submit" type="button" value="Submit"/></div>
</form>
<script src="/js/jquery.min.js"></script>
<script>
    $('#submit').click(function () {
        $('#cronExpression').val($('.cronExpression input').not(':first').map(function () {
            return $(this).val();
        }).get().join(" "));
        $.ajax({
            cache: true,
            type: "POST",
            url: '/quartz/add',
            data: $('#form').serialize(),
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
