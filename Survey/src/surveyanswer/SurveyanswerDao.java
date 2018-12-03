package surveyanswer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import common.ConnUtil;

public class SurveyanswerDao {
	private static SurveyanswerDao instance = null;
	private SurveyanswerDao() {}
	public static SurveyanswerDao getInstance(){
		if(instance == null) {
			synchronized (SurveyanswerDao.class) {
				instance = new SurveyanswerDao();
			}
		}
		return instance;
	}
	public void insertArticle(SurveyanswerDto article) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql="";
		try {
			conn = ConnUtil.getConnection();
			sql = "insert into SURVEYANSWERCONTENT values(SURVEYANSWERCONTENT_SEQ.nextval,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, article.getId());
			pstmt.setInt(2, article.getRnum());
			pstmt.setString(3, article.getRcontent());
			pstmt.setString(4, article.getTag());
			pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(rs!=null)try {rs.close();}catch(SQLException e) {}
			if(pstmt!=null)try {pstmt.close();}catch(SQLException e) {}
			if(conn!=null)try {conn.close();}catch(SQLException e) {}
		}
	}
	public ArrayList<String[]> gettype(String tag) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<String[]> article = new ArrayList<String[]>();
		try {
			conn = ConnUtil.getConnection();
			pstmt=conn.prepareStatement("select QCONTENTTYPE, QCONTENTNEC from SURVEYCREATE where tag=?");
			pstmt.setString(1, tag);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				String[] temp = new String[2];
				temp[0]=rs.getString(1);
				temp[1]=rs.getString(2);
				article.add(temp);
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
	public void deleteArticle(String tag) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql="";
		try {
			conn = ConnUtil.getConnection();			
			sql = "select count(*) from SURVEYANSWERCONTENT where tag=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, tag);			
			int count = pstmt.executeUpdate();
			pstmt.close();
			if(count>0) {
				sql = "delete from SURVEYANSWERCONTENT where tag=?";
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
