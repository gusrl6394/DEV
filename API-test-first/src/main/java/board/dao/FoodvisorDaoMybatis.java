package board.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import board.domain.FoodvisorVO;
@Repository
public class FoodvisorDaoMybatis implements FoodvisorDao{
	private SqlSessionTemplate sqlSessionTemplate;
	public FoodvisorDaoMybatis(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}
	public void setsqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}
	@Override
	public List<FoodvisorVO> list() {
		System.out.println("List<FoodvisorVO> list() 호출됨");
		return sqlSessionTemplate.selectList("foodvisorDao.list");
	}

	@Override
	public void delete(int seq) {
		sqlSessionTemplate.delete("foodvisorDao.delete",seq);
	}

	@Override
	public void deleteAll() {
		sqlSessionTemplate.delete("foodvisorDao.deleteAll");
	}

	@Override
	public int update(FoodvisorVO foodvisorVO) {
		return sqlSessionTemplate.update("foodvisorDao.update",foodvisorVO);
	}

	@Override
	public void insert(FoodvisorVO foodvisorVO) {
		//System.out.println(foodvisorVO.getTitle() + " / " + foodvisorVO.getAddress() + " / " + foodvisorVO.getImg()  + " / " + foodvisorVO.getContent() + " / " + foodvisorVO.getWriter() + " / " + foodvisorVO.getGrade() + ";");
		foodvisorVO.setWriter("Anonymous");
		System.out.println(foodvisorVO.getTitle() + " / " + foodvisorVO.getAddress() + " / " + foodvisorVO.getImg()  + " / " + foodvisorVO.getContent() + " / " + foodvisorVO.getWriter() + " / " + foodvisorVO.getGrade() + ";");
		sqlSessionTemplate.insert("foodvisorDao.reviewinsert", foodvisorVO);
	}

	@Override
	public FoodvisorVO select(int seq) {
		FoodvisorVO vo = (FoodvisorVO) sqlSessionTemplate.selectOne("foodvisorDao.select", seq);
		return vo;
	}

	@Override
	public int updateCnt(int seq) {
		return sqlSessionTemplate.update("foodvisorDao.updateCnt", seq);
	}
	@Override
	public void updatelike(int seq) {
		sqlSessionTemplate.update("foodvisorDao.updatelike", seq);
	}
	
}
