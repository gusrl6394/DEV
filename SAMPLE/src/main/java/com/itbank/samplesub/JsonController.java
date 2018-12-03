package com.itbank.samplesub;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.itbank.vo.JsonTestVO;
// TEST CODE
@Controller
public class JsonController {
	@RequestMapping(value="/test/json01", method = RequestMethod.GET)
	public String testhome() {
		return "test/json01";
	}
	@RequestMapping(value = "/test/ajax", method = RequestMethod.GET)
	public @ResponseBody JsonTestVO ajax(@RequestParam("id") int id, @RequestParam("name") String name) {
		System.out.println("JsonTestV0:"+id+","+name);
		JsonTestVO jtvo = new JsonTestVO(id, name);
	    System.out.println("JsonTestVO:"+jtvo.toString()); // 
		return jtvo;
	}
}
