<%-- 
    Document   : gruppi
    Created on : Jan 30, 2014, 1:05:41 AM
    Author     : HaoIlMito
--%>

<%@page import="Beans.Utente"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>

<!DOCTYPE html>
<html>
    <head>
        <title>Gruppi</title>
        <meta charset=utf-8>
        <link href=./bootstrap/css/bootstrap.css rel="stylesheet">
    </head> 
<body>    
            <header class="navbar navbar-inverse navbar-fixed-top bs-docs-nav" role="banner">
                <div class="container">
                    <div class="navbar-header">
                        <button class="navbar-toggle" data-target=".bs-navbar-collapse" data-toggle="collapse" type="button">
                            <span class="sr-only">Toggle navigation</span>
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                        </button>
                        <a class="navbar-brand" href="welcome_page">

                            Progetto Web

                        </a>
                    </div>
                    <nav class="collapse navbar-collapse bs-navbar-collapse" role="navigation">
                        <ul class="nav navbar-nav">
                            <li>
                                <a href="home">

                                     Home

                                </a>
                            </li>
                            <li>
                                <a href="Creazione_gruppo">

                                     Creazione gruppo

                                </a>
                            </li>
                            <li>
                                <a href="Gruppi">

                                     Gruppi

                                </a>
                            </li>
                        </ul>
                        <ul class="nav navbar-nav navbar-right">
                            <li>
                                <a href="Logout">
                                    <button class="btn btn-sm btn-danger">

                                        Logout

                                    </button>
                                </a>
                            </li>
                        </ul>
                    </nav>
                </div>
            </header>
            <br></br>
            <div class="section-background">
                <div class="section-title">
                    <h1>Crea gruppo!</h1>
                    <div class="section-content clearfix">Qui puoi creare il tuo gruppo personale, basta inserire il nome nel riquadro sottostante</div>
                </div>          
            </div>       
</div>
<div class="row" style="padding-top: 50px">

    <div class="container .col-md-6 .col-md-offset-3">

        <div class="panel panel-primary">

            <div class="panel-heading">

                <h3 class="panel-title">Crea Gruppo</h3>
            </div>
<div class="panel-body">

    <form class="form-horizontal" name="input" action="crea_gruppo" method="post">

        <div class="control-group">

            <label class="control-label" for="titolo_gruppo">Nome gruppo</label>

            <input type="text" name="titolo_gruppo">

            </br>

            <div class="control-group">
                <label class="control-label" for="amministratore_gruppo: ">Amministratore</label><c:out value="${user.username}"/>  
                <input id="amministratore_gruppo" type="hidden" name="amministratore" value="${user.username}">
                <input id="action" type="hidden" name="action" value="1">
            </div>
               </br><hr>
            <button class="btn btn-success" type="submit" name="crea!" value="">crea!</button>
    </form>

        </div>
    </div>
        </div>
    </div>
</div>
<script src=./bootstrap/js/bootstrap.min.js></script>
</body>
</html>

