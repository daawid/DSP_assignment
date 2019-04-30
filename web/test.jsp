<%-- 
    Document   : index
    Created on : 16-Dec-2017, 19:40:11
    Author     : dawid
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="title" scope="request" value="Login"/>
<%@include file="/WEB-INF/header.jspf"%>
<center><h2><c:out value="${DASH_MSG}"/></h2></center>
        <!-- Top content -->
        <div class="top-content">
            <div class="container">

                <div class="row">
                    <div class="col-sm-8 col-sm-offset-2 text">
                        <h1>Welcome to <strong>ABC travel</strong></h1>
                        <div class="warning">
                       	     <p><strong><c:out value="${LOGIN_MSG}"/></p>
                        </div>
                    </div>
                </div>

                 <div class="wrapperAddressInputs">
            <label for="originAddress">Origin</label>
            <input id="inputOriginAddress" name="originAddress" placeholder="Where are you coming from?" />
            <br />
            <label for="destinationAddress">Destination</label>
            <input id="inputDestinationAddress" name="destinationAddress" placeholder="Where are you going to?" />
            <label for="passengersNum">Passengers? (1-4)</label>
            <select id="selectPassengersNum" name="passengersNum">
                <option value="1">1</option>
                <option value="2">2</option>
                <option value="3">3</option>
                <option value="4">4</option>
            </select>
            <button id="buttonCalculateFare">Calculate Fare!</button>
            <p id="fareEstimate"></p>
            <div id="fareBreakdown" class="fareBreakdownContainer"></div>
        </div>

        <div id="map"></div>

    </div>
    </script>
    <script type="text/javascript" src="assets/js/app.js"></script>
    <script async defer type="text/javascript" src="https://maps.googleapis.com/maps/api/js?key=AIzaSyB0EiemjmNY3TyeKXZUO5a3879S5enCd4U&libraries=places&callback=initialize">
    </script>

            </div>
        </div>


        <!-- Javascript -->
        <script src="assets/js/jquery-1.11.1.min.js"></script>
        <script src="assets/bootstrap/js/bootstrap.min.js"></script>
        <script src="assets/js/jquery.backstretch.min.js"></script>
        <script src="assets/js/retina-1.1.0.min.js"></script>
        <script src="assets/js/scripts.js"></script>
    </body>
</html>