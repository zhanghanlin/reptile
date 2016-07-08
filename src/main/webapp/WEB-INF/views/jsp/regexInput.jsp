<%--
  Created by IntelliJ IDEA.
  User: andy
  Date: 16/7/8
  Time: 11:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="/regex/save" id="form">
    <div>Seed : <input name="seed"/><br/></div>
    <div>Regex : <input name="regex"/><br/></div>
    <div>Start : <input name="start" value="5"/><br/></div>
    <div>ThreadPool : <input name="thread" value="50"/><br/></div>
    <input type="hidden" id="data" name="data"/>
    <div>Data : <input class="data"/><a class="add" href="###">+</a><br/></div>
    <div><input id="submit" type="button" value="Submit"/></div>
</form>
<script src="/js/jquery.min.js"></script>
<script>
    $(".add").click(function () {
        $(this).parent().after('<div><input class="data"/><br/></div>');
    });
    $('#submit').click(function(){
        var data = {};
        $.each($('.data'),function(i,o) {
            var key = $(o).val().split(":")[0];
            var val = $(o).val().split(":")[1];
            data[key] = val;
        });
        $('#data').val(JSON.stringify(data));
        $.ajax({
            cache: true,
            type: "POST",
            url:'/regex/save',
            data:$('#form').serialize(),
            async: false,
            error: function() {
                alert("Connection error");
            },
            success: function(data) {
                alert("OK");
            }
        });
    });
</script>
</body>
</html>
