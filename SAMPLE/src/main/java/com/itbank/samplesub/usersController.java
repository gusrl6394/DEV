package com.itbank.samplesub;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.itbank.mapper.UserMapper;
import com.itbank.vo.User;

@Controller
public class usersController {
	@Autowired
    private UserMapper userMapper;
    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signup(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "users/signup";
    }
    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String create(@ModelAttribute User user) {
        userMapper.insertUser(user);
        userMapper.insertAuthority(user.getEmail(), "ROLE_USER");
        return "redirect:/users/login";
    }
}
