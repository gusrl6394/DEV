package foodvisor.dao;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import basic.BasicBoardDto;
import foodvisor.domain.FoodvisorVO;
import foodvisor.domain.Paging;
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
		String writertemp = Double.toString(Math.round(Math.random()*10000000));
		writertemp = writertemp.substring(0, writertemp.length()-2);
		String writertemp2 = Double.toString(Math.round(Math.random()*10000000));
		writertemp2 = writertemp2.substring(0, writertemp2.length()-2);
		String result = writertemp + writertemp2;
		foodvisorVO.setWriter(result);
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
	@Override
	public List<BasicBoardDto> getList() {
		Paging paging = new Paging();
		int pagelistsize = paging.getPagelistsize();
		int targetPage = 1;
		int indexpage = 0;
		
		List<BasicBoardDto> alllist = new ArrayList<BasicBoardDto>();
		alllist.clear();
		List<FoodvisorVO> list = sqlSessionTemplate.selectList("foodvisorDao.list");
		
		for(FoodvisorVO vo : list) {
			BasicBoardDto dto = new BasicBoardDto(
					vo.getSeq(),
					vo.getTitle(),
					vo.getReviewcnt(),
					vo.getRegdate(),
					"/foodvisor/foodvisorReviewRead?currentPage="+targetPage+"&seq="+vo.getSeq(),
					"foodvisor");
			alllist.add(dto);
			indexpage++;
			if(indexpage == pagelistsize) {
				targetPage++;
				indexpage=0;
			}
		}
		return alllist;
	}
	@Override
	public String getTableName() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String pwdconfirm(int seq) {
		return sqlSessionTemplate.selectOne("foodvisorDao.pwdconfirm",seq);
	}
	@Override
	public List<FoodvisorVO> paging(Paging paging) {
		return sqlSessionTemplate.selectList("foodvisorDao.paging", paging);
	}
	@Override
	public int totalcount() {
		return sqlSessionTemplate.selectOne("totalcount");
	}	
}
