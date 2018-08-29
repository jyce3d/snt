<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<!-- Template to use to produce a standard Entry page -->
<html>
<head>
<title>SNTmobile - Participants</title>
<meta name="viewport" content="width=device-width, minimum-scale=1, maximum-scale=1"> 
<link rel="stylesheet" href="http://code.jquery.com/mobile/latest/jquery.mobile.min.css"> 
<script src="http://code.jquery.com/jquery.min.js"></script> 
<script src="http://code.jquery.com/mobile/latest/jquery.mobile.min.js"></script> 
<!-- write your jascript here -->
<script type="text/javascript">
	$(document).on("pageinit", "#participantPage", function() {
	// intialize your control here
			$("#btnSave").on('click',function() {
							$('#participantForm').submit();	
			});
	});

</script>

</head>
<body >
	<div data-role="page" id="participantPage"> 
		<form method="POST" action="participants.htm" id="participantForm" data-ajax="false">
			<div data-role="popup" id="popupHeader">
			<a href="#" data-rel="back" data-role="button" data-theme="a" data-icon="delete" data-iconpos="notext" class="ui-btn-right"><spring:message code="snt.close"/></a>
				<h3>SnT-<spring:message code="snt.subject"/></h3>
				<p><spring:message code="snt.serverTime"/> : ${serverTime}<br>
				<spring:message code="snt.study"/> : ${studyName}<br>
				<spring:message code="snt.location"/> : <c:if test="${locationShortName!=null}">[${locationShortName}]</c:if> ${locationName}<br>
				<spring:message code="snt.userLogged"/> : ${userName} - ${userFirstName}, ${userLastName}<p>
			</div>
			<div data-role="header"> 
				<h1><a style="text-decoration : none;color: rgb(255,255,255)" href="#popupHeader" data-rel="popup">
				SnT-<spring:message code="snt.participants"/>-${serverTime}<br>
				${studyName} - ${locationShortName}</a></h1> 
			</div> 
					
			<div data-role="content" > 
				<input type="hidden" name="id" value="${location.id}">
      			<table>
 					<c:forEach items="${participantList}" var="pm">
 						<tr>
 							<td>
								<label for="chk_${pm.userId}">${pm.userName}<input name="chk_${pm.userId}" id="chk_${pm.userId}" type="checkbox"
						  		<c:if test="${pm.grant!=null}">
						  				checked
						  		</c:if>  value="t">
						  		<!-- ${pm.grant} -->
         						</label>
						  	</td><td> 
        						<select name="cbo_${pm.userId}" id="cbo_${pm.userId}"> 

					 	 		<c:forEach items="${grantList}" var="grant">
					 	 			<option value="${grant}"
									<c:if test="${pm.grant == grant}">
									  selected
									</c:if>
									><spring:message code="snt.grant.${grant}" />
									<!-- ${grant} -->
									</option>
         			  	 		</c:forEach>
         			  	 		</select>
         			  	 	</td>
         				</tr>
         			</c:forEach>
 				</table>
			</div>
			<div data-role="footer" class="ui-bar"> 
			<!--TODO: your additional controls here -->
				<a href="#" id="btnSave"  ><spring:message code="snt.save"/></a> 
				<a data-ajax="false" href="<c:url value='studyLocations.htm'/>" ><spring:message code="snt.studyLocationsBtn" /></a>  
				<a data-ajax="false" href="<c:url value='../j_spring_security_logout'/>" ><spring:message code="snt.logout" /></a>  
			</div>			
		</form>
	</div>
</body>
</html>