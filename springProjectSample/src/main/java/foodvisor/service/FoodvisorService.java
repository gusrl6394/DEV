package foodvisor.service;

import java.util.List;

import basic.InterfaceDao;
import foodvisor.domain.FoodvisorVO;
import foodvisor.domain.Paging;

public interface FoodvisorService extends InterfaceDao{
	public abstract List<FoodvisorVO> Reviewlist();
	public abstract void Reviewdelete(int seq);
	public abstract int Reviewedit(FoodvisorVO foodvisorVO);
	public abstract void Reviewwrite(FoodvisorVO foodvisorVO);
	public abstract void updateReviewcnt(int seq);
	public abstract void updateReviewlike(int seq);
	public abstract FoodvisorVO reviewread(int seq);
	public abstract String pwdconfirm(int seq);
	public abstract List<FoodvisorVO> paging(Paging paging);
	public abstract int totalcount();
}
