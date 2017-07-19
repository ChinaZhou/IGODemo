<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/6/19
  Time: 9:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${hint}</title>
    <%@include file="/views/common/common.jsp"%>
    <script>
        function showInfo() {
            var url = "/user/showAjaxInfo";
            var fd = "name=zhouc";
            $.post(url, fd, function(data) {
                alert(data.code+" "+data.result);
            });
        }

    </script>
</head>
<body>

${hint}
<br/>
<input type="button" onclick="showInfo()" value="SHOW INFO" />
</body>
</html>
