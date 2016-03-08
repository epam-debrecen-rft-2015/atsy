<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="atsy" tagdir="/WEB-INF/tags" %>
<%@page contentType="text/html;charset=UTF-8" %>
<atsy:secure_page>
    <jsp:body>
        <div id="settings">
            <h1 class="page-header"><spring:message code="settings.title"/></h1>
            <div id="password_section">
                <h3><spring:message code="settings.password.title"/></h3>
                <form role="form" method="POST" id="pw-form">
                    <div class="form-group" id="new-pw-group">
                        <input type="hidden" class="form-control" name="changeId" id="changeId">
                    </div>
                    <div class="form-group">
                        <label for="new-pw"><spring:message code="settings.password.new"/></label>
                        <input type="password" class="form-control" name="new-pw" id="new-pw">
                    </div>
                    <div class="form-group">
                        <label for="old-pw-one"><spring:message code="settings.password.old.one"/></label>
                        <input type="password" class="form-control" name="old-pw-one" id="old-pw-one">
                    </div>
                    <div class="form-group">
                        <label for="old-pw-two"><spring:message code="settings.password.old.two"/></label>
                        <input type="password" class="form-control" name="old-pw-two" id="old-pw-two">
                    </div>
                    <button type="submit" class="btn btn-success">
                        <spring:message code="save.button"/>
                    </button>
                </form>
            </div>
        </div>
    </jsp:body>
</atsy:secure_page>