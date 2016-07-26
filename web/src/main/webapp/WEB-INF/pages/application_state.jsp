<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="atsy" tagdir="/WEB-INF/tags" %>
<%@page contentType="text/html;charset=UTF-8" %>
<spring:url value="/secure/application_state" var="application_state"/>
<atsy:secure_page>
    <jsp:attribute name="pageJs">
        <script src="<c:url value="/resources/js/atsy-statehistory.js" />"></script>
    </jsp:attribute>
  <jsp:body>
      <div class="page-header">
          <h1><spring:message code="application.state.title"/>
              <small id="positionName">${states[0].position.name}</small>
          </h1>
      </div>
      <div class="button-panel clearfix">
          <form class="form-inline pull-right">
              <c:if test="${states[0].stateFullName != reject && states[0].stateFullName != accept}">
              <div class="form-group">
                  <label class="form-label"><spring:message code="statehistory.text.nextState"/></label>
              </div>
              <div class="form-group">
                  <div class="btn-group" role="group">
                      <c:choose>
                          <c:when test="${states[0].stateName == 'newstate'}">
                              <a href="#" class="btn btn-default"><spring:message code="statehistory.buttons.cv"/></a>
                              <a href="#" class="btn btn-warning"><spring:message code="statehistory.buttons.onHold"/></a>
                              <a href="#" class="btn btn-danger"><spring:message code="statehistory.buttons.reject"/></a>
                          </c:when>
                          <c:when test="${states[0].stateName == 'cv'}">
                              <a href="#" class="btn btn-default"><spring:message code="statehistory.buttons.hr"/></a>
                              <a href="#" class="btn btn-warning"><spring:message code="statehistory.buttons.onHold"/></a>
                              <a href="#" class="btn btn-danger"><spring:message code="statehistory.buttons.reject"/></a>
                          </c:when>
                          <c:when test="${states[0].stateName == 'hr'}">
                              <a href="#" class="btn btn-default"><spring:message code="statehistory.buttons.firstTest"/></a>
                              <a href="#" class="btn btn-warning"><spring:message code="statehistory.buttons.onHold"/></a>
                              <a href="#" class="btn btn-danger"><spring:message code="statehistory.buttons.reject"/></a>
                          </c:when>
                          <c:when test="${states[0].stateName == 'firstTest'}">
                              <a href="#" class="btn btn-default"><spring:message code="statehistory.buttons.tech"/></a>
                              <a href="#" class="btn btn-default"><spring:message code="statehistory.buttons.coding"/></a>
                              <a href="#" class="btn btn-default"><spring:message code="statehistory.buttons.profInterview"/></a>
                              <a href="#" class="btn btn-default"><spring:message code="statehistory.buttons.clientInterview"/></a>
                              <a href="#" class="btn btn-warning"><spring:message code="statehistory.buttons.onHold"/></a>
                              <a href="#" class="btn btn-danger"><spring:message code="statehistory.buttons.reject"/></a>
                          </c:when>
                          <c:when test="${states[0].stateName == 'tech'}">
                              <a href="#" class="btn btn-success"><spring:message code="statehistory.buttons.wageOffer"/></a>
                              <a href="#" class="btn btn-default"><spring:message code="statehistory.buttons.coding"/></a>
                              <a href="#" class="btn btn-default"><spring:message code="statehistory.buttons.profInterview"/></a>
                              <a href="#" class="btn btn-default"><spring:message code="statehistory.buttons.clientInterview"/></a>
                              <a href="#" class="btn btn-warning"><spring:message code="statehistory.buttons.onHold"/></a>
                              <a href="#" class="btn btn-danger"><spring:message code="statehistory.buttons.reject"/></a>
                          </c:when>
                          <c:when test="${states[0].stateName == 'coding'}">
                              <a href="#" class="btn btn-success"><spring:message code="statehistory.buttons.wageOffer"/></a>
                              <a href="#" class="btn btn-default"><spring:message code="statehistory.buttons.tech"/></a>
                              <a href="#" class="btn btn-default"><spring:message code="statehistory.buttons.profInterview"/></a>
                              <a href="#" class="btn btn-default"><spring:message code="statehistory.buttons.clientInterview"/></a>
                              <a href="#" class="btn btn-warning"><spring:message code="statehistory.buttons.onHold"/></a>
                              <a href="#" class="btn btn-danger"><spring:message code="statehistory.buttons.reject"/></a>
                          </c:when>
                          <c:when test="${states[0].stateName == 'profInterview'}">
                              <a href="#" class="btn btn-success"><spring:message code="statehistory.buttons.wageOffer"/></a>
                              <a href="#" class="btn btn-default"><spring:message code="statehistory.buttons.tech"/></a>
                              <a href="#" class="btn btn-default"><spring:message code="statehistory.buttons.coding"/></a>
                              <a href="#" class="btn btn-default"><spring:message code="statehistory.buttons.clientInterview"/></a>
                              <a href="#" class="btn btn-warning"><spring:message code="statehistory.buttons.onHold"/></a>
                              <a href="#" class="btn btn-danger"><spring:message code="statehistory.buttons.reject"/></a>
                          </c:when>
                          <c:when test="${states[0].stateName == 'clientInterview'}">
                              <a href="#" class="btn btn-success"><spring:message code="statehistory.buttons.wageOffer"/></a>
                              <a href="#" class="btn btn-default"><spring:message code="statehistory.buttons.tech"/></a>
                              <a href="#" class="btn btn-default"><spring:message code="statehistory.buttons.coding"/></a>
                              <a href="#" class="btn btn-default"><spring:message code="statehistory.buttons.profInterview"/></a>
                              <a href="#" class="btn btn-warning"><spring:message code="statehistory.buttons.onHold"/></a>
                              <a href="#" class="btn btn-danger"><spring:message code="statehistory.buttons.reject"/></a>
                          </c:when>
                          <c:when test="${states[0].stateName == 'wageOffer'}">
                              <a href="#" class="btn btn-success"><spring:message code="statehistory.buttons.agree"/></a>
                              <a href="#" class="btn btn-warning"><spring:message code="statehistory.buttons.onHold"/></a>
                          </c:when>
                          <c:when test="${states[0].stateName == 'agree'}">
                              <a href="#" class="btn btn-success"><spring:message code="statehistory.buttons.accept"/></a>
                              <a href="#" class="btn btn-warning"><spring:message code="statehistory.buttons.onHold"/></a>
                          </c:when>
                          <c:when test="${states[0].stateName == 'pause'}">
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
      <c:forEach var="data" items="${states}" varStatus="stat">
          <div class="page-header">
              <h4 class="col-sm-6 col-md-6 col-lg-6">${data.stateFullName}</h4>
              <c:if test="${stat.first}">
              <h4 class="glyphicon glyphicon-edit pull-right" id="latestStateEditButton" onclick="editLatestStateOnClick()"></h4>
              </c:if>
          </div>
          <form class="form form-horizontal" role="form" method="POST" action="#">
              <div class="form-group col-sm-12 col-md-12 col-lg-12">
                  <label for="name" class="control-label col-sm-4"><spring:message code="statehistory.field.date"/></label>
                  <div class="col-sm-8">
                      <p class="form-control-static <c:if test="${stat.first}">stateData"</c:if> id="creationDateP">${data.creationDate}</p>
                      <c:if test="${stat.first}">
                          <input class="stateInput" type="text" name="creationDate" id="creationDateInput" style="display:none">
                      </c:if>
                  </div>
              </div>
              <div class="form-group">
                  <label for="name" class="control-label col-sm-4"><spring:message code="statehistory.field.description"/></label>
                  <div class="col-sm-8">
                      <p class="form-control-static <c:if test="${stat.first}">stateData"</c:if> id="descriptionP">${data.description}</p>
                      <c:if test="${stat.first}">
                          <input class="stateInput" type="text" name="description" id="descriptionInput" style="display:none">
                      </c:if>
                  </div>
              </div>
              <c:choose>
                  <c:when test="${data.stateName == 'newstate'}">
                      <div class="form-group">
                          <label for="name" class="control-label col-sm-4"><spring:message code="statehistory.field.position"/></label>
                          <div class="col-sm-8">
                              <p class="form-control-static <c:if test="${stat.first}">stateData"</c:if> id="positionNameP">${data.position.name}</p>
                              <c:if test="${stat.first}">
                                  <input class="stateInput" type="text" name="position.name" id="positionNameInput" style="display:none">
                              </c:if>
                          </div>
                      </div>
                      <div class="form-group">
                          <label for="name" class="control-label col-sm-4"><spring:message code="statehistory.field.channel"/></label>
                          <div class="col-sm-8">
                              <p class="form-control-static <c:if test="${stat.first}">stateData"</c:if> id="channelNameP">${data.channel.name}</p>
                              <c:if test="${stat.first}">
                                  <input class="stateInput" type="text" name="channel.name" id="channelNameInput" style="display:none">
                              </c:if>
                          </div>
                      </div>
                  </c:when>
                  <c:when test="${data.stateName == 'cv'}">
                      <div class="form-group">
                          <label for="name" class="control-label col-sm-4"><spring:message code="statehistory.field.cv"/></label>
                          <div class="col-sm-8">
                              <p class="form-control-static glyphicon glyphicon-paperclip"></p>
                          </div>
                      </div>
                  </c:when>
                  <c:when test="${data.stateName == 'hr'}">
                      <div class="form-group">
                          <label for="name" class="control-label col-sm-4"><spring:message code="statehistory.field.languageSkill"/></label>
                          <div class="col-sm-8">
                              <p class="form-control-static <c:if test="${stat.first}">stateData"</c:if> id="languageSkillP">${data.languageSkill}</p>
                              <c:if test="${stat.first}">
                                  <input class="stateInput" type="text" name="languageSkill" id="languageSkillInput" style="display:none">
                              </c:if>
                          </div>
                      </div>
                  </c:when>
                  <c:when test="${data.stateName == 'firstTest'}">
                      <div class="form-group">
                          <label for="name" class="control-label col-sm-4"><spring:message code="statehistory.field.result"/></label>
                          <div class="col-sm-8">
                              <p class="form-control-static <c:if test="${stat.first}">stateData"</c:if> id="resultP" >${data.result}</p>
                              <c:if test="${stat.first}">
                                  <input class="stateInput" type="text" name="result" id="resultInput" style="display:none">
                              </c:if>
                          </div>
                      </div>
                  </c:when>
                  <c:when test="${data.stateName == 'wageOffer'}">
                      <div class="form-group">
                          <label for="name" class="control-label col-sm-4"><spring:message code="statehistory.field.offeredMoney"/></label>
                          <div class="col-sm-8">
                              <p class="form-control-static <c:if test="${stat.first}">stateData"</c:if> id="offeredMoneyP">${data.offeredMoney}</p>
                              <c:if test="${stat.first}">
                                  <input class="stateInput" type="text" name="offeredMoney" id="offeredMoneyInput" style="display:none">
                              </c:if>
                          </div>
                      </div>
                      <div class="form-group">
                          <label for="name" class="control-label col-sm-4"><spring:message code="statehistory.field.claim"/></label>
                          <div class="col-sm-8">
                              <p class="form-control-static <c:if test="${stat.first}">stateData"</c:if> id="claimP">${data.claim}</p>
                              <c:if test="${stat.first}">
                                  <input class="stateInput" type="text" name="claim" id="claimInput" style="display:none">
                              </c:if>
                          </div>
                      </div>
                      <div class="form-group">
                          <label for="name" class="control-label col-sm-4"><spring:message code="statehistory.field.feedbackDate"/></label>
                          <div class="col-sm-8">
                              <p class="form-control-static <c:if test="${stat.first}">stateData"</c:if> id="feedbackDateP">${data.feedbackDate}</p>
                              <c:if test="${stat.first}">
                                  <input class="stateInput" type="text" name="feedbackDate" id="feedbackDateInput" style="display:none">
                              </c:if>
                          </div>
                      </div>
                  </c:when>
                  <c:when test="${data.stateName == 'agree'}">
                      <div class="form-group">
                          <label for="name" class="control-label col-sm-4"><spring:message code="statehistory.field.dayOfStart"/></label>
                          <div class="col-sm-8">
                              <p class="form-control-static <c:if test="${stat.first}">stateData"</c:if> id="dayOfStartP">${data.dayOfStart}</p>
                              <c:if test="${stat.first}">
                                  <input class="stateInput" type="text" name="dayOfStart" id="dayOfStartInput" style="display:none">
                              </c:if>
                          </div>
                      </div>
                  </c:when>
              </c:choose>
              <c:if test="${stat.first}">
                  <button type="submit" class="btn btn-success stateInput" style="display:none">
                      <spring:message code="save.button"/>
                  </button>
                  <button type="reset" class="btn btn-danger stateInput" style="display:none" onclick="cancelButtonOnClick()">
                      <spring:message code="cancel.button"/>
                  </button>
              </c:if>
          </form>
      </c:forEach>
  </jsp:body>
</atsy:secure_page>