<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>


<html>
<head>
<title>SNTmobile - Login</title>
<meta name="viewport"
	content="width=device-width, minimum-scale=1, maximum-scale=1">
<link rel="stylesheet"
	href="http://code.jquery.com/mobile/latest/jquery.mobile.min.css">
<script src="http://code.jquery.com/jquery.min.js"></script>
<script src="http://code.jquery.com/mobile/latest/jquery.mobile.min.js"></script>
<script type="text/javascript">
	$(document).on("pageinit", "#userSignPage", function() {

			$("#btnSave").on('click',function() {
							$('#userForm').submit();	
			});
	});

</script>

</head>
<body>
	<div data-role="page" id="userSignPage">
		<form method="POST" action="changeSignature.htm" id="userForm" data-ajax="false" >
		<div data-role="popup" id="popupHeader">
			<a href="#" data-rel="back" data-role="button" data-theme="a" data-icon="delete" data-iconpos="notext" class="ui-btn-right"><spring:message code="snt.close"/></a>
				<h3>SnT-<spring:message code="snt.changeSignature"/></h3>
				<p><spring:message code="snt.serverTime"/> : ${serverTime}<br>
				<spring:message code="snt.study"/> : ${studyName}<br>
				<spring:message code="snt.location"/> : <c:if test="${locationShortName!=null}">[${locationShortName}]</c:if> ${locationName}<br>
				<spring:message code="snt.userLogged"/> : ${userName} - ${userFistName}, ${userLastName}<p>
		</div>
		<div data-role="header"> 
				<h1><a style="text-decoration : none;color: rgb(255,255,255)" href="#popupHeader" data-rel="popup">
				SnT-<spring:message code="snt.changeSignature"/>-${serverTime}<br>
				${studyName} - ${locationShortName}</a></h1> 
		</div> 
		<div data-role="content" > 
		 	<div data-role="fieldcontent">
		   		<label for="oldSignature"><spring:message code="snt.oldSignature"/><input type="hidden" name="id" value="${user.id}"> </label>
				<input type="PASSWORD" value="" name="oldSignature"  id="oldSignature">
				<c:if test="${oldSignatureNotMatch=='t'}" >
					<label><span style="color:red"><spring:message code="snt.error.oldSignatureMustMatch" /></span></label>
				</c:if>
				<label for="signature"><spring:message code="snt.signature"/> </label>
				<input type="PASSWORD" value="" name="signature" onChange="isSignatureDirty=true;" id="signature">
				<c:if test="${emptySignature=='t'}" >
					<label><span style="color:red"><spring:message code="snt.error.signatureCannotBeEmpty" /></span></label>
				</c:if>
				<c:if test="${invalidSignature=='t'}" >
					<label><span style="color:red"><spring:message code="snt.error.invalidSignature" /></span></label>
				</c:if>
				<label for="confirmSignature"><spring:message code="snt.confirmSignature"/></label>
				<input type="PASSWORD" value="" name="confirmSignature" id="confirmSignature">
				<c:if test="${signatureNotMatch=='t'}" >
					<label><span style="color:red"><spring:message code="snt.error.signatureMustMatch" /></span><label>
				</c:if>
		 </div>
		</div>
		<div data-role="footer" class="ui-bar"> 
		<a href="#" id="btnSave" ><spring:message code="snt.save"/></a> 
		<a data-ajax="false" href="<c:url value='../j_spring_security_logout'/>" ><spring:message code="snt.logout" /></a> 	
		</div> 
		</form>	
	</div>
</body>
</html>
