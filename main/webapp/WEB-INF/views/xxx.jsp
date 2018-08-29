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
<!-- write your jascript here -->
<script type="text/javascript">
	$(document).on("pageinit", "#xxx-page", function() {
	// intialize your control here
			$("#btn-xxx").on('click',function() {
							$('#xxx-form').submit();	
			});
	});

</script>

</head>
<body >
	<div data-role="page" id="xxx-page"> 
		<form method="POST" action="xxx.htm" id="xxx-form" data-ajax="false">
			<div data-role="popup" id="popupHeader">
			<a href="#" data-rel="back" data-role="button" data-theme="a" data-icon="delete" data-iconpos="notext" class="ui-btn-right"><spring:message code="snt.close"/></a>
				<h3>SnT-<spring:message code="snt.xxx"/></h3>
				<%@include file="headerPopupContent.jsp" %>
			</div>
			<div data-role="header"> 
				<h1><a style="text-decoration : none;color: rgb(255,255,255)" href="#popupHeader" data-rel="popup">
				SnT-<spring:message code="snt.xxx"/>-<%@include file="headerTitleContent.jsp" %></a></h1> 
			</div> 	
		
			<div data-role="content" > 
			 <div data-role="fieldcontent">
					<label for="xxx-name"><spring:message code="snt.xxxName"/></label>
					<input value="${xxx.name}" type="TEXT" id="xxxName" name="xxx-name" size="15"></input>
					<c:if test="${invalidxxxName=='t'}" >
								<label><span style="color:red"><spring:message code="snt.error.xxxErrorMessage" /></span></label>
					</c:if>
					<!-- hidden entity ID -->
					<input type="hidden" name="id" value="${xxx.id}">
			 </div>		
			</div>
			<div data-role="footer" class="ui-bar"> 
			<!--TODO: your additional controls here -->
				<a href="#" id="btn-save"  ><spring:message code="snt.save"/></a> 
				<a data-ajax="false" href="<c:url value='../j_spring_security_logout'/>" ><spring:message code="snt.logout" /></a>  
			</div>			
		</form>
	</div>
</body>
</html>