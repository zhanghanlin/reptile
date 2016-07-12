<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="/regex/save" id="form">
    <div>种子地址 : <input name="seed" style="width: 250px"/><br/></div>
    <div>URL正则: <input name="regex" style="width: 250px"/><br/></div>
    <div>爬取深度 : <input name="start" style="width: 40px" value="5"/><br/></div>
    <div>线程数量 : <input name="thread" style="width: 40px" value="50"/><br/></div>
    ----------------------------------------------
    <input type="hidden" id="data" name="data"/>
    <div class="col_data">数据列 :
        <a class="add" href="###">+</a>
        <div>
            <input class="col_name"/>
            <input class="col_select"/>
            <select class="col_type">
                <option value="1">无需处理</option>
                <option value="2">分割处理</option>
            </select>
            <input class="col_split"/>
            <input class="col_index"/>
        </div>
    </div>
    <div><input id="submit" type="button" value="Submit"/></div>
</form>
<script src="/js/jquery.min.js"></script>
<script>
    $(".add").click(function () {
        $(".col_data div:first").clone(true).appendTo(".col_data");
    });
    $('#submit').click(function () {
        var data = {};
        $.each($('.col_data div'), function (i, o) {
            var key = $(o).find('.col_name').val();
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
