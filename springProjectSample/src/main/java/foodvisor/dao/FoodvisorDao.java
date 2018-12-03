package foodvisor.dao;

import java.util.List;

import basic.InterfaceDao;
import foodvisor.domain.FoodvisorVO;
import foodvisor.domain.Paging;

public interface FoodvisorDao extends InterfaceDao{
	public abstract List<FoodvisorVO> list();
	public abstract void delete(int seq);
	public abstract void deleteAll();
	public abstract int update(FoodvisorVO foodvisorVO);
	public abstract void insert(FoodvisorVO foodvisorVO);
	public abstract FoodvisorVO select(int seq);
	public abstract int updateCnt(int seq);
	public abstract void updatelike(int seq);
	public abstract String pwdconfirm(int seq);
	public abstract List<FoodvisorVO> paging(Paging paing);
	public abstract int totalcount();
}
