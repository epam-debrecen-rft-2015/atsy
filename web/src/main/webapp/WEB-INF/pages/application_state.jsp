<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="atsy" tagdir="/WEB-INF/tags" %>
<%@page contentType="text/html;charset=UTF-8" %>
<spring:url value="/secure/application_state" var="application_state"/>
<atsy:secure_page>
  <jsp:attribute name="pageJs">
        <script src="<c:url value="/resources/js/atsy-application-state.js" />"></script>
  </jsp:attribute>
  <jsp:body>
      <div class="page-header">
          <h1><spring:message code="application.state.title"/>
              <small id="positionName"></small>
          </h1>
      </div>
      <div id="stateList">
      </div>
      <!--      <div id="state_table">
                <div>
                    <table class="table table-hover" id="states_table"  data-toggle="table" data-url="../secure/applications_states/${applicationId}" data-height="500"
                    data-sort-name="name">
                        <thead>
                            <tr>
                                <th data-field="stateType" data-align="left"><spring:message
                                                code="application.table.state"/></th>
                                <th data-field="creationDate" data-align="left"><spring:message
                                                code="application.table.creationdate"/></th>
                                <th data-field="description" data-align="left"><spring:message
                                                code="application.table.description"/></th>
                            </tr>
                        </thead>
                    </table>
                </div>
            </div>-->
  </jsp:body>
</atsy:secure_page>