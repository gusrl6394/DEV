package board.dao;

import java.util.List;

import board.domain.FoodvisorVO;

public interface FoodvisorDao {
	public abstract List<FoodvisorVO> list();
	public abstract void delete(int seq);
	public abstract void deleteAll();
	public abstract int update(FoodvisorVO foodvisorVO);
	public abstract void insert(FoodvisorVO foodvisorVO);
	public abstract FoodvisorVO select(int seq);
	public abstract int updateCnt(int seq);
	public abstract void updatelike(int seq);
}
