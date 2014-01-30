<%-- 
    Document   : home
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
            <title>

                Welcome_page

            </title>
            <meta charset="utf-8"></meta>
            <title>

                Welcome!

            </title>
            <link rel="stylesheet" href="./bootstrap/css/bootstrap.css"></link>
        </head>
        <body>
            <header class="navbar navbar-inverse navbar-fixed-top bs-docs-nav" role="banner">
                <div class="container">
                    <div class="navbar-header">
                        <button class="navbar-toggle" data-target=".bs-navbar-collapse" data-toggle="collapse" type="button">
                            <span class="sr-only">

                                Toggle navigation

                            </span>
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


