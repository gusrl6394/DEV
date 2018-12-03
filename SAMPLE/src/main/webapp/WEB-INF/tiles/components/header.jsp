<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="s"%>
<%@ page pageEncoding="utf-8"%>
<nav class="navbar navbar-default">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="navbar-header">
        <button type="button" class="navbar-toggle collapsed"
            data-toggle="collapse" data-target="#bs-example-navbar-collapse-1"
            aria-expanded="false">
            <span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span>
            <span class="icon-bar"></span> <span class="icon-bar"></span>
        </button>
        <a class="navbar-brand" href="#">Brand</a>
    </div>
    <!-- Collect the nav links, forms, and other content for toggling -->
    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
        <ul class="nav navbar-nav">
            <li><a href="#">맛집</a></li>
        </ul>
	    <ul class="nav navbar-nav navbar-left">
		    <li><a href="<c:url value='/books'/>">도서</a></li>
		    <!-- 관리자 페이지 버튼 -->
		    <s:authorize access="hasRole('ADMIN')">
		        <li><a href="<c:url value='/admin'/>">관리</a></li>
		    </s:authorize>
		</ul>
        <!-- 로그인 버튼 -->
        <s:authorize access="isAnonymous()">
                <c:url var="loginUrl" value="/login" />
                <ul class="nav navbar-nav navbar-right">
                    <li><a href="${ loginUrl }">로그인</a></li>
            </ul>
        </s:authorize>
        <!-- 로그아웃 버튼 -->
        <s:authorize access="isAuthenticated()">
            <c:url var="logoutUrl" value="/logout" />
            <form action="${logoutUrl}" method="post"
                class="navbar-form navbar-right">
                <button type="submit" class="btn btn-default">로그아웃</button>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
            </form>
        </s:authorize>
        <!-- /.navbar-collapse -->
    </div>
    <!-- /.container-fluid -->
</nav>