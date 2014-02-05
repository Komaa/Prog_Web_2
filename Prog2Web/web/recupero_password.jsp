<%-- 
    Document   : recupero_password
    Created on : 23-gen-2014, 12.46.35
    Author     : pietro-alex
--%>

<!-- filtro = 0 nome utente/email non presenti --> 

<%@page import="Beans.Utente"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>

<!DOCTYPE html>

<html>  
    <head>  
        <meta charset=" + "utf-8><title>Welcome!</title>  
        <link href=  
              ./bootstrap/css/bootstrap.css  
              rel="stylesheet">  
        <link href=  
              ./bootstrap/css/bootstrap.min.css  
              rel="stylesheet">  
    </head>  
    <body class="body_login">  
        <c:if test="${filtro == 0}"><div class="alert alert-warning"><strong>Nome utente e/o email non presenti</strong></div></c:if>
        <div class="container">  
            <div class="row">  
                <div class="col-md-4 col-md-offset-7">  
                    <div class="panel panel-default">  
                        <div class="panel-heading">  
                            <span class="glyphicon glyphicon-lock"></span> Recupero password</div>  
                        <div class="panel-body">  
                            <form class="form-horizontal" method="post" action="Controller?cmd=23" role="form">  
                                <div class="form-group">  
                                    <label for="cambio" class="col-sm-3 control-label">  
                                        Recupero</label>  
                                    <div class="col-sm-9">  
                                        <input type="text" name="cambio" class="form-control" id="cambio" placeholder="Inserisci username/email" autofocus required>  
                                    </div>  
                                </div>  
                               
                                <div class="form-group last">  
                                    <div class="col-sm-offset-3 col-sm-9">  
                                        <button type="submit" class="btn btn-success btn-sm">  
                                            Invia email</button>  
                                        <button type="reset" class="btn btn-default btn-sm">  
                                            Reset</button>  
                                    </div>  
                                </div>  
                            </form>  
                        </div>  

                    </div>  
                </div>  
            </div>  


         

                </div>  
                <script src="./bootstrap/js/bootstrap.min.js"></script>
                </body>
                </html>
