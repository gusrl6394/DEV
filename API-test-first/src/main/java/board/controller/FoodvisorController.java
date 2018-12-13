package board.controller;

import board.domain.FoodvisorAddrVO;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

@Controller
public class FoodvisorController{

	@RequestMapping(value="/", method=RequestMethod.GET)
	public String home() {
		return "index";
	}

	@RequestMapping(value="navermap")
	public String navermap(){
		return "naver/navermap";
	}

	// 응답결과만 반환
	@ResponseBody
	@RequestMapping(value="/sample/getAddrApi", method=RequestMethod.POST)
    public String getAddrApi(FoodvisorAddrVO foodvisorAddrVO) throws Exception {		
		// 요청변수 설정
		String currentPage = foodvisorAddrVO.getCurrentPage();
		String countPerPage = foodvisorAddrVO.getCountPerPage();
		String resultType = foodvisorAddrVO.getResultType();
		String confmKey = FoodvisorAddrVO.getConfmkey();
		String query = foodvisorAddrVO.getQuery();
		System.out.println("current, count, resulttype, keyword:"+currentPage+","+countPerPage+","+resultType+","+query);
		// OPEN API 호출 URL 정보 설정
		String apiUrl = "http://www.juso.go.kr/addrlink/addrLinkApi.do?currentPage="+currentPage+"&countPerPage="+countPerPage+"&keyword="+URLEncoder.encode(query,"UTF-8")+"&confmKey="+confmKey+"&resultType="+resultType;
		URL url = new URL(apiUrl);
    	BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(),"UTF-8"));
    	StringBuffer sb = new StringBuffer();
    	String tempStr = null;
    	while(true){
    		tempStr = br.readLine();
    		if(tempStr == null) break;
    		sb.append(tempStr);								// 응답결과 JSON 저장
    	}
    	br.close();
    	System.out.println(sb.toString());
    	return sb.toString();
    }
	
	// 응답결과만 반환
	@ResponseBody
	@RequestMapping(value="/sample/getGeocoding", method=RequestMethod.POST)
    public String API_MAP_Geocoding(@RequestParam(value="address1") String address1) throws Exception {	
		System.out.println("API_MAP_Geocoding 함수 실행");
		String clientId = "###";//애플리케이션 클라이언트 아이디값";
	    String clientSecret = "###";//애플리케이션 클라이언트 시크릿값";
	    String result="";
	    try {
	        String addr = URLEncoder.encode(address1,java.nio.charset.StandardCharsets.UTF_8.toString());
	        String apiURL = "https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode?query=" + addr; //json
	        //String apiURL = "https://openapi.naver.com/v1/map/geocode.xml?query=" + addr; // xml
	        URL url = new URL(apiURL);
	        HttpURLConnection con = (HttpURLConnection)url.openConnection();
	        con.setRequestMethod("GET");
	        con.setRequestProperty("Content-type", "application/x-www-form-urlencoded; charset=UTF-8");
	        con.setRequestProperty("X-NCP-APIGW-API-KEY-ID", clientId);
	        con.setRequestProperty("X-NCP-APIGW-API-KEY", clientSecret);
	        int responseCode = con.getResponseCode();
	        BufferedReader br;
	        if(responseCode==200) { // 정상 호출
	            br = new BufferedReader(new InputStreamReader(con.getInputStream()));
	        } else {  // 에러 발생
	            br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
	        }
	        String inputLine;
	        StringBuffer response = new StringBuffer();
	        while ((inputLine = br.readLine()) != null) {
	            response.append(inputLine);
	        }
	        br.close();
	        System.out.println(response.toString());
	        System.out.println("======================");
	        JsonParser jsonParser = new JsonParser();
	        // 전체 파싱
	        JsonObject jsonObject = (JsonObject) jsonParser.parse(response.toString());
	        // 파싱된것중에 addresses 필터
	        JsonArray addressesObject = (JsonArray) jsonObject.get("addresses");
	        // ArrayList 이기때문에 get(i)로 부름
	        System.out.println(addressesObject.get(0)); 
	        JsonObject item = (JsonObject) addressesObject.get(0);
	        // 리스트안에 있는 [0] 중에 roadAddress 불러오기
	        JsonElement address = item.get("roadAddress");
	        System.out.println("도로주소:"+address);
	        // 리스트안에 있는 [0] 중에 jibunAddress 불러오기 
	        JsonElement addrdetail = item.get("jibunAddress");
	        System.out.println("지번주소:"+addrdetail);
	        System.out.println("우편주소:"+item.get("addressElements").getAsJsonArray().get(0).getAsJsonObject().get("longName"));
	        JsonElement x = item.get("x");
	        JsonElement y = item.get("y");
	        result = x.toString().substring(1, x.toString().length()-1)+","+y.toString().substring(1, y.toString().length()-1);
	        System.out.println(result);
	    } catch (Exception e) {
	        System.out.println(e);
	    }		
		return result;
	}	

	@RequestMapping(value="naverlogin")
	public String naverlogin(){
		return  "naver/naverlogin";
	}
	@RequestMapping(value="navercallback")
	public String navercallback(){
		return "naver/navercallback";
	}
}
