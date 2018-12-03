package com.itbank.samplesub;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.itbank.mapper.BookMapper;
import com.itbank.vo.Book;

@Controller
public class APIController {
	@Autowired
    BookMapper bookMapper;
    @RequestMapping(value = "/api/books/search", method = RequestMethod.GET)
    public @ResponseBody List<Book> searchBook(@RequestParam("term") String term) {
    	System.out.println("APIController - String term:"+term);
        List<Book> bookList = bookMapper.search(term);
        System.out.println("bookList.toString():"+bookList.toString());
        return bookList;
    }
}
