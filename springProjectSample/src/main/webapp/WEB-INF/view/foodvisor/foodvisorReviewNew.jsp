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
<title>리뷰 등록</title>
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
              console.log(event.target.result);
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
        files.forEach(function(f) {
        	if(!f.type.match("image.*")) {
                alert("확장자는 이미지 확장자만 가능합니다.");
                files=null;
                filestate = false;
                console.log(files);
                return;
            }
        });
        if(filestate == true){
     	   Preloader.parallel(files,0);
        }
    });	
});

$(function() {
    $(document).on("click", '.selProductFile', function() {
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
	var noimage;
	var nofile;
	// 이미지 파일업로드 없을시 이미지 대체여부 묻는 if문
	if(sel_files.length == 0){
		noimage = confirm("이미지가 존재하지 않으면 기본이미지로 대체됩니다. 대체하시겠습니까?");
		console.log(noimage);
		// 확인 누를시 if문 실행
		if(noimage == true){
			// no-image라는 빈이미지 파일생성
			nofile = new File(['no-image'],'no-image.jpg',{type:'image/jpg'});
			console.log(nofile);
		}else{
			return;
		}	
	}
	var formData = new FormData();
	if(sel_files.length != 0){
		for (var i = 0; i < sel_files.length; i++) {
	        formData.append("imgPath", sel_files[i]);
	    }
	}else if(noimage == true){
		console.log("noimage == true");
		formData.append("imgPath", nofile);
	}else{
		alert("이미지 업로드 에러");
		return;
	}
	formData.append("title", $('#title').val());
	formData.append("address", $('#address').val());
	formData.append("grade", $('#grade').val());
	formData.append("content", $('#content').val()); 
	formData.append("pwd", $('#pwd').val()); 
	console.log("전송준비완료");
	$.ajax({
		url : "foodvisorReviewNew",
		enctype:"multipart/form-data",
		type:"post",
		data: formData,
		contentType: false,
		processData: false,
		cache: false,
		error:function(){	
			alert("error");
		},
		success:function(){
			window.location.replace("<c:url value="/foodvisor/foodvisorReviewlist"/>");
		}
	});
	console.log("==================");
}
$(function() {
    $(document).on("click", '.Ubtn', function() {
      var srcDiv = $(this).parents(".listDiv");
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
  	  var order = getOrder(".listDiv", $("#wrap")[0]);
  	  console.log(order);  	  
    });
});

$(function() {
    $(document).on("click", '.Dbtn', function() {
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
    	var order = getOrder(".listDiv", $("#wrap")[0]);
    	console.log(order);
    });
});
function getOrder(selector, container) {
  var order = [];
  $(selector, container).each(function () {
    order.push(this.id);
  });
  return order;
}
// 글자 Byte 체크함수
function len_chk(){  
  var frm = document.getElementById("content"); 	    
  //console.log(frm); // object
  //console.log(frm.value); // 글자
  var string = frm.value;
  var stringLength = string.length;
  //console.log(stringLength); // length
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
				<th><form:label path="title">제목</form:label></th>
				<td><form:input path="title"/></td>
			</tr>
			<tr>
				<th><form:label path="address">주소</form:label></th>
				<td><form:input path="address"/></td>
			</tr>
			<tr>
				<th><form:label path="img">썸네일 + 이미지</form:label></th>
				<td><input class="btn btn-sm btn-primary" multiple="multiple" type="file" id="file" name="imgPath" accept="image/*"/></td> 
			</tr>
			<tr>
				<th><form:label path="grade">점수</form:label></th>
				<td><input type="number" id="grade" name="name" min="1" max="100" value="0"/><br><div class="label label-info" style="font-size:1em;">최소 1점부터 ~ 100점까지 줄수있습니다.</div></td>
			</tr>
			<tr>
				<th><form:label path="content">내용</form:label><br><div class="label label-info" style="font-size:1em;">글자수 1000Byte 제한</div><br><br><div id="Numberofletters"></div></th>
				<td><textarea id="content" name="content" rows="15" style="resize: vertical;" onKeyup="len_chk()"></textarea><form:errors path="content"/></td>
			</tr>
			<tr>
				<th><form:label path="pwd">비밀번호</form:label></th>
				<td><form:password path="pwd"/></td>
			</tr>
		</table>
		<div>
			<input type="button" class="btn btn-lg btn-primary" id="send" value="등록" onclick="sendFile()">
			<button type="button" class="btn btn-lg btn-primary" onclick="location.href='<c:url value="/foodvisor/foodvisorReviewlist"/>'">목록</button>
		</div>		
	</form:form>
	<div class="alert alert-warning" role="alert" id="alert">
        <strong style="color:red">주의사항!</strong> 최상단 왼쪽부터 1번이미지, 2번이미지, 3번이미지, 4번이미지, 5번이미지 그 다음줄 6번이미지 순입니다.<br>
        <strong>파란색 점선 박스에 등록된 이미지는 썸네일로 등록됩니다.</strong><br>
        <strong>이미지만 등록이 가능하며 그외의 파일 등록시 처음부터 다시 등록하셔야 됩니다.</strong>
      </div>
    <div class="imgs_wrap" id="wrap">
        <!-- 이미지 추가될 장소 -->
    </div>
</body>
</html>