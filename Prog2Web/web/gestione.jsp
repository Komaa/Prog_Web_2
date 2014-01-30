<%-- 
    Document   : gestione
    Created on : Jan 29, 2014, 4:08:57 PM
    Author     : HaoIlMito
--%>

<%@page import="Beans.Utente"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
    <!DOCTYPE html>
    <html>
        <head>
            <meta charset="utf-8"></meta>
            <title>
                gestione
            </title>
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
                    <h1></h1>
                    <h1>

                        Benvenuto <c:out value="${user.username}"/>  

                    </h1>
                    <div class="section-content clearfix">

                        Qui puoi trovare informazioni sui gruppi che ti ha…

                    </div>
                    <div class="section-content clearfix">

                        Clicca su Creazione gruppo o gruppi per utilizzare…

                    </div>
                </div>
            </div>
            <div class="container span4 offset4">
                <h1>

                    Inviti ai gruppi

                </h1>
                <hr>

                <c:forEach var="invito" items="${listainviti}">
                     Key: <c:out value="${invito.key}"/>
                     Value: <c:out value="${invito.value}"/>
                </c:forEach>

            </div>
        </body>
    </html>


