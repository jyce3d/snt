<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<!-- Template to use to produce a standard Entry page -->
<html>
<head>
<title>SNTmobile -New Subject</title>
<meta name="viewport" content="width=device-width, minimum-scale=1, maximum-scale=1"> 
<link rel="stylesheet" href="http://code.jquery.com/mobile/latest/jquery.mobile.min.css"> 

<script src="http://code.jquery.com/jquery.min.js"></script> 
<script src="http://code.jquery.com/mobile/latest/jquery.mobile.min.js"></script> 
<!-- write your jascript here -->
<script type="text/javascript">

	$(document).on("pageinit", "#subjectPage", function() {
	// intialize your control here
		   
			$("#btnSave").on('click',function() {
							$('#subjectForm').submit();					
			});
	
			$("#cboSite").on('change',function() {
				$.ajax({
				 url:"ajaxSubjectInvestigators.htm",
				 type:"POST",
				 data : "locationId="+this.value,
				 cache:false,
				 async:false,
				 dataType:"json",
				 success: function(result) {
					var cboInvest = $("#cboInvestigator");
					cboInvest.empty();			
					$.each(result, function(text, val) {
						cboInvest.append("<option value="+val+">"+text+"</option>");
					});
					cboInvest.selectmenu("refresh"); // see jquery doc :http://jquerymobile.com/demos/1.0a4.1/docs/forms/forms-selects.html 	
				 },
				 error: function(xhr, ajaxOptions, thrownError) {
				 	alert(xhr.status);
				 	alert(xhr.responseText);
				 	alert(thrownError);
				 }
				});		
			});
	});

</script>

</head>
<body >
	<form method="POST" action="subject.htm" id="subjectForm" data-ajax="false">
	<div data-role="page" id="subjectPage"> 
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
				SnT-<spring:message code="snt.subject"/>-${serverTime}<br>
				${studyName} - ${locationShortName}</a></h1> 
			</div> 
			<div data-role="content" > 
      			<div data-role="fieldcontent">
					<label for="subjectKey"><spring:message code="snt.subjectKey"/></label>
					<input value="${subject.subjectKey}" type="TEXT" name="subjectKey" id="subjectKey" size="15">
					<input type="hidden" name="id" value="${subject.id}">
					
					<c:if test="${invalidSubjectKey=='t'}" >
							<label for="#"><span style="color:red"><spring:message code="snt.error.invalidSubjectKey" /></span></label>
					</c:if>
					<c:if test="${isDEA == 't'}">
						<label for="cboSite"><spring:message code="snt.subjectSite"/></label>
						 <select name="cboSite" id="cboSite" >
						  <c:forEach items="${locationList}" var="location">
						   <option value="${location.id}" 
						   	<c:if test="${subject.siteRef.id == location.id}">
						   		selected
						   	</c:if>
						   >${location.name}
						   </option>
						  </c:forEach>
						 </select>
						<label for="cboInvestigator"><spring:message code="snt.investigator"/></label>
							<select name="cboInvestigator" id="cboInvestigator">
								<c:forEach items="${investigatorList}" var="investigator">
									<option value="${investigator.id}"
										<c:if test="${subject.investigatorRef.id == investigator.id }">
											selected
										</c:if>
									>${investigator.name}
									</option>
								</c:forEach>
							</select>
					</c:if>	
					<label for="cboStatus"><spring:message code="snt.subjectStatus"/></label>
					<select name="cboStatus" id="cboStatus">
								<c:forEach items="${statusList}" var="status">
									<option value="${status}"
										<c:if test="${subject.status == status }">
											selected
										</c:if>
									><spring:message code="snt.subjectStatus.${status}"/>
									</option>
								</c:forEach>
					</select>

					<label for="active"><spring:message code="snt.subjectActive"/>
					<INPUT id="active" name="active" type="checkbox" 
							<c:if test="${subject.active=='1'}" >
				 				checked
				 			</c:if>
							value="t"></label>

				</div> <!-- field content -->

			</div>

			<div data-role="footer" class="ui-bar"> 
			<!--TODO: your additional controls here -->
				<c:choose>
					<c:when test="${subject.id == null}">
						<a href="#" id="btnSave"  ><spring:message code="snt.save"/></a> 
					</c:when>
					<c:otherwise>
						<a href="#dlgSave" id="btnDialog" ><spring:message code="snt.save"/></a>
					</c:otherwise>
				</c:choose>
				
				<a data-ajax="false" href="<c:url value='studyMenu.htm'/>" ><spring:message code="snt.studyMenu" /></a>  
				<a data-ajax="false" href="<c:url value='../j_spring_security_logout'/>" ><spring:message code="snt.logout" />:${userName}</a>  
			</div>			
	</div>
	<div data-role="dialog" id="dlgSave" data-url="dialog.htm">
				<div data-role="header">
					<h2><spring:message code="snt.save"/></h2>
				</div>
				<div data-role="content">
					<label for="reasonForUpdate"><spring:message code="snt.reasonForUpdate"/></label>
					<input value="" type="TEXT" name="reasonForUpdate" id="reasonForUpdate" >
				</div>
				<div data-role="footer">
					<a href="#" id="btnSave" onClick="$('#subjectForm').submit()" ><spring:message code="snt.save"/></a> 
					<a href="#dlgSave" data-rel="back" ><spring:message code="snt.back" /></a>
				</div>
	</div>
	</form>	
</body>
</html>