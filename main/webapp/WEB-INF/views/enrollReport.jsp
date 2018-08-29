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
<script src="http://code.highcharts.com/highcharts.js"></script>
<script src="http://code.highcharts.com/modules/exporting.js"></script>   

<!-- write your jascript here -->
<script type="text/javascript">
	$(document).on("pageshow", "#report-page", function() {
		var chart;
		 chart = new Highcharts.Chart({
		 	chart : {
                renderTo: 'container',
                type: 'line',
                marginRight: 130,
                marginBottom: 25		 	
		 	},
		 	title : {
		 		text : '<spring:message code="snt.enrollReport.title"/>',
		 		x: -20
		 	},
		 	subtitle : {
		 		text: '${studyName}',
		 		x: -20
		 	},
		 	xAxis : {
		 	 categories : [${xAxis}]
		 	},
		 	series: [{
		 		name: '<spring:message code="snt.subjects"/>',
		 		data : [${serie}]
		 	}]
		 	
		 	
		 });
	
	});

</script>

</head>
<body >
	<div data-role="page" id="report-page"> 
			<div data-role="popup" id="popupHeader">
			<a href="#" data-rel="back" data-role="button" data-theme="a" data-icon="delete" data-iconpos="notext" class="ui-btn-right"><spring:message code="snt.close"/></a>
				<h3>SnT-<spring:message code="snt.enrollReport.title"/></h3>
				<%@include file="headerPopupContent.jsp" %>
			</div>
			<div data-role="header"> 
				<h1><a style="text-decoration : none;color: rgb(255,255,255)" href="#popupHeader" data-rel="popup">
				SnT-<spring:message code="snt.enrollReport.title"/>-<%@include file="headerTitleContent.jsp" %></a></h1> 
			</div> 	
		
			<div data-role="content" > 
			 <div id="container" style="min-width: 400px; height: 400px; margin: 0 auto"></div>
			</div>
			<div data-role="footer" class="ui-bar"> 
			<!--TODO: your additional controls here -->
	 			<a data-ajax="false" href="studyMenu.htm"><spring:message code="snt.studyMenu"/></a>
				<a data-ajax="false" href="<c:url value='../j_spring_security_logout'/>" ><spring:message code="snt.logout" /></a>  
			</div>			
	</div>
</body>
</html>