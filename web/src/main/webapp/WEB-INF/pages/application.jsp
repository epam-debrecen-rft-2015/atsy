<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="atsy" tagdir="/WEB-INF/tags" %>
<%@page contentType="text/html;charset=UTF-8" %>
<spring:url value="/secure/application" var="application"/>
<spring:url value="/secure/new_application" var="new_application"/>

<atsy:secure_page>
    <jsp:attribute name="pageJs">
       <c:url value="/resources/js/atsy-application.js" var="urlValue" /> <script src="${urlValue}"></script>
       <c:url value="/resources/js/atsy-new-application.js" var="urlValue" /> <script type="text/javascript" src="${urlValue}"></script>
    </jsp:attribute>
    <jsp:body>
        <div id="application_page">
            <h1 class="page-header"><spring:message code="application.create.title"/></h1>

            <div class="alert alert-danger ${empty candidateIdErrorMessage ? 'hidden' : ''}" id="candidate_error">
                ${candidateIdErrorMessage}
            </div>

            <form class="form" role="form" method="POST" id="application-create-form" action="${new_application}">
                <input type="hidden" name="candidateId" id="candidateId" value="${candidateId}">
                <div class="form-group row ${not empty positionErrorMessage ? 'has-error' : ''}" id="positionDiv">
                    <spring:message code="application.popup.position.drop.down.default.value" var="i18n_position_drop_down_default_value"/>

                    <label id="positionLabel" class="control-label text-left" for="drop">
                        <spring:message code="application.create.position.label"/>
                        <span class="input">*</span>
                    </label>

                    <div class="selectContainer" id="drop">
                        <select class="input form-control" name="position.id" id="position">
                        <option value = "${i18n_position_drop_down_default_value}">${i18n_position_drop_down_default_value}</option>
                        </select>
                        <span class = "help-block with-errors" id="position_error">
                            ${positionErrorMessage}
                        </span>
                    </div>
                </div>

                <div class="form-group row ${not empty channelErrorMessage ? 'has-error' : ''}" id="sourceDiv">
                    <spring:message code="application.popup.application.source.drop.down.default.value" var="i18n_application_source_drop_down_default_value"/>

                    <label id="sourceLabel" class="control-label text-left" for="source">
                      <spring:message code="application.create.source.label"/>
                      <span class="input">*</span>
                    </label>

                    <div class="selectContainer" id="dropSource">
                        <select class="input form-control" name="channel.id" id="channel">
                            <option value = "${i18n_application_source_drop_down_default_value}">${i18n_application_source_drop_down_default_value}</option>
                        </select>
                        <span class = "help-block with-errors" id="application_source_error">
                            ${channelErrorMessage}
                        </span>
                    </div>
                </div>

                <div class="form-group row" id="descriptionDiv">
                    <spring:message code="candidate.description.field" var="i18ndescription"/>

                    <label class="control-label text-left" for="description">
                      <spring:message code="candidate.description.label"/>
                    </label>

                    <div>
                        <textarea rows="4" cols="4" class="input form-control" id="description" name="description"
                            placeholder="${i18ndescription}"><c:out value = "${candidate.description}"/></textarea>
                    </div>
                </div>

                <div class="form-group row">
                    <a href="candidate/details/${candidateId}" class="btn btn-danger" id="cancel_button"><spring:message code="cancel.button"/></a>
                    <button type="submit" class="btn btn-success" id="save_new_apply_button" >
                        <spring:message code="save.button"/>
                    </button>
                </div>
            </form>
            </div>
    </jsp:body>
</atsy:secure_page>