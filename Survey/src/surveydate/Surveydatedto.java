package surveydate;

import java.sql.Timestamp;

public class Surveydatedto {
	private String tag; // setProperty
	private Timestamp startdate; //set
	private Timestamp enddate; // set
	private int respondent; 
	private int respondentlimit; // setProperty
	private int point; // setProperty
	private Timestamp createdate; // set
	private Timestamp editdate; // set
	private int surveynum; // setProperty
	private String target; // setProperty
	private String id; // setProperty
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public Timestamp getStartdate() {
		return startdate;
	}
	public void setStartdate(Timestamp startdate) {
		this.startdate = startdate;
	}
	public Timestamp getEnddate() {
		return enddate;
	}
	public void setEnddate(Timestamp enddate) {
		this.enddate = enddate;
	}
	public int getRespondent() {
		return respondent;
	}
	public void setRespondent(int respondent) {
		this.respondent = respondent;
	}
	public int getRespondentlimit() {
		return respondentlimit;
	}
	public void setRespondentlimit(int respondentlimit) {
		this.respondentlimit = respondentlimit;
	}
	public int getPoint() {
		return point;
	}
	public void setPoint(int point) {
		this.point = point;
	}
	public Timestamp getCreatedate() {
		return createdate;
	}
	public void setCreatedate(Timestamp createdate) {
		this.createdate = createdate;
	}
	public Timestamp getEditdate() {
		return editdate;
	}
	public void setEditdate(Timestamp editdate) {
		this.editdate = editdate;
	}
	public int getSurveynum() {
		return surveynum;
	}
	public void setSurveynum(int surveynum) {
		this.surveynum = surveynum;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
}
