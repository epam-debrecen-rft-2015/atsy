<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="atsy" tagdir="/WEB-INF/tags" %>
<%@page contentType="text/html;charset=UTF-8" %>
<spring:url value="/secure/positions/manage" var="position"/>
<spring:url value="/secure/channels/manage" var="channel"/>
<spring:url value="/secure/password/manage" var="password"/>
<atsy:secure_page>

<jsp:attribute name="pageJs">
  <c:url value="/resources/thirdparty/bootstrap/js/bootstrap.min.js" var="urlValue"/><script src="${urlValue}"></script>
  <c:url value="/resources/thirdparty/bootbox/bootbox.js" var="urlValue"/><script src="${urlValue}"></script>
  <c:url value="/resources/thirdparty/jquery/jquery.i18n.properties.js" var="urlValue"/><script src="${urlValue}"></script>
  <c:url value="/resources/js/atsy-i18n-config.js" var="urlValue" /><script src="${urlValue}"></script>
  <c:url value="/resources/js/atsy-settings.js" var="urlValue"/><script src="${urlValue}"></script>
  <c:url value="/resources/js/atsy-deletable-event-config.js" var="urlValue" /><script src="${urlValue}"></script>
  <script type="text/javascript">
      window.messages['settings.positions.error.empty'] = '<spring:message code="settings.positions.error.empty"/>';
      window.messages['settings.channels.error.empty'] = '<spring:message code="settings.channels.error.empty"/>';
      window.messages['settings.password.error.empty'] = '<spring:message code="settings.password.error.empty"/>';
    </script>
</jsp:attribute>

<jsp:body>
    <h1>
      <spring:message code="settings.title"/>
    </h1>
    <hr />

    <div id="settings">
      <ul role="tablist" class="nav nav-tabs">
        <li role="presentation">
           <a href="#positions_content" aria-controls="positions" role="tab" data-toggle="tab" id="positions_link">
            <spring:message code="settings.positions.title"/>
           </a>
        </li>
        <li role="presentation">
           <a href="#channels_content" aria-controls="channels" role="tab" data-toggle="tab" id="channels_link">
            <spring:message code="settings.channels.title"/>
           </a>
        </li>
        <li role="presentation">
           <a href="#password_content" aria-controls="password" role="tab" data-toggle="tab" id="password_link">
            <spring:message code="settings.password.title"/>
           </a>
        </li>
      </ul>

      <div class="tab-content">
        <div role="tabpanel" id="positions_content" class="tab-pane">
          <jsp:include page="manage_positions.jsp"/>
        </div>
        <div role="tabpanel" id="channels_content" class="tab-pane">
          <jsp:include page="manage_channels.jsp"/>
        </div>
        <div role="tabpanel" id="password_content" class="tab-pane">
          <jsp:include page="password_change.jsp"/>
        </div>
      </div>
    </div>

  </jsp:body>
</atsy:secure_page>