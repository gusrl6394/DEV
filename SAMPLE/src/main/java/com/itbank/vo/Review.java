package com.itbank.vo;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class Review {
	private Integer  id;
	@NotEmpty
	private String text;
	@NotNull
	private Integer rating;
	@NotNull
	private Integer  bookId;
	private Integer  userId;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Integer getBookId() {
		return bookId;
	}
	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getRating() {
		return rating;
	}
	public void setRating(Integer rating) {
		this.rating = rating;
	}
	@Override
	public String toString() {
		return "Review [id=" + id + ", text=" + text + ", rating=" + rating + ", bookId=" + bookId + ", userId="
				+ userId + "]";
	}
	
}
