<%@tag description="Overall Page template" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@attribute name="header" fragment="true" %>
<%@attribute name="footer" fragment="true" %>
<html>
<head>
    <link href="<c:url value="/resources/thirdparty/bootstrap/css/bootstrap.min.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/css/atsy.css" />" rel="stylesheet">
    <script src="<c:url value="/resources/thirdparty/jquery/jquery-1.11.3.min.js" />"></script>
    <script src="<c:url value="/resources/thirdparty/bootstrap/js/bootstrap.min.js" />"></script>
    <script src="<c:url value="/resources/js/atsy.js" />"></script>
</head>
  <body>
    <div id="pageheader">
      <jsp:invoke fragment="header"/>
    </div>
    <div id="body">
      <jsp:doBody/>
    </div>
    <div id="pagefooter">
      <jsp:invoke fragment="footer"/>
    </div>
  </body>
</html>