package foodvisor.domain;

import org.apache.ibatis.type.Alias;

@Alias("paging")
public class Paging {
	// 첫페이지로 이동
	private int firstPage;
	// 이전버튼
	private boolean prev;
	// 시작페이지
	private int startPage;
	// 현재페이지
	private int currentPage;
	// 다음페이지
	private int endPage;
	// 다음버튼
	private boolean next;
	// 맨끝페이지로 이동
	private int finalPage;
	// 게시물 갯수
	private int totalcount;
	// 페이지당 보여줄 갯수
	private int pagelistsize;
	// 페이지 버튼 갯수
	private int pagebuttonsize;
	// 출력할 첫 게시물 NUMBER
	private int startboard;
	// 출력할 마지막 게시물 NUMBER
	private int endboard;
	
	public Paging() {
		this.firstPage = 1;
		this.currentPage = 1;
		this.pagelistsize = 5;
		this.pagebuttonsize = 5;
	}
	
	public void makePaging() {
		// 게시물 갯수 0개인경우
		if(totalcount == 0) {
			System.out.println("게시물 없음");
			return;
		}
		// 마지막 페이지 = 총 게시물 갯수 / 페이지당 보여줄 갯수
		finalPage = totalcount  / pagelistsize;
		// 만약 총 게시물 갯수 / 페이지당 보여줄 갯수가 0으로 나눠떨어지지 않다면 1페이지 추가
		if(totalcount % pagelistsize > 0) {
			finalPage++;
		}
		// startPage 계산
		// EX) 현재페이지 13page, 페이지당 보여줄 갯수 : 5게시글;
		// ((13-1)/5)*5+1 = 11
		startPage = ((currentPage - 1) / pagelistsize) * pagelistsize + 1;
		// endPage 계산
		// EX) 11 + 5 - 1 = 15 
		endPage = startPage + pagelistsize -1;
		// 마지막페이지가 최종페이지를 넘을경우 최종페이지로 맞춤
		if(endPage > finalPage) {
			setEndPage(finalPage);
		}		
		// 현재페이지가 마지막 페이지보다 크다면 마지막페이지로 맞춤
		if(currentPage > finalPage) {			
			setCurrentPage(finalPage);
		}		
		// 페이지 요청시 1미만일경우 1로 설정
		if(currentPage<1) {
			setCurrentPage(1);
		}
		// 이전 버튼 = 처음페이지 == 1? 맞으면 false, 틀리면 true
		prev=startPage==1?false:true;
		// 다음버튼 = 엔드페이지 * 페이지당 보여줄 갯수 >= ? 맞으면 false, 틀리면 true
		next=endPage*pagelistsize>=totalcount?false:true;
		
		// 1페이지 요청시 if문 실행, 아닌경우 else문 실행
		// 여기서 startboard 와 endboard는 쿼리문에 쓸 변수들
		if(currentPage == 1) {
			startboard = 1;
			endboard = 1 * pagelistsize;
		}else {
			startboard = ((currentPage - 1) * pagelistsize) + 1;
			endboard = currentPage * pagelistsize;
		}
	}
	public int getFirstPage() {
		return firstPage;
	}

	public void setFirstPage(int firstPage) {
		this.firstPage = firstPage;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public int getTotalcount() {
		return totalcount;
	}

	public void setTotalcount(int totalcount) {
		this.totalcount = totalcount;
	}

	public int getPagelistsize() {
		return pagelistsize;
	}

	public void setPagelistsize(int pagelistsize) {
		this.pagelistsize = pagelistsize;
	}

	public int getPagebuttonsize() {
		return pagebuttonsize;
	}

	public void setPagebuttonsize(int pagebuttonsize) {
		this.pagebuttonsize = pagebuttonsize;
	}

	public boolean isPrev() {
		return prev;
	}

	public void setPrev(boolean prev) {
		this.prev = prev;
	}

	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public boolean isNext() {
		return next;
	}

	public void setNext(boolean next) {
		this.next = next;
	}

	public int getFinalPage() {
		return finalPage;
	}

	public void setFinalPage(int finalPage) {
		this.finalPage = finalPage;
	}

	@Override
	public String toString() {
		return "Paging [firstPage=" + firstPage + ", prev=" + prev + ", startPage=" + startPage + ", currentPage="
				+ currentPage + ", endPage=" + endPage + ", next=" + next + ", finalPage=" + finalPage + ", totalcount="
				+ totalcount + ", pagelistsize=" + pagelistsize + ", pagebuttonsize=" + pagebuttonsize + ", startboard="
				+ startboard + ", endboard=" + endboard + "]";
	}

	
	
	
}
