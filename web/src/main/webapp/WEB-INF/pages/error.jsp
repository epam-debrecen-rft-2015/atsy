<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="atsy" tagdir="/WEB-INF/tags" %>
<%@page contentType="text/html;charset=UTF-8" %>

<atsy:page>
    <jsp:body>
        <div id="error">
            <div class="jumbotron">
                <div class="row text-center">
                    <div>
                        <a href="/atsy/secure/welcome">
                            <img src="/atsy/resources/img/epam-logo-login.png" class="img-rounded">
                        </a>
                    </div>
                    <div class="description">
                        <h3><b><c:out value="${requestScope['javax.servlet.error.status_code']}"/></b></h3>
                        <h4><c:out value="${requestScope['javax.servlet.error.message']}"/></h4>
                    </div>
                    <div>
                        <a href="/atsy/secure/welcome">
                            <h5>
                                <span class="glyphicon glyphicon-chevron-left"></span>
                                    <spring:message code="back.to.the.homepage"/>
                            </h5>
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </jsp:body>
</atsy:page>
