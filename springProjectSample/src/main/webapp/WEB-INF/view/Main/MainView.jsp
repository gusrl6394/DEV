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
		<table >
			<tr>
				<th>
					전체 게시판
				</th>
			</tr>
			<c:forEach var = "list" items  = "${Recent}" varStatus = "Loop">
					
			
				<tr>
					<td>
						<a href = "${pageContext.request.contextPath}${list.add}" target = "mainView"> ${list.subject} </a>
					</td>
				</tr>
						
				
			</c:forEach>
		</table>
</body>
</html>