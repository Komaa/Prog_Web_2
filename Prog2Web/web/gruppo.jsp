<%-- 
    Document   : gruppo
    Created on : Jan 30, 2014, 3:38:42 PM
    Author     : HaoIlMito
--%>
<%@page import="Beans.Utente"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="./bootstrap/css/bootstrap.css"></link>
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
                    <a class="navbar-brand" href="Controller?cmd=16">

                        Progetto Web

                    </a>
                </div>
                <nav class="collapse navbar-collapse bs-navbar-collapse" role="navigation">
                    <ul class="nav navbar-nav">
                        <li>
                            <a href="Controller?cmd=16">

                                Home

                            </a>
                        </li>
                        <li>
                            <a href="Controller?cmd=8">

                                Creazione gruppo

                            </a>
                        </li>
                        <li>
                            <a href="Controller?cmd=13">

                                Gruppi

                            </a>
                        </li>
                        <li>
                            <a href="Controller?cmd=5">

                                Gestisci account

                            </a>
                        </li>
                    </ul>
                    <ul class="nav navbar-nav navbar-right">
                        <li>
                            <a href="Controller?cmd=2">
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
                <h1>Forum del gruppo <c:out value="${gruppo.titolo}"/></h1>
                <div class="section-content clearfix">Qui puoi trovare i post degli altri utenti o scrivere il tuo! </div>
            </div>          
        </div>       
    </div>

    <br><br>

    <div class="row" style="padding-top: 50px">
        <div class="container .col-md-6 .col-md-offset-3">
            <div class="panel panel-primary">
                <div class="panel-heading">
                    <h3 class="panel-title"><img src="./img/${user.avatar}" style="max-height: 50px; max-width: 50px"> <c:out value="${user.username}"/> scrivi un post!</h3></div>
                <div class="panel-body">
  <!--SCRIVERE IL COMANDO QUI  -->                  <form class="form-horizontal" name="input" action="SCRIVERE COMANDO QUI" ENCTYPE="multipart/form-data" method="post">
                        <div class="control-group">
                            <div class="insert_comment">
                                <h4>Messaggio</h4><br>
                                <textarea name="messaggio" id="messaggio" cols="100" rows="5"></textarea>
                            </div>
                            <div class="control-group">
                                <br>
                                <input type="file" name=file></input><hr>

                            </div>
                            </br>
                            <div class="control-group">
                                <button class="btn btn-success" name="commenta">commenta</button>
                                <button type="reset" class="btn btn-danger">Cancella!</button>
                            </div>
                    </form>

                </div>
                </div>
            </div>
        </div>
                    <div class="container .col-md-6 .col-md-offset-3">   
                        <div class="panel-group">
                            <div class="panel panel-primary">
                                <div class="panel-heading">
                                    <h4>
                                        Qui sotto ci sono tutti i post del gruppo!
                                    </h4>
                                </div>
                                <div id="forum">
                                    <div class="panel-body">







                                    </div>
                                    <script src=./bootstrap/js/bootstrap.min.js></script>
                                    </body>
                                    </html>
