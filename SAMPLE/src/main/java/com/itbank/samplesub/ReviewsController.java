package com.itbank.samplesub;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.itbank.mapper.ReviewMapper;
import com.itbank.vo.Review;

@Controller
public class ReviewsController {
	@Autowired
    private ReviewMapper reviewMapper;
    @RequestMapping(value = "/reviews", method = RequestMethod.POST)
    public String create(@Valid @ModelAttribute Review review, BindingResult result, RedirectAttributes flash) {
        if (result.hasErrors()) {
            List<FieldError> fieldErrors = result.getFieldErrors();
            flash.addFlashAttribute("fieldErrors", fieldErrors);
            flash.addFlashAttribute("review", review);
            return "redirect:/books/" + review.getBookId();
        }
        System.out.println("ReviewsController/create method:"+review);
        reviewMapper.create(review);
        return "redirect:/books/" + review.getBookId();
    }
}
