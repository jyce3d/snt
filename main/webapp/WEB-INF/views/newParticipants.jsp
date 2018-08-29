<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page session="false" %>
<!-- Template to use to produce a standard List page -->
<html>
<head>
	<title>Home</title>
	<meta name="viewport" content="width=device-width, minimum-scale=1, maximum-scale=1"> 
	<link rel="stylesheet" href="http://code.jquery.com/mobile/latest/jquery.mobile.min.css"> 
	<script src="http://code.jquery.com/jquery.min.js"></script> 
	<script src="http://code.jquery.com/mobile/latest/jquery.mobile.min.js"></script> 
</head>
<body>
	<div data-role="page" id="page-study"> 
		<div data-role="popup" id="popupHeader">
			<a href="#" data-rel="back" data-role="button" data-theme="a" data-icon="delete" data-iconpos="notext" class="ui-btn-right"><spring:message code="snt.close"/></a>
				<h3>SnT-<spring:message code="snt.availParticipants"/></h3>
				<p><spring:message code="snt.serverTime"/> : ${serverTime}<br>
				<spring:message code="snt.study"/> : ${studyName}<br>
				<spring:message code="snt.location"/> : <c:if test="${locationShortName!=null}">[${locationShortName}]</c:if> ${locationName}<br>
				<spring:message code="snt.userLogged"/> : ${userName} - ${userFistName}, ${userLastName}<p>
		</div>
	
		<div data-role="header"> 
				<h1><a style="text-decoration : none;color: rgb(255,255,255)" href="#popupHeader" data-rel="popup">
				SnT-<spring:message code="snt.availParticipants"/>-${serverTime}<br>
				${studyName} - ${locationShortName}</a></h1> 
		</div> 
		<div data-role="content"> 
			<ul data-role="listview" data-inset="true" data-filter="true"> 
			<c:forEach items="${locationList}" var="location">
				<li><a href="participants.htm?id=${location.id}" target="_self" data-ajax="false">${location.name}</a></li>
			</c:forEach> 
			</ul>		
		</div> 
		<div data-role="footer" class="ui-bar"> 
			<a data-ajax="false" href="<c:url value='studyList.htm'/>" ><spring:message code="snt.studies" /></a>  
 			<a data-ajax="false" href="<c:url value='../j_spring_security_logout' />"><spring:message code="snt.logout" /></a> 
		</div> 
		
	</div>	
</body>
</html>