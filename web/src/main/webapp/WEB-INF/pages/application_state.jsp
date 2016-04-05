<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="atsy" tagdir="/WEB-INF/tags" %>
<%@page contentType="text/html;charset=UTF-8" %>
<spring:url value="/secure/application_state" var="application_state"/>
<atsy:secure_page>
    <jsp:body>
        <div id="settings">
            <h1 class="page-header"><spring:message code="settings.title"/></h1>
            <div id="state_table">
                                <div>
                                    <table class="table table-hover" id="states_table"  data-toggle="table" data-url="../secure/applications_states/${stateId}" data-height="500"
                                           data-sort-name="name">
                                        <thead>
                                        <tr>
                                            <th data-field="position.name" data-align="left"><spring:message
                                                    code="candidate.table.application.position"/></th>
                                        </tr>
                                        </thead>
                                    </table>
                                </div>
                            </div>
        </div>
    </jsp:body>
</atsy:secure_page>