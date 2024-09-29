<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>DELETE Test Form</title>
    <script>
        function sendDeleteRequest() {
            var id = document.getElementById('id').value;
            var xhr = new XMLHttpRequest();
            xhr.open('POST', 'deleteTest', true);
            xhr.setRequestHeader('X-HTTP-Method-Override', 'DELETE');
            xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
            xhr.onload = function () {
                if (xhr.status >= 200 && xhr.status < 300) {
                    alert('DELETE请求已接收，删除的ID为：' + id);
                } else {
                    alert('请求失败');
                }
            };
            xhr.send('id=' + encodeURIComponent(id));
            return false; // 阻止表单的默认提交行为
        }
    </script>
</head>
<body>
<h2>发送DELETE请求</h2>
<form onsubmit="return sendDeleteRequest();">
    <label for="id">输入要删除的ID：</label>
    <input type="text" id="id" name="id"><br><br>
    <input type="submit" value="删除">
</form>
</body>
</html>