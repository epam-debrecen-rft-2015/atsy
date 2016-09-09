<%
/*  Ez a jsp nem egy teljes értékű oldal, hanem a settings oldal egy részét képezi.
 *  A 3 behúzott beállítási jsp közül ez az egyetlen, ami jelenleg nem működik az előírásoknak
 *  megfelelően. A hiba nem csak ebben a jsp-ben keresendő. A lényegi hiba a controllerben van.
 *  Ehhez a jsp-hez a PasswordChangeController tartozik, ami nem rest-kontroller. Épp itt a hiba.
 *  A másik két jsp egy-egy rest-kontroller segítségével végzi el a munkát. Éppen ezért nincs
 *  átirányítás és hasonló dolgok. Már elkezdtem egy rest-kontroller írását. PasswordChangeController
 *  a neve. A nem rest-kontroller pedig átneveztem ugye a név ütközés miatt, az már úgy végződik,
 *  hogy Backup. Elviel már működik is, de úgy néz ki, hogy ez a jsp még nem készült fel arra, hogy
 *  egy rest-es választ kezeljen le. Például ez a jsp nem használ javascirpt kódot, nincs ajax hívás.
 *  A másik kettő esetében viszont van. Ezeket a különbségeket kell áthidalni. Az ajaxos rész esetében
 *  érdemes lesz Kóós Danitól segítséget kérni.
 */
%>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="atsy" tagdir="/WEB-INF/tags" %>
<%@page contentType="text/html;charset=UTF-8" %>
<div id="password_change">
    <div id="password_section">
        <form role="form" method="POST" id="pw-form" action="./password">
            <c:if test="${not empty validationErrorKey}">
                <div id="globalMessage" class="alert alert-danger" role="alert">
                     <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="false"></span>
                     <spring:message code="${validationErrorKey}"/>
                </div>
            </c:if>
            <c:if test="${not empty validationSuccessKey}">
                 <div id="globalMessage" class="alert alert-success" role="alert">
                     <span class="glyphicon glyphicon-ok" aria-hidden="false"></span>
                     <spring:message code="${validationSuccessKey}"/>
                 </div>
            </c:if>
            <div class="form-group">
                <label for="new-pw"><spring:message code="settings.password.new"/></label>
                <input type="password" class="form-control" name="newPassword" id="new-pw">
            </div>
            <div class="form-group">
                <label for="new-pw-two"><spring:message code="settings.password.new.two"/></label>
                <input type="password" class="form-control" name="newPasswordConfirm" id="new-pw-two">
            </div>
            <div class="form-group">
                <label for="old-pw"><spring:message code="settings.password.old"/></label>
                <input type="password" class="form-control" name="oldPassword" id="old-pw">
            </div>
            <button type="submit" class="btn btn-success">
                <spring:message code="save.button"/>
            </button>
        </form>
    </div>
</div>