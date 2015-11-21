<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="atsy" tagdir="/WEB-INF/tags" %>
<%@page contentType="text/html;charset=UTF-8" %>
<spring:url value="/secure/positions" var="positions"/>
<spring:url value="/secure/channels" var="channels"/>
<spring:url value="/secure/welcome" var="welcome"/>

<atsy:secure_page>
    <jsp:attribute name="pageJs">
        <script src="<c:url value="/resources/js/atsy-candidate-create.js" />"></script>
    </jsp:attribute>
  <jsp:body>
    <div id="candidate_creation">
      <h1 class="page-header"><spring:message code="candidate.create.title"/>
          <button class="btn btn-primary pull-right" id="enableModify"><spring:message code="candidate.modify.button"/></button>
      </h1>
      <div id="candidate_data">
        <div class="row">
          <form class="form" role="form" method="POST" id="candidate-create-form" action="candidate">
            <div class="form-group"
                 id="nameDiv">
              <input type="hidden" name="candidateId" id="candidateId">
              <spring:message code="candidate.name.field" var="i18nname"/>
              <label class="control-label col-lg-2 col-md-2 col-sm-2 text-right" for="name"><spring:message
                        code="candidate.name.label"/></label>
                <div class="col-lg-4 col-md-4 col-sm-4">
                    <input type="text" class="input form-control " name="name" id="name"
                           placeholder="${i18nname}">
                </div>

            </div>
              <div class="form-group"
                   id="placeDiv">
                  <spring:message code="candidate.place.field" var="i18nplace"/>
                  <label class="control-label col-lg-2 col-md-2 col-sm-2 text-right" for="referer"><spring:message
                          code="candidate.place.label"/></label>
                  <div class="col-lg-4 col-md-4 col-sm-4">
                      <input type="text" class="input form-control" name="referer" id="referer"
                             placeholder="${i18nplace}">
                  </div>

              </div>
              <div class="form-group"
                   id="emailDiv">
                  <spring:message code="candidate.email.field" var="i18nemail"/>
                  <label class="control-label col-lg-2 col-md-2 col-sm-2 text-right" for="email"><spring:message
                          code="candidate.email.label"/></label>
                  <div class="col-lg-4 col-md-4 col-sm-4">
                      <input type="text" class="input form-control" name="email" id="email"
                             placeholder="${i18nemail}">
                  </div>

              </div>
              <div class="form-group"
                   id="englishDiv">
                  <label class="control-label col-lg-2 col-md-2 col-sm-2 text-right" for="drop"><spring:message
                          code="candidate.english.label"/></label>
                  <div class="selectContainer col-lg-4 col-md-4 col-sm-4" id="drop">
                      <select class="input form-control" name="color" id="languageSkill">
                          <option value=0><spring:message code="candidate.english.level.default"/></option>
                          <option value=1 selected>1</option>
                          <option value=2>2</option>
                          <option value=3>3</option>
                          <option value=4>4</option>
                          <option value=5>5</option>
                          <option value=6>6</option>
                          <option value=7>7</option>
                          <option value=8>8</option>
                          <option value=9>9</option>
                          <option value=10>10</option>
                      </select>
                  </div>
              </div>
              <div class="form-group"
                   id="phoneDiv">
                  <spring:message code="candidate.phone.field" var="i18nphone"/>
                  <label class="control-label col-lg-2 col-md-2 col-sm-2 text-right" for="phone"><spring:message
                          code="candidate.phone.label"/></label>
                  <div class="col-lg-4 col-md-4 col-sm-4">
                      <input type="text" class="input form-control" name="phone" id="phone"
                             placeholder="${i18nphone}">
                  </div>

              </div>
              <div class="form-group"
                   id="descriptionDiv">
                  <div id="fix" class="col-md-6 col-sm-6 col-lg-6"></div>
                  <spring:message code="candidate.description.field" var="i18ndescription"/>
                  <label class="control-label col-lg-2 col-md-2 col-sm-2 text-right" for="description"><spring:message
                          code="candidate.description.label"/></label>
                  <div class="col-lg-10 col-md-10 col-sm-10">
                      <textarea rows="4" cols="4" class="input form-control" id="description" placeholder="${i18ndescription}"></textarea>
                  </div>

              </div>
              <div class="text-right col-lg-12 col-md-12 col-sm-12">
                  <button class="btn btn-danger" id="cancelButton"><spring:message code="cancel.button"/></button>
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