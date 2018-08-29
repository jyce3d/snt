<!--  Display the common header information for all the pages Those informations are automatically completed via the ControllerAdvice AOP class -->
				<p><spring:message code="snt.serverTime"/> : ${serverTime}<br>
				<spring:message code="snt.study"/> : ${studyName}<br>
				<spring:message code="snt.location"/> : <c:if test="${locationShortName!=null}">[${locationShortName}]</c:if> ${locationName}<br>
				<spring:message code="snt.userLogged"/> : ${userName} - ${userFirstName}, ${userLastName}<p>
