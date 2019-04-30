<%-- 
    Document   : index
    Created on : 16-Dec-2017, 19:40:11
    Author     : dawid
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="title" scope="request" value="Admin dashboard"/>
<%@include file="/WEB-INF/header.jspf"%>

<!-- Top content -->
<center>
    <div class="panel panel-info" >
        <div class="panel-heading">
            <h3>Welcome to <strong>ABC travel </strong><c:out value="${username}"/></h3>
        </div>
    </div>

    <div class="container">    
        <div id="loginbox" style="margin-top:50px;" class="mainbox col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2">                    
            <div class="panel panel-info" >
                <div class="panel-heading">
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

            </div>
        </div>
        <h4><c:out value="${DASH_MSG}"/></h4>
    </div> <!-- /container -->

    <div class="panel panel-body" style="margin-left: 10px;">
        <c:choose>
            <c:when test="${!empty listMembers}">
                <%@include file="/WEB-INF/admin/listMembers.jspf"%>
            </c:when>
            <c:when test="${!empty listOutstandingBalances}">
                <%@include file="/WEB-INF/admin/listOutstandingBalances.jspf"%>
            </c:when>
            <c:when test="${!empty listBookings}">
                <%@include file="/WEB-INF/admin/listBookings.jspf"%>
            </c:when>
            <c:when test="${!empty listSuspendedMembers}">
                <%@include file="/WEB-INF/admin/listSuspendedMembers.jspf"%>
            </c:when>
            <c:when test="${!empty listUnapprovedBookings}">
                <%@include file="/WEB-INF/admin/listUnapprovedBookings.jspf"%>
            </c:when>
        </c:choose>
    </div>

</center>
</body>
</html>