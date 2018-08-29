<%--
  User: jcelis
  Date: 8/02/2013
  Time: 18:24
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>


<html>
<head>
<title>SNTmobile - Login</title>
<meta name="viewport" content="width=device-width, minimum-scale=1, maximum-scale=1"> 
<link rel="stylesheet" href="http://code.jquery.com/mobile/latest/jquery.mobile.min.css"> 
<script src="http://code.jquery.com/jquery.min.js"></script> 
<script src="http://code.jquery.com/mobile/latest/jquery.mobile.min.js"></script> 
<script >
$(document).bind("mobileinit", function(){
    $.mobile.page.prototype.options.domCache = false;
});
</script>	
</head>
<body>
	<div data-role="page" id="login-page"> 
		<div data-role="header"> 
			<h1>SNT - EDCmobile - Login</h1> 
		</div> 
		<div data-role="content"> 

	<form id="j_snt_security_check" name="j_snt_security_check" action="j_snt_security_check"
		method="POST" data-ajax="false" >
		<table>
			<tr>
				<td><spring:message code="snt.userName"/></td>
				<td><input type="TEXT" name="j_username" value=""></td>
			</tr>
			<tr>
				<td><spring:message code="snt.password"/></td>
				<td><input type="PASSWORD" name="j_password" value=""></td>
			</tr>
			<c:if test="${loginError=='t'}" >
				<tr>
				 <td colspan="2"><span style="color:red"><spring:message code="snt.loginError"></spring:message></span></td>
				</tr>	 
			</c:if>
		</table>

	</form>
	</div>
	<div data-role="footer" class="ui-bar"> 
			<a href="#" onclick="$('#j_snt_security_check').submit()"><spring:message code="snt.login"/></a> 
	</div> 
	
</div>
</body>
</html>