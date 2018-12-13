package board.service;

import java.util.List;

import org.springframework.stereotype.Service;

import board.dao.FoodvisorDao;
import board.domain.FoodvisorVO;
@Service
public class FoodvisorServiceImpl implements FoodvisorService{
	private FoodvisorDao foodvisorDao;
	
	public FoodvisorDao getFoodvisorDao() {
		return foodvisorDao;
	}
	public void setFoodvisorDao(FoodvisorDao foodvisorDao) {
		this.foodvisorDao = foodvisorDao;
	}
	
	public FoodvisorDao getFoodvisorServiceImpl() {
		return foodvisorDao;
	}
	public void setFoodvisorServiceImpl(FoodvisorDao foodvisorDao) {
		this.foodvisorDao = foodvisorDao;
	}
	
	@Override
	public List<FoodvisorVO> Reviewlist() {
		return foodvisorDao.list();
	}

	@Override
	public void Reviewdelete(int seq) {
		foodvisorDao.delete(seq);
	}

	@Override
	public int Reviewedit(FoodvisorVO foodvisorVO) {
		return foodvisorDao.update(foodvisorVO);
	}

	@Override
	public void Reviewwrite(FoodvisorVO foodvisorVO) {
		foodvisorDao.insert(foodvisorVO);
	}

	@Override
	public void updateReviewcnt(int seq) {
		foodvisorDao.updateCnt(seq);
	}
	
	@Override
	public void updateReviewlike(int seq) {
		foodvisorDao.updatelike(seq);
	}	

	@Override
	public FoodvisorVO reviewread(int seq) {		
		return foodvisorDao.select(seq);
	}

	

}
