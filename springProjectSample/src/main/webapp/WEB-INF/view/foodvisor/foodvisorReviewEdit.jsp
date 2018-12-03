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
<title>리뷰 수정</title>
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
.listDiv:nth-child(1){
	border: 3px dotted blue;
}
.my_button {
    display: inline-block;
    width: 200px;
    text-align: center;
    padding: 10px;
    background-color: #006BCC;
    color: #fff;
    text-decoration: none;
    border-radius: 5px;
}
#alert{
    width:95%;
    left: 10%;
    right: 10%;
    margin-left: auto;
    margin-right: auto;
}
#wrap{
	border: 2px solid #A8A8A8;
	position:absolute;
    min-height:198px;
    width:95%;
    left: 2.5%;
    right: 2.5%;
    margin-left: auto;
    margin-right: auto;
}
img {
    width: 150px;
 	height: 150px;
}
.listDiv {
  padding: 10px 0 10px 20px;  
  border: 1px solid #ddd;
  width: 200px;
  height: 200px;
  position: relative;
  float: left;
  margin: 10px;
}
.UDbtn {
  position: absolute;
  left: 4px;
  top: 6px;
  font-size: 10px;
  text-align: left;
  cursor: pointer;
}
</style>
<script type="text/javascript">
var files;
var sel_files=[];
$(document).ready(function () { 	
	class Preloader {
		  constructor () {}
		  static parallel (imageArray,idx) {
		    let index = idx || 0
		    if (imageArray && imageArray.length > index) {
		      var img = new FileReader();
		      img.onload = function (event) {
		    	  Preloader.parallel(imageArray, index + 1)
		    	  console.log(imageArray[index])
		    	  sel_files.push(imageArray[index]);
		    	  // onclick=\"deleteImageAction("+index+")\"
		    	  var temp = "<a href=\"javascript:void(0);\" id=\"img_id_"+index+"\">"
	              +"<img src=\"" + event.target.result + "\" data-file='"+imageArray[index].name+"'class='selProductFile' title='Click to remove'></a>";
	              var html = "<div class=\"listDiv\" id=\"a"+(index+1)+"\">";
	              html += "<span class=\"UDbtn\">";
	              html += "<b class=\"Ubtn\">▲</b><br>";
	              html += "<b class=\"Dbtn\">▼</b><br>";
	              html += "<b style=\"font-size:2em\">"+(index+1)+"</b>";
	              html += "</span>";
	              html += "<span class=\"value\">";
	              html += temp;
	              html += "</span></div>";              
	              $(".imgs_wrap").append(html);
		      }
		      img.src = imageArray[index]
		      img.readAsDataURL(imageArray[index]); 
		    }
		  }
		}
		// 파일 업로드 감지시 동작되는 메소드
		$(document).on("change", 'input[type=file]', function() {
			files = document.getElementById('file').files;		
			files = Array.from(files);
			console.log(files);
			console.log(files.length);
			// 초기화
			sel_files=[];
			$(".imgs_wrap").empty();
	        var index = 0;
	        var filestate = true;
	        // 파일들이 전부 이미지인지 확인
	        files.forEach(function(f) {
	        	if(!f.type.match("image.*")) {
	                alert("확장자는 이미지 확장자만 가능합니다.");
	                files = null;
	                filestate = false;
	                console.log(files);
	                return;
	            }
	        });
	        // 파일 순차적으로 로딩시작
	        if(filestate == true){
	     	   Preloader.parallel(files,0);
	        }
	    });	
});

$(function() {
    $(document).on("click", '.selProductFile', function() {
    	if(files == undefined || sel_files.length == 0){
	       	noedit(false);
	    }

    	// 값 가져오기
    	var index = $(this).parent("a").attr("id");
    	//console.log("docoument.on.index:"+index.substring(index.length-1));
    	var countstart = index.substring(index.length-1);
    	var delimg = $(this).parent("a").attr("id", "img_id_1000");
    	var srcparent = $(this).parents(".listDiv");
    	//console.log("parent1:"+srcparent.attr("id"));
    	//console.log("parent2:"+srcparent.attr("class"));
    	
    	sel_files.splice(countstart, 1);
    	
    	// 탐색현재위치 찾기
    	var cnt = parseInt(countstart);
    	var temp = srcparent.next();
    	for(var i=0; i<sel_files.length; i++){
    	  var orign = temp.attr('id');
    	  var orignObj = temp;
    	  //console.log("orign.id:"+orign);
    	  if(!orign){
    		//console.log(!orign);
    	  }else{
    		var nextsecond = orignObj.children().last().children().first();
    		//nextsecond.attr("onclick","deleteImageAction("+countstart+")");
    		nextsecond.attr("id","img_id_"+cnt);
    		cnt = cnt + 1;
    		temp = temp.next();
    		//console.log(!orign);
    		}  			
  	 	 }
    	
    	var img_id = "#img_id_1000";
        $(img_id).parents(".listDiv").remove();    	
    });
});

function noedit(state){
		sel_files = [];
		var counting = 0;
		$('.selProductFile').each(function () {
			var imgsrc = this.src;
			console.log("imgsrc:"+imgsrc);
			imgsrc = imgsrc.substring(imgsrc.lastIndexOf("/")+1, imgsrc.length);
			console.log("after-imgsrc:"+imgsrc);
			sel_files.push(imgsrc);
			counting++;
		});
		if(state){
			if(counting==0){			
				noimage = confirm("이미지가 존재하지 않으면 기본이미지로 대체됩니다. 대체하시겠습니까?");
				console.log(noimage);
				// 확인 누를시 if문 실행
				if(noimage == true){
					sel_files.push("no-image.jpg");
					return true;
				}else{
					return false;
				}
			}else{
				return true;
			}
		}
		console.log(sel_files);
}
// 비번 확인통과시 수정내용이 반영되어서 서버에 보낼 최종 메소드
function finalsend(){
	
	var formData = new FormData();
	console.log("sel_files:"+sel_files);
	console.log("sel_files.length:"+sel_files.length);
	console.log("files.length:"+files);
	
	// 새파일업로드 없고 기존컨텐츠로 수정하고 바로 보낸경우
	if(files == undefined || sel_files.length == 0){
		var nodeitresult = noedit(true);
		if (!nodeitresult){
			// nodeit의 결과값이 false이면 실행되는 문장
			console.log("결과 : false");
			return;
		}
		console.log("결과 : true");
		//alert("멈춤");
		var tostr = sel_files.toString();
		console.log("tostr:"+tostr);
		formData.append("img", tostr);
	    formData.append("title", $('#title').val());
		formData.append("address", $('#address').val());
		formData.append("grade", $('#grade').val());
		formData.append("content", $('#content').val()); 
		formData.append("pwd", $('#pwd').val()); 
		
		$.ajax({
			url:"<c:url value="/foodvisor/foodvisorReviewedit?currentPage=${currentPage}&seq=${foodvisorVO.seq}"/>",
			type:"post",
			enctype:"multipart/form-data",
			data: formData,
			cache: false,
			contentType: false,
			processData: false,
			async: false,
			error:function(){	
				alert("error");
			},
			success:function(){		
				var result = "<c:url value='/foodvisor/foodvisorReviewRead?currentPage=${currentPage}&seq=${foodvisorVO.seq}'/>";
				window.location.replace(result);
			}
		});
		console.log("==================");
		return;
	}
	
	for (var i = 0; i < sel_files.length; i++) {
        formData.append("imgPath", sel_files[i]);
    }
	
	formData.append("title", $('#title').val());
	formData.append("address", $('#address').val());
	formData.append("grade", $('#grade').val());
	formData.append("content", $('#content').val()); 
	formData.append("pwd", $('#pwd').val());
	// formData 송출 확인시 아래 주석 해체
	/* var entries = formData.entries();
	console.log(entries.next());
	console.log(entries.next());
	console.log(entries.next());
	console.log(entries.next());
	console.log(entries.next());
	console.log(entries.next());
	console.log(entries.next());
	console.log(entries.next());
	console.log(entries.next());
	console.log(entries.length);
	console.log(formData);
	alert("멈춤")
	*/
	$.ajax({
		url:"<c:url value="/foodvisor/foodvisorReviewedit?currentPage=${currentPage}&seq=${foodvisorVO.seq}"/>",
		enctype:"multipart/form-data",
		type:"post",
		data: formData,
		contentType: false,
		processData: false,
		cache: false,
		async: false,
		error:function(){	
			alert("error");
		},
		success:function(){
			var result = "<c:url value='/foodvisor/foodvisorReviewRead?currentPage=${currentPage}&seq=${foodvisorVO.seq}'/>";
			window.location.replace(result);
		}
	});
	console.log("==================");
}
// 등록버튼 누를시 동작되는 메소드
function sendFile() {
	console.log("**********************")
	console.log(sel_files.length);
	console.log($('#title').val());
	console.log($('#address').val());
	console.log($('#grade').val());
	console.log($('#content').val());
	console.log($('#pwd').val());
	console.log("**********************")
	if($('#title').val() == "" || $('#address').val() == "" || $('#grade').val() == 0 || $('#content').val() == "" || $('#pwd').val() == ""){
		alert("빈칸이 존재합니다. 또는 점수가 1점~10점 가능합니다.");
		return;
	}
	
	console.log("보내기전:"+$("#pwd").val());
	var pwdresulttemp="";
	var pwdparam = {
		pwd : $("#pwd").val()
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
	if(pwdresulttemp != "ok"){
		alert("비번실패");
		return;
	}else{
		finalsend();
	}
}

$(function() {
	// 이미지에서 업버튼 누를시 동작되는 메소드
    $(document).on("click", '.Ubtn', function() {

   	  if(files == undefined || sel_files.length == 0){
     	noedit(false);
      }

  	  var srcDiv = $(this).parents(".listDiv");
  	  console.log("상위객체 존재없음:"+!(srcDiv.prev().attr('id')));
  	  if (!(srcDiv.prev().attr('id'))){
  		  alert("더 이상 이동 불가능");
  		  return;
  	  }
  	  
      var prevDiv = $(srcDiv).prev();
	  var srcsecond = srcDiv.children().last().children().first();
	  var srcscondidtemp = srcsecond.attr("id");
	  var prevsecond = prevDiv.children().last().children().first()
	  var prevscondidtemp = prevsecond.attr("id");
	  //console.log("srcscondidtemp:"+srcscondidtemp);
	  //console.log("prevscondidtemp:"+prevscondidtemp);
	  
	  // attr onclick 속성 바꾸기
	  srcsecond.attr("id", prevscondidtemp);
	  prevsecond.attr("id", srcscondidtemp);
	  
  	  // 현재위치 찾기
  	  var cnt = 0;
  	  var temp = srcDiv.prev();
  	  for(var i=0; i<sel_files.length; i++){
  	    var orign = temp.attr('id');
  		if(!orign){
  			console.log(!orign);
  		}else{
  			cnt = cnt + 1;
  			temp = temp.prev();
  			console.log(!orign);
  		}  			
	  }
  	  // 파일위치 교체
  	  console.log("cnt:"+cnt);
  	  console.log(sel_files[cnt]);
  	  var o1 = sel_files[cnt-1];
  	  var o2 = sel_files[cnt];
  	  sel_files[cnt-1] = o2;
  	  sel_files[cnt] = o1;
      console.log(sel_files);
  	 
  	  var tgtDiv = srcDiv.prev();
  	  if (tgtDiv[0]) {
  	    tgtDiv.before(srcDiv);
  	  }
  	  // 아래 getOrder함수 실행
  	  var order = getOrder(".listDiv", $("#wrap")[0]);
  	  console.log(order);
    });
});

$(function() {
	// 이미지에서 다운버튼 누를시 동작되는 메소드
    $(document).on("click", '.Dbtn', function() {

    	if(files == undefined || sel_files.length == 0){
         	noedit(false);
        }


    	var srcDiv = $(this).parents(".listDiv");
    	console.log("하위객체 존재없음:"+!(srcDiv.next().attr('id')));
    	if (!(srcDiv.next().attr('id'))){
    	   alert("더 이상 이동 불가능");
    	   return;
    	}
    	
    	var srcsecond = srcDiv.children().last().children().first();
    	var srcscondidtemp = srcsecond.attr("id");  	  
        var nextDiv = $(srcDiv).next();
    	var nextsecond = nextDiv.children().last().children().first()
    	var nextscondidtemp = nextsecond.attr("id");
    	  
    	//console.log("srcscondidtemp:"+srcscondidtemp);
    	//console.log("nextscondidtemp:"+nextscondidtemp);
    	  
    	// attr onclick 속성 바꾸기
    	srcsecond.attr("id", nextscondidtemp);
    	nextsecond.attr("id", srcscondidtemp);
    	
    	// 현재위치 찾기
    	var cnt = 0;
    	var temp = srcDiv.prev();
    	for(var i=0; i<sel_files.length; i++){
    	  var orign = temp.attr('id');
    	  if(!orign){
    	    console.log(!orign);
    	  }else{
    		cnt = cnt + 1;
    		temp = temp.prev();
    		console.log(!orign);
    	  }  			
     	}
    	// 파일위치 교체
    	console.log("cnt:"+cnt);
    	console.log(sel_files[cnt]);
    	var o1 = sel_files[cnt+1];
    	var o2 = sel_files[cnt];
    	sel_files[cnt+1] = o2;
    	sel_files[cnt] = o1;
        console.log(sel_files);        
        
    	var tgtDiv = srcDiv.next();
    	if (tgtDiv[0]) {
    	  tgtDiv.after(srcDiv);
    	}
    	// 아래 getOrder함수 실행
    	var order = getOrder(".listDiv", $("#wrap")[0]);
    	console.log(order);
    });
});
// 이미지 업,다운 버튼 누를시 console.log 출력하기위해 정보를 order 배열에 저장되어서 반환
function getOrder(selector, container) {
  var order = [];
  $(selector, container).each(function () {
    order.push(this.id);
  });
  return order;
}
// 내용 타이핑 입력시 자동진행되는 메소드
// 글자수 1000Byte 제한
function len_chk(){  
  var frm = document.getElementById("content"); 	    
  console.log(frm); // object
  console.log(frm.value); // 글자
  var string = frm.value;
  var stringLength = string.length;
  console.log(stringLength); // length
  var stringByteLength = 0;
  
  //개선된 FOR문으로 문자열 BYTE 계산
  //https://programmingsummaries.tistory.com/239
  console.time("개선된FOR방식");
  stringByteLength = (function(s,b,i,c){
      for(b=i=0;c=s.charCodeAt(i++);b+=c>>11?3:c>>7?2:1);
      return b
  })(string);
  console.log(stringByteLength + " Bytes"); // Bytes
  $('#Numberofletters').text(stringByteLength + " Bytes");
  console.timeEnd("개선된FOR방식");
  
  if(stringByteLength > 1000){  
    /* alert("글자수는 1000자로 제한됩니다.!");  */ 
    frm.value = frm.value.substring(0,frm.value.length-1);  
    len_chk();
  }else{
	  frm.focus();
	  return;
  }
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
				<th><form:label path="title">제목</form:label></th>
				<td><form:input path="title" value="${foodvisorVO.title}"/><form:errors path="title"/></td>
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
				<td><input type="number" id="grade" name="name" min="1" max="100" value="${foodvisorVO.grade}"/></td>
			</tr>
			<tr>
				<th><form:label path="address">주소</form:label></th>
				<td><form:input path="address" value="${foodvisorVO.address}"/><form:errors path="address"/></td>
			</tr>
			<tr>
				<th><form:label path="img">썸네일 + 이미지</form:label></th>
				<td><input class="btn btn-sm btn-primary" multiple="multiple" type="file" id="file" name="imgPath" accept="image/*"/></td> 
			</tr>			
			<tr>
				<th><form:label path="content">내용</form:label><br><div class="label label-info" style="font-size:1em;">글자수 1000Byte 제한</div><br><br><div id="Numberofletters"></div></th>
				<td><textarea id="content" name="content" rows="15" style="resize: vertical;" onKeyup="len_chk()">${foodvisorVO.content}</textarea><form:errors path="content"/></td>
			</tr>
			<tr>
				<th><form:label path="pwd">비밀번호</form:label></th>
				<td><form:password path="pwd"/>
				</td>
			</tr>
		</table>
		<div>
			<input type="button" class="btn btn-lg btn-primary" id="send" value="등록" onclick="sendFile()">
			<button type="button" class="btn btn-lg btn-primary" onclick="location.href='<c:url value="/foodvisor/foodvisorReviewlist"/>?currentPage=${currentPage}'">목록</button>
		</div>		
	</form:form>
	<div class="alert alert-warning" role="alert" id="alert">
        <strong>주의사항!</strong> 최상단 왼쪽부터 1번이미지, 2번이미지, 3번이미지, 4번이미지, 5번이미지 그 다음줄 6번이미지 순입니다.<br>
        <strong>파란색 점선 박스에 등록된 이미지는 썸네일로 등록됩니다.</strong>
      </div>
    <div class="imgs_wrap" id="wrap">
    	<% int index=1; %>
        <c:forEach var="foodvisor" items="${foodvisorVO.imgarr}" varStatus="loop">			
			<div class="listDiv" id="a${index}">
				<span class="UDbtn">
				<b class="Ubtn">▲</b><br>
				<b class="Dbtn">▼</b><br>
				<b style="font-size:2em"><%=index%></b>
				</span>
				<span class="value">
				<%-- onclick="deleteImageAction(<%=index%>)" --%>
					<a href="javascript:void(0);" id="img_id_<%=index%>">
						<img src="<c:url value="/resources/foodvisor/images/${foodvisor}"/>" data-file='${foodvisor}' class='selProductFile' title='Click to remove'>
					</a>
				</span>
			</div>
		<% index++; %>
		</c:forEach>
    </div>
</body>
</html>