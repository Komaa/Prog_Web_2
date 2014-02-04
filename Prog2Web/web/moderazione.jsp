<%-- 
    Document   : moderazione
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
        <title>Moderazione</title>
        <meta charset=utf-8>
        <link href=./bootstrap/css/bootstrap.css rel="stylesheet">
        <link href=./css/demo_page.css rel="stylesheet">
        <link href=./css/demo_table.css rel="stylesheet">
        <link href=./css/jquery.dataTables.css rel="stylesheet">
        <script src="./js/jquery.js" type="text/javascript"></script>
        <script src="./js/jquery.dataTables.js" type="text/javascript"></script>
    </head> 
    <body>  
        <script>

            $(document).ready(function() {

                $("#moderatore").dataTable();

            });

        </script>
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
                <h1>Lista dei gruppi</h1>
                <div class="section-content clearfix">Qui puoi moderare e gestire i gruppi</div>
            </div>          
        </div>     
        <br><h1>Elenco gruppi</h1> <br>
        <table class="table table-striped" id="moderatore"> 
            <thead>
                <tr>
                    <th>Nome Gruppo</th>
                    <th>Numero Partecipanti</th> 
                    <th>Privato/Pubblico</th> 
                    <th>Nro post</th> 
                    <th>Chiudi</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="lista_gruppi" items="${listagruppimoderatore}">

                    <tr> 
                <form class="form-horizontal" name="input" action="Controller?cmd=21" method="post">
                    <td><a href="Controller?cmd=14&cod_gruppo=<c:out value="${lista_gruppi.id_gruppo}"/>&user_id=<c:out value="${user.cod}"/>"><c:out value="${lista_gruppi.titolo}"/></a></td>
                    <td><c:out value="${lista_gruppi.num_partecipanti}"/></td>
                    <c:choose>
                        <c:when test="${lista_gruppi.tipo == 0}"><td> Pubblico </td></c:when>
                        <c:otherwise><td> Privato </td> </c:otherwise>
                    </c:choose>
                    <td><c:out value="${lista_gruppi.num_post}"/></td>  
                    <td><button type="submit" class="btn btn-danger" >Chiudi il gruppo </button></td>
                    <input type="hidden" name="id_gruppo" value="<c:out value="${lista_gruppi.id_gruppo}"/>">
                </form>
            </tr>

        </c:forEach>
    </tbody>
</table>  


</div>
<script src=./bootstrap/js/bootstrap.min.js></script>
</body>
</html>

