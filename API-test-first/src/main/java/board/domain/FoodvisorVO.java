package board.domain;

import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.type.Alias;
import org.springframework.web.multipart.MultipartFile;
@Alias("foodvisorVO")
public class FoodvisorVO{
	private int num; 
	private int seq;
	private String title;
	private String address;
	private String addressdetail;
	private String img;
	private List<MultipartFile> imgPath;
	private String[] imgarr;
	private String previewimg;
	private String content;
	private String writer;
	private Timestamp regdate;
	private int grade;
	private int reviewcnt;
	private int likecnt;
	private int targetvolume;
	private int remainvolume;
	private String upDir;
	public FoodvisorVO() { }
	public FoodvisorVO(String title, String address, String img, String content, String writer, Timestamp regdate, int reviewcnt, int likecnt) {
		super();
		this.title = title;
		this.address = address;
		this.img = img;
		this.content = content;
		this.writer = writer;
		this.regdate = regdate;
		this.reviewcnt = reviewcnt;
		this.likecnt = likecnt;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}	
	public String getAddressdetail() {
		return addressdetail;
	}
	public void setAddressdetail(String addressdetail) {
		this.addressdetail = addressdetail;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public List<MultipartFile> getImgPath() {
		return imgPath;
	}
	public void setImgPath(List<MultipartFile> imgPath) {
		this.imgPath = imgPath;
	}
	public String[] getImgarr() {
		return imgarr;
	}
	public void setImgarr(String[] imgarr) {
		this.imgarr = imgarr;
	}
	public String getPreviewimg() {
		return previewimg;
	}
	public void setPreviewimg(String previewimg) {
		this.previewimg = previewimg;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Timestamp getRegdate() {
		return regdate;
	}
	
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public void setRegdate(Timestamp regdate) {
		this.regdate = regdate;
	}
	public int getGrade() {
		return grade;
	}
	public void setGrade(int grade) {
		this.grade = grade;
	}
	public int getReviewcnt() {
		return reviewcnt;
	}
	public void setReviewcnt(int reviewcnt) {
		this.reviewcnt = reviewcnt;
	}
	public int getLikecnt() {
		return likecnt;
	}
	public void setLikecnt(int likecnt) {
		this.likecnt = likecnt;
	}
	public int getTargetvolume() {
		return targetvolume;
	}
	public void setTargetvolume(int targetvolume) {
		this.targetvolume = targetvolume;
	}
	public int getRemainvolume() {
		return remainvolume;
	}
	public void setRemainvolume(int remainvolume) {
		this.remainvolume = remainvolume;
	}
	public String getUpDir() {
		return upDir;
	}
	public void setUpDir(String upDir) {
		this.upDir = upDir;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	
}
