<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>POST Test Form</title>
</head>
<body>
<h2>发送POST请求</h2>
<form action="postTest" method="post">
    <label for="param">输入参数：</label>
    <input type="text" id="param" name="param"><br><br>
    <input type="submit" value="提交">
</form>
</body>
</html>