<%-- 
    Document   : gestisci_gruppo
    Created on : Jan 30, 2014, 2:16:23 PM
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
                    <h1>Gruppi!</h1>
                    <div class="section-content clearfix">Qui puoi modificare </div>
                </div>          
            </div>       
</div>
                <form action="Controller?cmd=10" method="post">
                    Nome gruppo: <input id="titolo_gruppo" type="text" name="titolo" value="<c:out value="${gruppo.titolo}"/>">
                    <input type="hidden" name="cod_gruppo" value="<c:out value="${gruppo.id_gruppo}"/>"> </br>
                    <button type="submit" class="btn btn-success">Cambia titolo</button>
                </form>
                
                    
                 <form action="Controller?cmd=10" method="post">
                    Nome gruppo: <input id="titolo_gruppo" type="text" name="titolo" value="<c:out value="${gruppo.titolo}"/>">
                    <input type="hidden" name="cod_gruppo" value="<c:out value="${gruppo.id_gruppo}"/>"> </br>
                    <button type="submit" class="btn btn-success">Cambia titolo</button>
                </form>
                </br>
                
                <form action="GeneraPdf" method="post"><button type="submit" class="btn btn-success">Genera pdf</button></form>;
                
                </br>


                <!-- sul form modifica titolo / modifica tipo gruppo -> passa anche un hidden con l' id_gruppo e il campo titolo si chiamerà titolo e il campo tipo è tipo (10 cambio titolo) (11 cambia tipo)  se invito, invio anche l'id_utente, id_gruppo-->
                
                <table class="table table-striped"> 

                    <tr><th>Id Gruppo</th> <th>Nome gruppo</th> <th>Forum</th> <th>Amministra</th></tr>
  
                <c:forEach var="lista_gruppi" items="${listagruppi}">
                    <tr> 
                        <td><c:out value="${lista_gruppi.id_gruppo}"/></td>
                        <td><c:out value="${lista_gruppi.titolo}"/></td>
                        <td><a href="Controller?cmd=14&cod_gruppo=<c:out value="${lista_gruppi.id_gruppo}"/>" class="btn btn-success" role="button">Entra!</a></td>
                        <td><a href="Controller?cmd=19&cod_gruppo=<c:out value="${lista_gruppi.id_gruppo}"/>" class="btn btn-success" role="button">Amministra</a></td>
                    </tr>
                </c:forEach>
                </table>  
                <br><h1>Pubblici</h1> <br>
                <table class="table table-striped">
                <tr><th>Nome utente</th> <th>codice</th> <th>Invita</th></tr>
                <c:forEach var="invita" items="${invitabili}">
                    <tr> 
                        <td> Key: <c:out value="${invita.username}"/></td> 
                        <td> Value: <c:out value="${invita.cod}"/></td>
                    </tr>
                </c:forEach>
                </table>
                



</div>
<script src=./bootstrap/js/bootstrap.min.js></script>
</body>
</html>

