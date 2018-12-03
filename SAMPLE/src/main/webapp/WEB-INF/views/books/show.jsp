<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>    
<div class="jumbotron">
    <h1>${ book.title }</h1>
    <p>${ book.author } 저</p>
</div>
<div class="thumbnail">
    <img src="${ book.image }">
</div>
<div class="page-header">
    <h2>리뷰</h2>
</div>
<c:url var="reviewsPath" value="/reviews" />
<f:form modelAttribute="review" action="${ reviewsPath }?${_csrf.parameterName}=${_csrf.token}" method="post">
    <c:forEach var="error" items="${ fieldErrors }">
        <div class="alert alert-warning">
            <strong>${ error.getField() }</strong>: ${ error.getDefaultMessage() }
        </div>
    </c:forEach>
    
    <f:textarea path="text" cssClass="form-control" rows="5" />
    <!-- 평점 선택창 -->
    <!-- http://plugins.krajee.com/star-rating -->
    <!-- One of xl, lg, md, sm, or xs. Defaults to md -->
    <input name="rating" id="rating-system" type="text" class="rating rating-loading" data-size="md">
    
    <f:hidden path="bookId" />
    <f:hidden path="userId" />
    
    <button class="btn btn-block btn-primary" type="submit">리뷰 등록</button>
</f:form>

<table class="table table-stripped" id="reviews">
    <thead>
        <tr>
            <th>Rating</th> <!-- 평점 -->
            <th>User</th>
            <th>Text</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="review" items="${ reviews }" varStatus="status">
            <!-- 평점 기준 별표시 출력 -->
            <tr>
                <%-- <td><c:forEach var="rating" items="${ ratingOptions }" varStatus="status" begin="1" end="${ review.rating }">★</c:forEach></td> --%>
                <td><input type="text" class="rating rating-loading" value="${ review.rating }" data-size="xs" readonly></td>
 				<td>익명</td>
                <td>${ review.text }</td>
            </tr>
        </c:forEach>
    </tbody>
</table>

<nav aria-label="Page navigation">
    <ul class="pagination">
        <!-- first -->
        <li><a href="<c:url value="/books/${ book.id }?pageNum=1#reviews" />" aria-label="Previous"><span aria-hidden="true">&laquo;</span></a></li>
        <c:forEach var="i" begin="${paging.start}" end="${paging.end}" step="1">
            <li><a href="<c:url value="/books/${ book.id }?pageNum=${i}#reviews" />">${i}</a></li>
        </c:forEach>
        <!-- last -->
        <li><a href="<c:url value="/books/${ book.id }?pageNum=${paging.lastPageNum}#reviews" />" aria-label="Next"> <span aria-hidden="true">&raquo;</span></a></li>
    </ul>
</nav>


<Script>
// https://stackoverflow.com/questions/901115/how-can-i-get-query-string-values-in-javascript?page=1&tab=votes#tab-top
// https://api.jquery.com/addclass/
function getParameterByName(name, url) {
    if (!url) url = window.location.href;
    name = name.replace(/[\[\]]/g, '\\$&');
    var regex = new RegExp('[?&]' + name + '(=([^&#]*)|&|#|$)'),
        results = regex.exec(url);
    if (!results) return null;
    if (!results[2]) return '';
    return decodeURIComponent(results[2].replace(/\+/g, ' '));
}
var pageNum = getParameterByName("pageNum");
if (pageNum == null)
    pageNum = 1;
$("#review-page-index-" + pageNum).addClass("active");
</Script>