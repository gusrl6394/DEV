package surveydate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import common.ConnUtil;

public class Surveydatedao {
	private static Surveydatedao instance = null;
	private Surveydatedao() {}
	public static Surveydatedao getInstance(){
		if(instance == null) {
			synchronized (Surveydatedao.class) {
				instance = new Surveydatedao();
			}
		}
		return instance;
	}
	public boolean insertArticle(Surveydatedto article) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql="";
		boolean flag= false;
		try {
			conn = ConnUtil.getConnection();
			int ex;		
			sql = "insert into SURVEYDATE values(?,?,?,?,?,?,?,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, article.getTag());
			pstmt.setTimestamp(2, article.getStartdate());
			pstmt.setTimestamp(3, article.getEnddate());
			pstmt.setInt(4, 0);
			pstmt.setInt(5, article.getRespondentlimit());
			pstmt.setInt(6, article.getPoint());
			pstmt.setTimestamp(7, article.getCreatedate());
			pstmt.setTimestamp(8, article.getEditdate());
			pstmt.setInt(9, article.getSurveynum());
			pstmt.setString(10, article.getTarget());
			pstmt.setString(11, article.getId());
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
		// BOARD_SEQ.nextval
	}
	public int getArticleCount() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int x = 0;
		try {
			conn = ConnUtil.getConnection();
			pstmt = conn.prepareStatement("select count(*) from SURVEYDATE");
			rs = pstmt.executeQuery();
			if(rs.next()) {
				x = rs.getInt(1);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			if(rs!=null)try {rs.close();}catch(SQLException e) {}
			if(pstmt!=null)try {pstmt.close();}catch(SQLException e) {}
			if(conn!=null)try {conn.close();}catch(SQLException e) {}
		}
		return x;
	}
	public List<Surveydatedto> getArticles(){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Surveydatedto> articleList = null;
		try {
			conn = ConnUtil.getConnection();
			pstmt=conn.prepareStatement("select * from SURVEYDATE order by createdate asc");
			rs = pstmt.executeQuery();
			if(rs.next()) {
				articleList = new ArrayList<Surveydatedto>();
				do {
					Surveydatedto article = new Surveydatedto();
					article.setTag(rs.getString("tag"));
					article.setStartdate(rs.getTimestamp("startdate"));
					article.setEnddate(rs.getTimestamp("enddate"));
					article.setRespondent(rs.getInt("respondent"));
					article.setRespondentlimit(rs.getInt("respondentlimit"));
					article.setPoint(rs.getInt("point"));
					article.setCreatedate(rs.getTimestamp("createdate"));
					article.setEditdate(rs.getTimestamp("editdate"));
					article.setSurveynum(rs.getInt("surveynum"));
					article.setTarget(rs.getString("target"));
					article.setId(rs.getString("id"));
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
	public int taglastnumCheck(String tag){
		int lastnum = -1;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = ConnUtil.getConnection();
			pstmt = conn.prepareStatement("select count(*) from SURVEYCREATE where tag=?");
			pstmt.setString(1, tag);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				lastnum = rs.getInt(1);
				System.out.println("lastnum : " + lastnum);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			if(rs!=null)try {rs.close();}catch(SQLException e) {}
			if(pstmt!=null)try {pstmt.close();}catch(SQLException e) {}
			if(conn!=null)try {conn.close();}catch(SQLException e) {}
		}		
		return lastnum;
	}
	public int taglastnum(String tag){
		int last = -1;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = ConnUtil.getConnection();
			pstmt = conn.prepareStatement("select SURVEYNUM from SURVEYDATE where tag=?");
			pstmt.setString(1, tag);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				last = rs.getInt(1);
				System.out.println("last : " + last);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			if(rs!=null)try {rs.close();}catch(SQLException e) {}
			if(pstmt!=null)try {pstmt.close();}catch(SQLException e) {}
			if(conn!=null)try {conn.close();}catch(SQLException e) {}
		}		
		return last;
	}
	public int getsurveyattendtarget(String tag) {
		int result=-1;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = ConnUtil.getConnection();
			pstmt = conn.prepareStatement("select TARGET from SURVEYDATE where tag=?");
			pstmt.setString(1, tag);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				result = rs.getInt(1);
				System.out.println("result : " + result);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			if(rs!=null)try {rs.close();}catch(SQLException e) {}
			if(pstmt!=null)try {pstmt.close();}catch(SQLException e) {}
			if(conn!=null)try {conn.close();}catch(SQLException e) {}
		}		
		return result;
	}
	public void deleteArticle(String tag) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql="";
		try {			
			conn = ConnUtil.getConnection();			
			sql = "select count(*) from SURVEYDATE where tag=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, tag);			
			int count = pstmt.executeUpdate();
			pstmt.close();
			if(count>0) {
				sql = "delete from SURVEYDATE where tag=?";
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
