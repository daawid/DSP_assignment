<%-- 
    Document   : register
    Created on : 27-Feb-2018, 15:15:03
    Author     : dawid
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="title" scope="request" value="Register"/>
<%@include file="/WEB-INF/header.jspf"%>

<script>
    function enableSubmit() {
        document.getElementById("btn-signup").disabled = false;
    }
</script>

<center>
    <div class="container">    
        <div id="loginbox" style="margin-top:50px;" class="mainbox col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2">   

            <div class="panel panel-info" >
                <div class="panel-heading" style="width:362px;">


                    <form id="signupform" class="form-horizontal" role="form" action="registration" method="POST">
                        <h4><div class="panel-title" >Register your details</div></h4>
                        <br>

                        <c:if test="${!empty ERR_MSG}">
                            <div id="signupalert" class="alert alert-danger">
                                <c:out value="${ERR_MSG}"/>
                            </div>
                        </c:if>


                </div>     
                <div class="panel-body" >
                    <form id="loginform" class="form-horizontal" role="form">

                        <div class="form-group">

                            <div class="col-md-9">
                                <input type="text" class="form-control" name="firstName" value="${firstName}" placeholder="First name" required>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-md-9">
                                <input type="text" class="form-control" name="lastName" value="${lastName}" placeholder="Last name" required>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-md-9">
                                <input type="date" class="form-control" name="dob" value="${dob}" placeholder="Date of birth (YYYY-MM-DD)" required>
                            </div>
                        </div>     
                        <div class="form-group">
                            <div class="col-md-9">
                                <c:if test="${empty addresses}">
                                    <c:if test="${manualAddress == true}">
                                        <input type="text" name="address" class="form-control" placeholder="Address" value="${address}" required>
                                    </c:if>
                                    <c:if test="${manualAddress != true}">
                                        <input name="postcode" type="text" class="form-control" placeholder="Postcode" required>
                                        <br>
                                        <button name="submit" type="submit" value="lookupPostcode" class="btn btn-info">Find address</button>
                                    </c:if>
                                </c:if>
                                <c:if test="${!empty addresses}">
                                    <select class="form-control" name="address">
                                        <c:forEach items="${addresses}" var="address">
                                            <option>
                                                <c:out value="${address}" />
                                            </option>
                                        </c:forEach>
                                    </select>
                                </c:if>
                            </div>
                        </div>
                </div>   
                <div class="form-group">
                    <div class="col-md-9">
                        <input type="email" class="form-control" name="email" value="${email}" placeholder="Email" required>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-md-9">
                        <input type="password" class="form-control" name="password" placeholder="Password" required>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-md-9">
                        <input type="password" class="form-control" name="repeatPassword" placeholder="Repeat password" required>
                    </div>
                </div>



                <div class="form-group">
                    <!-- Button -->                                        
                    <div class="col-md-offset-3 col-md-9">
                        <button id="btn-signup" name="submit" type="submit" value="createAccount" class="btn btn-info"><i class="icon-hand-right"></i>Submit</button>
                    </div>
                </div>                            
                </form>
            </div>
        </div>
</center>

</body>
</html>
