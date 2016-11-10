<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="atsy" tagdir="/WEB-INF/tags" %>
<%@page contentType="text/html;charset=UTF-8" %>
<spring:url value="/secure/candidate" var="candidate"/>
<spring:url value="/secure/candidate/details" var="candidateCreate"/>

<atsy:secure_page>
    <jsp:attribute name="pageJs">
        <c:url value="/resources/js/atsy-candidate.js" var="urlValue"/><script src="${urlValue}"></script>
        <c:url value="/resources/js/atsy-deletable-event-config.js" var="urlValue" /><script src="${urlValue}"></script>
        <c:url value="/resources/thirdparty/bootstrap/js/bootstrap.min.js" var="urlValue"/><script src="${urlValue}"></script>
        <c:url value="/resources/thirdparty/bootbox/bootbox.js" var="urlValue"/><script src="${urlValue}"></script>
        <c:url value="/resources/thirdparty/jquery/jquery.i18n.properties.js" var="urlValue"/><script src="${urlValue}"></script>
        <c:url value="/resources/js/atsy-i18n-config.js" var="urlValue" /><script src="${urlValue}"></script>
    </jsp:attribute>
    <jsp:body>
        <div id="welcome">
            <div id="search" class="col-lg-12 col-md-12 col-sm-12" >
                <div class="well">
                    <fieldset >
                        <form id="searchCandidate" class="form-inline" action="candidate" method="GET" role="form">
                            <div class="form-group">
                                <spring:message code="candidate.name.field" var="i18nName"/>
                                <label><spring:message
                                        code="candidate.search.button"/></label>
                                <input type="text" class="form-control" name="name" id="filter_name" placeholder="${i18nName}">
                                <spring:message code="candidate.email.field" var="i18nEmail"/>
                                <input type="text" class="form-control" name="email" id="filter_email" placeholder="${i18nEmail}">
                                <spring:message code="candidate.phone.field" var="i18nPhone"/>
                                <input type="text" class="form-control" name="phone" id="filter_phone" placeholder="${i18nPhone}">
                                <spring:message code="candidate.position.field" var="i18nPosition"/>
                                <input type="text" class="form-control" name="position" id="filter_postion" placeholder="${i18nPosition}">
                                <button id=searchButton type="submit" class="btn btn-primary"><spring:message
                                        code="candidate.search.button"/></button>
                            </div>
                        </form>
                    </fieldset>
                </div>
            </div>
            <div id="new_candidate" class="text-right">
                <a class="btn btn-success" href="${candidateCreate}" id="add_candidate_button"><spring:message code="welcome.candidates.add.button"/></a>
            </div>
            <div id="candidates_table">

                <div class="globalMessage alert alert-danger" id="errorMessageForDeleting" role="alert" style="display: none">
                    <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
                    <span class="error-message"></span>
                </div>

                <div>
                    <table class="table table-striped table-hover cursor-pointer" id="candidates"
                    data-url="${candidate}" data-height="500" data-sort-name="name" data-escape="true"
                    data-pagination="true" data-pagination-loop="false" data-side-pagination="server" data-query-params-type="" >
                        <thead>
                        <tr>
                            <th data-field="name" data-align="left" data-sortable="true" data-formatter="escapeHTMLFormatter">
                                <spring:message code="welcome.candidates.table.name.title"/>
                            </th>
                            <th data-field="email" data-align="left" data-sortable="true">
                                <spring:message code="welcome.candidates.table.email.title"/>
                            </th>
                            <th data-field="phone" data-align="left" data-sortable="true">
                                <spring:message code="welcome.candidates.table.phone.title"/></th>
                            <th data-field="positions" data-align="left" data-sortable="true" data-formatter="escapeHTMLFormatter">
                                <spring:message code="welcome.candidates.table.positions.title"/>
                            </th>
                            <th data-field="operations" data-align="left" data-sortable="false" data-formatter="actionsFormatter" data-events="candidatesEvents">
                                <spring:message code="welcome.candidates.table.actions.title"/>
                            </th>
                        </tr>
                        </thead>
                    </table>
                </div>
            </div>
        </div>
    </jsp:body>
</atsy:secure_page>
