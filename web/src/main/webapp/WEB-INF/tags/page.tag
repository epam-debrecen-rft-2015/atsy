<%@tag description="Overall Page template" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@attribute name="header" fragment="true" %>
<%@attribute name="footer" fragment="true" %>
<%@attribute name="refs" fragment="true" %>
<html>
<head>
    <link rel="stylesheet" href="<c:url value="/resources/thirdparty/bootstrap/css/bootstrap.min.css" />">
    <link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/bootstrap-table/1.9.1/bootstrap-table.min.css">
    <link rel="stylesheet" href="<c:url value="/resources/css/atsy.css" />">

</head>
<body>
<div class="container">

    <div id="pageheader">
        <jsp:invoke fragment="header"/>
    </div>
    <div id="body" class="container-fluid">
        <jsp:doBody/>
    </div>
    <div id="pagefooter">
        <jsp:invoke fragment="footer"/>
    </div>
</div>
<script src="<c:url value="/resources/thirdparty/jquery/jquery-1.11.3.min.js" />"></script>
<script src="<c:url value="/resources/thirdparty/bootstrap/js/bootstrap.min.js" />"></script>
<script src="<c:url value="/resources/js/atsy.js" />"></script>
<!-- Latest compiled and minified JavaScript -->
<script src="<c:url value="/resources/thirdparty/bootstrap-table/bootstrap-table.min.js" />"></script>
<script src="<c:url value="/resources/thirdparty/bootstrap-table/locale/bootstrap-table-${pageContext.response.locale}.min.js" />"></script>
<script src="<c:url value="/resources/thirdparty/knockout-3.4.0/knockout-3.4.0.js" />"></script>
<!-- Latest compiled and minified Locales -->
<script src="<c:url value="/resources/js/atsy-secure.js" />"></script>

<jsp:invoke fragment="refs"/>
</body>
</html>