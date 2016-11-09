<%@tag description="Overall Page template" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@attribute name="header" fragment="true" %>
<%@attribute name="footer" fragment="true" %>
<%@attribute name="refs" fragment="true" %>
<html lang="${pageContext.response.locale}">
<head>
    <c:url value="/resources/thirdparty/bootstrap/css/bootstrap.min.css" var="urlValue" /> <link rel="stylesheet" href="${urlValue}">
    <link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/bootstrap-table/1.9.1/bootstrap-table.min.css">
    <c:url value="/resources/css/atsy.css" var="urValue" /><link rel="stylesheet" href="${urValue}">
    <title>Applicant Tracking System</title>
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
<c:url value="/resources/thirdparty/jquery/jquery-1.11.3.min.js" var="urlValue" /><script src="${urlValue}"></script>
<c:url value="/resources/thirdparty/bootstrap/js/bootstrap.min.js" var="urlValue"/><script src="${urlValue}"></script>
<c:url value="/resources/js/atsy.js" var="urlValue"/><script src="${urlValue}"></script>
<!-- Latest compiled and minified JavaScript -->
<c:url value="/resources/thirdparty/bootstrap-table/bootstrap-table.js" var="urlValue"/><script src="${urlValue}"></script>
<c:url value="/resources/thirdparty/bootstrap-table/locale/bootstrap-table-${pageContext.response.locale}.js" var="urlValue"/><script src="${urlValue}"></script>
<c:url value="/resources/thirdparty/knockout-3.4.0/knockout-3.4.0.js" var="urlValue"/><script src="${urlValue}"></script>
<!-- Latest compiled and minified Locales -->

<jsp:invoke fragment="refs"/>
</body>
</html>