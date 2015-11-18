<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="atsy" tagdir="/WEB-INF/tags" %>
<%@page contentType="text/html;charset=UTF-8" %>
<spring:url value="/secure/positions" var="positions"/>
<spring:url value="/secure/channels" var="channels"/>
<atsy:secure_page>
  <jsp:body>
    <div id="candidate_creation">
      <h1 class="page-header"><spring:message code="candidate.create.title"/></h1>
      <div id="candidate_data">
        <div class="row">
          <form role="form" method="POST" id="candidate-create-form">
            <div class="form-group col-lg-6 col-md-6 col-sm-6"
                 id="nameDiv">
              <spring:message code="candidate.name.field" var="i18nname"/>
              <label class="control-label" for="name"><spring:message
                        code="candidate.name.label"/></label>
              <input type="text" class="form-control" name="name" id="name"
                     placeholder="${i18nname}">
            </div>
              <div class="form-group col-lg-6 col-md-6 col-sm-6"
                   id="placeDiv">
                  <spring:message code="candidate.place.field" var="i18nplace"/>
                  <label class="control-label" for="place"><spring:message
                          code="candidate.place.label"/></label>
                  <input type="text" class="form-control" name="place" id="place"
                         placeholder="${i18nplace}">
              </div>
              <div class="form-group col-lg-6 col-md-6 col-sm-6"
                   id="emailDiv">
                  <spring:message code="candidate.email.field" var="i18nemail"/>
                  <label class="control-label" for="email"><spring:message
                          code="candidate.email.label"/></label>
                  <input type="text" class="form-control" name="email" id="email"
                         placeholder="${i18nemail}">
              </div>
              <div class="form-group col-lg-6 col-md-6 col-sm-6"
                   id="englishDiv">
                  <label class="control-label" for="drop"><spring:message
                          code="candidate.english.label"/></label>
                  <div class="selectContainer" id="drop">
                      <select class="form-control" name="color">
                          <option value="0"><spring:message code="candidate.english.level.default"/></option>
                          <option value="1">1</option>
                          <option value="2">2</option>
                          <option value="3">3</option>
                          <option value="4">4</option>
                          <option value="5">5</option>
                          <option value="6">6</option>
                          <option value="7">7</option>
                          <option value="8">8</option>
                          <option value="9">9</option>
                          <option value="10">10</option>
                      </select>
                  </div>
              </div>
              <div class="form-group col-lg-6 col-md-6 col-sm-6"
                   id="phoneDiv">
                  <spring:message code="candidate.phone.field" var="i18nphone"/>
                  <label class="control-label" for="phone"><spring:message
                          code="candidate.phone.label"/></label>
                  <input type="text" class="form-control" name="phone" id="phone"
                         placeholder="${i18nphone}">
              </div>
              <div class="form-group col-lg-12 col-md-12 col-sm-12"
                   id="descriptionDiv">
                  <spring:message code="candidate.description.field" var="i18ndescription"/>
                  <label class="control-label" for="description"><spring:message
                          code="candidate.description.label"/></label>
                  <input type="text" class="form-control text-area" name="description" id="description"
                         placeholder="${i18ndescription}">
              </div>
              <div class="text-right col-lg-12 col-md-12 col-sm-12">
                  <button type="submit" class="btn btn-danger" id="cancelButton">
                      <spring:message code="cancel.button"/>
                  </button>
                  <button type="submit" class="btn btn-success" id="saveButton">
                      <spring:message code="save.button"/>
                  </button>
              </div>
          </form>
        </div>
      </div>
    </div>
  </jsp:body>
</atsy:secure_page>