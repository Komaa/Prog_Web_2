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
                <h1>Lista dei gruppi</h1>
                <div class="section-content clearfix">Qui puoi vedere la lista dei gruppi a cui sei iscritto o sei l'amministratore</div>
            </div>          
        </div>     
        <br><h1>Privati/amministratore</h1> <br>
        <table class="table table-striped"> 

            <tr><th>Id Gruppo</th> <th>Nome gruppo</th> <th>Forum</th> <th>Amministra</th></tr>

            <c:forEach var="lista_gruppi" items="${listagruppi}">
                <tr> 
                    <td><c:out value="${lista_gruppi.id_gruppo}"/></td>
                    <td><c:out value="${lista_gruppi.titolo}"/></td>
                    <td><a href="Controller?cmd=14&cod_gruppo=<c:out value="${lista_gruppi.id_gruppo}"/>&user_id=<c:out value="${user.cod}"/>" class="btn btn-success" role="button">Entra!</a></td>
                    <c:choose>
                        <c:when test="${user.cod == lista_gruppi.id_amministratore}"><td><a href="Controller?cmd=19&cod_gruppo=<c:out value="${lista_gruppi.id_gruppo}"/>" class="btn btn-success" role="button">Amministra</a></td></c:when>
                    </c:choose>

                </tr>
            </c:forEach>
        </table>  
        <br><h1>Pubblici</h1> <br>
        <table class="table table-striped">
            <tr><th>Id Gruppo</th> <th>Nome gruppo</th> <th>Forum</th> <th>Amministra</th></tr>
                    <c:forEach var="lista_gruppi" items="${listagruppipubblici}">
                <tr> 
                    <td><c:out value="${lista_gruppi.key}"/></td>
                    <td><c:out value="${lista_gruppi.value}"/></td>
                    <td><a href="Controller?cmd=14&cod_gruppo=<c:out value="${lista_gruppi.key}"/>" class="btn btn-success" role="button">Entra!</a></td>               
                </tr>
            </c:forEach>
        </table>


    </div>
    <script src=./bootstrap/js/bootstrap.min.js></script>
</body>
</html>

