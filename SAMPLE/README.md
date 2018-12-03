프로젝트)
# 1. 개요
- 스프링을 이용한 책관리 사이트

https://cloudstudying.kr/courses/12 사이트를 보고 따라한 예제 프로젝트입니다.

그러므로 저작권은 해당 사이트에 있습니다.

자동완성 자바스크립트 파트중에 아래코드처럼 수정하였으니 참고바랍니다.
# 원본
```javascript
$(function() {
    $("#searchBook").autocomplete({
        source : function(request, response) {
            $.ajax({
                type : 'get',
                url : "<c:url value='/api/books/search'/>",
                data : {
                    term : request.term
                },
                success : function(data) {
                    var bookList = data.bookList;
                    response($.map(bookList, function(item) {
                        return item.title;
                    }));
                }
            });
        }
    });
});
```

# 수정본
```javascript
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
```


# 2. 특징 및 버전
- MVC 패턴
- 부트스트랩
- Mapper 이용
- STS 3.9.6
- PostgreSQL 11
- 윈도우10
- Tomcat 8.5
- 스프링 타일즈3 사용
- 스프링 시큐리티 사용
- Form validation
- 도서 검색시 자동완성(Jquery)
- 페이지 네이션
- Ajax
- 권한 부여 및 제한
- 리뷰 별점
- @Scheduled

# 3. 공부당시 24번(주기적 메소드 수행)하기까지 있었으나 깃허브 업데이트당시 25번(도서 주문하기),

# 26번(지도 API 적용하기)가 첨부되었습니다. 추후 업데이트 하겠습니다. 