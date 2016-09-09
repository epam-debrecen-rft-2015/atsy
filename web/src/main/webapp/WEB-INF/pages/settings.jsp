<%
/*  Ez a jsp fájl korábban egy egy egyszerű oldalt tartalmazott, amiben 3 link volt.
 *  Minden link átirányított egy másik oldalra, aminek saját jsp-je volt. Mostmár ez az oldal
 *  tartalmazza az összes beállítást, egy fülekkel elválasztott táblázat-szerű formában.
 *  Ez úgy van megoldva, hogy egyfajta merge történik eközött a jsp és az egyes beállítási
 *  lehetőségeket nyújtó jsp-k között. A <jsp:include> tag behúzza, gyakorlatilag bemásolja
 *  egy megadott jsp tartalmát oda, ahol a tag van. De ahhoz, hogy ez működjön, a behúzott jsp
 *  nem lehet egy teljes értékű oldal. Minden jsp első néhány sora bizonyos beállításokat tartalmaz.
 *  Ezen kívül jsp: -al kezdődő tag-eket. Ezek a tag-ek nem alapértelmezettek, a secure-page.tag
 *  fájlban vannak megadva. Ezek nem duplikálódhatnak, mert hibás működéshez vezethez, pl egy
 *  táblázat duplikálva, egymásba ágyazva jelenik meg, vagy épp többszörösen jelenik meg a felső
 *  navigációs sáv (ahol a logó is van). Ezeket a behúzandó jsp-kből törölni kellett. Ezen kívül
 *  bizonyos címeket is törölni kellett, pl a Beállítások cím nem kell, hogy benne legyen a
 *  behúzandó jsp-kben, mert ebben már meg van adva.
 */
%>




<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="atsy" tagdir="/WEB-INF/tags" %>
<%@page contentType="text/html;charset=UTF-8" %>
<spring:url value="/secure/positions/manage" var="position"/>
<spring:url value="/secure/channels/manage" var="channel"/>
<spring:url value="/secure/password/manage" var="password"/>
<atsy:secure_page>

<%
/*  Az alábbi jsp_attribute tag a teljes tartalmával együtt át lett másolva a többi beállítási jsp-ből.
 *  Ez a szakasz felelős a szükséges javascript állományok behúzásával. Azért van az, hogy csak egy
 *  jsp:attribute tag van, mert a positions és a channels beállítási jsp-k eredetileg ugyanazt a
 *  javascript fájlt használták, a password -ös jsp pedig nem használt javascript-et. Ez utóbbi egy
 *  olyan dolog, amin majd változtatni kell.
 */
%>
<jsp:attribute name="pageJs">
  <c:url value="/resources/js/atsy-settings.js" var="urlValue"/><script src="${urlValue}"></script>
  <script type="text/javascript">
    window.messages['settings.channels.error.empty'] = '<spring:message code="settings.channels.error.empty"/>';
  </script>
</jsp:attribute>

<jsp:body>
    <h1>
      <spring:message code="settings.title"/>
    </h1>
    <hr />

    <div>
      <%
      /*  Az ul -tag adja a füles-táblázatot. A css class-ok amik itt használva vannak nem az
       * atsy.css -ben hanem bootstrap-es css-ekben vannak. Minden li -tag, vagyis list-element
       * egy fület fog eredményezni. A spring:message -tagben van megadva, hogy a fülnek mi legyen
       * a felirata. De azt, hogy a fülre kattintás (a kattintás event-el nem kell nekünk foglalkozni)
       * milyen műveletet eredményez, nem itt kell megadni, hanem majd az egyel lentebbi div-ben.
       */
      %>
      <ul role="tablist" class="nav nav-tabs">
        <li role="presentation">
           <a href="#positions" aria-controls="positions" role="tab" data-toggle="tab">
            <spring:message code="settings.positions.title"/>
           </a>
        </li>
        <li role="presentation">
           <a href="#channels" aria-controls="channels" role="tab" data-toggle="tab">
            <spring:message code="settings.channels.title"/>
           </a>
        </li>
        <li role="presentation">
           <a href="#password" aria-controls="password" role="tab" data-toggle="tab">
            <spring:message code="settings.password.title"/>
           </a>
        </li>
      </ul>

      <%
      /*  Ebben a szakaszban van megadva, hogy egy adott fülre kattintás milyen műveletet hoz magával.
       *  Az adott div id attribútuma az, amit fentebb a href és az aria-control -ban meg kell megadni
       *  ahhoz, hogy a fülre kattintás - művelet végrehajtás hozzárendelés megtörténjen.
       *  A jsp:include Az egyetlen művelet mindhárom esetben. A tényleges műveletek a behúzott
       *  jsp-kben vannak.
       */
      %>
      <div class="tab-content">
        <div role="tabpanel" id="positions" class="tab-pane">
          <jsp:include page="manage_positions.jsp"/>
        </div>
        <div role="tabpanel" id="channels" class="tab-pane">
          <jsp:include page="manage_channels.jsp"/>
        </div>
        <div role="tabpanel" id="password" class="tab-pane">
          <jsp:include page="password_change.jsp"/>
        </div>
      </div>
    </div>

  </jsp:body>
</atsy:secure_page>