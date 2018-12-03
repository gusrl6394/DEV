package surveycreate;

public class SurveycreateDto {
	private int num;
	private String id;
	private String tag;
	private int no;
	private int noref;
	private int nodepth;
	private String qtitle;
	private String qcontent;
	private String qcontenttype;
	private int qcontentmax;
	private String qcontentnec;
	private int qcontentnum;
	private int qjumpanswer;
	private int qjump;
	public SurveycreateDto() {
		this.nodepth=0; // 1-1, 1-2 아직 미구현
		this.qcontent=null; // 답안(단답,서술형)
		this.qcontentmax=-1; // 답안중복갯수
		this.qcontentnec=null; // 답안필수
		this.qcontentnum=-1; // 답안갯수
		this.qjumpanswer=-1; // 답안선택시 질문 점프
		this.qjump=-1; // 해당질문으로 점프 그 사이 점프 비활성화
	}
	public int getNum() {		
		return num;
	}
	public void setNum(int num) {
		System.out.println("num:"+num);
		this.num = num;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		System.out.println("id:"+id);
		this.id = id;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		System.out.println("tag:"+tag);
		this.tag = tag;
	}
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		System.out.println("no:"+no);
		this.no = no;
	}
	public int getNoref() {
		return noref;
	}
	public void setNoref(int noref) {
		System.out.println("noref:"+noref);
		this.noref = noref;
	}
	public int getNodepth() {
		return nodepth;
	}
	public void setNodepth(int nodepth) {
		System.out.println("nodepth:"+nodepth);
		this.nodepth = nodepth;
	}
	public String getQtitle() {
		return qtitle;
	}
	public void setQtitle(String qtitle) {
		System.out.println("qtitle:"+qtitle);
		this.qtitle = qtitle;
	}
	public String getQcontent() {
		return qcontent;
	}
	public void setQcontent(String qcontent) {
		System.out.println("qcontent:"+qcontent);
		this.qcontent = qcontent;
	}
	public String getQcontenttype() {
		return qcontenttype;
	}
	public void setQcontenttype(String qcontenttype) {
		System.out.println("qcontenttype:"+qcontenttype);
		this.qcontenttype = qcontenttype;
	}
	public int getQcontentmax() {
		return qcontentmax;
	}
	public void setQcontentmax(int qcontentmax) {
		System.out.println("qcontentmax:"+qcontentmax);
		this.qcontentmax = qcontentmax;
	}
	public String getQcontentnec() {
		return qcontentnec;
	}
	public void setQcontentnec(String qcontentnec) {
		System.out.println("qcontentnec:"+qcontentnec);
		this.qcontentnec = qcontentnec;
	}
	public int getQcontentnum() {
		return qcontentnum;
	}
	public void setQcontentnum(int qcontentnum) {
		System.out.println("qcontentnum:"+qcontentnum);
		this.qcontentnum = qcontentnum;
	}
	public int getQjumpanswer() {
		return qjumpanswer;
	}
	public void setQjumpanswer(int qjumpanswer) {
		System.out.println("qjumpanswer:"+qjumpanswer);
		this.qjumpanswer = qjumpanswer;
	}
	public int getQjump() {
		return qjump;
	}
	public void setQjump(int qjump) {
		System.out.println("qjump:"+qjump);
		this.qjump = qjump;
	}
}
