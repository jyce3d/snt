<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page session="false" %>
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
				<h3>SnT-<spring:message code="snt.studies"/></h3>
				<p><spring:message code="snt.serverTime"/> : ${serverTime}<br>
				<spring:message code="snt.userLogged"/> : ${userName} - ${userFirstName}, ${userLastName}<p>
		</div>
		<div data-role="header"> 
				<h1><a style="text-decoration : none;color: rgb(255,255,255)" href="#popupHeader" data-rel="popup">
				SnT-<spring:message code="snt.studies"/>-${serverTime}
				</a></h1> 
		</div> 
		<div data-role="content"> 
			<ul data-role="listview" data-inset="true" data-filter="true"> 
			<c:forEach items="${studyList}" var="study">		
				<li><a href="studyLocations.htm?studyId=${study.id}" data-ajax="false">${study.studyName} <span class="ui-li-count ui-btn-up-cui-btn-corner-all">${study.subjectCount}</span></a></li>
			</c:forEach> 
			</ul>		
		</div> 
		<div data-role="footer" class="ui-bar"> 
		<c:choose> 
		 <c:when test="${isAdmin=='t'}" >
			<a href="users.htm" data-ajax="false" target="_self"><spring:message code="snt.config"/></a> 
			<a data-ajax href="praetorian.htm">Pi</a>
		 </c:when>
		 <c:otherwise>
			<a href="changePassword.htm?id=${user.id}" data-ajax="false"><spring:message code="snt.changePassword"/></a> 		 
		 </c:otherwise>
		</c:choose>
		<a data-ajax="false" href="<c:url value='../j_spring_security_logout'/>" ><spring:message code="snt.logout" /></a>  
		</div> 
		
	</div>	
</body>
</html>
