<%-- 
    Document   : index
    Created on : 23-gen-2014, 12.46.35
    Author     : pietro-alex
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
                                        <button ahref="Controller?cmd=22" type="reset" class="btn btn-default btn-sm">  
                                            Recupero password</button>  
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
