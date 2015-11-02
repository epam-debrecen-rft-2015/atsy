<%@tag description="Overall Page template" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@attribute name="header" fragment="true" %>
<%@attribute name="footer" fragment="true" %>
<%@attribute name="refs" fragment="true" %>
<html>
<head>
    <link href="<c:url value="/resources/thirdparty/bootstrap/css/bootstrap.min.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/css/atsy.css" />" rel="stylesheet">
    <script src="<c:url value="/resources/thirdparty/jquery/jquery-1.11.3.min.js" />"></script>
    <script src="<c:url value="/resources/thirdparty/bootstrap/js/bootstrap.min.js" />"></script>
    <script src="<c:url value="/resources/js/atsy.js" />"></script>

    <link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/bootstrap-table/1.9.1/bootstrap-table.min.css">

    <!-- Latest compiled and minified JavaScript -->
    <script src="//cdnjs.cloudflare.com/ajax/libs/bootstrap-table/1.9.1/bootstrap-table.min.js"></script>

    <!-- Latest compiled and minified Locales -->
    <script src="//cdnjs.cloudflare.com/ajax/libs/bootstrap-table/1.9.1/locale/bootstrap-table-zh-CN.min.js"></script>
    <jsp:invoke fragment="refs"/>
</head>
<body>
<div class="container">
    <div id="pageheader">
        <jsp:invoke fragment="header"/>
    </div>
    <div id="body">
        <jsp:doBody/>
    </div>
    <div id="pagefooter">
        <jsp:invoke fragment="footer"/>
    </div>
</div>
</body>
</html>