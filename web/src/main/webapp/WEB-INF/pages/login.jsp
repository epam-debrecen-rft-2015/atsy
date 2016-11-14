<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="atsy" tagdir="/WEB-INF/tags" %>
<%@page contentType="text/html;charset=UTF-8" %>
<spring:eval var="timeZone" expression="@environment.getProperty('time_zone')"/>
<fmt:setTimeZone value="${timeZone}" scope="session"/>

<atsy:page>

    <jsp:body>
        <div id="login" class="container">
            <div class="jumbotron">
                <div class="row">
                    <div class="col-lg-6 col-md-6 col-sm-6 left_column">
                        <img src="resources/img/epam-logo-login.png" class="img-rounded">
                    </div>
                    <div class="col-lg-6 col-md-6 col-sm-6 right_column">
                        <form role="form" method="POST" id="login-form" action="/atsy/login">
                            <c:if test="${param.error ne null}">
                                <div id="globalMessage" class="alert alert-danger" role="alert">
                                    <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="false"></span>
                                    <spring:message code="login.backend.auth.failed"/>
                                </div>
                            </c:if>
                            <div class="form-group <c:if test="${not empty loginErrorKey}">has-error </c:if>"
                                 id="userDiv">
                                <spring:message code="uname.field" var="i18nUname"/>
                                <span id="missingUsername"><spring:message
                                        code="login.frontend.missingUsername"/></span>
                                <input type="text" class="form-control" name="username" id="name" autofocus="autofocus"
                                       placeholder="${i18nUname}">
                            </div>
                            <div class="form-group <c:if test="${not empty loginErrorKey}">has-error </c:if>"
                                 id="passwordDiv">
                                <spring:message code="passwd.field" var="i18nPasswd"/>
                                <span id="missingPassword"><spring:message
                                        code="login.frontend.missingPassword"/></span>
                                <input type="password" class="form-control" name="password" id="password"
                                       placeholder="${i18nPasswd}">
                            </div>
                            <button type="submit" class="btn btn-success" id="loginButton">
                                <spring:message code="login.button"/>
                            </button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </jsp:body>
</atsy:page>
