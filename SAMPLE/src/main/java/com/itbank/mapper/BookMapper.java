package com.itbank.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Service;

import com.itbank.vo.Book;

@Service
public interface BookMapper {
	@Insert("insert into books (id, title, author, image) values (nextval('seq_books'), #{title}, #{author}, #{image})")
    public boolean create(Book book);
	@Select("select * from books")
	public List<Book> getList();
	@Select("select * from books where id = #{id}")
    public Book getBook(int id);
	@Update("update books set title = #{title}, author = #{author}, image = #{image} where id = #{id}")
	public boolean update(Book book);
	@Delete("delete from books where id = #{id}")
	public boolean delete(int id);
	@Select("select * from books where title like '%'||#{title}||'%'")
	public List<Book> search(String title);	
}
