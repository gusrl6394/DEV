package memberone;

import java.sql.Timestamp;

public class memberDto {
	private String id; // setProperty
	private String pass; // setProperty
	private String email; // setProperty
	private Timestamp regdate; // insert메소드 중에 생성되서 db저장
	private int point; // setProperty
	private String surveyexe; // setProperty
	public memberDto() {}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Timestamp getRegdate() {
		return regdate;
	}
	public void setRegdate(Timestamp regdate) {
		this.regdate = regdate;
	}
	public int getPoint() {
		return point;
	}
	public void setPoint(int point) {
		this.point = point;
	}
	public String getSurveyexe() {
		return surveyexe;
	}
	public void setSurveyexe(String surveyexe) {
		this.surveyexe = surveyexe;
	}	
}
