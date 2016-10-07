<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="atsy" tagdir="/WEB-INF/tags" %>
<%@page contentType="text/html;charset=UTF-8" %>
<spring:url value="/secure/channels" var="channels"/>
<div id="channel_settings">
    <div id="channels_section">
            <div class="row">
                <div class="col-lg-6 col-md-6 col-sm-6">
                    <div class="globalMessage alert alert-danger" role="alert"
                         style="display: none" id="errorMessageForDeleting">
                        <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
                        <span class="error-message"></span>
                    </div>
                    <table data-toggle="table" id="channels" data-url="${channels}" data-height="299"
                           data-sort-name="name"
                           data-sort-order="desc"
                           data-escape="true">
                        <thead>
                        <tr>
                            <th data-field="name" data-align="left" data-sortable="true"><spring:message
                                    code="settings.channels.table.name.title"/></th>
                            <th data-field="channelId" data-align="left"
                                data-formatter="actionFormatter"
                                data-events="channelsEvents"><spring:message
                                    code="settings.channels.table.action.title"/>
                            </th>
                        </tr>
                        </thead>
                    </table>
                </div>
                <div class="col-lg-6 col-md-6 col-sm-6">
                    <fieldset>
                        <legend><spring:message code="settings.channels.form.title"/></legend>
                        <form role="form" method="POST" id="channel-form" action="./channels">

                            <div class="globalMessage alert alert-danger" role="alert"
                                 style="display: none" id="errorMessageForCreating">
                                <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
                                <span class="error-message"></span>
                            </div>

                            <div class="form-group">
                                <input type="hidden" name="id" id="channelId">
                                <label for="channel_name"><spring:message
                                        code="settings.channels.channel_name"/></label>
                                <input type="text" class="name form-control" name="name" id="channel_name"/>
                            </div>
                            <button type="submit" class="btn btn-success"><spring:message
                                    code="settings.channels.save"/></button>
                            <button type="reset" class="btn btn-danger"><spring:message
                                    code="settings.channels.cancel"/></button>
                        </form>
                    </fieldset>
                </div>
            </div>
    </div>
</div>