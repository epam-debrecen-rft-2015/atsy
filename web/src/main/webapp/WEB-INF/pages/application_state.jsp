<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="atsy" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page contentType="text/html;charset=UTF-8" %>
<spring:url value="/secure/application_state" var="application_state"/>
<c:url value="/resources/thirdparty/bootstrap-datepicker/bootstrap-datepicker.js" var="bootstrap_datepicker_js"/>
<c:url value="/resources/thirdparty/bootstrap-datepicker/bootstrap-datepicker.css" var="bootstrap_datepicker_css"/>
<c:url value="/resources/thirdparty/bootstrap-datetimepicker/bootstrap-datetimepicker.js" var="bootstrap_datetimepicker_js"/>
<c:url value="/resources/thirdparty/bootstrap-datetimepicker/moment-with-locales.js" var="moment_js"/>
<atsy:secure_page>
    <jsp:attribute name="pageJs">

        <c:url value="/resources/js/atsy-statehistory.js" var="urlValue"/><script src="${urlValue}"></script>
       <c:url value="/resources/js/atsy-statehistory-create.js" var="urlValue" /> <script src="${urlValue}"></script>
       <c:url value="/resources/thirdparty/bootstrap-validator/validator.js" var="urlValue" /> <script src="${urlValue}"
                        type="text/javascript"></script>
        <script src="${bootstrap_datepicker_js}" type="text/javascript"></script>
        <link href="${bootstrap_datepicker_css}" rel="stylesheet" type="text/css">
        <script src="${moment_js}" type="text/javascript"></script>
        <script src="${bootstrap_datetimepicker_js}" type="text/javascript"></script>

    </jsp:attribute>
  <jsp:body>
      <div class="page-header">
          <h1><c:out value="${candidate.name}"/>

              <small id="positionName"><c:out value = "${states[0].positionName}"/></small>
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
                                <a href="?applicationId=${applicationId}&state=${stateflow.toStateDTO.name}" class="btn btn-default" id="newStateButton"><spring:message code="statehistory.buttons.${stateflow.toStateDTO.name}"/></a>
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
      <div class="panel panel-danger hidden" role="alert"  data-bind="css: { hidden: !showFileError() }">
          <div class="panel-heading" data-bind="text: fileErrorMessage"></div>
      </div>

      <div id="stateList">
      <c:forEach var="data" items="${states}" varStatus="stat">

          <div class="page-header">
              <h4>
                  <span>${data.stateFullName}</span>
                  <c:if test="${stat.first}">
                      <button class="btn btn-default pull-right" id="latestStateEditButton" onclick="editLatestStateOnClick()">
                          <span class="glyphicon glyphicon-edit"></span>
                      </button>
                  </c:if>
              </h4>
          </div>
          <form data-toggle="validator" class="form form-horizontal" role="form" method="POST" enctype="multipart/form-data" id="create-state-form" action="#">
              <c:if test="${stat.first}">
                <input type="hidden" name="id" value="${data.id}" data-bind="valueWithInit: 'id'"/>
                <input type="hidden" id="stateId" name="stateId" value="${data.stateId}" data-bind="valueWithInit: 'stateId'"/>
                <input type="hidden" name="stateName" value="${data.stateName}" data-bind="valueWithInit: 'stateName'"/>
              </c:if>

              <div class="form-group">
                  <label for="creationDateInput" class="control-label col-sm-4"><spring:message code="statehistory.field.date"/></label>
                  <div class="col-sm-8">
                      <fmt:formatDate value='${data.creationDate}' pattern='yyyy-MM-dd HH:mm' var="formattedCreationDate"/>

                      <p class="form-control-static ${stat.first ? 'stateData' : ''}">${formattedCreationDate}</p>
                      <c:if test="${stat.first}">
                          <spring:message code="statehistory.error.parse.date" var="errorParseDateMessage"/>

                          <input class="form-control stateInput hidden" readOnly="true" type="text" name="creationDate" id="creationDateInput"
                            value="${formattedCreationDate}"
                            data-bind="valueWithInit: 'creationDate'"
                            data-error="${errorParseDateMessage}"
                            pattern="^\d{4}-\d{2}-\d{2} \d{2}:\d{2}$"/>
                      </c:if>
                  </div>
                  <div class="help-block with-errors"></div>
              </div>

              <div class="form-group">
                  <label for="descriptionInput" class="control-label col-sm-4"><spring:message code="statehistory.field.description"/></label>
                  <div class="col-sm-8">
                      <p class="form-control-static ${stat.first ? 'stateData' : ''}"><c:out value = "${data.description}"/></p>
                      <c:if test="${stat.first}">
                          <textarea class="form-control stateInput hidden resizeable" wrap="soft" name="description" id="descriptionInput"
                            data-bind="valueWithInit: 'description'"
                            maxlength="2000"><c:out value = "${data.description}"/></textarea>
                      </c:if>
                  </div>
                  <div class="help-block with-errors"></div>
              </div>
              <c:choose>

                  <c:when test="${data.stateName == 'newstate'}">
                       <div class="form-group">
                            <label for="positionSelector"  class="control-label col-sm-4"><spring:message code="statehistory.field.position"/></label>
                            <div class="col-sm-8">
                                <p class="form-control-static ${stat.first ? 'stateData' : ''}"><c:out value="${data.positionName}"/></p>
                                  <c:if test="${stat.first}">
                                      <select required class="form-control stateInput hidden" id="positionSelector" data-bind="valueWithInit: 'positionId'">
                                          <option disabled="disabled" selected="selected" value="${data.positionId}">
                                               <c:out value="${data.positionName}"/>
                                           </option>
                                      </select>
                                  </c:if>
                             </div>
                       </div>
                       <div class="form-group">
                           <label for="channelSelector"  class="control-label col-sm-4"><spring:message code="statehistory.field.channel"/></label>
                            <div class="col-sm-8">
                                 <p class="form-control-static ${stat.first ? 'stateData' : ''}">${data.channelName}</p>
                                 <c:if test="${stat.first}">
                                     <select required class="form-control stateInput hidden" id="channelSelector" data-bind="valueWithInit: 'channelName'">
                                         <option disabled="disabled" selected="selected">
                                             <c:out value="${data.channelName}"/>
                                         </option>
                                     </select>
                                 </c:if>
                           </div>
                       </div>

                  </c:when>
                    <c:when test ="${data.stateName == 'coding'}">
                       <div class="form-group">
                          <label for="recommendedPositionLevelInput"  class="control-label col-sm-4"><spring:message code="statehistory.field.recommendedPositionLevel"/>
                              <span class="stateInput hidden">*</span>
                          </label>
                          <div class="col-sm-8">
                            <p class="form-control-static ${stat.first ? 'stateData' : ''}"> <c:out value = "L${data.recommendedPositionLevel}"/></p>
                            <c:if test="${stat.first}">
                                <spring:message code="statehistory.error.recommendedPositionLevel.range" var="errorRecommendedPositionLevelOutOfRange"/>
                                <select required class="form-control stateInput hidden" id="recommendedPositionLevelInput"
                                  data-bind="valueWithInit: 'recommendedPositionLevel'"
                                  data-error="${errorRecommendedPositionLevelOutOfRange}">
                                    <option disabled ${data.recommendedPositionLevel eq null ? 'selected="selected"' : ''}>
                                      <spring:message code="common.pleaseChoose"/>
                                    </option>
                                    <c:forEach begin="0" end="5" step="1" var="index">
                                        <option value="${index}"
                                          ${index eq data.recommendedPositionLevel ? 'selected="selected"' : ''}>
                                              ${index}
                                        </option>
                                    </c:forEach>
                                </select>
                                </select>
                            </c:if>
                          </div>
                          <div class="help-block with-errors"></div>
                          </div>
                    </c:when>
                  <c:when test="${data.stateName == 'cv'}">
                      <div class="form-group">
                          <label for="name" class="control-label col-sm-4"><spring:message code="statehistory.field.cv"/></label>

                          <c:if test="${empty candidate.cvFilename && states[0].stateName == 'cv'}">

                              <div class="form-group col-lg-4 col-md-4 col-sm-4">
                                  <label class="btn btn-primary" for="file" id="fileUploadLabel">
                                      <i class="glyphicon glyphicon-upload"></i>
                                  </label>
                                  <input class="input form-control" type="file" name="file" id="file" style="display:none;"/>
                                  <p id="fileName"/>
                              </div>
                          </c:if>

                          <c:if test="${not empty candidate.cvFilename}">

                              <label class="control-label col-lg-1 text-left" for="name">
                                  <c:url value='/secure/fileDownload/validate/${candidate.id}' var="urlValue" />
                                  <a href="javascript:void(0)" data-file="${urlValue}" id="downloadLink">
                                      <c:out value="${candidate.cvFilename}"/>
                                  </a>
                              </label>
                          </c:if>
                      </div>
                  </c:when>

                <c:when test ="${data.stateName == 'tech'}">
                   <div class="form-group">
                      <label for="recommendedPositionLevelInput"  class="control-label col-sm-4"><spring:message code="statehistory.field.recommendedPositionLevel"/>
                          <span class="stateInput hidden">*</span>
                      </label>
                      <div class="col-sm-8">
                        <p class="form-control-static ${stat.first ? 'stateData' : ''}"> <c:out value="L${data.recommendedPositionLevel}"/></p>
                        <c:if test="${stat.first}">
                            <spring:message code="statehistory.error.recommendedPositionLevel.range" var="recommendedPositionLevelOutOfRange"/>
                            <select required class="form-control stateInput hidden" id="recommendedPositionLevelInput"
                              data-bind="valueWithInit: 'recommendedPositionLevel'"
                              data-error="${recommendedPositionLevelOutOfRange}">
                                <option disabled ${data.recommendedPositionLevel eq null ? 'selected="selected"' : ''}>
                                  <spring:message code="common.pleaseChoose"/>
                                </option>
                                <c:forEach begin="0" end="5" step="1" var="index">
                                    <option value="${index}"
                                      ${index eq data.recommendedPositionLevel ? 'selected="selected"' : ''}>
                                          ${index}
                                    </option>
                                </c:forEach>
                            </select>
                            </select>
                        </c:if>
                      </div>
                      <div class="help-block with-errors"></div>
                      </div>
                </c:when>

                  <c:when test="${data.stateName == 'hr'}">
                      <div class="form-group">
                          <label for="languageSkillInput" class="control-label col-sm-4"><spring:message code="statehistory.field.languageSkill"/></label>
                          <div class="col-sm-8">
                              <p class="form-control-static" id="languageSkillP">${data.languageSkill}</p>
                          </div>
                      </div>
                  </c:when>
                  <c:when test="${data.stateName == 'firstTest'}">
                      <div class="form-group">
                          <label for="resultInput" class="control-label col-sm-4"><spring:message code="statehistory.field.result"/>
                              <span class="stateInput hidden">*</span>
                          </label>
                          <div class="col-sm-8">
                              <p class="form-control-static ${stat.first ? 'stateData' : ''}">${data.result}%</p>
                              <c:if test="${stat.first}">
                                  <spring:message code="statehistory.error.result.range" var="errorResultRangeMessage"/>

                                  <input required class="form-control stateInput hidden" type="text" pattern="^(?:100|[1-9]?[0-9])$" name="result" id="resultInput" value="${data.result}"
                                    data-bind="valueWithInit: 'result'"
                                    data-error="${errorResultRangeMessage}" />
                              </c:if>
                          </div>
                          <div class="help-block with-errors"></div>
                      </div>
                      <div class="form-group">
                        <label for="recommendationInput" class="control-label col-sm-4"><spring:message code="statehistory.field.recommendation"/>
                            <span class="stateInput hidden">*</span>
                        </label>
                        <div class="col-sm-8">
                          <p class="form-control-static ${stat.first ? 'stateData' : ''}">
                            <spring:message code="common.yes" var="yesMessage" />
                            <spring:message code="common.no" var="noMessage"/>

                            <c:out value="${data.recommendation == 1 ? yesMessage : noMessage}" />
                          </p>
                          <c:if test="${stat.first}">
                            <select required class="form-control stateInput hidden" id="recommendationInput"
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
                        <label for="reviewerNameInput"  class="control-label col-sm-4"><spring:message code="statehistory.field.reviewerName"/>
                            <span class="stateInput hidden">*</span>
                        </label>
                        <div class="col-sm-8">
                          <p class="form-control-static ${stat.first ? 'stateData' : ''}">${data.reviewerName}</p>
                          <c:if test="${stat.first}">
                            <spring:message code="statehistory.error.reviewerName.length" var="errorReviewerNameLengthMessage" />


                            <input type="text " class="form-control stateInput hidden" id="reviwerNameInput"  value="${fn:escapeXml(data.reviewerName)}"
                              data-bind="valueWithInit: 'reviewerName'"
                              data-error="${errorReviewerNameLengthMessage}"
                              pattern="^.{3,100}$" required />
                          </c:if>
                        </div>
                        <div class="help-block with-errors"></div>
                      </div>
                      <div class="form-group">
                        <label for="recommendedPositionLevelInput"  class="control-label col-sm-4"><spring:message code="statehistory.field.recommendedPositionLevel"/>
                            <span class="stateInput hidden">*</span>
                        </label>
                        <div class="col-sm-8">
                          <p class="form-control-static ${stat.first ? 'stateData' : ''}">L${data.recommendedPositionLevel}</p>
                          <c:if test="${stat.first}">
                              <spring:message code="statehistory.error.recommendedPositionLevel.range" var="errorRecommendedPositionLevelRangeMessage"/>

                              <select required class="form-control stateInput hidden" id="recommendedPositionLevelInput"
                                data-bind="valueWithInit: 'recommendedPositionLevel'"
                                data-error="${errorRecommendedPositionLevelRangeMessage}">
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
                  <c:when test="${data.stateName == 'clientInterview'}">
                    <div class="form-group">
                      <label for="recommendedPositionLevelInput"  class="control-label col-sm-4"><spring:message code="statehistory.field.recommendedPositionLevel"/>
                          <span class="stateInput hidden">*</span>
                      </label>
                      <div class="col-sm-8">
                        <p class="form-control-static ${stat.first ? 'stateData' : ''}">L${data.recommendedPositionLevel}</p>
                        <c:if test="${stat.first}">
                            <spring:message code="statehistory.error.recommendedPositionLevel.range" var="errorRecommendedPositionLevelRangeMessage"/>

                            <select required class="form-control stateInput hidden" id="recommendedPositionLevelInput"
                              data-bind="valueWithInit: 'recommendedPositionLevel'"
                              data-error="${errorRecommendedPositionLevelRangeMessage}">
                                <option disabled ${data.recommendedPositionLevel eq null ? "selected" : " "}>
                                                                        <spring:message code="common.pleaseChoose"/></option>
                                <c:forEach begin="0" end="5" step="1" var="index">
                                    <option value="${index}" ${index eq data.recommendedPositionLevel ? "selected" : "" }>${index}</option>
                                </c:forEach>
                            </select>
                            </select>
                        </c:if>
                      </div>
                      <div class="help-block with-errors"></div>
                    </div>
                  </c:when>
                    <c:when test ="${data.stateName == 'profInterview'}">
                       <div class="form-group">
                          <label for="recommendedPositionLevelInput"  class="control-label col-sm-4"><spring:message code="statehistory.field.recommendedPositionLevel"/>
                              <span class="stateInput hidden">*</span>
                          </label>
                          <div class="col-sm-8">
                            <p class="form-control-static ${stat.first ? 'stateData' : ''}"> <c:out value = "L${data.recommendedPositionLevel}"/></p>
                            <c:if test="${stat.first}">
                                <spring:message code="statehistory.error.recommendedPositionLevel.range" var="recommendedPositionLevelOutOfRange"/>
                                <select required class="form-control stateInput hidden" id="recommendedPositionLevelInput"
                                  data-bind="valueWithInit: 'recommendedPositionLevel'"
                                  data-error="${recommendedPositionLevelOutOfRange}">
                                    <option disabled ${data.recommendedPositionLevel eq null ? 'selected="selected"' : ''}>
                                      <spring:message code="common.pleaseChoose"/>
                                    </option>
                                    <c:forEach begin="0" end="5" step="1" var="index">
                                        <option value="${index}"
                                          ${index eq data.recommendedPositionLevel ? 'selected="selected"' : ''}>
                                              ${index}
                                        </option>
                                    </c:forEach>
                                </select>
                                </select>
                            </c:if>
                          </div>
                          <div class="help-block with-errors"></div>
                          </div>
                    </c:when>
                  <c:when test="${data.stateName == 'wageOffer'}">
                      <spring:message code="common.thousand.huf" var="thousandHUF"/>
                      <div class="form-group">
                          <label for="name" class="control-label col-sm-4"><spring:message code="statehistory.field.offeredMoney"/>
                              <span class="stateInput hidden">*</span>
                          </label>
                          <div class="col-sm-8">
                              <p class="form-control-static ${stat.first ? 'stateData' : ''}"><c:out value="${data.offeredMoney} ${thousandHUF}"/></p>
                              <c:if test="${stat.first}">
                                  <spring:message code="statehistory.error.offeredMoney.negative" var="errorOfferedMoneyNegativeMessage"/>
                                  <input required class="form-control stateInput hidden" type="number" name="offeredMoney" id="offeredMoneyInput"  value="${data.offeredMoney}"
                                  data-error="${errorOfferedMoneyNegativeMessage}"
                                  data-bind="valueWithInit: 'offeredMoney'"
                                  min="0">
                                  <span class="stateDataPostfix hidden" ><c:out value="${thousandHUF}"/></span>
                              </c:if>

                          </div>
                          <div class="help-block with-errors"></div>
                      </div>
                      <div class="form-group">
                          <label for="name" class="control-label col-sm-4"><spring:message code="statehistory.field.claim"/>
                              <span class="stateInput hidden">*</span>
                          </label>
                          <div class="col-sm-8">
                              <p class="form-control-static ${stat.first ? 'stateData' : ''}"><c:out value="${data.claim} ${thousandHUF}"/></p>
                              <c:if test="${stat.first}">
                                  <spring:message code="statehistory.error.claim.negative" var="errorClaimNegativeMessage"/>
                                  <input required class="form-control stateInput hidden" type="number" name="claim" id="claimInput" value="${data.claim}"
                                  data-error="${errorClaimNegativeMessage}"
                                  data-bind="valueWithInit: 'claim'"
                                  min="0">
                                  <span class="stateDataPostfix hidden" ><c:out value="${thousandHUF}"/></span>
                              </c:if>
                          </div>
                          <div class="help-block with-errors"></div>
                      </div>

                      <div class="form-group">
                          <label for="name" class="control-label col-sm-4"><spring:message code="statehistory.field.feedbackDate"/>
                              <span class="stateInput hidden">*</span>
                          </label>
                          <div class="col-sm-8">
                              <fmt:formatDate value="${data.feedbackDate}" type="date" pattern="yyyy-MM-dd HH:mm" var="formattedFeedbackDate"/>
                              <p class="form-control-static ${stat.first ? 'stateData' : ''}">${formattedFeedbackDate}</p>
                              <c:if test="${stat.first}">
                                  <div class='input-group date' name='feedbackDate' id='feedbackDateInput' >
                                      <input required type='text' class="form-control stateInput hidden" value="${formattedFeedbackDate}" data-bind="datetimepickerBinding: 'feedbackDate'" />
                                      <span class="input-group-addon stateInput hidden">
                                          <span class="glyphicon glyphicon-calendar"></span>
                                      </span>
                                  </div>
                              </c:if>
                          </div>
                          <div class="help-block with-errors"></div>
                      </div>
                  </c:when>
                  <c:when test="${data.stateName == 'agree'}">
                      <div class="form-group">
                          <label for="name" class="control-label col-sm-4"><spring:message code="statehistory.field.dayOfStart"/>
                              <span class="stateInput hidden">*</span>
                          </label>
                          <div class="col-sm-8">
                              <fmt:formatDate value="${data.dayOfStart}" type="date" pattern="yyyy-MM-dd" var="formattedDayOfStart"/>
                              <p class="form-control-static ${stat.first ? 'stateData' : ''}">${formattedDayOfStart}</p>
                              <c:if test="${stat.first}">
                                  <div class='input-group date' name='dayOfStart' id='dayOfStartInput'>
                                      <input required type='text' class="form-control stateInput hidden" value="${formattedDayOfStart}" data-bind="datetimepickerBinding: 'dayOfStart'" />
                                      <span class="input-group-addon stateInput hidden">
                                          <span class="glyphicon glyphicon-calendar"></span>
                                      </span>
                                  </div>
                              </c:if>
                          </div>
                      </div>
                  </c:when>
                  <c:when test="${data.stateName == 'accept'}">
                      <div class="form-group">
                          <label for="name" class="control-label col-sm-4"><spring:message code="statehistory.field.dateOfEnter"/>
                              <span class="stateInput hidden">*</span>
                          </label>
                          <div class="col-sm-8">
                              <fmt:formatDate value="${data.dateOfEnter}" type="date" pattern="yyyy-MM-dd" var="formattedDateOfEnter"/>
                              <p class="form-control-static ${stat.first ? 'stateData' : ''}">${formattedDateOfEnter}</p>
                              <c:if test="${stat.first}">
                                  <input class="form-control stateInput hidden" type="date" name="dateOfEnter" id="dateOfEnterInput" value="${formattedDateOfEnter}"
                                  data-bind="valueWithInit: 'dateOfEnter'" required>
                              </c:if>
                          </div>
                      </div>
                  </c:when>
              </c:choose>
              <c:if test="${stat.first}">
                  <button type="submit" class="btn btn-success stateInput hidden"  data-bind="enable: canSave" id="saveButton">
                      <spring:message code="save.button"/>
                  </button>
                  <button type="reset" class="btn btn-danger stateInput hidden" data-bind="click: modify_display_true" onclick="cancelButtonOnClick()" id="cancelButton">
                      <spring:message code="cancel.button"/>
                  </button>
              </c:if>
          </form>
      </c:forEach>
  </jsp:body>

</atsy:secure_page>