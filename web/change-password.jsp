<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="title" scope="request" value="Change password"/>
<%@include file="/WEB-INF/header.jspf"%>

<center>
    <h4><c:out value="${DASH_MSG}"/></h4>
    <div class="container">    
        <div id="loginbox" style="margin-top:50px;" class="mainbox col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2">                    
            <div class="panel panel-info" >
                <div class="panel-heading">

                    <div style="padding-top:30px" class="panel-body" >
                        <div style="display:none" id="login-alert" class="alert alert-danger col-sm-12"></div>

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
                    <c:if test="${!empty WARNING_MSG}">
                        <div id="signupalert" style="" class="alert alert-warning">
                            <c:out value="${WARNING_MSG}"/>
                        </div>
                    </c:if>   

                    <form id="loginform" class="form-horizontal" role="form" action="changePassword" method="POST">
                        <div style="margin-bottom: 25px" class="input-group">
                            <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
                            <label for="currentPassword" class="sr-only">Current password</label>
                            <input id="login-password" type="password" class="form-control" name="currentPassword" placeholder="Current password">    
                        </div>

                        <div style="margin-bottom: 25px" class="input-group">
                            <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
                            <label for="newPassword" class="sr-only">Current password</label>
                            <input id="login-password" type="password" class="form-control" name="newPassword" placeholder="New password">
                        </div>

                        <div style="margin-bottom: 25px" class="input-group">
                            <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
                            <label for="confirmNewPassword" class="sr-only">Current password</label>
                            <input id="login-password" type="password" class="form-control" name="confirmNewPassword" placeholder="Confirm new password">
                        </div>



                        <div style="margin-top:10px" class="form-group">
                            <!-- Button -->

                            <div class="col-sm-12 controls">
                                <button id="btn-login" type="submit" name="submit" class="btn btn-primary">Change password</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>

    </div> <!-- /container -->
</center>
</body>
</html>
