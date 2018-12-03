<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page session="false" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>리뷰 읽기</title>
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
	min-width: 100px;
	min-height: 100px;
}
th, div{
	text-align: center;
}
textarea, input[type=text], input[type=file], input[type=number]{
	width: 100%;
}
.preview{
	width: 250px;
	height: 250px;
	position: relative;
	float: left;
	margin: 0 0 10px 100px;
}
</style>
<script type="text/javascript">
// 추천누르면 동작되는 메소드
function sendFile(seq) {
	var param = {
		number : seq
	}
	$.ajax({
		url:"<c:url value='/foodvisor/foodvisorReview/up'/>",
		type:"get",
		data : param,
		dataType:"json",
		cache:false,
		error:function(request, status, error){	
			alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
		},
		success:function(data){
			alert("추천완료");
			$('#likecnt').val(data);
			console.log("like-data:"+data);
		}
	});
	console.log("==================");
}
// 리뷰삭제 버튼 누를시 동작되는 메소드
function reviewdelete(seq){
	/* prompt(문자열, 초기값) */
	var name_value = prompt("게시판 비번을 입력해주십시오.", "");	
	var pwdresulttemp="";
	var pwdparam = {
		pwd : name_value
		}
	// AJAX 비번확인 요청
	// AJAX 동기식 처리로 전환
	// 동기가 필요한이유 : 데이터 처리를 요청한 순서대로 해야 하는 경우, .ajax()로 가져온 데이터를 전역 변수로 넘겨야 하는 경우
	$.ajax({
		url:"<c:url value="/foodvisor/pwd/${foodvisorVO.seq}"/>"
		,type:"post"
		,data:pwdparam						
		,dataType:"text"
		,async: false
		,success:function(data){			
			pwdresulttemp = data;
		}
		,error:function(request,status,error){
			alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
	    }
	});
	//alert("성공:"+pwdresulttemp);
	if(pwdresulttemp != "ok"){
		alert("비번실패");
		return;
	}

	$.ajax({
		url:"<c:url value="/foodvisor/foodvisorReviewdelete/${foodvisorVO.seq}"/>"
		,type:"post"
		,data:pwdparam						
		,dataType:"text"
		,async: false
		,success:function(){			
			var result = "<c:url value='/foodvisor/foodvisorReviewlist'/>";
			window.location.replace(result);
		}
		,error:function(request,status,error){
			alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
	    }
	});
}

</script>
</head>
<body>
	<form:form commandName="foodvisorVO" method="POST" enctype="multipart/form-data">
		<table border="1">
			<tr>
				<th><form:label path="seq">원글 번호</form:label></th>
				<td><form:input path="seq" value="${foodvisorVO.seq}" readonly="true"/></td>
			</tr>
			<tr>
				<th><form:label path="title">TITLE</form:label></th>
				<td><form:input path="title" value="${foodvisorVO.title}" readonly="true"/></td>
			</tr>
			<tr>
				<th><form:label path="writer">작성자</form:label></th>
				<td><form:input path="writer" value="${foodvisorVO.writer}" readonly="true"/></td>
			</tr>
			<tr>
				<th><form:label path="regdate">작성일</form:label></th>
				<td><form:input path="regdate" value="${foodvisorVO.regdate}" readonly="true"/></td>
			</tr>
			<tr>
				<th><form:label path="reviewcnt">조회수</form:label></th>
				<td><form:input path="reviewcnt" value="${foodvisorVO.reviewcnt}" readonly="true"/></td>
			</tr>
			<tr>
				<th><form:label path="likecnt">좋아요</form:label></th>
				<td><form:input path="likecnt" value="${foodvisorVO.likecnt}" readonly="true"/></td>
			</tr>			
			<tr>
				<th><form:label path="grade">점수</form:label></th>
				<td><form:input path="grade" value="${foodvisorVO.grade}" readonly="true"/></td>
			</tr>
			<tr>
				<th><form:label path="address">주소</form:label></th>
				<td><form:input path="address" value="${foodvisorVO.address}" readonly="true"/></td>
			</tr>			
			<tr>
				<td colspan="2">
					<c:forEach var="foodvisor" items="${foodvisorVO.imgarr}" varStatus="loop">
						<div>
							<img class="preview" src="<c:url value="/resources/foodvisor/images/${foodvisor}"/>"/>
						</div>
					</c:forEach>
				</td>
			</tr>
			<tr>
				<th><form:label path="content">내용</form:label></th>
				<td><form:textarea path="content" value="${foodvisorVO.content}" readonly="true" cols="35" rows="15" style="resize: vertical;"/></td>
			</tr>
		</table>
		<div>
			<button type="button" class="btn btn-default" onclick="sendFile(${foodvisorVO.seq})">추천</button>
			<button type="button" class="btn btn-default" onclick="reviewdelete(${foodvisorVO.seq})">삭제</button>
			<button type="button" class="btn btn-default" onclick="location.href='<c:url value="/foodvisor/foodvisorReviewedit"/>?currentPage=${currentPage}&seq=${foodvisorVO.seq}'">수정</button>
			<button type="button" class="btn btn-default" onclick="location.href='<c:url value="/foodvisor/foodvisorReviewlist"/>?currentPage=${currentPage}'">목록</button>
		</div>		
	</form:form>
</body>
</html>