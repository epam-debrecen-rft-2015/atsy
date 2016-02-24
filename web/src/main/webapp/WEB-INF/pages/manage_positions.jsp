<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="atsy" tagdir="/WEB-INF/tags" %>
<%@page contentType="text/html;charset=UTF-8" %>
<spring:url value="/secure/positions" var="positions"/>
<atsy:secure_page>
 <jsp:attribute name="pageJs">
     <script src="<c:url value="/resources/js/atsy-settings.js" />"></script>
     <script type="text/javascript">
         window.messages['settings.positions.error.empty'] = '<spring:message code="settings.positions.error.empty"/>';
     </script>
    </jsp:attribute>
    <jsp:body>
        <div id="settings">
            <h1 class="page-header"><spring:message code="settings.title"/></h1>
            <div id="positions_section">
                <h3><spring:message code="settings.positions.title"/></h3>
                <div>
                    <div class="row">
                        <div class="col-lg-6 col-md-6 col-sm-6">
                            <table data-toggle="table" id="positions" data-url="${positions}" data-height="299"
                                   data-sort-name="name"
                                   data-sort-order="desc">
                                <thead>
                                <tr>
                                    <th data-field="name" data-align="left" data-sortable="true"><spring:message
                                            code="settings.positions.table.name.title"/></th>
                                    <th data-field="positionId" data-align="left"
                                        data-formatter="actionFormatter"
                                        data-events="positionsEvents"><spring:message
                                            code="settings.positions.table.action.title"/>
                                    </th>
                                </tr>
                                </thead>
                            </table>
                            <div class="button-panel clearfix add">
                                <a href="javasript:void(0)" class="btn btn-primary btn-add"><spring:message
                                        code="settings.positions.new"/></a>
                            </div>
                        </div>
                        <div class="col-lg-6 col-md-6 col-sm-6">
                            <fieldset>
                                <legend><spring:message code="settings.positions.form.title"/></legend>
                                <form role="form" method="POST" id="position-form" action=".">

                                    <div class="globalMessage alert alert-danger" role="alert"
                                         style="display: none">
                                        <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
                                        <span class="error-message"></span>
                                    </div>

                                    <div class="form-group">
                                        <input type="hidden" name="positionId" id="positionId">
                                        <label for="position_name"><spring:message
                                                code="settings.positions.position_name"/></label>
                                        <input type="text" class="name form-control" name="name" id="position_name">
                                    </div>
                                    <button type="submit" class="btn btn-success"><spring:message
                                            code="settings.positions.save"/></button>
                                    <button type="reset" class="btn btn-danger"><spring:message
                                            code="settings.positions.cancel"/></button>
                                </form>
                            </fieldset>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </jsp:body>
</atsy:secure_page>