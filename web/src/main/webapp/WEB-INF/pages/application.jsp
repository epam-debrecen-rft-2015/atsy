<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="atsy" tagdir="/WEB-INF/tags" %>
<%@page contentType="text/html;charset=UTF-8" %>
<spring:url value="/secure/application" var="application"/>
<spring:url value="/secure/new_application_popup" var="new_application_popup"/>

<atsy:secure_page>
    <jsp:attribute name="pageJs">
       <c:url value="/resources/js/atsy-application.js" var="urlValue" /> <script src="${urlValue}"></script>
       <c:url value="/resources/js/atsy-new-application-popup.js" var="urlValue" /> <script type="text/javascript" src="${urlValue}"></script>
    </jsp:attribute>
    <jsp:body>
        <div id="application_page">
            <h1 class="page-header">
              <spring:message code="application.create.title"/>
            </h1>
        <div class="modal fade" id="modal" role="dialog">
            <div class="modal-dialog" >
                <div class="modal-content" id="pop_up_content">
                    <div class="modal-header">
                    <h4 id="new_application_popup_title">Új jelentkezés</h4>
                    </div>
                    <div class="modal-body">
                        <form class="form" role="form" method="POST" id="application-create-form" action="${new_application_popup}">
                            <input type="hidden" name="candidateId" id="candidateId" value="${candidateId}">
                            <div class="form-group" id="positionDiv">
                                <label id="positionLabel" class="control-label col-lg-2 col-md-2 col-sm-2 text-right"
                                    for="drop"><spring:message
                                    code="application.create.position.label"/></label>

                                <div class="selectContainer col-lg-4 col-md-4 col-sm-4" id="drop">
                                    <spring:message code="application.popup.position.drop.down.default.value" var="i18n_position_drop_down_default_value"/>
                                    <select class="input form-control" name="position.id" id="position">
                                    <option value = "${i18n_position_drop_down_default_value}">${i18n_position_drop_down_default_value}</option>
                                    </select>
                                    <span style="color: red" id="position_error"/>
                                </div>
                            </div>
                            <div class="form-group" id="sourceDiv">
                                <label id="sourceLabel" class="control-label col-lg-2 col-md-2 col-sm-2 text-right"
                                    for="source"><spring:message
                                    code="application.create.source.label"/></label>

                                <div class="selectContainer col-lg-4 col-md-4 col-sm-4" id="dropSource">
                                    <spring:message code="application.popup.application.source.drop.down.default.value" var="i18n_application_source_drop_down_default_value"/>
                                    <select class="input form-control" name="channel.id" id="channel">
                                    <option value = "${i18n_application_source_drop_down_default_value}">${i18n_application_source_drop_down_default_value}</option>
                                    </select>
                                    <span style="color: red" id="application_source_error"/>
                                </div>
                            </div>
                            <div class="form-group" id="descriptionDiv">
                                <spring:message code="candidate.description.field" var="i18ndescription"/>
                                <label class="control-label col-lg-12 col-md-12 col-sm-12 text-left"
                                    for="description"><spring:message
                                    code="candidate.description.label"/></label>
                                <div class="col-lg-12 col-md-12 col-sm-12">
                                    <textarea rows="4" cols="4" class="input form-control" id="description" name="description"
                                        placeholder="${i18ndescription}"><c:out value = "${candidate.description}"/></textarea>
                                    <p class="showValue form-control-static"><c:out value = "${candidate.description}"/></p>
                                </div>
                            </div>
                            <div class="col-lg-offset-8 col-md-offset-8 col-sm-offset-8 col-lg-4 col-md-4 col-sm-4">
                                <a href="candidate/${candidateId}" class="btn btn-danger" id="cancel_button">Vissza</a>
                                <button type="submit" class="btn btn-success" id="save_new_apply_button" >
                                    <spring:message code="save.button"/>
                                </button>
                            </div>
                        </form>
                        </div>
                        <div class="modal-footer" id="footer">
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </jsp:body>
</atsy:secure_page>
