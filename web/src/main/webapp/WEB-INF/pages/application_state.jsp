<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="atsy" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html;charset=UTF-8" %>
<spring:url value="/secure/application_state" var="application_state"/>
<atsy:secure_page>
    <jsp:attribute name="pageJs">
        <script src="<c:url value="/resources/js/atsy-statehistory.js" />"></script>
        <script src="<c:url value="/resources/js/atsy-statehistory-create.js" />"></script>
        <script src="<c:url value="/resources/thirdparty/bootstrap-validator/validator.js" />"
                        type="text/javascript"></script>
    </jsp:attribute>
  <jsp:body>
      <div class="page-header">
          <h1><spring:message code="application.state.title"/>
              <small id="positionName">${states[0].position.name}</small>
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

      <div class="panel panel-danger hidden" role="alert"  data-bind="css: { hidden: !showError() }">
          <div class="panel-heading" data-bind="text: errorMessage"></div>
          <div class="panel-body">
              <ul id="field-messages">
                  <!-- ko foreach: fieldMessages -->
                      <li data-bind="text: $data"></li>
                  <!-- /ko -->
              </ul>
          </div>
      </div>

      <div id="stateList">
      <c:forEach var="data" items="${states}" varStatus="stat">
          <fmt:parseDate pattern="yyyy-MM-dd HH:mm:ss" value="${data.creationDate}" var="parsedCreationDate" />
          <div class="page-header">
              <h4 class="col-sm-6 col-md-6 col-lg-6">${data.stateFullName}</h4>
              <c:if test="${stat.first}">
              <h4 class="glyphicon glyphicon-edit pull-right" id="latestStateEditButton" onclick="editLatestStateOnClick()"></h4>
              </c:if>
          </div>
          <form data-toggle="validator" class="form form-horizontal" role="form" method="POST" id="create-state-form" action="#">
              <c:if test="${stat.first}">
                <input type="hidden" name="id" value="${data.id}" data-bind="valueWithInit: 'id'"/>
                <input type="hidden" name="stateId" value="${data.stateId}" data-bind="valueWithInit: 'stateId'"/>
              </c:if>

              <div class="form-group col-sm-12 col-md-12 col-lg-12">
                  <label for="creationDateInput" class="control-label col-sm-4"><spring:message code="statehistory.field.date"/></label>
                  <div class="col-sm-8">
                      <p class="form-control-static <c:if test="${stat.first}">stateData</c:if>" id="creationDateP"><fmt:formatDate value='${parsedCreationDate}' pattern='yyyy-MM-dd HH:mm'/></p>
                      <c:if test="${stat.first}">
                          <input class="stateInput" readOnly="true" type="text" name="creationDate" id="creationDateInput" style="display:none"
                            value="<fmt:formatDate value='${parsedCreationDate}' pattern='yyyy-MM-dd HH:mm'/>"
                            data-bind="valueWithInit: 'creationDate'"
                            data-error="<spring:message code="statehistory.error.parse.date"/>"
                            pattern="^\d{4}-\d{2}-\d{2} \d{2}:\d{2}$"/>
                      </c:if>
                  </div>
                  <div class="help-block with-errors"></div>
              </div>

              <div class="form-group">
                  <label for="descriptionInput" class="control-label col-sm-4"><spring:message code="statehistory.field.description"/></label>
                  <div class="col-sm-8">
                      <p class="form-control-static <c:if test="${stat.first}">stateData</c:if>" id="descriptionP">${data.description}</p>
                      <c:if test="${stat.first}">
                          <textarea class="stateInput" wrap="soft" name="description" id="descriptionInput" style="display:none; resize: both;"
                            data-bind="valueWithInit: 'description'"
                            maxlength="2000">${data.description}</textarea>
                      </c:if>
                  </div>
                  <div class="help-block with-errors"></div>
              </div>

              <c:choose>
                  <c:when test="${data.stateName == 'newstate'}">
                      <div class="form-group">
                          <label for="positionNameInput" class="control-label col-sm-4"><spring:message code="statehistory.field.position"/></label>
                          <div class="col-sm-8">
                              <p class="form-control-static <c:if test="${stat.first}">stateData</c:if>" id="positionNameP">${data.position.name}</p>
                              <c:if test="${stat.first}">
                                  <input class="stateInput" type="text" name="position.name" id="positionNameInput" style="display:none" value="${data.position.name}"
                                    data-bind="valueWithInit: 'name'">
                              </c:if>
                          </div>
                      </div>
                      <div class="form-group">
                          <label for="channelNameInput" class="control-label col-sm-4"><spring:message code="statehistory.field.channel"/></label>
                          <div class="col-sm-8">
                              <p class="form-control-static <c:if test="${stat.first}">stateData</c:if>" id="channelNameP">${data.channel.name}</p>
                              <c:if test="${stat.first}">
                                  <input class="stateInput" type="text" name="channel.name" id="channelNameInput" style="display:none" value="${data.channel.name}"
                                    data-bind="valueWithInit: 'channelName'">
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
                          <label for="languageSkillInput" class="control-label col-sm-4"><spring:message code="statehistory.field.languageSkill"/></label>
                          <div class="col-sm-8">
                              <p class="form-control-static <c:if test="${stat.first}">stateData</c:if>" id="languageSkillP">${data.languageSkill}</p>
                              <c:if test="${stat.first}">
                                  <input class="stateInput" type="number" name="languageSkill" id="languageSkillInput" style="display:none" value="${data.languageSkill}"
                                  data-error="<spring:message code="candidate.error.language.incorrect"/>"
                                  data-bind="valueWithInit: 'languageSkill'"
                                  max="10" min="0">
                              </c:if>
                          </div>
                          <div class="help-block with-errors"></div>
                      </div>
                  </c:when>
                  <c:when test="${data.stateName == 'firstTest'}">
                      <div class="form-group">
                          <label for="resultInput" class="control-label col-sm-4"><spring:message code="statehistory.field.result"/></label>
                          <div class="col-sm-8">
                              <p class="form-control-static <c:if test='${stat.first}'>stateData</c:if>" id="resultP" >${data.result}%</p>
                              <c:if test="${stat.first}">
                                  <input required class="stateInput" type="number" name="result" id="resultInput" style="display:none;" value="${data.result}"
                                    data-bind="valueWithInit: 'result'"
                                    max="100" min="0"
                                    data-error="<spring:message code='statehistory.error.result.range'/>" />
                              </c:if>
                          </div>
                          <div class="help-block with-errors"></div>
                      </div>
                      <div class="form-group">
                        <label for="recommendationInput" class="control-label col-sm-4"><spring:message code="statehistory.field.recommendation"/></label>
                        <div class="col-sm-8">
                          <p class="form-control-static <c:if test="${stat.first}">stateData</c:if>" id="recommendationP">
                            <c:choose>
                              <c:when test="${data.recommendation == 1}">
                                <spring:message code="common.yes"/>
                              </c:when>
                              <c:when test="${data.recommendation == 0}">
                                <spring:message code="common.no"/>
                              </c:when>
                            </c:choose>
                          </p>
                          <c:if test="${stat.first}">
                            <select required class="stateInput" id="recommendationInput" style="display:none;"
                              data-bind="valueWithInit: 'recommendation'">
                              <option disabled ${data.recommendation == null ? 'selected' : ''}><spring:message code="common.pleaseChoose"/></option>
                              <option value="1" ${data.recommendation == 1 ? 'selected' : ''}><spring:message code="common.yes"/></option>
                              <option value="0" ${data.recommendation == 0 ? 'selected' : ''}><spring:message code="common.no"/></option>
                            </select>
                          </c:if>
                        </div>
                        <div class="help-block with-errors"></div>
                      </div>
                      <div class="form-group">
                        <label for="reviewerNameInput"  class="control-label col-sm-4"><spring:message code="statehistory.field.reviewerName"/></label>
                        <div class="col-sm-8">
                          <p class="form-control-static <c:if test='${stat.first}'>stateData</c:if>" id="reviewerNameP">${data.reviewerName}</p>
                          <c:if test="${stat.first}">
                            <input type="text" class="stateInput" id="reviwerNameInput" style="display:none" value="${data.reviewerName}"
                              data-bind="valueWithInit: 'reviewerName'"
                              data-error="<spring:message code='statehistory.error.reviewerName.length'/>"
                              pattern="^.{3,100}$" required />
                          </c:if>
                        </div>
                        <div class="help-block with-errors"></div>
                      </div>
                      <div class="form-group">
                        <label for="recommendedPositionLevelInput"  class="control-label col-sm-4"><spring:message code="statehistory.field.recommendedPositionLevel"/></label>
                        <div class="col-sm-8">
                          <p class="form-control-static <c:if test='${stat.first}'>stateData</c:if>" id="reviewerNameP">L${data.recommendedPositionLevel}</p>
                          <c:if test="${stat.first}">
                              <select required class="stateInput" id="recommendedPositionLevelInput"
                                data-bind="valueWithInit: 'recommendedPositionLevel'"
                                data-error="<spring:message code='statehistory.error.recommendedPositionLevel.range'/>"
                                style="display:none;">
                                  <option disabled <c:if test="${data.recommendedPositionLevel eq null}"> selected="selected" </c:if>>
                                                                          <spring:message code="common.pleaseChoose"/></option>
                                  <c:forEach begin="0" end="5" step="1" var="index">
                                      <option value="${index}" <c:if
                                              test="${index eq data.recommendedPositionLevel}"> selected="selected" </c:if>>${index}</option>
                                  </c:forEach>
                              </select>
                              </select>
                          </c:if>
                        </div>
                        <div class="help-block with-errors"></div>
                      </div>
                  </c:when>
                  <c:when test="${data.stateName == 'wageOffer'}">
                      <div class="form-group">
                          <label for="name" class="control-label col-sm-4"><spring:message code="statehistory.field.offeredMoney"/></label>
                          <div class="col-sm-8">
                              <p class="form-control-static <c:if test="${stat.first}">stateData"</c:if> id="offeredMoneyP">${data.offeredMoney}</p>
                              <c:if test="${stat.first}">
                                  <input class="stateInput" type="number" name="offeredMoney" id="offeredMoneyInput" style="display:none" value="${data.offeredMoney}"
                                  data-error="<spring:message code="statehistory.error.offeredMoney.negative"/>"
                                  data-bind="valueWithInit: 'offeredMoney'"
                                  min="0">
                              </c:if>
                          </div>
                          <div class="help-block with-errors"></div>
                      </div>
                      <div class="form-group">
                          <label for="name" class="control-label col-sm-4"><spring:message code="statehistory.field.claim"/></label>
                          <div class="col-sm-8">
                              <p class="form-control-static <c:if test="${stat.first}">stateData"</c:if> id="claimP">${data.claim}</p>
                              <c:if test="${stat.first}">
                                  <input class="stateInput" type="number" name="claim" id="claimInput" style="display:none" value="${data.claim}"
                                  data-error="<spring:message code="statehistory.error.claim.negative"/>"
                                  data-bind="valueWithInit: 'claim'"
                                  min="0">
                              </c:if>
                          </div>
                          <div class="help-block with-errors"></div>
                      </div>
                      <div class="form-group">
                          <label for="name" class="control-label col-sm-4"><spring:message code="statehistory.field.feedbackDate"/></label>
                          <div class="col-sm-8">
                              <p class="form-control-static <c:if test="${stat.first}">stateData"</c:if> id="feedbackDateP">${data.feedbackDate}</p>
                              <c:if test="${stat.first}">
                                  <input class="stateInput" type="text" name="feedbackDate" id="feedbackDateInput" style="display:none" value="${data.feedbackDate}"
                                  data-error="<spring:message code="statehistory.error.parse.date"/>"
                                  data-bind="valueWithInit: 'feedbackDate'"
                                  pattern="^\d{4}-\d{2}-\d{2} \d{2}:\d{2}:\d{2}$">
                              </c:if>
                          </div>
                          <div class="help-block with-errors"></div>
                      </div>
                  </c:when>
                  <c:when test="${data.stateName == 'agree'}">
                      <div class="form-group">
                          <label for="name" class="control-label col-sm-4"><spring:message code="statehistory.field.dayOfStart"/></label>
                          <div class="col-sm-8">
                              <fmt:formatDate value="${data.dayOfStart}" type="date" pattern="yyyy-MM-dd" var="formattedDayOfStart"/>
                              <p class="form-control-static <c:if test="${stat.first}">stateData"</c:if> id="dayOfStartP">${formattedDayOfStart}</p>
                              <c:if test="${stat.first}">
                                  <input class="stateInput" type="date" name="dayOfStart" id="dayOfStartInput" style="display:none" value="${formattedDayOfStart}"
                                  data-bind="valueWithInit: 'dayOfStart'">
                              </c:if>
                          </div>
                      </div>
                  </c:when>
              </c:choose>
              <c:if test="${stat.first}">
                  <button type="submit" class="btn btn-success stateInput" style="display:none" data-bind="enable: canSave">
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