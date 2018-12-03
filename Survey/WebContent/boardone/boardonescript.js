function qcontentmaxCreate(type){
	if(type=="radio" || type=="checkbox"){		
		var innerstr = "<b>답안 갯수 : </b>"+"<input type=\"number\" id=\"qcontentnum\" name=\"qcontentnum\" value=\"4\" min=\"1\">";
		console.log(innerstr);
		document.getElementById('answermax').innerHTML=innerstr;
	}else{
		var innerstr ="";
		console.log(innerstr);
		document.getElementById('answermax').innerHTML=innerstr;
	}
}
function createanswer(type){
	console.log(type);
	var max;
	if(type=="radio" || type=="checkbox"){
		max = document.getElementById("qcontentnum").value
	}
	console.log("type : "+type);
	console.log("max if전 : "+max);
	if(type==""){
		alert("답변 유형을 선택해주세요.");
		return;
	}
	if(type=="radio" || type=="checkbox"){
		if(max==""){
			console.log("max if후 : "+max);
			alert("답안 갯수를 선택해주세요.");
			return;
		}
		var innerstr ="";
		if(type=="radio"){			
			for(var i=0; i<max; i++){
				innerstr += (i+1)+"번) "+"<input type=\"radio\" id=\""+(i+1)+"-"+"qcontent\" name=\"qcontent\" disabled value=\""+(i+1)+"\"/> : "
				+"<input type=\"text\" name=\"qcontenttext"+(i+1)+"\" size=60><br>";
			}
			innerstr += "<br>답안 ○번 선택시 다음 질문으로 넘어가기(옵션)<br><input type=\"number\" id=\"qjumpanswer\" name=\"qjumpanswer\" value=\"0\" min=\"0\">번 답안시 <input type=\"number\" id=\"qjump\" name=\"qjump\" value=\"0\" min=\"0\">질문으로 넘어가기";
			console.log(innerstr);			
		}
		if(type=="checkbox"){
			var innerstr =" - 중복답변 갯수 설정 : <input type=\"number\" id=\"qcontentmax\" name=\"qcontentmax\" value=\"0\" min=\"0\"><br><br>"			
			for(var i=0; i<max; i++){
				innerstr += (i+1)+"번) "+"<input type=\"checkbox\" id=\""+(i+1)+"-"+"qcontent\" name=\"qcontent\" disabled value=\""+(i+1)+"\"/> "
				+"<input type=\"text\" name=\"qcontenttext"+(i+1)+"\" size=60><br>";
			}
			console.log(innerstr);			
		}
		
		document.getElementById('content create').innerHTML=innerstr;
		return;
	}
	if(type=="text"){
		var innerstr = "<input type=\"text\" id=\"qcontent\" name=\"qcontent\"/><br>";
		console.log(innerstr);
		document.getElementById('content create').innerHTML=innerstr;		
		return;
	}
	if(type=="textarea"){
		var innerstr = "<textarea id=\"qcontent\" id=\"qcontent\" name=\"qcontent\" rows=\"13\" cols=\"50\"></textarea>";
		document.getElementById('content create').innerHTML=innerstr;
		console.log(innerstr);
		return;
	}	
}
function surveyCheck(){
	var today = new Date();
	var dd = today.getDate();
	console.log("dd:"+dd);
	var mm = today.getMonth()+1; //January is 0!
	console.log("mm:"+mm);
	var yyyy = today.getFullYear();
	console.log("yyyy:"+yyyy);
	if(dd<10) {
	    dd='0'+dd
	} 
	if(mm<10) {
	    mm='0'+mm
	}
	today=yyyy+'-'+mm+'-'+dd;
	console.log("today : "+today);
	
	var strDate1 = today;
	var strDate2 = document.getElementById("startdatere").value;
	var strDate3 = document.getElementById("enddatere").value;
	console.log("strDate1 : "+strDate1);
	console.log("strDate2 : "+strDate2);
	console.log("strDate3 : "+strDate3);
	
	// 날짜 계산 준비
	var arr1 = strDate1.split('-');
	var arr2 = strDate2.split('-');
	var arr3 = strDate3.split('-');
	
	var dat1 = new Date(arr1[0], arr1[1], arr1[2]);
	var dat2 = new Date(arr2[0], arr2[1], arr2[2]);
	var dat3 = new Date(arr3[0], arr3[1], arr3[2]);

	
	// 날짜 차이 구하기
	var diff1 = dat2 - dat1; // 시작날짜 - 현재날짜
	var diff2 = dat3 - dat1; // 종료날짜 - 현재날짜
	var diff3 = dat3 - dat2; // 종료날짜 - 시작날짜	
	
	var currDay = 24*60*60*1000; // 시*분*초*밀리세컨
	var currMonth = currDay*30;//월 만듬
	var currYear = currMonth*12;//년 만듬
	
	var res1 = parseInt(diff1/currDay);
	var res2 = parseInt(diff2/currDay);
	var res3 = parseInt(diff3/currDay);
	
	console.log(parseInt(diff1/currDay));
	console.log(parseInt(diff2/currDay));
	console.log(parseInt(diff3/currDay));
	
	if(res1<0){
		alert("시작,종료 설정날짜는 오늘보다 과거로 갈수없습니다.");
		return;
	} else if(res2<0 || res3<0){
		alert("시작,종료 날짜를 잘못설정되었습니다.");
		return;
	}
	document.surveycreateForm.submit();
}