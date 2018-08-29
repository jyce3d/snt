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
<link rel="stylesheet" type="text/css" href="http://dev.jtsage.com/cdn/datebox/latest/jqm-datebox.min.css" /> 
<script src="http://code.jquery.com/jquery.min.js"></script> 
<script src="http://code.jquery.com/mobile/latest/jquery.mobile.min.js"></script> 
<script type="text/javascript" src="http://dev.jtsage.com/cdn/datebox/latest/jqm-datebox.core.min.js"></script>
<script type="text/javascript" src="http://dev.jtsage.com/cdn/datebox/latest/jqm-datebox.mode.datebox.min.js"></script>
<script type="text/javascript" src="http://dev.jtsage.com/cdn/datebox/i18n/jquery.mobile.datebox.i18n.en_US.utf8.js"></script>
<!-- write your jascript here -->
<script type="text/javascript">
	$(document).on("pageinit", "#formData-page", function() {
	// intialize your control here
			$("#btn-draft").on('click',function() {
							$('#form-status').val("${statusDraft}");
							$('#formData-form').submit();	
			});
			
			$("#btn-audit").on('click',function() {
				this.disable = true;
			});
		
			$("[id^='item-def-']").on('change', function() {
			 var sub = this.name.substring(9);
			 var arr = sub.split("-");
			 var obj = $("#item-dirty-"+arr[0]+"-"+arr[1]);
			 $("#item-dirty-"+arr[0]+"-"+arr[1]).val("t");			
			});

			
	});

</script>

</head>
<body >
	<form method="POST" action="formData.htm" id="formData-form" name="formData-form" data-ajax="false">
	<div data-role="page" id="formData-page"> 
	
			<div data-role="popup" id="popupHeader">
			<a href="#" data-rel="back" data-role="button" data-theme="a" data-icon="delete" data-iconpos="notext" class="ui-btn-right"><spring:message code="snt.close"/></a>
				<h3>SnT-<spring:message code="snt.formData"/></h3>
				<%@include file="headerPopupContent.jsp" %>
			</div>
			<div data-role="header"> 
				<h1><a style="text-decoration : none;color: rgb(255,255,255)" href="#popupHeader" data-rel="popup">
				SnT-<spring:message code="snt.formData"/>-<%@include file="headerTitleContent.jsp" %></a></h1> 
			</div> 	
		
			<div data-role="content" > 
			<h3><spring:message code="snt.subjectKey"/>${subject.subjectKey}<br>
			${studyEvent.name}-${formDef.name}</h3>
			<c:forEach items="${formDef.itemGroupDefList}" var="itemGroup">
				<h3 style="background-color:blue ; color:white">${itemGroup.description}</h3>
				<c:forEach items="${itemGroup.itemDefList}" var="itemDef" >
			 		<div data-role="fieldcontent">
						<label for="item-def-${itemGroup.id}-${itemDef.id}">${itemDef.question}<c:if test="${itemDef.required=='t'}"> <em  style="color:red">*</em> </c:if></label>
						<c:choose>
							<c:when test="${itemDef.dataType == 1 }"> <!-- INT -->
								<c:choose>
								 <c:when test="${itemDef.codeList!=null}">
								 	<SELECT id="item-def-${itemGroup.id}-${itemDef.id}"  <c:if test="${itemDef.required=='t'}"> required="required" </c:if> name="item-def-${itemGroup.id}-${itemDef.id}" >
								 		<OPTION value=""><spring:message code="snt.nav" /></OPTION>
								 		<c:forEach items="${itemDef.codeList.codeListItems}" var="codeItem">
								 			<OPTION value="${codeItem.codedValue}" <c:if test="${codeItem.codedValue == itemDef.value}"> SELECTED </c:if> >${codeItem.decode}</OPTION>
								 		</c:forEach>
								 	</SELECT>
								 	<c:forEach items="${itemDef.codeList.codeListItems}" var="codeItem">
								 			<INPUT type="HIDDEN" value="${codeItem.decode}" id="item-display-${itemGroup.id}-${itemDef.id}-${codeItem.codedValue}" name="item-display-${itemGroup.id}-${itemDef.id}-${codeItem.codedValue}" />
									</c:forEach>
								 	
								 </c:when>
								 <c:otherwise>
								 	<INPUT type="TEXT" id="item-def-${itemGroup.id}-${itemDef.id}" name="item-def-${itemGroup.id}-${itemDef.id}" value="${itemDef.value}"  />
								 </c:otherwise>
								</c:choose>
							</c:when>
							<c:when test="${itemDef.dataType == 2 }"> <!-- BOOL -->
							</c:when>
							<c:when test="${itemDef.dataType == 3 }"> <!-- DATE 3 combo's-->
								<input name="item-def-${itemGroup.id}-${itemDef.id}" value="${itemDef.value}" <c:if test="${itemDef.required=='t'}"> required="required" </c:if>  id="item-def-${itemGroup.id}-${itemDef.id}" placeholder="mm/dd/yyyy"   pattern="^\d{1,2}\\\d{1,2}\\\d{4}$" type="text" data-role="datebox" data-options='{"mode":"datebox", "useNewStyle":true}' />
							</c:when>
							<c:when test="${itemDef.dataType == 4 }"> <!-- TEXT -->
								<c:choose>
								 <c:when test="${itemDef.codeList!=null}">
								 	<SELECT id="item-def-${itemGroup.id}-${itemDef.id}" <c:if test="${itemDef.required=='t'}"> required="required" </c:if> name="item-def-${itemGroup.id}-${itemDef.id}">
								 		<OPTION value=""><spring:message code="snt.nav" /></OPTION>
								 		<c:forEach items="${itemDef.codeList.codeListItems}" var="codeItem">
								 			<OPTION value="${codeItem.codedValue}" <c:if test="${codeItem.codedValue == itemDef.value}"> SELECTED </c:if>  >${codeItem.decode}</OPTION>
								 		</c:forEach>
								 	</SELECT>
								 	<c:forEach items="${itemDef.codeList.codeListItems}" var="codeItem">
								 			<INPUT type="HIDDEN" value="${codeItem.decode}" id="item-display-${itemGroup.id}-${itemDef.id}-${codeItem.codedValue}" name="item-display-${itemGroup.id}-${itemDef.id}-${codeItem.codedValue}" />
									</c:forEach>
								 </c:when>
								 <c:otherwise>
								 	<INPUT type="TEXT" id="item-def-${itemGroup.id}-${itemDef.id}" name="item-def-${itemGroup.id}-${itemDef.id}"  placeholder="<srpring:message code="snt.entry.text"  />" value="${itemDef.value}" />
								 </c:otherwise>
								</c:choose>
							</c:when>
							<c:when test="${itemDef.dataType == 5 }"> <!-- URI -->
							</c:when>
							<c:when test="${itemDef.dataType == 6 }"> <!-- FLOAT with entry mask -->
							</c:when>
							<c:otherwise>
							</c:otherwise>	
						</c:choose>
						<c:if test="${invalid-item=='t'}" >
								<label><span style="color:red"><spring:message code="snt.error.xxxErrorMessage" /></span></label>
						</c:if>
					<!-- hidden entity ID -->
						<input type="hidden" name="item-dirty-${itemGroup.id}-${itemDef.id}"  id="item-dirty-${itemGroup.id}-${itemDef.id}"  value="f">
			 		</div>
				 </c:forEach>
			 </c:forEach>		
			</div>
			<input type="hidden" name="id" value="${formDef.id}" />
			<input type="hidden" name="studyEventId" value="${studyEvent.id}" />
			<input type="hidden" name="studyEventDataId" value="${studyEventDataId}" />
			<input type="hidden" name="subjectId" value="${subject.id}" />
			<input type="hidden" name="formDataId" value="${formDataId}" />
			<input type="hidden" name="form-status" id="form-status" />;

			<div data-role="footer" class="ui-bar"> 
			<!--TODO: your additional controls here -->
				<c:choose>
					<c:when test="${formDataId == null}">
					<a href="#" id="btn-draft"  ><spring:message code="snt.draft"/></a> 
					</c:when>
					<c:otherwise>
						<a href="#dlg-draft" id="btn-dlg-draft" ><spring:message code="snt.draft"/></a>
					</c:otherwise>
				</c:choose>
				<c:choose>
					<c:when test="${isDEA=='t'}">
						<a href="#" id="btn-rts"  ><spring:message code="snt.rts"/></a> 
					</c:when>
					<c:otherwise>
						<c:if test="${form-status != '3'}">
						<a href="#dlg-sign" id="btn-final"  ><spring:message code="snt.final"/></a> 			
						</c:if>	
					</c:otherwise>
				</c:choose>
				<a data-ajax="false" id="btn-audit" href="formAuditPdf.htm?form-data-id=${formDataId}" ><spring:message code="snt.auditTrail" /></a>
				<a data-ajax="false" href="<c:url value='../j_spring_security_logout'/>" ><spring:message code="snt.logout" /></a>  
			</div>			
		</div>
		<div data-role="dialog" id="dlg-draft" >
		<script>
			$(document).on("pageinit", "#dlg-draft", function() {
	// intialize your control here
				
				$("#btn-dlg-draft2").on('click',function() {
							$('#form-status').val("${statusDraft}");
							$('#formData-form').submit();	
				});
			});
		</script>
				<div data-role="header">
					<h2><spring:message code="snt.draft"/></h2>
				</div>
				<div data-role="content">
					<label for="reasonForUpdate"><em  style="color:red">*</em><spring:message code="snt.reasonForUpdate"/></label>
					<input value="" type="TEXT" name="reasonForUpdate" id="reasonForUpdate" >
	
				</div>
				<div data-role="footer">
					<a href="#" id="btn-dlg-draft2"  ><spring:message code="snt.draft"/></a> 
					<a href="#dlg-draft" data-rel="back" ><spring:message code="snt.back" /></a>
				</div>
		</div>
		
		<div data-role="dialog" id="dlg-sign" >
		<script>
			$(document).on("pageinit", "#dlg-sign", function() {
	// intialize your control here
			
				$("#btn-dlg-draft3").on('click',function() {
					$.ajax({
					 url:"ajaxVerifySignature.htm",
					 type:"POST",
					 data: {login:$("#login").val(), signature:$("#signature").val()},
					 cache:false,
					 asynch:false,
					 dataType:"json",
					 success: function (r) {
					
					  	if (r.result == 't') {
					  		$('#form-status').val("${statusSign}");
							$('#formData-form').submit();	
					  	}
					  	else alert("invalid signature");
			
			
					 },
					 error: function(xhr, ajaxOptions, thrownError) {
					 	// signature failed, or not enough access rights.
					 	alert("invalid signature");
					 }
					});
					
	
				});
			});
		</script>
				<div data-role="header">
					<h2><spring:message code="snt.final"/></h2>
				</div>
				<div data-role="content">
				<label for="meaning"><spring:message code="snt.meaning" /></label>
				<input type="TEXT" name="meaning" id="meaning" value="${meaning}" disabled />
				<label for="legalReason"><spring:message code="snt.legalReason"/></label>
				<TEXTAREA disabled id="legalReason" name="legalReason">${legalReason}</TEXTAREA>
				<label for="login"><spring:message code="snt.login" /><em  style="color:red">*</em></label>
				<input type="TEXT" value="" name="login" id="login" />
				<label for="signature"><spring:message code="snt.signature"/><em  style="color:red">*</em></label>
				<input type="PASSWORD" name="signature" value="" id ="signature" />
				<c:choose>
					<c:when test="${formDataId!=null}">
						<label for="reasonForUpdateSign"><spring:message code="snt.reasonForUpdate"/><em  style="color:red">*</em></label>
						<input value="" type="TEXT" name="reasonForUpdateSign" id="reasonForUpdateSign" >
					</c:when>
				</c:choose>
				<input type="HIDDEN" name = "signatureDefId" id="signatureDefId" value="${signatureDefId}" />
	
				</div>
				<div data-role="footer">
					<a href="#" id="btn-dlg-draft3"  ><spring:message code="snt.final"/></a> 
					<a href="#dlg-sign" data-rel="back" ><spring:message code="snt.back" /></a>
				</div>
		</div>
		
	</form>
	
</body>
</html>