<%-- 
    Document   : index
    Created on : 23-gen-2014, 12.46.35
    Author     : pietro-alex
--%>
<!--se filtro = 0 password/username non corretta | 
                1 deve confermare email la email prima 
                2 sei sloggato correttamente  
                3 registrato con successo
                4 password registrazione non coincidono
                5 inserire email valida
                6 inserire file di jpg o png
                  -->
                  
                  
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="Beans.Utente"%>
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
        
        <c:if test="${filtro == 0}"><div class="alert alert-danger"><strong>Hai sbagliato ad inserire il nome utente e/o la password</strong></div></c:if>
        <c:if test="${filtro == 1}"><div class="alert alert-danger"><strong>Devi confermare la email prima di loggare</strong></div></c:if>
        <c:if test="${filtro == 2}"><div class="alert alert-success"><strong>Sei sloggato correttamente</strong></div></c:if>
        <c:if test="${filtro == 3}"><div class="alert alert-success"><strong>Ti sei registrato con successo, controlla la tua email per verifare il tuo account!</strong></div></c:if>
        <c:if test="${filtro == 4}"><div class="alert alert-warning"><strong>Le password non coincidono!</strong></div></c:if>
        <c:if test="${filtro == 5}"><div class="alert alert-success"><strong>Inserisci una email valida!</strong></div></c:if>
        <c:if test="${filtro == 6}"><div class="alert alert-success"><strong>Inserisci un file jpg oppure png!</strong></div></c:if>
        <c:if test="${filtro == 7}"><dvi class="alert alert-success"><strong>Confermato con successso la email</strong></div></c:if>
        <c:if test="${filtro == 8}"><dvi class="alert alert-warning"><strong>Una mail con la password è stata inviata alla sua email</strong></div></c:if> 
            
       <div class="container">  
            <div class="row">  
                <div class="col-md-4 col-md-offset-7">  
                    <div class="panel panel-default">  
                        <div class="panel-heading">  
                            <span class="glyphicon glyphicon-lock"></span> Login</div>  
                        <div class="panel-body">  
                            <form class="form-horizontal" method="post" action="Controller?cmd=1" role="form">  
                                <div class="form-group">  
                                    <label for="username" class="col-sm-3 control-label">  
                                        Username</label>  
                                    <div class="col-sm-9">  
                                        <input type="username" name="username" class="form-control" id="username" placeholder="Username" autofocus required>  
                                    </div>  
                                </div>  
                                <div class="form-group">  
                                    <label for="Password" class="col-sm-3 control-label">  
                                        Password</label>  
                                    <div class="col-sm-9">  
                                        <input type="password" class="form-control" id="Password" name="password"  placeholder="Password" required>  
                                    </div>  
                                </div>  
                                <div class="form-group">  
                                </div>  
                                <div class="form-group last">  
                                    <div class="col-sm-offset-3 col-sm-9">  
                                        <button type="submit" class="btn btn-success btn-sm">  
                                            Sign in</button>  
                                        <a href="Controller?cmd=22">Recupera password</a> 
                                         
                                    </div>  
                                </div>  
                            </form>  
                        </div>  

                    </div>  
                </div>  
            </div>  


            <div class="container">  
                <div class="row">  
                    <div class="col-md-4 col-md-offset-7">  
                        <div class="panel panel-default">  
                            <div class="panel-heading">  
                                <span class="glyphicon glyphicon-lock"></span>Registrazione</div>  
                            <div class="panel-body">  
                                <form class="form-horizontal" method="post" action="Controller?cmd=4" role="form" enctype="multipart/form-data">  
                                    <div class="form-group">  
                                        <label for="username" class="col-sm-3 control-label">  
                                            Username</label>  
                                        <div class="col-sm-9">  
                                            <input type="username" name="username" class="form-control" id="username" placeholder="Username" autofocus required>  
                                        </div>  
                                    </div>  
                                    <div class="form-group">  
                                        <label for="Password" class="col-sm-3 control-label">  
                                            Password</label>  
                                        <div class="col-sm-9">  
                                            <input type="password" class="form-control" id="Password" name="password"  placeholder="Password" required>  
                                        </div>  
                                    </div>  
                                    <div class="form-group">  
                                        <label for="Password" class="col-sm-3 control-label">  
                                            Ripeti Password</label>  
                                        <div class="col-sm-9">  
                                            <input type="password" class="form-control" id="Password1" name="password1"  placeholder="Password1" required>  
                                        </div> 
                                    </div>  
                                    <div class="form-group">  
                                        <label for="Password" class="col-sm-3 control-label">  
                                            email</label>  
                                        <div class="col-sm-9">  
                                            <input type="email" class="form-control" id="email" name="email"  placeholder="email" required>  
                                        </div>  
                                    </div> 
                                    <div class="form-group">  
                                        <label for="Password" class="col-sm-3 control-label">  
                                            Avatar</label>  
                                        <div class="col-sm-9">  
                                            <input name="image" size="10485760" type="file"/>
                                        </div>  
                                    </div> 

                                    <div class="form-group">  
                                    </div>  
                                    <div class="form-group last">  
                                        <div class="col-sm-offset-3 col-sm-9">  
                                            <button type="submit" class="btn btn-success btn-sm">  
                                                Sign in</button>  
                                            <button type="reset" class="btn btn-default btn-sm">  
                                                Reset</button>  
                                        </div>  
                                    </div>  
                                </form>  
                            </div>  

                        </div>  
                    </div>  
                </div>  
                <script src="./bootstrap/js/bootstrap.min.js"></script>
                </body>
                </html>
