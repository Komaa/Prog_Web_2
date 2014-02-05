<%-- 
    Document   : gruppo
    Created on : Jan 30, 2014, 3:38:42 PM
    Author     : HaoIlMito
--%>
<!-- filtro = 0 inserisci un commento
              1 file già presente nel gruppo
              2 hai inserito il commento con successo -->

<%@page import="Beans.Utente"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jstl/xml" %>
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
                            <a href="Controller?cmd=3">
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
    <c:if test="${filtro == 0}"><div class="alert alert-danger"><strong>Inserisci un commento!</strong></div></c:if>
    <c:if test="${filtro == 1}"><div class="alert alert-danger"><strong>Il file è già presente nel gruppo</strong></div></c:if>
    <c:if test="${filtro == 2}"><div class="alert alert-success"><strong>Hai inserito il commento con successo</strong></div></c:if>
    <br><br>
    <c:choose> <c:when test="${gruppo.aperto == 1  || user.cod_utente == 0}"> <div class="alert alert-warning"><strong>Il gruppo è chiuso, nessuno ci può scrivere</strong></div> </c:when>
        <c:otherwise>
        <div class="row" style="padding-top: 50px">
        <div class="container .col-md-6 .col-md-offset-3">
            <div class="panel panel-primary">
                <div class="panel-heading">
                    <h3 class="panel-title"><img src="./img/${user.avatar}" style="max-height: 50px; max-width: 50px"> <c:out value="${user.username}"/> scrivi un post!</h3></div>
                <div class="panel-body">
                    <form class="form-horizontal" name="input" action="Controller?cmd=15" ENCTYPE="multipart/form-data" method="post">
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
                            <input type="hidden" name="user_id" value="<c:out value="${user.cod}"/>"> 
                            <input type="hidden" name="cod_gruppo" value="<c:out value="${gruppo.id_gruppo}"/>"> 
                            <div class="control-group">
                                <button class="btn btn-success" name="commenta">commenta</button>
                                <button type="reset" class="btn btn-danger">Cancella!</button>
                            </div>
                    </form>

                </div>
            </div>
        </div>
    </div>
    </c:otherwise>
    </c:choose>
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
                        <c:forEach var="comment" items="${commenti}">
                        <div class="comments">
                            
                            <div class="panel panel-info">
                                <div class="panel-heading">
                                    <h3 class="panel-title"><img src="./img/${comment.utente.avatar}" style="max-height: 100px; max-width: 100px"> Autore: <c:out value="${comment.utente.username}"/>  scritto in data: <c:out value="${comment.data}"/>
                                </div>
                                <div class="panel-body" style="min-height:100px">

                                    <h4>
                                       <c:out value="${comment.text}" escapeXml="false"/>

                                    </h4>
                                </div>
                                <div class="panel-footer">
                                    <c:choose>
                                        <c:when test="${comment.allegato == 'noallegato'}">Allegato: no allegato</c:when>
                                        <c:otherwise>Allegato: <a href="groupsfolder/<c:out value="${gruppo.titolo}"/>/<c:out value="${comment.allegato}"/>" target="_Blank"><c:out value="${comment.allegato}"/></a></c:otherwise>
                                    </c:choose>
                                    
                                    <br><br>

                                    <br><br>

                                </div>                                
                            </div>   




                        </div>
                        </c:forEach>
                    </div>

                </div>
                <script src=./bootstrap/js/bootstrap.min.js></script>
                </body>
                </html>
