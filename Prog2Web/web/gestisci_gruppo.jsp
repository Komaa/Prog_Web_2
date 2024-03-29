<%-- 
    Document   : gestisci_gruppo
    Created on : Jan 30, 2014, 2:16:23 PM
    Author     : HaoIlMito
--%>

<!-- filtro 0 = gruppo creato con successo
            1 = titolo cambiato con successo
            2 = inserisci un titolo del gruppo 
            3 = tipo del gruppo cambiato con successo 
            4 = utente invitato con successo 
            5 = nome gruppo già presente -->

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
                <h1>Gruppi!</h1>
                <div class="section-content clearfix">Qui puoi modificare le informazioni che riguardano il gruppo <c:out value="${gruppo.titolo}"/> </div>
            </div>          
        </div>       
    </div>

    <c:if test="${filtro == 0}"><div class="alert alert-success"><strong>Gruppo creato con successo</strong></div></c:if>
    <c:if test="${filtro == 1}"><div class="alert alert-success"><strong>Titolo cambiato con successo</strong></div></c:if>
    <c:if test="${filtro == 2}"><div class="alert alert-danger"><strong>Inserisci un titolo del gruppo</strong></div></c:if>
    <c:if test="${filtro == 3}"><div class="alert alert-success"><strong>Tipo del gruppo cambiato con successo </strong></div></c:if>
    <c:if test="${filtro == 4}"><div class="alert alert-success"><strong>Utente invitato con successo  </strong></div></c:if>
    <c:if test="${filtro == 5}"><div class="alert alert-danger"><strong>Nome gruppo già presente  </strong></div></c:if>
    <br><br>

    <form action="Controller?cmd=10" method="post">
        Nome gruppo: <input id="titolo_gruppo" type="text" name="titolo" value="<c:out value="${gruppo.titolo}"/>">
        <input type="hidden" name="cod_gruppo" value="<c:out value="${gruppo.id_gruppo}"/>"> </br>
        <button type="submit" class="btn btn-success">Cambia titolo</button>
    </form>
    
    <br>

    <form class="form-horizontal" name="input" action="Controller?cmd=11" method="post">
        <div class="control-group">
            <input type="hidden" name="cod_gruppo" value="<c:out value="${gruppo.id_gruppo}"/>">
            <input type="radio" name="tipo" value="0"  checked="checked">Pubblico 
            <input type="radio" name="tipo" value="1">Privato
        </div>
        </br><hr>
        <button class="btn btn-success" type="submit">Modifica tipo</button>
    </form>
    <!-- sul form modifica titolo / modifica tipo gruppo -> passa anche un hidden con l' id_gruppo e il campo titolo si chiamerà titolo e il campo tipo è tipo (10 cambio titolo) (11 cambia tipo)  se invito, invio anche l'id_utente, id_gruppo-->

    <br><h1>Persone da poter invitare</h1> <br>
    <table class="table table-striped">
        <tr><th>Nome utente</th> <th>codice</th> <th>Invita</th></tr>
                <c:forEach var="invita" items="${invitabili}">
            <tr> 
                <td><c:out value="${invita.username}"/></td> 
                <td><c:out value="${invita.cod}"/></td>
                <td> <a href="Controller?cmd=12&id_utente=<c:out value="${invita.cod}"/>&cod_gruppo=<c:out value="${gruppo.id_gruppo}"/>" class="btn btn-success" role="button">Invita</a> </td>
            </tr>
        </c:forEach>
    </table>




</div>
<script src=./bootstrap/js/bootstrap.min.js></script>
</body>
</html>

