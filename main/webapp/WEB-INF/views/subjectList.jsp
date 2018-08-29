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
				<h3>SnT-<spring:message code="snt.subjects"/></h3>
				<p><spring:message code="snt.serverTime"/> : ${serverTime}<br>
				<spring:message code="snt.study"/> : ${studyName}<br>
				<spring:message code="snt.location"/> : <c:if test="${locationShortName!=null}">[${locationShortName}]</c:if> ${locationName}<br>
				<spring:message code="snt.userLogged"/> : ${userName} - ${userFirstName}, ${userLastName}<p>
		</div>
		<div data-role="header"> 
				<h1><a style="text-decoration : none;color: rgb(255,255,255)" href="#popupHeader" data-rel="popup">
				SnT-<spring:message code="snt.subjects"/>-${serverTime}<br>
				${studyName} - ${locationShortName}</a></h1> 
		</div> 	
		<div data-role="content"> 
			<div data-role="collapsible-set" >
			<c:forEach items="${subjectList}" var="subject">
				<div data-role="collapsible" data-collapsed="true">
					<h3>${subject.subjectKey}</h3>
					<ul data-role="listview" data-inset="true"> 
					<li><a href="subject.htm?id=${subject.id}" target="_self" data-ajax="false"><img src="../resources/images/editIcon.png"><spring:message code="snt.editSubject"/></a></li>
					<li><a href="studyEvents.htm?id=${subject.id}" target="_self" data-ajax="false"><img src="../resources/images/visitIcon.png"><spring:message code="snt.subjectEvents"/></a></li>
					</ul>		
				</div>
			</c:forEach> 
			</div>
		</div> 
		<div data-role="footer" class="ui-bar"> 
 			<a data-ajax="false" href="studyMenu.htm"><spring:message code="snt.studyMenu"/></a>
 			<a data-ajax="false" href="<c:url value='../j_spring_security_logout' />"><spring:message code="snt.logout" />:${userName}</a> 
		</div> 
		
	</div>	
</body>
</html>