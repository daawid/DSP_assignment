<%-- 
    Document   : error
    Created on : 15-Nov-2017
    Author     : DAWID
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="title" scope="request" value="Error"/>
<%@include file="/WEB-INF/header.jspf"%>

<center>
    <div class="container">    
        <div id="loginbox" style="margin-top:50px;" class="mainbox col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2">                    
            <div class="panel panel-info" >
                <div class="panel-heading">


        <h2><div class="panel-title">Error</div></h2>
                </div>  
                <div style="padding-top:30px" class="panel-body" >
                    <div style="display:none" id="login-alert" class="alert alert-danger col-sm-12"></div>

                    <form id="loginform" class="form-horizontal" role="form" action="login" method="POST">

                        <c:if test="${!empty ERR_MSG}">
                            <div id="signupalert" style="" class="alert alert-danger">
                                <c:out value="${ERR_MSG}"/>
                            </div>
                        </c:if>
                </div>
            </div>
        </div>
    </div> <!-- /container -->
</center>
</body>
</html>
