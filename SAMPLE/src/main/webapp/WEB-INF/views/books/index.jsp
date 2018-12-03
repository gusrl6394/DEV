<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" session="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="s"%>    
<title>Books</title>
<body>
    <div class="container">
        <div class="jumbotron">
            <h1>Books INDEX</h1>
            <p>views/books/index.jsp</p>
        </div>
        
        <div class="search">
		    <c:url var="booksSearchPath" value="/books/search" />
		    <form action="${ booksSearchPath }" method="get">
		        <div class="row">
		            <div class="col-md-10">
		                <input name="query" id="searchBook" class="form-control input-lg" type="text" placeholder="도서명으로 검색">
		            </div>
		            <div class="col-md-2">
		                <button type="submit" class="form-control input-lg btn btn-primary">
		                    <span class="glyphicon glyphicon-search" aria-hidden="true"></span>
		       			        검색
		                </button>
		            </div>
		        </div>
		    </form>
		</div>
        
        <script>
		    $(function() {
	    	    var extstr = ['ㄱ','ㄴ','ㄷ','ㄹ','ㅁ','ㅂ','ㅅ','ㅇ','ㅈ','ㅊ','ㅋ','ㅌ','ㅍ','ㅎ'];
		        $("#searchBook").autocomplete({		        	
		            source : function(request, response) {
		            	console.log("request.term:"+request.term);
		            	var str = request.term;
		            	if(str.length < 2){
		            		return;
		            	}else{
		            		var laststr = str.charAt(str-1);
		            		for(var i=0; i<extstr.length; i++){
		            			if(laststr==extstr[i]){
		            				return;
		            			}
		            		}
		            	}
		                $.ajax({
		                    type : 'get',
		                    dataType: 'JSON',
		                    data : {term : request.term},
		                    url : "<c:url value='/api/books/search'/>",		                    
		                    success : function(data) {
		                    	console.log(data);
		                        response($.map(data, function(item) {
		                        	console.log(item.title);
		                            return item.title;
		                        }));
		                    },
		                    error : function(xhr, status, error){
		            			alert('error');
		            		}
		                });
		            },
		          	//조회를 위한 최소글자수
		            minLength: 2,
		            select: function( event, ui ) {
		            // 만약 검색리스트에서 선택하였을때 선택한 데이터에 의한 이벤트발생
		            }
		        });
		    });
		</script>
        
        <div class="row">
            <c:forEach var="book" items="${books}" varStatus="status">
                <div class="col-md-4">
			        <div class="thumbnail">
			        	<c:url var="show" value="/books/${ book.id }"/>
			            <a href="${show}"><img src="${ book.image }" /></a>
			            <div class="caption">
			                <h3>${ book.title } <small>${ book.author }</small></h3>
			                <s:authorize access="hasRole('ADMIN')">    
			                	<a href="<c:url value='/books/edit/${ book.id }' />" class="btn btn-lg btn-default">수정</a>
			             	   <a href="<c:url value='/books/delete/${ book.id }' />" class="btn btn-lg btn-danger">삭제</a>
			            	</s:authorize>
			            </div>
			        </div>
			    </div>
            </c:forEach>
        </div>
        <s:authorize access="hasRole('ADMIN')">        
			<a href="<c:url value="/books/new" />" class="btn btn-lg btn-primary">도서 등록</a>
		</s:authorize>
    </div>
</body>
</html>