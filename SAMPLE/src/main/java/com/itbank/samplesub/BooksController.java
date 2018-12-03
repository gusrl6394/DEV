package com.itbank.samplesub;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.itbank.helper.FileHelper;
import com.itbank.mapper.BookMapper;
import com.itbank.mapper.ReviewMapper;
import com.itbank.vo.Book;
import com.itbank.vo.Paging;
import com.itbank.vo.Review;

@Controller
public class BooksController {
	@Autowired
	private BookMapper bookMapper;
	@Autowired
    private ReviewMapper reviewMapper;
	// 평점 옵션
	private static Map<Integer, String> ratingOptions;
	static {
		try {
			ratingOptions = new HashMap<Integer, String>();
			ratingOptions.put(0, "☆☆☆☆☆");
			ratingOptions.put(1, "★☆☆☆☆");
			ratingOptions.put(2, "★★☆☆☆");
			ratingOptions.put(3, "★★★☆☆");
			ratingOptions.put(4, "★★★★☆");
			ratingOptions.put(5, "★★★★★");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	@RequestMapping(value="/books", method=RequestMethod.GET)
	public String index(Model model) {
		List<Book> books = bookMapper.getList();
		model.addAttribute("books", books);
		return "books/index";
	}
	@RequestMapping(value="/books/new", method=RequestMethod.GET)
	public String newBook() {
		return "books/new";
	}
	@RequestMapping(value="/books", method=RequestMethod.POST)
	public String create(@ModelAttribute Book book, @RequestParam MultipartFile file, HttpServletRequest request) {
	    String fileUrl = FileHelper.upload("/uploads", file, request);
	    /* C:\\dev\\sts-workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp1\\wtpwebapps\\SAMPLE\\resources\\uploads */ 
	    book.setImage(fileUrl);
	    bookMapper.create(book);
	    return "redirect:/books";
	}
	
	@RequestMapping(value="/books/edit/{id}", method=RequestMethod.GET)
	public String edit(@PathVariable int id, Model model) {
		Book book = bookMapper.getBook(id);
		model.addAttribute("book", book);
		return "books/edit";
	}
	@RequestMapping(value="/books/update", method=RequestMethod.POST)
	public String update(@ModelAttribute Book book) {
		System.out.println(book.toString());
		bookMapper.update(book);
		return "redirect:/books";
	}
	@RequestMapping(value = "/books/delete/{id}", method = RequestMethod.GET)
	public String delete(@PathVariable int id) {
	    bookMapper.delete(id);
	    return "redirect:/books";
	}
	// https://stackoverflow.com/questions/41620395/spring-set-default-pathvariable
	// https://acuriouscoder.net/optional-path-variables-in-spring-mvc/
	// https://stackoverflow.com/questions/17934972/get-query-string-values-in-spring-mvc-controller
	@RequestMapping(value = "/books/{id}", method = RequestMethod.GET)
	public String show(@PathVariable int id, @RequestParam(value="pageNum", required=false) Optional<Integer> pageNum, Model model, Paging paging) {
	    Book book = bookMapper.getBook(id);
	    model.addAttribute("book", book);
	    // 등록된 리뷰들
	    if (pageNum.isPresent()) {
	        paging.setIndex(pageNum.get());
	        System.out.println("pageNum:"+pageNum.get());
	    }	    
	    List<Review> reviews = reviewMapper.getReviews(id, paging);
	    model.addAttribute("reviews", reviews);
	    // 페이징
	    paging.setTotal(reviewMapper.getReviewsCnt(id));
	    model.addAttribute("paging", paging);
	    // 새로운 리뷰 등록
	    if (!model.containsAttribute("review")) {
	        Review review = new Review();
	        review.setBookId(id);
	        model.addAttribute("review", review);
	    }
	    // 평점
	    model.addAttribute("ratingOptions", ratingOptions);

	    return "books/show";
	}
}
