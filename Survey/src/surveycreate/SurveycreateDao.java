package surveycreate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import common.ConnUtil;

public class SurveycreateDao {
	private static SurveycreateDao instance = null;
	private SurveycreateDao() {}
	public static SurveycreateDao getInstance(){
		if(instance == null) {
			synchronized (SurveycreateDao.class) {
				instance = new SurveycreateDao();
			}
		}
		return instance;
	}
	public boolean insertArticle(SurveycreateDto article) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql="";
		boolean flag= false;
		try {
			conn = ConnUtil.getConnection();
			int ex;		
			sql = "insert into SURVEYCREATE values(SURVEYCREATE_SEQ.nextval,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, article.getId());
			pstmt.setString(2, article.getTag());
			pstmt.setInt(3, article.getNo());
			pstmt.setInt(4, article.getNoref());
			pstmt.setInt(5, article.getNodepth());
			pstmt.setString(6, article.getQtitle());
			pstmt.setString(7, article.getQcontent());
			pstmt.setString(8, article.getQcontenttype());
			pstmt.setInt(9, article.getQcontentmax());
			pstmt.setString(10, article.getQcontentnec());
			pstmt.setInt(11, article.getQcontentnum());
			pstmt.setInt(12, article.getQjumpanswer());
			pstmt.setInt(13, article.getQjump());
			ex = pstmt.executeUpdate();
			if(ex>0) flag = true;
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(rs!=null)try {rs.close();}catch(SQLException e) {}
			if(pstmt!=null)try {pstmt.close();}catch(SQLException e) {}
			if(conn!=null)try {conn.close();}catch(SQLException e) {}
		}
		return flag;
	}
	public List<SurveycreateDto> getArticles(String tag){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<SurveycreateDto> articleList = null;
		try {
			conn = ConnUtil.getConnection();
			pstmt=conn.prepareStatement("select * from SURVEYCREATE where tag=? order by num");
			pstmt.setString(1, tag);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				articleList = new ArrayList<SurveycreateDto>();
				do {
					SurveycreateDto article = new SurveycreateDto();
					article.setNum(rs.getInt("num"));
					article.setId(rs.getString("id"));
					article.setTag(rs.getString("tag"));
					article.setNo(rs.getInt("no"));
					article.setNoref(rs.getInt("noref"));
					article.setNodepth(rs.getInt("nodepth"));
					article.setQtitle(rs.getString("qtitle"));
					article.setQcontent(rs.getString("qcontent"));
					article.setQcontenttype(rs.getString("qcontenttype"));
					article.setQcontentmax(rs.getInt("qcontentmax"));
					article.setQcontentnec(rs.getString("qcontentnec"));
					article.setQcontentnum(rs.getInt("qcontentnum"));
					article.setQjumpanswer(rs.getInt("qjumpanswer"));
					article.setQjump(rs.getInt("qjump"));
					articleList.add(article);
				}while(rs.next());
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			if(rs!=null)try {rs.close();}catch(SQLException e) {}
			if(pstmt!=null)try {pstmt.close();}catch(SQLException e) {}
			if(conn!=null)try {conn.close();}catch(SQLException e) {}
		}
		return articleList;
	}
	public SurveycreateDto getArticle(int num, String tag) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		SurveycreateDto article = null;
		try {
			conn = ConnUtil.getConnection();
			pstmt=conn.prepareStatement("select * from SURVEYCREATE where num=? and tag=?");
			pstmt.setInt(1, num);
			pstmt.setString(2, tag);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				article = new SurveycreateDto();
				article.setNum(rs.getInt("num"));
				article.setId(rs.getString("id"));
				article.setTag(rs.getString("tag"));
				article.setNo(rs.getInt("no"));
				article.setNoref(rs.getInt("noref"));
				article.setNodepth(rs.getInt("nodepth"));
				article.setQtitle(rs.getString("qtitle"));
				article.setQcontent(rs.getString("qcontent"));
				article.setQcontenttype(rs.getString("qcontenttype"));
				article.setQcontentmax(rs.getInt("qcontentmax"));
				article.setQcontentnec(rs.getString("qcontentnec"));
				article.setQcontentnum(rs.getInt("qcontentnum"));
				article.setQjumpanswer(rs.getInt("qjumpanswer"));
				article.setQjump(rs.getInt("qjump"));
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			if(rs!=null)try {rs.close();}catch(SQLException e) {}
			if(pstmt!=null)try {pstmt.close();}catch(SQLException e) {}
			if(conn!=null)try {conn.close();}catch(SQLException e) {}
		}
		return article;
	}
	public boolean updateArticle(SurveycreateDto article) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql="";
		boolean flag= false;
		try {
			conn = ConnUtil.getConnection();
			int ex;		
			sql = "update SURVEYCREATE set no=?, noref=?, qtitle=?, qcontent=?, qcontenttype=?, qcontentmax=?, qcontentnec=?, qcontentnum=?, qjumpanswer=?, qjump=? where num = ? and tag = ?";			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, article.getNo());
			pstmt.setInt(2, article.getNoref());
			pstmt.setString(3, article.getQtitle());
			pstmt.setString(4, article.getQcontent());
			pstmt.setString(5, article.getQcontenttype());
			pstmt.setInt(6, article.getQcontentmax());
			pstmt.setString(7, article.getQcontentnec());
			pstmt.setInt(8, article.getQcontentnum());
			pstmt.setInt(9, article.getQjumpanswer());
			pstmt.setInt(10, article.getQjump());
			pstmt.setInt(11, article.getNum());
			pstmt.setString(12, article.getTag());
			ex = pstmt.executeUpdate();
			if(ex>0) flag = true;
			if(flag==true) {
				pstmt.close();
				sql="update SURVEYDATE set editdate=? where tag=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
				pstmt.setString(2, article.getTag());
				pstmt.executeUpdate();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(rs!=null)try {rs.close();}catch(SQLException e) {}
			if(pstmt!=null)try {pstmt.close();}catch(SQLException e) {}
			if(conn!=null)try {conn.close();}catch(SQLException e) {}
		}
		return flag;
	}
	public void deleteArticle(String tag) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql="";
		try {			
			conn = ConnUtil.getConnection();			
			sql = "select count(*) from SURVEYCREATE where tag=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, tag);			
			int count = pstmt.executeUpdate();
			pstmt.close();
			if(count>0) {
				sql = "delete from SURVEYCREATE where tag=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, tag);
				pstmt.executeUpdate();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(rs!=null)try {rs.close();}catch(SQLException e) {}
			if(pstmt!=null)try {pstmt.close();}catch(SQLException e) {}
			if(conn!=null)try {conn.close();}catch(SQLException e) {}
		}
	}
}
