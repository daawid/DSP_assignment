<%-- 
    Document   : index
    Created on : 16-Dec-2017, 19:40:11
    Author     : dawid
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="title" scope="request" value="Member dashboard"/>
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

    <c:choose>
        <c:when test="${!empty makeBooking}">
            <%@include file="/WEB-INF/makeBooking.jspf"%>
        </c:when>
        <c:when test="${!empty listMadeBookings}">
            <%@include file="/WEB-INF/listMadeBookings.jspf"%>
        </c:when>   
        <c:when test="${!empty listPayments}">
            <%@include file="/WEB-INF/listPayments.jspf"%>
        </c:when>
        <c:when test="${!empty checkOutstanding}">
            <%@include file="/WEB-INF/checkOutstanding.jspf"%>
        </c:when>
        <c:when test="${!empty listMadeBookings}">
            <%@include file="/WEB-INF/listMadeBookings.jspf"%>
        </c:when>
        <c:when test="${!empty editBooking}">
            <%@include file="/WEB-INF/editBooking.jspf"%>
        </c:when>
    </c:choose>



</center>
</body>
</html>