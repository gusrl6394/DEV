package basic;

import java.sql.Timestamp;

public class BasicBoardDto {
	private int num;			//id
	private String subject;		//제목
	private int readcount;		//조회수
	private Timestamp regdate;	//날짜
	private String add;			//주소
	private String tablename;	//누구 db 인가?
	
	public BasicBoardDto() {
	}
	public BasicBoardDto(int num, String subject, int readcount, Timestamp regdate, String add, String tablename) {
		this.num = num;
		this.subject = subject;
		this.readcount = readcount;
		this.regdate = regdate;
		this.add = add;
		this.tablename = tablename;
	}
	
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public int getReadcount() {
		return readcount;
	}
	public void setReadcount(int readcount) {
		this.readcount = readcount;
	}
	public Timestamp getRegdate() {
		return regdate;
	}
	public void setRegdate(Timestamp regdate) {
		this.regdate = regdate;
	}
	public String getAdd() {
		return add;
	}
	public void setAdd(String add) {
		this.add = add;
	}
	public String getTablename() {
		return tablename;
	}
	public void setTablename(String tablename) {
		this.tablename = tablename;
	}
	
	
	
}
