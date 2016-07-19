<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="atsy" tagdir="/WEB-INF/tags" %>
<%@page contentType="text/html;charset=UTF-8" %>

<atsy:secure_page>
    <jsp:body>
        <div id="error" class="col-lg-12 col-md-12 col-sm-12" >
            <div class="well">
              <h1><spring:message code="technical.error.message"/>
            </div>
        </div>
    </jsp:body>
</atsy:secure_page>
