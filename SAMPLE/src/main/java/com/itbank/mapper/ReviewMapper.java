package com.itbank.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import com.itbank.vo.Paging;
import com.itbank.vo.Review;
@Service
public interface ReviewMapper {
	 @Insert("INSERT INTO reviews (text, rating, book_id) VALUES (#{text}, #{rating}, #{bookId})")
	 void create(Review review);
	 @Select("SELECT * FROM reviews WHERE book_id = #{bookId} ORDER BY id DESC LIMIT #{p.rows} OFFSET (#{p.index} - 1) * #{p.rows} ")
	 public List<Review> getReviews(@Param("bookId") int bookId, @Param("p") Paging paging);
	 @Select("SELECT COUNT(*) FROM reviews WHERE book_id = #{bookId}")
	 public	int getReviewsCnt(@Param("bookId") int bookId);
}
