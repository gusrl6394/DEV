<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	여기는 인기 게시판
	<hr>
		<table boarder = "1">
			<tr>
				<th>
					인기 게시판
				</th>
			</tr>
			<c:forEach var = "table" items = "${hit}" varStatus = "Loop">
				<tr>
					<td>
						<a href = "${pageContext.request.contextPath}${table.add}" target = "mainView"> ${table.subject } </a>
					</td>
				</tr>
			</c:forEach>
		</table>
</body>
</html>