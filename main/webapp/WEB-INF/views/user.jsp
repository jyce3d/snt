<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>


<html>
<head>
<title>SNTmobile - Login</title>
<meta name="viewport" content="width=device-width, minimum-scale=1, maximum-scale=1"> 
<link rel="stylesheet" href="http://code.jquery.com/mobile/latest/jquery.mobile.min.css"> 
<script src="http://code.jquery.com/jquery.min.js"></script> 
<script src="http://code.jquery.com/mobile/latest/jquery.mobile.min.js"></script> 

<script type="text/javascript">
	var isPasswordDirty = false;
	var isSignatureDirty = false;
	$(document).on("pageinit", "#userPage", function() {
	     


			$("#btnSave").on('click',function() {
							if (isPasswordDirty==false) {
								$("#passwordDirty")[0].value="";
							} else
								$("#passwordDirty")[0].value="t";
							if (isSignatureDirty==false)
							    $("#signatureDirty")[0].value="";
							else
							    $("#signatureDirty")[0].value="t";							   
							$('#userForm').submit();	
			});
	});

</script>
</head>
<body >
	<div data-role="page" id="userPage"> 
		<form method="POST" action="user.htm" id="userForm" data-ajax="false">
		<div data-role="popup" id="popupHeader">
				<a href="#" data-rel="back" data-role="button" data-theme="a" data-icon="delete" data-iconpos="notext" class="ui-btn-right"><spring:message code="snt.close"/></a>
				<h3>SnT-<spring:message code="snt.userName"/></h3>
				<p><spring:message code="snt.serverTime"/> : ${serverTime}<br>
				<spring:message code="snt.study"/> : ${studyName}<br>
				<spring:message code="snt.location"/> : <c:if test="${locationShortName!=null}">[${locationShortName}]</c:if> ${locationName}<br>
				<spring:message code="snt.userLogged"/> : ${userName} - ${userFirstName}, ${userLastName}<p>
		</div>
		<div data-role="header"> 
				<h1><a style="text-decoration : none;color: rgb(255,255,255)" href="#popupHeader" data-rel="popup">
				SnT-<spring:message code="snt.userName"/>-${serverTime}<br>
				${studyName} - ${locationShortName}</a></h1> 
		</div> 	

		<div data-role="content" > 
		
         	<div data-role="collapsible-set"> 
         	<div data-role="collapsible" data-collapsed="false"  > 
         	<h3><spring:message code="snt.userProps"/></h3> 
         	
      		<div data-role="fieldcontent">
				<label for="userName"><spring:message code="snt.userName"/></label>
					<input type="hidden" name="id" value="${user.id}"></td>
				<input value="${user.name}" type="TEXT" id="userName" name="userName" size="15">
				<c:if test="${invalidUserName=='t'}" >
					<label><span style="color:red"><spring:message code="snt.error.invalidUserName" /></span></label>
				</c:if>
				<label for="password"><spring:message code="snt.password"/> </label>
				<input type="hidden" name="passwordDirty" value="" id="passwordDirty"><input type="PASSWORD" value="**********" id="password" name="password" onChange="isPasswordDirty=true;" id="password">
				<c:if test="${emptyPassword=='t'}" >
					<label><span style="color:red"><spring:message code="snt.error.passwordCannotBeEmpty" /></span></label>
				</c:if>
				<c:if test="${invalidPassword=='t'}" >
					<label><span style="color:red"><spring:message code="snt.error.invalidPassword" /></span></label>
				</c:if>
				<label for="confirmedPassword"><spring:message code="snt.confirmPassword"/></label>
				<input type="PASSWORD" value="**********" name="confirmPassword" id="confirmPassword">
				<c:if test="${passwordNotMatch=='t'}" >
					<label><span style="color:red"><spring:message code="snt.error.passwordMustMatch" /></span></label>
				</c:if>
				<label for="signature"><spring:message code="snt.signature"/></label>
				<input type="hidden" name="signatureDirty" value="" id="signatureDirty"><input type="PASSWORD" value="**********" name="signature" onChange="isSignatureDirty=true;" id="signature">
				<c:if test="${emptySignature=='t'}" >
					<label><span style="color:red"><spring:message code="snt.error.signatureCannotBeEmpty" /></span></label>
				</c:if>
				<c:if test="${invalidSignature=='t'}" >
					<label><span style="color:red"><spring:message code="snt.error.invalidSignature" /></span></label>
				</c:if>
				<label for="confirmSignature"><spring:message code="snt.confirmSignature"/></label>
				<input type="PASSWORD" value="**********" name="confirmSignature" id="confirmSignature">
				<c:if test="${signatureNotMatch=='t'}" >
					<label><span style="color:red"><spring:message code="snt.error.signatureMustMatch" /></span></label>
				</c:if> 
				<label for="userMail"><spring:message code="snt.userMail"/>
				<input  type="TEXT" value="${user.userMail}" id="userMail" name="userMail" size="15">
				<c:if test="${wrongUserMail=='t'}" >
					<label><span style="color:red"><spring:message code="snt.error.wrongMail" /></span></label>
				</c:if>
				<label for="enabled"><spring:message code="snt.user.enabled"/><INPUT name="enabled" type="checkbox" id="enabled"
					<c:if test="${user.enabled=='true'}" >
				 		checked
				 	</c:if>
					value="t"></label>
		</div> <!-- data role fieldcontent -->   
		</div> <!-- collapsible general properties -->
		<div data-role="collapsible" data-collapsed="true">
			<h3><spring:message code="snt.authorityList"></spring:message></h3>
			<div data-role="fieldcontain">
				<fieldset data-role="controlgroup">
					<legend><b><spring:message code="snt.userAuthority"/></b></legend>
					<c:forEach items="${authorityList}" var="authority">
						<label for="authority">${authority.authority}<input name="chk_${authority.authority}" id="chk_${authority.authority}" type="checkbox"
					 	 <c:forEach items="${userAuthorityList}" var="userAuth">
						  <c:if test="${authority.authority == userAuth}">
						  	checked
						  </c:if> 
         			  	 </c:forEach>
						 value="t">
         				</label>
         			</c:forEach>
 
				</fieldset>
			</div>
		
		</div>

		</div> <!-- collapsible set -->	
		</div>
	<div data-role="footer" class="ui-bar"> 
			<a data-ajax="false" href="<c:url value='../j_spring_security_logout'/>" ><spring:message code="snt.logout" /></a>  
			<a data-ajax="false"  href="#" id="btnSave"  ><spring:message code="snt.save"/></a> 
			<a data-ajax="false"  href="secure/users.htm"><spring:message code="snt.config"/></a> 
			<a data-ajax="false"  href="secure/users.htm"><spring:message code="snt.resetPassword"/></a> 		
	</div> 
	</form>

</div>

			
</body>
</html>