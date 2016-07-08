<%--
  Created by IntelliJ IDEA.
  User: andy
  Date: 16/7/8
  Time: 11:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<c:forEach var="item" items="${list}" varStatus="status">
    <div>${item.seed}##${item.regex}##${item.start}##${item.thread}##${item.data}</div>
</c:forEach>
</body>
</html>
