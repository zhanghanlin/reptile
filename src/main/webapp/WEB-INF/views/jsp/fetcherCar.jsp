<%--
  Created by IntelliJ IDEA.
  User: andy
  Date: 16/6/12
  Time: 14:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="/fetcher/save">
    Seed : <input name="seed" value="http://bj.58.com/ershouche"/>
    Regex : <input name="regex" value="http://bj.58.com/ershouche/pn[0-9]/.*"/>
    Start : <input name="start" value="5"/>
    ThreadPool : <input name="thread" value="10"/>
    <input type="submit"/>
</form>
</body>
</html>
