package foodvisor.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import org.apache.ibatis.type.Alias;
import org.springframework.web.multipart.MultipartFile;
@Alias("foodvisorVO")
public class FoodvisorVO implements Serializable{
	// 시퀀스
	private int seq;
	// 제목
	private String title;
	// 주소
	private String address;
	// DB에 이미지 파일이름 저장될 변수
	private String img;
	// 클라이언트에서 서버로 업로드시 파일이 담길 임시 장소
	private List<MultipartFile> imgPath;
	// View에 담겨질 이미지 배열
	private String[] imgarr;
	// 리스트에 보여줄 미리보기 이미지 파일이름
	private String previewimg;
	// 내용
	private String content;
	// 글쓴이
	private String writer;
	// 작성일자
	private Timestamp regdate;
	// 점수
	private int grade;
	// 조회수
	private int reviewcnt;
	// 좋아요
	private int likecnt;
	// 패스워드
	private String pwd;
	public FoodvisorVO() { }
	public FoodvisorVO(String title, String address, String img, String content, String writer, Timestamp regdate, int reviewcnt, int likecnt, String pwd) {
		super();
		this.title = title;
		this.address = address;
		this.img = img;
		this.content = content;
		this.writer = writer;
		this.regdate = regdate;
		this.reviewcnt = reviewcnt;
		this.likecnt = likecnt;
		this.pwd = pwd;
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
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	@Override
	public String toString() {
		return "FoodvisorVO [seq=" + seq + ", title=" + title + ", address=" + address + ", img=" + img + ", imgPath="
				+ imgPath + ", imgarr=" + Arrays.toString(imgarr) + ", previewimg=" + previewimg + ", content="
				+ content + ", writer=" + writer + ", regdate=" + regdate + ", grade=" + grade + ", reviewcnt="
				+ reviewcnt + ", likecnt=" + likecnt + ", pwd=" + pwd + "]";
	}
	
	
}
