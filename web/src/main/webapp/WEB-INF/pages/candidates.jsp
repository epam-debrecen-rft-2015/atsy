<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="atsy" tagdir="/WEB-INF/tags" %>
<%@page contentType="text/html;charset=UTF-8" %>
<spring:url value="/secure/candidates" var="candidates"/>


<atsy:secure_page>
    <jsp:attribute name="pageJs">
     <script src="<c:url value="/resources/js/atsy-candidate.js" />"></script>
    </jsp:attribute>
    <jsp:body>
        <div id="welcome">
            <div id="search" class="col-lg-12 col-md-12 col-sm-12" >
                <div class="well">
                    <fieldset >
                        <form id="searchCandidate" class="form-inline" action="candidates" role="form">
                            <div class="form-group">
                                <spring:message code="candidate.name.field" var="i18nName"/>
                                <label><spring:message
                                        code="candidate.search.button"/></label>
                                <input type="text" class="form-control" name="name" id="filter_name" placeholder="${i18nName}">
                            <%--</div>--%>
                            <%--<div class="form-group">--%>
                                <spring:message code="candidate.email.field" var="i18nEmail"/>
                                <input type="text" class="form-control" name="email" id="filter_email" placeholder="${i18nEmail}">
                            <%--</div>--%>
                            <%--<div class="form-group">--%>
                                <spring:message code="candidate.phone.field" var="i18nPhone"/>
                                <input type="text" class="form-control" name="phone" id="filter_phone" placeholder="${i18nPhone}">
                            <%--</div>--%>
                            <%--<div class="form-group">--%>
                                <spring:message code="candidate.position.field" var="i18nPosition"/>
                                <input type="text" class="form-control" name="position" id="filter_postion" placeholder="${i18nPosition}">
                                <button type="submit" class="btn btn-success"><spring:message
                                        code="candidate.search.button"/></button>
                            </div>
                        </form>
                    </fieldset>
                </div>
            </div>
            <div id="candidates_table">
                <div class="col-lg-12 col-md-12 col-sm-12">
                    <table data-toggle="table" id="candidates" data-url="${candidates}" data-height="299" data-sort-name="name">
                        <thead>
                        <tr>
                            <th data-field="name" data-align="left" data-sortable="true"><spring:message
                                    code="welcome.candidates.table.name.title"/></th>
                            <th data-field="email" data-align="left" data-sortable="true">
                                <spring:message code="welcome.candidates.table.email.title"/>
                            </th>
                            <th data-field="phone" data-align="left" data-sortable="true"><spring:message
                                    code="welcome.candidates.table.phone.title"/></th>
                            <th data-field="positions" data-align="left" data-sortable="true"><spring:message
                                    code="welcome.candidates.table.positions.title"/></th>
                            <th data-field="actions" data-align="left">
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