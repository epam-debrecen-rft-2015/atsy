<%@tag description="Overall Page template" pageEncoding="UTF-8"%>
<%@taglib prefix="atsy" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<spring:url value="/resources/img/epam-logo-navbar.png" var="logo"/>
<spring:url value="/login" var="login"/>
<spring:url value="/secure/settings" var="settings"/>
<atsy:page>
    <jsp:attribute name="header">
     <nav class="navbar navbar-default">
           <div class="container-fluid">
             <div class="navbar-header">
               <a class="navbar-brand" href="#">
                 <img alt="Brand" src="${logo}" class="img-rounded">
               </a>
             </div>
              <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                   <ul class="nav navbar-nav">
                     <li><a href="#"><span class="glyphicon glyphicon-home" aria-hidden="true"/></a></li>
                     <li><a href="${settings}"><span class="glyphicon glyphicon-cog" aria-hidden="true"/></a></li>
                   </ul>
                   <ul class="nav navbar-nav navbar-right">
                     <li><a href="${login}"><span class="glyphicon glyphicon-log-out" aria-hidden="true"/></a></li>
                   </ul>
              </div>


           </div>
         </nav>
    </jsp:attribute>
    <jsp:body>
       <jsp:doBody/>
    </jsp:body>
</atsy:page>