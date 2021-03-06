<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="atsy" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<spring:url value="/secure/positions" var="positions"/>
<spring:url value="/secure/channels" var="channels"/>
<spring:url value="/secure/welcome" var="welcome"/>
<spring:url value="/secure/candidate/details" var="candidateURL"/>
<spring:url value="/secure/application" var="application"/>

<atsy:secure_page>
    <jsp:attribute name="pageJs">
       <c:url value="/resources/thirdparty/bootstrap-validator/validator.js" var="urlValue" /> <script src="${urlValue}"
                type="text/javascript"></script>
       <c:url value="/resources/thirdparty/jquery/jquery.i18n.properties.js" var="urlValue"/><script src="${urlValue}"></script>
       <c:url value="/resources/thirdparty/bootbox/bootbox.js" var="urlValue"/><script src="${urlValue}"></script>
       <c:url value="/resources/js/atsy-i18n-config.js" var="urlValue" /><script src="${urlValue}"></script>
       <c:url value="/resources/js/atsy-deletable-event-config.js" var="urlValue" /><script src="${urlValue}"></script>
       <c:url value="/resources/js/atsy-candidate-create.js" var="urlValue" /><script src="${urlValue}"></script>
    </jsp:attribute>
    <jsp:body>

         <div id="candidate_creation" data-bind="${not empty candidate.id ? 'initDisplay: value, ' : ''} css: { display: modify() == true }">
            <h1 class="page-header">
                <c:choose>
                    <c:when test="${not empty candidate.id}">
                        <p class="showValue">
                            <c:out value="${candidate.name}"/>
                        </p>

                        <p class="edit">
                            <spring:message code="candidate.edit.title"/>
                            <small>
                              <c:out value="${candidate.name}"/>
                             </small>
                        </p>
                    </c:when>
                    <c:otherwise>
                        <spring:message code="candidate.create.title"/>
                    </c:otherwise>
                </c:choose>
            </h1>

            <div id="candidate_data">
                <div class="row">
                    <form data-toggle="validator" class="form" role="form" method="POST" enctype="multipart/form-data" id="candidate-create-form" action="${candidateURL}" data-bind="css: { 'has-error': showError }">
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
                        <div class="form-group"
                             id="nameDiv">
                            <input type="hidden" name="candidateId" id="candidateId" data-bind="valueWithInit: 'id'" value="${candidate.id}" >
                            <input type="hidden" name="cvFilename" id="cvFilename" data-bind="valueWithInit: 'cvFilename'" value="${candidate.cvFilename}" >
                            <spring:message code="candidate.name.field" var="i18nname"/>
                            <label class="control-label col-lg-2 col-md-2 col-sm-2 text-right"
                                   for="name"><spring:message
                                   code="candidate.name.label"/>
                                <span class="input">*</span>
                            </label>

                            <div class="form-group col-lg-4 col-md-4 col-sm-4">
                               <spring:message code="candidate.error.name.empty" var="nameEmptyValue"/> <input type="text" class="input form-control " name="name" id="name" data-bind="valueWithInit: 'name'"
                                      value="${fn:escapeXml(candidate.name)}"
                                       placeholder="${i18nname}"
                                       data-error="${nameEmptyValue}"
                                       required maxlength="100"
                                       autofocus>

                                <div id="name-errors" class="help-block with-errors"></div>
                                <p class="showValue form-control-static"><c:out value="${candidate.name}"/></p>
                            </div>

                        </div>
                        <div class="form-group"
                             id="placeDiv">
                            <spring:message code="candidate.place.field" var="i18nplace"/>
                            <label class="control-label col-lg-2 col-md-2 col-sm-2 text-right"
                                   for="referer"><spring:message
                                    code="candidate.place.label"/></label>

                            <div class="form-group col-lg-4 col-md-4 col-sm-4">
                                <input type="text" class="input form-control" name="referer" id="referer" data-bind="valueWithInit: 'referer'"
                                       value="${fn:escapeXml(candidate.referer)}"
                                       placeholder="${i18nplace}" maxlength="20">

                                <p class="showValue form-control-static"><c:out value = "${candidate.referer}"/></p>
                            </div>

                        </div>
                        <div class="error col-lg-12 col-md-12 col-sm-12">
                        </div>
                        <div class="form-group"
                             id="emailDiv">
                            <spring:message code="candidate.email.field" var="i18nemail"/>
                            <label class="control-label col-lg-2 col-md-2 col-sm-2 text-right"
                                   for="email"><spring:message
                                   code="candidate.email.label"/>
                                <span class="input">*</span>
                            </label>

                            <div class="form-group col-lg-4 col-md-4 col-sm-4">
                            <spring:message code="candidate.error.email.empty" var="emailEmptyValue"/>
                            <spring:message code="candidate.error.email.incorrect" var="emailIncorrectValue"/>
                                <input type="text" class="input form-control" name="email" id="email" data-bind="valueWithInit: 'email'"
                                       value="${candidate.email}"
                                       placeholder="${i18nemail}"
                                       data-error="${emailEmptyValue}"
                                       data-pattern="[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}"
                                      data-pattern-error="${emailIncorrectValue}"
                                       required maxlength="255">
                                <span id="email-errors" class="help-block with-errors"></span>

                                <p class="showValue form-control-static">
                                  <a href="mailto:${candidate.email}"><c:out value = "${candidate.email}"/></a>
                                </p>
                            </div>

                        </div>
                        <div class="form-group"
                             id="englishDiv">
                            <label class="control-label col-lg-2 col-md-2 col-sm-2 text-right"
                                   for="drop"><spring:message
                                    code="candidate.english.label"/></label>

                            <div class="selectContainer col-lg-4 col-md-4 col-sm-4">
                                <select class="input form-control" name="languageSkill" id="languageSkill" data-bind="valueWithInit: 'languageSkill'">
                                    <option value=0 <c:if
                                            test="${0 eq candidate.languageSkill}"> selected="selected" </c:if>>
                                        <spring:message code="candidate.english.level.default"/></option>
                                    <c:forEach begin="1" end="10" step="1" var="index">
                                        <option value="${index}" <c:if
                                                test="${index eq candidate.languageSkill}"> selected="selected" </c:if>>${index}</option>
                                    </c:forEach>
                                </select>

                                <p class="showValue form-control-static"><c:out value = "${candidate.languageSkill}"/></p>
                            </div>
                        </div>

                        <div class="error col-lg-12 col-md-12 col-sm-12"></div>

                        <div class="form-group"
                             id="phoneDiv">
                            <spring:message code="candidate.phone.field" var="i18nphone"/>
                            <label class="control-label col-lg-2 col-md-2 col-sm-2 text-right"
                                   for="phone"><spring:message
                                   code="candidate.phone.label"/>
                                <span class="input">*</span>
                            </label>

                            <div class="form-group col-lg-4 col-md-4 col-sm-4">
                            <spring:message code="candidate.error.phone.empty" var="phoneEmptyValue"/>
                            <spring:message code="candidate.error.phone.incorrect" var="phoneIncorrectValue"/>
                                <input type="text" class="input form-control" name="phone" id="phone" data-bind="valueWithInit: 'phone'"
                                       value="${candidate.phone}"
                                       placeholder="${i18nphone}"
                                       data-error="${phoneEmptyValue}"
                                       data-pattern="^\+?[0-9]+$"
                                       data-pattern-error="${phoneIncorrectValue}"
                                       required maxlength="20">

                                <div id="phone-errors" class="help-block with-errors"></div>

                                <p class="showValue form-control-static"><c:out value ="${candidate.phone}"/></p>
                            </div>

                        </div>

                        <div class="form-group" id="cvDiv">
                            <label class="control-label col-lg-2 col-md-2 col-sm-2 text-right" for="name">
                                <spring:message code="cv"/>
                            </label>

                            <c:if test="${empty candidate.cvFilename}">

                                <div class="form-group col-lg-4 col-md-4 col-sm-4">
                                    <label class="btn btn-primary" for="file">
                                        <i class="glyphicon glyphicon-upload"></i>
                                    </label>
                                    <input class="input form-control" type="file" name="file" id="file" style="display:none;"/>
                                    <p id="fileName"/>
                                </div>
                            </c:if>

                            <c:if test="${not empty candidate.cvFilename}">

                                <label class="control-label col-lg-2 col-md-4 col-sm-4 text-left" for="name">
                                    <c:url value='/secure/fileDownload/validate/${candidateId}' var="urlValue" />
                                    <a href="javascript:void(0)" data-file="${urlValue}" id="downloadLink">
                                        <c:out value="${candidate.cvFilename}"/>
                                    </a>
                                </label>
                            </c:if>
                        </div>
                        <div class="error col-lg-12 col-md-12 col-sm-12"></div>

                        <div class="form-group"
                             id="descriptionDiv">
                            <spring:message code="candidate.description.field" var="i18ndescription"/>
                            <label class="control-label col-lg-2 col-md-2 col-sm-2 text-right"
                                   for="description"><spring:message
                                    code="candidate.description.label"/></label>

                            <div class="col-lg-10 col-md-10 col-sm-10">
                                <textarea rows="4" cols="4" class="input form-control" id="description" data-bind="valueWithInit: 'description'"
                                          placeholder="${i18ndescription}"><c:out value = "${candidate.description}"/></textarea>

                                <p class="showValue form-control-static"><c:out value = "${candidate.description}"/></p>
                            </div>
                        </div>

                        <div class="text-right col-lg-12 col-md-12 col-sm-12">
                            <a class="btn btn-default showValue" href="${welcome}" id="cancelButton"><spring:message
                                    code="back.button"/></a>
                            <button class="btn btn-primary showValue" id="enableModify" data-bind="click: modify_display_false"><spring:message
                                    code="candidate.modify.button"/></button>
                            <c:choose>
                                <c:when test="${not empty candidate.id}">
                                    <button class="btn btn-danger " id="cancelButtonModify" type="reset" data-bind="click: modify_display_true"><spring:message
                                            code="cancel.button"/></button>
                                </c:when>
                                <c:otherwise>
                                    <a class="btn btn-danger" href="${welcome}" id="cancelButtonAdd"><spring:message
                                            code="cancel.button"/></a>
                                </c:otherwise>
                            </c:choose>
                            <button type="submit" id="save-button" class="btn btn-success">
                                <spring:message code="save.button"/>
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <c:choose>
            <c:when test="${not empty candidate.id}">
                <div id="new_application" class="text-right">
                    <a class="btn btn-success" href="${application}?candidateId=${candidate.id}" id="add_application_button"><spring:message
                            code="candidate.new.application.button"/></a>
                </div>
                <div id="application_table">
                        <table class="table table-hover cursor-pointer" id="applications_table"  data-toggle="table" data-url="../../applications/${candidate.id}" data-height="500"
                               data-sort-name="name" data-escape="true" data-pagination="true" data-pagination-loop="false" data-side-pagination="server" data-query-params-type="">
                            <thead>
                            <tr>
                                <th data-field="name" data-align="left"><spring:message
                                        code="candidate.table.application.position"/></th>
                                <th data-field="creationDate" data-align="left" data-formatter="creationDateFormatter">
                                    <spring:message code="candidate.table.application.added.date"/>
                                </th>
                                <th data-field="modificationDate" data-align="left" data-formatter="modificationDateFormatter">
                                <spring:message
                                        code="candidate.table.application.modified.date"/></th>
                                <th data-field="stateType" data-align="left"><spring:message
                                        code="candidate.table.application.state"/></th>
                                <th data-field="id" data-align="left"
                                    data-formatter="actionFormatter"
                                    data-events="channelsEvents">
                                    <spring:message code="candidate.table.application.action"/></th>

                            </tr>

                            </thead>
                        </table>
                    </div>
                </div>

            </c:when>
        </c:choose>
    </jsp:body>
</atsy:secure_page>