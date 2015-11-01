<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="atsy" tagdir="/WEB-INF/tags" %>
<%@page contentType="text/html;charset=UTF-8"%>

<atsy:secure_page>
<jsp:body>
<h1><spring:message code="settings.title"/></h1>
<div id="positions_section">
<h2><spring:message code="settings.positions.title"/></h2>
 <div class="jumbotron">
            <div class="row">
                <div class="col-lg-6 col-md-6 col-sm-6 left_column">
                    <div id="positions" class="list-group">
                    <c:forEach items="${positions}" var="position" >
                      <a href="#" class="list-group-item" id="position_${position.positionId}">${position.name}</a>
                      </c:forEach>
                    </div>
                </div>
                <div class="col-lg-6 col-md-6 col-sm-6 right_column">
                    <form role="form" method="POST" id="login-form">
</form>
</div>
</div>
</div>
<div>
</jsp:body>
</atsy:secure_page>