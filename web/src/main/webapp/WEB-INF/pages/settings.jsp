<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="atsy" tagdir="/WEB-INF/tags" %>
<%@page contentType="text/html;charset=UTF-8" %>
<spring:url value="/secure/positions/manage" var="position"/>
<spring:url value="/secure/channels/manage" var="channel"/>
<spring:url value="/secure/password/manage" var="password"/>
<atsy:secure_page>
 <jsp:attribute name="pageJs">
     <script src="<c:url value="/resources/js/atsy-settings.js" />"></script>
    </jsp:attribute>
    <jsp:body>
        <div id="settings">
            <h1 class="page-header"><spring:message code="settings.title"/></h1>
            <ul>
              <li><a href="${position}"><spring:message code="settings.positions.title"/></a></li>
              <li><a href="${channel}"><spring:message code="settings.channels.title"/></a></li>
              <li><a href="${password}"><spring:message code="settings.password.title"/></a></li>
            </ul>
        </div>
    </jsp:body>
</atsy:secure_page>