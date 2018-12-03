package Main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import basic.BasicBoardDto;
import basic.InterfaceDao;

public class MainDB {
	
	private List<InterfaceDao> dblist;
	private List<BasicBoardDto> AllList;
	private List<BasicBoardDto> Hitlist;
	private List<BasicBoardDto> Recentlist;
	private LinkedHashMap<String, InterfaceDao> dbMap;
	private final int HitMax = 3;
	private final int RecentMax = 5;
	private int listMax = 0;
	
	//정렬 클래스
	HitSort hitSort = new HitSort();
	DateSort dateSort = new DateSort();
	
	//생성자 각각의 리스트 초기화
	public MainDB() {
		dblist = new ArrayList<InterfaceDao>();
		AllList = new ArrayList<BasicBoardDto>();
		Hitlist = new ArrayList<BasicBoardDto>();
		Recentlist = new ArrayList<BasicBoardDto>();
		
		dbMap = new LinkedHashMap<String, InterfaceDao>();
		
	}
	
	//현재 DB 수량 카운트
	public int getDBcount() {
		return dblist.size();
	}
	
	//DB 가져오기
	public ArrayList<InterfaceDao> getDBs(){
		return (ArrayList<InterfaceDao>) dblist;
	}
	
	//Bean을 통해서 각DB 저장하기.
	public void setDblist(List<InterfaceDao> dao) {		
		dblist = dao;
		//setMap(); (2.0.0 버전 이후 사용 예정)
		setList();
	}
	
	
	
	//각각의 DB를 Map에 담기
	public void setMap() {
		for(InterfaceDao dao : dblist) {
			if(dao instanceof InterfaceDao) {
				dbMap.put(dao.getTableName(), dao);				
			}
		}
	}
	public LinkedHashMap<String, InterfaceDao> getMap(){
		return  dbMap;
	}
	//각각의 DB에서 글 뽑아 AllList에 담기
	public void setList() {
		//홈 메인 갱신 될때 마다 호출 되어야함.
		
		Hitlist.clear();
		Recentlist.clear();
		
		for(InterfaceDao dao : dblist) {
			if(dao instanceof InterfaceDao) {
				sort(dao.getList());
			}
		}
		System.out.println("set List");
		
	}
	
	//인기글, 최근글 정렬
	public void sort(List<BasicBoardDto> list) {
		
		Collections.sort(list, hitSort);
		setHitList(list);
		
		Collections.sort(list, dateSort);
		setRecentList(list);
	}
	
	//인기 글 저장
	public void setHitList(List<BasicBoardDto> list){		
		System.out.println("setHitList" + list.size());
		if(list.size() >= HitMax)
			listMax = HitMax;
		else
			listMax = list.size();
		
		for(int i = 0; i < listMax; i++) {
			Hitlist.add(list.get(i));
		}
	}

	//인기글 가져감
	public List<BasicBoardDto> getHitList(){
		return Hitlist;
	}

	//최근 글 저장
	public void setRecentList(List<BasicBoardDto> list){	
		System.out.println("setRecentList");
		/* (2.0.0 버전 이후 사용 예정)
		if(list.size() >= RecentMax)
			listMax = RecentMax;
		else
			listMax = list.size();
		*/
		for(int i = 0; i < list.size(); i++) {
			Recentlist.add(list.get(i));
		}
	}
	
	//최근글 가져감
	public List<BasicBoardDto> getRecentList(){
		return Recentlist;
	}
	
	
	//조회수 정렬
	class HitSort implements Comparator<BasicBoardDto>{

		@Override
		public int compare(BasicBoardDto o1, BasicBoardDto o2) {
			// TODO Auto-generated method stub
			if(o1.getReadcount() > o2.getReadcount())
				return -1;
			else
				return 1;
		}
	}
	//날짜별 정렬
	class DateSort implements Comparator<BasicBoardDto>{

		@Override
		public int compare(BasicBoardDto o1, BasicBoardDto o2) {
			// TODO Auto-generated method stub
			String str1 = o1.getRegdate()+"";
			String str2 = o2.getRegdate()+"";
			
			return str2.compareTo(str1);	
		}
	}
}


