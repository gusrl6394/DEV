package board.service;

import java.util.List;

import board.domain.FoodvisorVO;

public interface FoodvisorService {
	public abstract List<FoodvisorVO> Reviewlist();
	public abstract void Reviewdelete(int seq);
	public abstract int Reviewedit(FoodvisorVO foodvisorVO);
	public abstract void Reviewwrite(FoodvisorVO foodvisorVO);
	public abstract void updateReviewcnt(int seq);
	public abstract void updateReviewlike(int seq);
	public abstract FoodvisorVO reviewread(int seq);
}
