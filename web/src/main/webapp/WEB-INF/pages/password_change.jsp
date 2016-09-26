<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="atsy" tagdir="/WEB-INF/tags" %>
<%@page contentType="text/html;charset=UTF-8" %>
<div id="password_change">
    <div id="password_section">
        <form role="form" method="POST" id="pw-form" action="./password">
            <div class="globalMessage alert alert-danger" role="alert"
                 style="display: none">
                <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
                <span class="error-message"></span>
            </div>
            <div class="form-group">
                <label for="new-pw"><spring:message code="settings.password.new"/></label>
                <input type="password" class="newPassword form-control" name="newPassword" id="new-pw">
            </div>
            <div class="form-group">
                <label for="new-pw-two"><spring:message code="settings.password.new.two"/></label>
                <input type="password" class="newPasswordConfirm form-control" name="newPasswordConfirm" id="new-pw-two">
            </div>
            <div class="form-group">
                <label for="old-pw"><spring:message code="settings.password.old"/></label>
                <input type="password" class="oldPassword form-control" name="oldPassword" id="old-pw">
            </div>
            <button type="submit" class="btn btn-success">
                <spring:message code="save.button"/>
            </button>
        </form>
    </div>
</div>