<%-- 
    Document   : home
    Created on : Jan 29, 2014, 4:08:57 PM
    Author     : HaoIlMito
--%>
<!-- filtro 0 = invito rifiutato con successo 
            1 = invito accettato con successo
            -->

<%@page import="Beans.Utente"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
    <!DOCTYPE html>
    <html>
        <head>
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
                            

                            <c:if test="${user.tipo == 1}">
                            <li>
                                <a href="Controller?cmd=20">

                                     Moderazione

                                </a>
                            </li>
                            </c:if>
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

                        <img src="./img/${user.avatar}" style="max-height: 300px; max-width: 300px">Benvenuto <c:out value="${user.username}"/>  ultimo login: <c:out value="${user.data}"/> 

                    </h1>
                    <div class="section-content clearfix">

                        Qui puoi trovare informazioni sui gruppi che ti hanno invitato

                    </div>
                    <div class="section-content clearfix">

                        Clicca su Creazione gruppo o gruppi per creare/gestire un gruppo oppure quick display per vedere gli ultimi aggiornamenti

                    </div>
                </div>
            </div>
                        
            <c:if test="${filtro == 0}"><div class="alert alert-success"><strong>Invito rifiutato con successo</strong></div></c:if>
            <c:if test="${filtro == 1}"><div class="alert alert-success"><strong>Invito accettato con successo</strong></div></c:if>
            
            <div class="container span4 offset4">
                <h1>

                    Inviti ai gruppi

                </h1>
                <hr>
                <table class="table table-striped"> 
                    <tr><th>Accetta / Rifiuta</th> <th>Nome gruppo</th></tr>
                <c:forEach var="invito" items="${listainviti}">
                    <tr> 
                        <td>
                            <a href="Controller?cmd=18&user_id=<c:out value="${invita.cod}"/>&cod_gruppo=<c:out value="${invito.key}"/>" class="btn btn-success" role="button">Accetta</a>
                            <a href="Controller?cmd=17&user_id=<c:out value="${invita.cod}"/>&cod_gruppo=<c:out value="${invito.key}"/>" class="btn btn-danger" role="button">Rifiuta</a>
                        </td> 
                        <td><c:out value="${invito.value}"/></td>
                    </tr>
                </c:forEach>
                </table>
            </div>
                        
            
            <div class="container span4 offset4">
                <h1>

                    Quick display

                </h1>
                <hr>
                <table class="table table-striped"> 
                    <tr><th>Accetta / Rifiuta</th> <th>Nome gruppo</th><th>Entra nel forum!</th></tr>
                <c:forEach var="lista_gruppi" items="${listaaggruppi}">
                <tr> 
                    <td><c:out value="${lista_gruppi.key}"/></td>
                    <td><c:out value="${lista_gruppi.value}"/></td>
                    <td><a href="Controller?cmd=14&cod_gruppo=<c:out value="${lista_gruppi.key}"/>" class="btn btn-success" role="button">Entra!</a></td>               
                </tr>
                </c:forEach>
                </table>
        </div>
        </body>
    </html>


