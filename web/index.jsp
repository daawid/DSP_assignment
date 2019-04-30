<%-- 
    Document   : index
    Created on : 16-Nov-2017
    Author     : DAWID
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="title" scope="request" value="Home"/>
<%@include file="/WEB-INF/header.jspf"%>

<center>
    <div class="panel panel-info" >
        <div class="panel-heading">
            <h3>Welcome to <strong>ABC travel</strong></h3>
            <div class="warning">
            </div>
        </div>
    </div>


    <div class="container">    
        <div id="loginbox" style="margin-top:50px;" class="mainbox col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2">                    
            <div class="panel panel-info" >
                <div class="panel-heading">


                    <h4><div class="panel-title">Sign in</div></h4>
                </div>     
                <c:if test="${!empty SUCCESS_MSG}">
                    <div id="signupalert" style="" class="alert alert-success">
                        <c:out value="${SUCCESS_MSG}"/>
                    </div>
                </c:if>
                
                <c:if test="${!empty ERR_MSG}">
                    <div id="signupalert" style="" class="alert alert-danger">
                        <c:out value="${ERR_MSG}"/>
                    </div>
                </c:if>
                
                <c:out value="${LOGIN_MSG}"/>
                <div style="padding-top:30px" class="panel-body" >
                    <div style="display:none" id="login-alert" class="alert alert-danger col-sm-12"></div>

                    <form id="loginform" class="form-horizontal" role="form" action="login" method="POST">

                        <div style="margin-bottom: 25px" class="input-group">
                            <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
                            <input id="login-username" type="text" class="form-control" name="username" placeholder="Email">    
                        </div>

                        <div style="margin-bottom: 25px" class="input-group">
                            <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
                            <input id="login-password" type="password" class="form-control" name="password" placeholder="Password">
                        </div>


                        <div style="margin-top:10px" class="form-group">
                            <!-- Button -->

                            <div class="col-sm-12 controls">
                                <button id="btn-login" type="submit" name="submit" class="btn btn-success">Login</button>
                            </div>
                        </div>


                        <div class="form-group">
                            <div class="col-md-12 control">
                                <div style="border-top: 1px solid#888; padding-top:15px; font-size:85%" >
                                    Not a member? 
                                    <a href="register.jsp">Register</a>
                                    <br>
                                    <a href="resetPassword.jsp">Forgotten password</a>
                                </div>

                            </div>
                        </div>    
                    </form>     



                </div>                     
            </div>  
        </div>
    </div> 
</center>
</body>
