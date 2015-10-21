<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="atsy" tagdir="/WEB-INF/tags" %>
<%@page contentType="text/html;charset=UTF-8"%>

<atsy:page>
<jsp:body>
    <div class="container">
        <div class="jumbotron">
            <div class="row">
                <div class="col-md-6" id="trick">
                    <img src="logo.png" class="img-rounded">
                </div>
                <div class="col-md-6">
                    <form role="form">
                        <div class="alert alert-danger" role="alert">
                            <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
                            A megadott adatok nem megfelelőek!
                        </div>
                        <div class="form-group has-error">
                            <input type="text" class="form-control" id="username" placeholder="Felhasználónév">
                        </div>
                        <div class="form-group has-error">
                            <input type="password" class="form-control" id="password" placeholder="Jelszó">
                        </div>
                        <button type="submit" class="btn btn-success">Belépés</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</jsp:body>
</atsy:page>
