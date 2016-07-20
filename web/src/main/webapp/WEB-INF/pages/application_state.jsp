<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="atsy" tagdir="/WEB-INF/tags" %>
<%@page contentType="text/html;charset=UTF-8" %>
<spring:url value="/secure/application_state" var="application_state"/>
<atsy:secure_page>
  <jsp:body>
      <div class="page-header">
          <h1><spring:message code="application.state.title"/>
              <small id="positionName">${states[0].position.name}</small>
          </h1>
      </div>
      <div id="stateList">
      <c:forEach var="data" items="${states}">
          <div class="page-header">
              <h4>${data.stateType}</h4>
          </div>
          <form class="form-horizontal">
              <div class="form-group">
                  <label for="name" class="control-label col-sm-4"><spring:message code="statehistory.field.date"/></label>
                  <div class="col-sm-8">
                      <p class="form-control-static">${data.creationDate}</p>
                  </div>
              </div>
              <div class="form-group">
                  <label for="name" class="control-label col-sm-4"><spring:message code="statehistory.field.description"/></label>
                  <div class="col-sm-8">
                      <p class="form-control-static">${data.description}</p>
                  </div>
              </div>
              <spring:message code="candidate.table.state.cv" var="cv" />
              <spring:message code="candidate.table.state.hr" var="hr" />
              <spring:message code="candidate.table.state.firstTest" var="firstTest" />
              <spring:message code="candidate.table.state.wageOffer" var="wageOffer" />
              <spring:message code="candidate.table.state.candidateReject" var="candidateReject" />
              <spring:message code="candidate.table.state.agree" var="agree" />
              <c:choose>
                  <c:when test="${data.stateType == cv}">
                      <div class="form-group">
                          <label for="name" class="control-label col-sm-4"><spring:message code="statehistory.field.cv"/></label>
                          <div class="col-sm-8">
                              <p class="form-control-static glyphicon glyphicon-paperclip"></p>
                          </div>
                      </div>
                  </c:when>
                  <c:when test="${data.stateType == hr}">
                      <div class="form-group">
                          <label for="name" class="control-label col-sm-4"><spring:message code="statehistory.field.languageSkill"/></label>
                          <div class="col-sm-8">
                              <p class="form-control-static">${data.languageSkill}</p>
                          </div>
                      </div>
                  </c:when>
                  <c:when test="${data.stateType == firstTest}">
                      <div class="form-group">
                          <label for="name" class="control-label col-sm-4"><spring:message code="statehistory.field.result"/></label>
                          <div class="col-sm-8">
                              <p class="form-control-static">${data.result}</p>
                          </div>
                      </div>
                  </c:when>
                  <c:when test="${data.stateType == wageOffer}">
                      <div class="form-group">
                          <label for="name" class="control-label col-sm-4"><spring:message code="statehistory.field.offeredMoney"/></label>
                          <div class="col-sm-8">
                              <p class="form-control-static">${data.offeredMoney}</p>
                          </div>
                      </div>
                      <div class="form-group">
                          <label for="name" class="control-label col-sm-4"><spring:message code="statehistory.field.claim"/></label>
                          <div class="col-sm-8">
                              <p class="form-control-static">${data.claim}</p>
                          </div>
                      </div>
                      <div class="form-group">
                          <label for="name" class="control-label col-sm-4"><spring:message code="statehistory.field.feedbackDate"/></label>
                          <div class="col-sm-8">
                              <p class="form-control-static">${data.feedbackDate}</p>
                          </div>
                      </div>
                  </c:when>
                  <c:when test="${data.stateType == agree}">
                      <div class="form-group">
                          <label for="name" class="control-label col-sm-4"><spring:message code="statehistory.field.dayOfStart"/></label>
                          <div class="col-sm-8">
                              <p class="form-control-static">${data.feedbackDate}</p>
                          </div>
                      </div>
                  </c:when>
              </c:choose>
          </form>
      </c:forEach>
  </jsp:body>
</atsy:secure_page>