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
        <script src="<c:url value="/resources/js/atsy-application.js" />"></script>
    </jsp:attribute>
  <jsp:body>
    <div id="application_page">
      <h1 class="page-header">
        <spring:message code="application.create.title"/>
      </h1>
      <div class="modal fade" id="modal">
        <div class="modal-dialog" role="dialog">
          <div class="modal-content">
            <div class="modal-header">
              <h4 class="modal-title">Új jelentkezés</h4>
            </div>
            <div class="modal-body">
              <form class="form" role="form" method="POST" id="application-create-form" action="${new_application_popup}">
                <input type="hidden" name="candidateId" id="candidateId" value="${candidateId}">
                <div class="form-group"
                     id="positionDiv">
                  <label class="control-label col-lg-2 col-md-2 col-sm-2 text-right"
                         for="drop"><spring:message
                          code="candidate.english.label"/></label>

                  <div class="selectContainer col-lg-4 col-md-4 col-sm-4" id="drop">
                    <select class="input form-control" name="position.positionId" id="position">
                      <!--<option value=0 <c:if
                              test="${0 eq candidate.languageSkill}"> selected="selected" </c:if>>
                        <spring:message code="candidate.english.level.default"/></option>
                      <c:forEach begin="1" end="10" step="1" var="index">
                        <option value="${index}" <c:if
                                test="${index eq candidate.languageSkill}"> selected="selected" </c:if>>${index}</option>
                      </c:forEach>-->
                    </select>

                  </div>
                </div>
                <div class="form-group"
                     id="sourceDiv">
                  <label class="control-label col-lg-2 col-md-2 col-sm-2 text-right"
                         for="source"><spring:message
                          code="candidate.english.label"/></label>

                  <div class="selectContainer col-lg-4 col-md-4 col-sm-4" id="dropSource">
                    <select class="input form-control" name="source" id="source">
                      <option value=0  selected="selected">
                        <spring:message code="application.create.source.direct"/></option>
                      <option value=1>
                        <spring:message code="application.create.source.professionad"/></option>
                      <option value=2>
                        <spring:message code="application.create.source.professiondb"/></option>
                      <option value=3>
                        <spring:message code="application.create.source.fb"/></option>
                      <option value=4>
                        <spring:message code="application.create.source.linkedinad"/></option>
                      <option value=5>
                        <spring:message code="application.create.source.linkedindb"/></option>
                      <option value=6>
                        <spring:message code="application.create.source.suggest"/></option>
                      <option value=7>
                        <spring:message code="application.create.source.job"/></option>
                      <option value=8>
                        <spring:message code="application.create.source.professionalday"/></option>
                      <option value=9>
                        <spring:message code="application.create.source.schonerz"/></option>
                      <option value=10>
                        <spring:message code="application.create.source.dtk"/></option>
                    </select>
                  </div>
                </div>
                <div class="form-group"
                     id="descriptionDiv">
                  <spring:message code="candidate.description.field" var="i18ndescription"/>
                  <label class="control-label col-lg-2 col-md-2 col-sm-2 text-right"
                         for="description"><spring:message
                          code="candidate.description.label"/></label>

                  <div class="col-lg-10 col-md-10 col-sm-10">
                                <textarea rows="4" cols="4" class="input form-control" id="description"
                                          placeholder="${i18ndescription}">${candidate.description}</textarea>

                    <p class="showValue form-control-static">${candidate.description}</p>
                  </div>
                </div>
                <div class="modal-footer">
                  <a href="candidate-show.html" class="btn btn-danger">Vissza</a>
                  <button type="submit" class="btn btn-success">
                    <spring:message code="save.button"/>
                  </button>
                </div>
              </form>
            </div>

          </div>
        </div>
      </div>
    </div>
  </jsp:body>
</atsy:secure_page>