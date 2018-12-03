package com.itbank.samplesub;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class SessionsController {
	@RequestMapping(value = "/login", method = RequestMethod.GET)
    public String home() {
        return "statics/login";
    }
}
