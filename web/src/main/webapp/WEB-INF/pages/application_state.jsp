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
      <spring:message code="candidate.table.state.newstate" var="newstate" />
      <spring:message code="candidate.table.state.cv" var="cv" />
      <spring:message code="candidate.table.state.hr" var="hr" />
      <spring:message code="candidate.table.state.firstTest" var="firstTest" />
      <spring:message code="candidate.table.state.tech" var="tech" />
      <spring:message code="candidate.table.state.coding" var="coding" />
      <spring:message code="candidate.table.state.profInterview" var="profInterview" />
      <spring:message code="candidate.table.state.clientInterview" var="clientInterview" />
      <spring:message code="candidate.table.state.wageOffer" var="wageOffer" />
      <spring:message code="candidate.table.state.agree" var="agree" />
      <spring:message code="candidate.table.state.accept" var="accept" />
      <spring:message code="candidate.table.state.reject" var="reject" />
      <spring:message code="candidate.table.state.pause" var="pause" />
      <div class="button-panel clearfix">
          <form class="form-inline pull-right">
              <c:if test="${states[0].stateType != reject && states[0].stateType != accept}">
              <div class="form-group">
                  <label class="form-label">Következő lépés:</label>
              </div>
              <div class="form-group">
                  <div class="btn-group" role="group">
                      <c:choose>
                          <c:when test="${states[0].stateType == newstate}">
                              <a href="#" class="btn btn-default"><spring:message code="statehistory.buttons.cv"/></a>
                              <a href="#" class="btn btn-warning"><spring:message code="statehistory.buttons.onHold"/></a>
                              <a href="#" class="btn btn-danger"><spring:message code="statehistory.buttons.reject"/></a>
                          </c:when>
                          <c:when test="${states[0].stateType == cv}">
                              <a href="#" class="btn btn-default"><spring:message code="statehistory.buttons.hr"/></a>
                              <a href="#" class="btn btn-warning"><spring:message code="statehistory.buttons.onHold"/></a>
                              <a href="#" class="btn btn-danger"><spring:message code="statehistory.buttons.reject"/></a>
                          </c:when>
                          <c:when test="${states[0].stateType == hr}">
                              <a href="#" class="btn btn-default"><spring:message code="statehistory.buttons.firstTest"/></a>
                              <a href="#" class="btn btn-warning"><spring:message code="statehistory.buttons.onHold"/></a>
                              <a href="#" class="btn btn-danger"><spring:message code="statehistory.buttons.reject"/></a>
                          </c:when>
                          <c:when test="${states[0].stateType == firstTest}">
                              <a href="#" class="btn btn-default"><spring:message code="statehistory.buttons.tech"/></a>
                              <a href="#" class="btn btn-default"><spring:message code="statehistory.buttons.coding"/></a>
                              <a href="#" class="btn btn-default"><spring:message code="statehistory.buttons.profInterview"/></a>
                              <a href="#" class="btn btn-default"><spring:message code="statehistory.buttons.clientInterview"/></a>
                              <a href="#" class="btn btn-warning"><spring:message code="statehistory.buttons.onHold"/></a>
                              <a href="#" class="btn btn-danger"><spring:message code="statehistory.buttons.reject"/></a>
                          </c:when>
                          <c:when test="${states[0].stateType == tech}">
                              <a href="#" class="btn btn-success"><spring:message code="statehistory.buttons.wageOffer"/></a>
                              <a href="#" class="btn btn-default"><spring:message code="statehistory.buttons.coding"/></a>
                              <a href="#" class="btn btn-default"><spring:message code="statehistory.buttons.profInterview"/></a>
                              <a href="#" class="btn btn-default"><spring:message code="statehistory.buttons.clientInterview"/></a>
                              <a href="#" class="btn btn-warning"><spring:message code="statehistory.buttons.onHold"/></a>
                              <a href="#" class="btn btn-danger"><spring:message code="statehistory.buttons.reject"/></a>
                          </c:when>
                          <c:when test="${states[0].stateType == coding}">
                              <a href="#" class="btn btn-success"><spring:message code="statehistory.buttons.wageOffer"/></a>
                              <a href="#" class="btn btn-default"><spring:message code="statehistory.buttons.tech"/></a>
                              <a href="#" class="btn btn-default"><spring:message code="statehistory.buttons.profInterview"/></a>
                              <a href="#" class="btn btn-default"><spring:message code="statehistory.buttons.clientInterview"/></a>
                              <a href="#" class="btn btn-warning"><spring:message code="statehistory.buttons.onHold"/></a>
                              <a href="#" class="btn btn-danger"><spring:message code="statehistory.buttons.reject"/></a>
                          </c:when>
                          <c:when test="${states[0].stateType == profInterview}">
                              <a href="#" class="btn btn-success"><spring:message code="statehistory.buttons.wageOffer"/></a>
                              <a href="#" class="btn btn-default"><spring:message code="statehistory.buttons.tech"/></a>
                              <a href="#" class="btn btn-default"><spring:message code="statehistory.buttons.coding"/></a>
                              <a href="#" class="btn btn-default"><spring:message code="statehistory.buttons.clientInterview"/></a>
                              <a href="#" class="btn btn-warning"><spring:message code="statehistory.buttons.onHold"/></a>
                              <a href="#" class="btn btn-danger"><spring:message code="statehistory.buttons.reject"/></a>
                          </c:when>
                          <c:when test="${states[0].stateType == clientInterview}">
                              <a href="#" class="btn btn-success"><spring:message code="statehistory.buttons.wageOffer"/></a>
                              <a href="#" class="btn btn-default"><spring:message code="statehistory.buttons.tech"/></a>
                              <a href="#" class="btn btn-default"><spring:message code="statehistory.buttons.coding"/></a>
                              <a href="#" class="btn btn-default"><spring:message code="statehistory.buttons.profInterview"/></a>
                              <a href="#" class="btn btn-warning"><spring:message code="statehistory.buttons.onHold"/></a>
                              <a href="#" class="btn btn-danger"><spring:message code="statehistory.buttons.reject"/></a>
                          </c:when>
                          <c:when test="${states[0].stateType == wageOffer}">
                              <a href="#" class="btn btn-success"><spring:message code="statehistory.buttons.agree"/></a>
                              <a href="#" class="btn btn-warning"><spring:message code="statehistory.buttons.onHold"/></a>
                          </c:when>
                          <c:when test="${states[0].stateType == agree}">
                              <a href="#" class="btn btn-success"><spring:message code="statehistory.buttons.accept"/></a>
                              <a href="#" class="btn btn-warning"><spring:message code="statehistory.buttons.onHold"/></a>
                          </c:when>
                          <c:when test="${states[0].stateType == pause}">
                              <a href="#" class="btn btn-success"><spring:message code="statehistory.buttons.agree"/></a>
                              <a href="#" class="btn btn-default"><spring:message code="statehistory.buttons.newApplication"/></a>
                              <a href="#" class="btn btn-default"><spring:message code="statehistory.buttons.cv"/></a>
                              <a href="#" class="btn btn-default"><spring:message code="statehistory.buttons.hr"/></a>
                              <a href="#" class="btn btn-default"><spring:message code="statehistory.buttons.firstTest"/></a>
                              <a href="#" class="btn btn-default"><spring:message code="statehistory.buttons.tech"/></a>
                              <a href="#" class="btn btn-default"><spring:message code="statehistory.buttons.coding"/></a>
                              <a href="#" class="btn btn-default"><spring:message code="statehistory.buttons.profInterview"/></a>
                              <a href="#" class="btn btn-default"><spring:message code="statehistory.buttons.clientInterview"/></a>
                              <a href="#" class="btn btn-default"><spring:message code="statehistory.buttons.wageOffer"/></a>
                          </c:when>
                      </c:choose>
                  </div>
              </div>
              </c:if>
          </form>
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
              <c:choose>
                  <c:when test="${data.stateType == newstate}">
                      <div class="form-group">
                          <label for="name" class="control-label col-sm-4"><spring:message code="statehistory.field.position"/></label>
                          <div class="col-sm-8">
                              <p class="form-control-static">${data.position.name}</p>
                          </div>
                      </div>
                      <div class="form-group">
                          <label for="name" class="control-label col-sm-4"><spring:message code="statehistory.field.channel"/></label>
                          <div class="col-sm-8">
                              <p class="form-control-static">${data.applicationDTO.channelId}</p>
                          </div>
                      </div>
                  </c:when>
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