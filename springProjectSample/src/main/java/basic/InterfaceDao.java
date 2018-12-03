package basic;

import java.util.List;

public interface InterfaceDao {
	
	//불러 오는 함수가 있어야함	
	//매번 hit, recent 내용을 불러옴.
	public abstract List<BasicBoardDto>getList();
	public abstract String getTableName();
	
}
