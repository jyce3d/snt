<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<!-- Template to use to produce a standard Entry page -->
<html>
<head>
<title>SNTmobile - Login</title>
<meta name="viewport" content="width=device-width, minimum-scale=1, maximum-scale=1"> 
<link rel="stylesheet" href="http://code.jquery.com/mobile/latest/jquery.mobile.min.css"> 
<script src="http://code.jquery.com/jquery.min.js"></script> 
<script src="http://code.jquery.com/mobile/latest/jquery.mobile.min.js"></script> 

</head>
<body >
	<div data-role="page" id="studyMenuPage"> 
			<div data-role="popup" id="popupHeader">
			<a href="#" data-rel="back" data-role="button" data-theme="a" data-icon="delete" data-iconpos="notext" class="ui-btn-right"><spring:message code="snt.close"/></a>
				<h3>SnT-<spring:message code="snt.studyMenu"/></h3>
				<p><spring:message code="snt.serverTime"/> : ${serverTime}<br>
				<spring:message code="snt.study"/> : ${studyName}<br>
				<spring:message code="snt.location"/> : <c:if test="${locationShortName!=null}">[${locationShortName}]</c:if> ${locationName}<br>
				<spring:message code="snt.userLogged"/> : ${userName} - ${userFirstName}, ${userLastName}<p>
			</div>
			<div data-role="header"> 
				<h1><a style="text-decoration : none;color: rgb(255,255,255)" href="#popupHeader" data-rel="popup">
				SnT-<spring:message code="snt.studyMenu"/>-${serverTime}<br>
				${studyName} - ${locationShortName}</a></h1> 
			</div> 

			<div data-role="content" > 
				<a href="subjectList.htm" id="btnSubjects" target="_self" data-ajax="false" data-role="button"><spring:message code="snt.subjects" /></a> 
				<a href="subject.htm" id="btnNewSubject" target="_self" data-ajax="false" data-role="button"><spring:message code="snt.newSubject" /></a> 
				<a href="queries.htm" id="btnQueries" target="_self" data-ajax="false" data-role="button"><spring:message code="snt.queries" /></a> 
				<a href="pending.htm" id="btnPending" target="_self" data-ajax="false" data-role="button"><spring:message code="snt.pending" /></a> 
				<a href="reports.htm" id="btnReports" target="_self" data-ajax="false" data-role="button"><spring:message code="snt.reports" /></a> 
			</div>
			<div data-role="footer" class="ui-bar"> 
			<!--TODO: your additional controls here -->
				<a data-ajax="false" href="<c:url value='studyLocations.htm'/>" ><spring:message code="snt.studyLocationsBtn" /></a>  
				<a data-ajax="false" href="<c:url value='../j_spring_security_logout'/>" ><spring:message code="snt.logout" /></a>  
			</div>			
	</div>
</body>
</html>