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
				<h3>SnT-<spring:message code="snt.studyEventList"/></h3>
				<%@include file="headerPopupContent.jsp" %>
		</div>
		<div data-role="header"> 
				<h1><a style="text-decoration : none;color: rgb(255,255,255)" href="#popupHeader" data-rel="popup">
				SnT-<spring:message code="snt.studyEventList"/>-<%@include file="headerTitleContent.jsp" %></a></h1> 

		</div> 	
		<div data-role="content"> 
			<h2><spring:message code="snt.studyEventListFor"/> ${subjectData.subjectKey} </h2>
			<div data-role="collapsible-set" >
			<c:forEach items="${studyEventList}" var="studyEvent">
				<div data-role="collapsible" data-collapsed="true">
					<h3>[${studyEvent.scheduledDate}]&nbsp;${studyEvent.studyEventName}</h3>
					<div data-role="fieldcontain">
				 	<label for="description"><spring:message code="snt.studyEventDescription"/></label>
				 	<input type="TEXT" disabled id="description" value="${studyEvent.studyEventDescr}" />
				 	<label for="scheduledDate"><spring:message code="snt.scheduledEventDate"/></label>
					<input type="TEXT" disabled id="scheduledDate" value="${studyEvent.scheduledDate}" />
				 	<label for="tolerance"><spring:message code="snt.eventTolerance"/></label>
				 	<input type="TEXT" disabled id="tolerance" value="[${studyEvent.minTolerance} ; ${studyEvent.maxTolerance}]" />
				 	<label for="effectiveDate"><spring:message code="snt.effectiveEventDate"/></label>
				 	<input type="TEXT" disabled id="effectiveDate" value="${studyEvent.effectiveDate}" 
				 	/>
				 	<input type="button" id="editStudyEvent" value='<spring:message code="snt.editStudyEvent"/>' />
					</div>
				    <ul data-role="listview" data-inset="true"> 
				    <li data-role="list-divider"><spring:message code="snt.formList"/></li>
				    <c:forEach items="${studyEvent.formDescrList}" var="formDescr" >
						<li><a href="formData.htm?formId=${formDescr.formDefId}&id=${studyEvent.studyEventDefId}&subjectId=${subjectData.id}" target="_self" data-ajax="false">
							<c:choose> 
								<c:when test="${formDescr.status==1}" >
									<img src="../resources/images/formDraftIcon.png">
								</c:when>
								<c:when test="${formDescr.status==2}" >
									<img src="../resources/images/formRtsIcon.png">
								</c:when>
								<c:when test="${formDescr.status==3}" >
									<img src="../resources/images/signIcon.png">
								</c:when>
								<c:otherwise>
									<img src="../resources/images/formIcon.png">
								</c:otherwise>
								
							</c:choose>
							${formDescr.formName}</a></li>
					</c:forEach>
					</ul>
				</div>
			</c:forEach> 
			</div>		
		</div> 
		<div data-role="footer" class="ui-bar"> 
 			<a data-ajax="false" href="#config"><spring:message code="snt.studies"/></a>
 			<a data-ajax="false" href="<c:url value='../j_spring_security_logout' />"><spring:message code="snt.logout" /></a> 
		</div> 
		
	</div>	
</body>
</html>