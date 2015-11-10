<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="atsy" tagdir="/WEB-INF/tags" %>
<%@page contentType="text/html;charset=UTF-8" %>
<spring:url value="/secure/candidates" var="candidates"/>

<atsy:secure_page>
    <jsp:body>
        <div id="welcome">
            <div id="candidates_table">
                <div class="col-lg-12 col-md-12 col-sm-12">
                    <table data-toggle="table" id="candidates" data-url="${candidates}" data-height="299">
                        <thead>
                        <tr>
                            <th data-field="name" data-align="left" data-sortable="true"><spring:message
                                    code="welcome.candidates.table.name.title"/></th>
                            <th data-field="email" data-align="left">
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