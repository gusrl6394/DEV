<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <!-- http://webproject.co.kr/59 -->
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%> 
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>목록</title>
<link type="text/css" rel="stylesheet" href="<c:url value="/resources/foodvisor/css/bootstrap.css"/>">
<script type="text/javascript" src="<c:url value="/resources/foodvisor/js/jquery-3.3.1.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/foodvisor/js/popper.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/foodvisor/js/bootstrap.js"/>"></script>
<style type="text/css">
table{
	width:95%;
	margin-left: auto;
    margin-right: auto;
}
th, td{
	text-align: center;
	vertical-align: middle;
}
div{
	text-align: center;
}
td:nth-child(2), td:nth-child(4){
	word-break:break-all;
}
td:nth-child(3){
	width: 100px;
	max-width: 100px;
}
.preview{
	width: 100px;
	height: 100px;
}
</style>
<script type="text/javascript">
$(document).ready(function () {
var agent = navigator.userAgent, match;
var app, version;

if((match = agent.match(/MSIE ([0-9]+)/)) || (match = agent.match(/Trident.*rv:([0-9]+)/))) app = 'Internet Explorer';
else if(match = agent.match(/Chrome\/([0-9]+)/)) app = 'Chrome';
else if(match = agent.match(/Firefox\/([0-9]+)/)) app = 'Firefox';
else if(match = agent.match(/Safari\/([0-9]+)/)) app = 'Safari';
else if((match = agent.match(/OPR\/([0-9]+)/)) || (match = agent.match(/Opera\/([0-9]+)/))) app = 'Opera';
else app = 'Unknown';

if(app !== 'Unknown') version = match[1];

console.log('Browser: ' + app);
console.log('Version: ' + version);
});
</script>
</head>
<body>
	<table border="1">
		<tr>
			<th>번호</th>
			<th>제목</th>
			<th>썸네일</th>
			<th>작성자</th>
			<th>작성일</th>
			<th>조회수</th>
			<th>좋아요</th>
		</tr>
		<c:forEach var="foodvisor" items="${foodvisorVO}" varStatus="loop" >
			<tr>
				<td>${foodvisor.seq}
				<td><a href="<c:url value="/foodvisor/foodvisorReviewRead?currentPage=${paging.currentPage}&seq=${foodvisor.seq}"/>">
					${foodvisor.title}</a></td>
				<td><img class="img-thumbnail preview" src="<c:url value="/resources/foodvisor/images/${foodvisor.previewimg}"/>"/></td>
				<td>${foodvisor.writer}</td>
				<td>${foodvisor.regdate}</td>
				<td>${foodvisor.reviewcnt}</td>
				<td>${foodvisor.likecnt}</td>
			</tr>
		</c:forEach>
	</table>
	<br>
	<div>
		<c:if test="${paging.prev}">
			<button type="button" class="btn btn-primary" onclick="location.href='<c:url value="/foodvisor/foodvisorReviewlist"/>?currentPage=1'">처음</button>
			&nbsp;&nbsp;&nbsp;&nbsp;
			<button type="button" class="btn btn-primary" onclick="location.href='<c:url value="/foodvisor/foodvisorReviewlist"/>?currentPage=${paging.startPage-1}'">이전</button>
			&nbsp;
		</c:if>
		
		<c:forEach begin="${paging.startPage}" end="${paging.endPage}" var="nm">
			<button type="button" class="btn btn-default" onclick="location.href='<c:url value="/foodvisor/foodvisorReviewlist"/>?currentPage=${nm}'">${nm}</button>
			&nbsp;
		</c:forEach>
		<c:if test="${paging.next}">
			<button type="button" class="btn btn-primary" onclick="location.href='<c:url value="/foodvisor/foodvisorReviewlist"/>?currentPage=${paging.endPage+1}'">다음</button>
			&nbsp;&nbsp;&nbsp;&nbsp;
			<button type="button" class="btn btn-primary" onclick="location.href='<c:url value="/foodvisor/foodvisorReviewlist"/>?currentPage=${paging.finalPage}'">마지막</button>
		</c:if>
	</div>
	<br>
	<div>
		<button type="button" class="btn btn-lg btn-info" onclick="location.href='<c:url value="/foodvisor/foodvisorReviewNew"/>'">등록</button>
		<br>서버 : <%=application.getServerInfo() %>
		<br>서블릿 버전 : <%=application.getMajorVersion()%>.<%= application.getMinorVersion() %>
		<br>JSP : <%= JspFactory.getDefaultFactory().getEngineInfo().getSpecificationVersion() %>
	</div>
</body>
</html>