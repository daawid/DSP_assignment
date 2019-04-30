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
                <p><strong><c:out value="${LOGIN_MSG}"/></p>
            </div>
        </div>
    </div>


    <div class="container">    
        <div id="loginbox" style="margin-top:50px;" class="mainbox col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2">                    
            <div class="panel panel-info" >
                <div class="panel-heading">


                    <h4><div class="panel-title">Reset your password</div></h4>
                </div>     


                <div style="padding-top:30px" class="panel-body" >
                    <div style="display:none" id="login-alert" class="alert alert-danger col-sm-12"></div>

                    <form id="loginform" class="form-horizontal" role="form" action="resetPassword" method="POST">

                        <c:if test="${!empty ERR_MSG}">
                            <div id="signupalert" style="" class="alert alert-danger">
                                <c:out value="${ERR_MSG}"/>
                            </div>
                        </c:if>

                        <div style="margin-bottom: 25px" class="input-group">
                            <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
                            <input id="login-username" type="text" class="form-control" name="username" placeholder="Email">    
                        </div>
                        <div style="margin-top:10px" class="form-group">
                            <!-- Button -->

                            <div class="col-sm-12 controls">
                                <button id="btn-login" type="submit" name="submit" class="btn btn-success">Reset password</button>
                            </div>

                    </form>     



                </div>                     
            </div>  
        </div>


    </div> 
</div>
</center>
</body>