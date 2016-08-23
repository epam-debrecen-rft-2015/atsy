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
              <small id="positionName"><c:out value = "${states[0].position.name}"/></small>
          </h1>
      </div>

      <c:if test="${not empty stateflows}">
        <div class="button-panel clearfix">
            <form class="form-inline pull-right">
                <div class="form-group">
                    <label class="form-label"><spring:message code="statehistory.text.nextState"/></label>
                </div>
                <div class="form-group">
                    <div class="btn-group" role="group">
                        <c:forEach var="stateflow" items="${stateflows}" varStatus="status">
                        <c:choose>
                            <c:when test="${stateflow.toStateDTO.name == 'pause'}">
                                <a href="?applicationId=${applicationId}&state=${stateflow.toStateDTO.name}" class="btn btn-warning"><spring:message code="statehistory.buttons.${stateflow.toStateDTO.name}"/></a>
                            </c:when>
                            <c:when test="${stateflow.toStateDTO.name == 'reject'}">
                                <a href="?applicationId=${applicationId}&state=${stateflow.toStateDTO.name}" class="btn btn-danger"><spring:message code="statehistory.buttons.${stateflow.toStateDTO.name}"/></a>
                            </c:when>
                            <c:otherwise>
                                <a href="?applicationId=${applicationId}&state=${stateflow.toStateDTO.name}" class="btn btn-default"><spring:message code="statehistory.buttons.${stateflow.toStateDTO.name}"/></a>
                            </c:otherwise>
                        </c:choose>
                        </c:forEach>
                    </div>
                </div>
            </form>
        </div>
      </c:if>

      <div id="stateList">
      <c:forEach var="data" items="${states}" varStatus="stat">
          <div class="page-header">
              <h4 class="col-sm-6 col-md-6 col-lg-6">${data.stateFullName}</h4>
              <c:if test="${stat.first}">
              <h4 class="glyphicon glyphicon-edit pull-right" id="latestStateEditButton" onclick="editLatestStateOnClick()"></h4>
              </c:if>
          </div>
          <form class="form form-horizontal" role="form" method="POST" action="#">

              <input type="hidden" name="applicationId" value="${applicationId}" />
              <input type="hidden" name="id" value="${data.id}"/>
              <input type="hidden" name="stateId" value="${data.stateId}"/>

              <div class="form-group col-sm-12 col-md-12 col-lg-12">
                  <label for="name" class="control-label col-sm-4"><spring:message code="statehistory.field.date"/></label>
                  <div class="col-sm-8">
                      <p class="form-control-static <c:if test="${stat.first}">stateData"</c:if> id="creationDateP">${data.creationDate}</p>
                      <c:if test="${stat.first}">
                          <input class="stateInput" type="text" name="creationDate" id="creationDateInput" style="display:none" value="${data.creationDate}" data-formatter="creationDateFormatter">
                      </c:if>
                  </div>
              </div>
              <div class="form-group">
                  <label for="name" class="control-label col-sm-4"><spring:message code="statehistory.field.description"/></label>
                  <div class="col-sm-8">
                      <p class="form-control-static <c:if test="${stat.first}">stateData"</c:if> id="descriptionP"><c:out value = "${data.description}"/></p>
                      <c:if test="${stat.first}">
                          <input class="stateInput" type="text" name="description" id="descriptionInput" style="display:none" value="<c:out value = "${data.description}"/>">
                      </c:if>
                  </div>
              </div>
              <c:choose>
                  <c:when test="${data.stateName == 'newstate'}">
                      <div class="form-group">
                          <label for="name" class="control-label col-sm-4"><spring:message code="statehistory.field.position"/></label>
                          <div class="col-sm-8">
                              <p class="form-control-static <c:if test="${stat.first}">stateData"</c:if> id="positionNameP"><c:out value = "${data.position.name}"/></p>
                              <c:if test="${stat.first}">
                                  <input class="stateInput" type="text" name="position.name" id="positionNameInput" style="display:none" value="<c:out value = "${data.position.name}"/>">
                              </c:if>
                          </div>
                      </div>
                      <div class="form-group">
                          <label for="name" class="control-label col-sm-4"><spring:message code="statehistory.field.channel"/></label>
                          <div class="col-sm-8">
                              <p class="form-control-static <c:if test="${stat.first}">stateData"</c:if> id="channelNameP"><c:out value = "${data.channel.name}"/></p>
                              <c:if test="${stat.first}">
                                  <input class="stateInput" type="text" name="channel.name" id="channelNameInput" style="display:none" value="<c:out value = "${data.channel.name}"/>">
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
                                  <input class="stateInput" type="text" name="languageSkill" id="languageSkillInput" style="display:none" value="${data.languageSkill}">
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
                                  <input class="stateInput" type="text" name="result" id="resultInput" style="display:none" value="${data.result}">
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
                                  <input class="stateInput" type="text" name="offeredMoney" id="offeredMoneyInput" style="display:none" value="${data.offeredMoney}">
                              </c:if>
                          </div>
                      </div>
                      <div class="form-group">
                          <label for="name" class="control-label col-sm-4"><spring:message code="statehistory.field.claim"/></label>
                          <div class="col-sm-8">
                              <p class="form-control-static <c:if test="${stat.first}">stateData"</c:if> id="claimP">${data.claim}</p>
                              <c:if test="${stat.first}">
                                  <input class="stateInput" type="text" name="claim" id="claimInput" style="display:none" value="${data.claim}">
                              </c:if>
                          </div>
                      </div>
                      <div class="form-group">
                          <label for="name" class="control-label col-sm-4"><spring:message code="statehistory.field.feedbackDate"/></label>
                          <div class="col-sm-8">
                              <p class="form-control-static <c:if test="${stat.first}">stateData"</c:if> id="feedbackDateP">${data.feedbackDate}</p>
                              <c:if test="${stat.first}">
                                  <input class="stateInput" type="text" name="feedbackDate" id="feedbackDateInput" style="display:none" value="${data.feedbackDate}">
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
                                  <input class="stateInput" type="text" name="dayOfStart" id="dayOfStartInput" style="display:none" value="${data.dayOfStart}">
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