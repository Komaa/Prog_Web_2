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

                    Qui puoi cambiare la tua password e il tuo avatar!

                </div>
            </div>
        </div>
        <div class="container span4 offset4">
            <h1>

                Cambio password

            </h1>
            <hr>

            <form class="form-horizontal" method="post" action="Controller?cmd=6" role="form">  
                <div class="form-group">  
                    <label for="username" class="col-sm-3 control-label">  
                        Password vecchia </label>  
                    <div class="col-sm-9">  
                        <input type="password" name="pass" class="form-control" id="username" placeholder="Password vecchia" autofocus required>  
                    </div>  
                </div>  
                <div class="form-group">  
                    <label for="Password" class="col-sm-3 control-label">  
                        Password nuova </label>  
                    <div class="col-sm-9">  
                        <input type="password" class="form-control" id="Password" name="pass1"  placeholder="Password nuova" required>  
                    </div>  
                </div>  
                <div class="form-group">  
                    <label for="Password" class="col-sm-3 control-label">  
                        Ripeti Password</label>  
                    <div class="col-sm-9">  
                        <input type="password" class="form-control" id="Password" name="pass2"  placeholder="Ripeti password" required>  
                    </div>  
                </div>  
                <div class="form-group">  
                </div>  
                <div class="form-group last">  
                    <div class="col-sm-offset-3 col-sm-9">  
                        <button type="submit" class="btn btn-warning btn-sm">  
                            Modifica</button>  
                        <button type="reset" class="btn btn-default btn-sm">  
                            Reset</button>  
                    </div>  
                </div>  
            </form>  



        </div>
        <div class="container span4 offset4">
            <h1>

                Cambio Avatar!

            </h1>
            <hr>

            <form class="form-horizontal" method="post" action="Controller?cmd=7" role="form">  
                <div class="form-group">  
                    <label for="image" class="col-sm-3 control-label">  
                        Avatar</label>  
                    <div class="col-sm-9">  
                        <input name="image" size="10485760" type="file"/>
                    </div>  
                </div> 

                <div class="form-group last">  
                    <div class="col-sm-offset-3 col-sm-9">  
                        <button type="submit" class="btn btn-warning btn-sm">  
                            Modifica</button>  
                        <button type="reset" class="btn btn-default btn-sm">  
                            Reset</button>  
                    </div>  
                </div>  
            </form>  
        </div>
    </body>
</html>


