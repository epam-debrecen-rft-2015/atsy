<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="atsy" tagdir="/WEB-INF/tags" %>
<%@page contentType="text/html;charset=UTF-8" %>
<spring:url value="/secure/application" var="application"/>

<atsy:secure_page>
  <jsp:body>
    <div id="application_page">
      <h1 class="page-header">
        <spring:message code="candidate.create.title"/>
      </h1>


    </div>
  </jsp:body>
</atsy:secure_page>