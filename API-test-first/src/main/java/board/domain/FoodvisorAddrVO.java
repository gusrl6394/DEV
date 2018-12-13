package board.domain;

public class FoodvisorAddrVO {
	private String currentPage;
	private String countPerPage;
	private String resultType;
	// 도로명 주소 API키
	private static final String confmKey = "###";
	private String query;
	public String getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}
	public String getCountPerPage() {
		return countPerPage;
	}
	public void setCountPerPage(String countPerPage) {
		this.countPerPage = countPerPage;
	}
	public String getResultType() {
		return resultType;
	}
	public void setResultType(String resultType) {
		this.resultType = resultType;
	}	
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public static String getConfmkey() {
		return confmKey;
	}
	
}
