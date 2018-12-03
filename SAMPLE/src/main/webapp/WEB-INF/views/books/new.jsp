<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" session="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<body>
    <div class="container">
        <div class="jumbotron">
            <h1>Books NEW</h1>
            <p>views/books/new.jsp</p>
        </div>
        <form action="<c:url value="/books" />?${_csrf.parameterName}=${_csrf.token}" method="post" enctype="multipart/form-data">
            <!-- <div class="form-group form-group-lg">
                <label class="control-label">ID(number)</label>
                <input name="id" type="text" class="form-control">
            </div> -->
            <div class="form-group form-group-lg">
                <label class="control-label">도서 제목</label>
                <input name="title" type="text" class="form-control">
            </div>
            <div class="form-group form-group-lg">
                <label class="control-label">저자</label>
                <input name="author" type="text" class="form-control">
            </div>            
            <div class="form-group form-group-lg">
                <label class="control-label">이미지</label>
                <!-- <input name="image" type="text" class="form-control"> -->
                <input name="file" type="file">
            </div>
            <%-- <sec:csrfInput /> --%>
            <%-- <input type="hidden"  name="${_csrf.parameterName}"   value="${_csrf.token}"/> --%>
            <button type="submit" class="btn btn-lg btn-primary">전송</button>
        </form>
    </div>
</body>
</html>