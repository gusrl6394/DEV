package memberone;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import common.ConnUtil;

public class memberDao {
	private static memberDao instance = null;
	private memberDao() {}
	public static memberDao getInstance(){
		if(instance == null) {
			synchronized (memberDao.class) {
				instance = new memberDao();
			}
		}
		return instance;
	}
	// 아이디 체크
	public boolean idCheck(String id) {
		boolean result = true;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = ConnUtil.getConnection();
			String sqlstr = "select * from USERINFO where id = ?";
			pstmt = conn.prepareStatement(sqlstr);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if(!rs.next()) result=false;
		}catch(SQLException sqle) {
			sqle.printStackTrace();
		}finally {
			if(rs!=null)try {rs.close();}catch(SQLException sqle1) {}
			if(pstmt!=null)try {pstmt.close();}catch(SQLException sqle2) {}
			if(conn!=null)try {conn.close();}catch(SQLException sqle3) {}
		}
		return result;
	}
	// 회원가입 동작 구현
	public boolean memberInsert(memberDto dto) { // 매개변수 예제는 대문자로 시작
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean flag= false;
		try {
			conn = ConnUtil.getConnection();
			String strQuery;
			int ex;		
			strQuery = "insert into USERINFO(NUM, REGDATE, ID, PASS, EMAIL, POINT, SURVEYEXE) values(USERINFO_SEQ.nextval,?,?,?,?,?,?)";
			pstmt = conn.prepareStatement(strQuery);
			pstmt.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
			pstmt.setString(2, dto.getId());
			pstmt.setString(3, dto.getPass());
			pstmt.setString(4, dto.getEmail());
			pstmt.setInt(5, dto.getPoint());
			pstmt.setString(6, dto.getSurveyexe());
			ex = pstmt.executeUpdate();
			if(ex>0) flag= true;
		}catch(Exception e) {
			System.out.println("Exception : " + e);
		}finally {
			if(rs != null)try {rs.close();}catch(SQLException sqle1) {}
			if(pstmt != null)try {pstmt.close();}catch(SQLException sqle2) {}
			if(conn != null)try {conn.close();}catch(SQLException sqle3) {}
		}
		return flag;
	}
	// 로그인 동작 구현
	public int loginCheck(String id, String pass) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int check = -1;
		try {
			conn = ConnUtil.getConnection();
			String query = "select PASS from USERINFO where ID = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				String dbPass = rs.getString("pass");
				if(pass.equals(dbPass)) check = 1;
				else check=0;
			}
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("Exception : " + e);
		}finally {
			if(rs != null)try {rs.close();}catch(SQLException sqle1) {}
			if(pstmt != null)try {pstmt.close();}catch(SQLException sqle2) {}
			if(conn != null)try {conn.close();}catch(SQLException sqle3) {}
		}
		return check;
	}
	// 정보수정 동작 구현
	public memberDto getMember(String id) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		memberDto dto = null;
		try {
			conn = ConnUtil.getConnection();
			String query = "select * from USERINFO where ID = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				dto = new memberDto();
				dto.setId(rs.getString("id"));
				dto.setPass(rs.getString("pass"));
				dto.setEmail(rs.getString("email"));
				dto.setRegdate(rs.getTimestamp("regdate"));
			}
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("Exception : " + e);
		}finally {
			if(rs != null)try {rs.close();}catch(SQLException sqle1) {}
			if(pstmt != null)try {pstmt.close();}catch(SQLException sqle2) {}
			if(conn != null)try {conn.close();}catch(SQLException sqle3) {}
		}
		return dto;
	}
	public void memberUpdate(memberDto dto) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = ConnUtil.getConnection();
			String query = "update USERINFO set PASSWORD=? where ID = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, dto.getPass());
			pstmt.setString(2, dto.getId());
			pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("Exception : " + e);
		}finally {
			if(pstmt != null)try {pstmt.close();}catch(SQLException sqle2) {}
			if(conn != null)try {conn.close();}catch(SQLException sqle3) {}
		}
	}
	//회원정보 탈퇴
	public int deleteMember(String id, String pass) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String dbPass="";
		int result = -1;
		try {
			conn = ConnUtil.getConnection();
			String query = "select PASS from USERINFO where ID = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				dbPass = rs.getString("pass");
				if(pass.equals(dbPass)) {
					pstmt.close();
					query="delete from USERINFO where ID=?";
					pstmt = conn.prepareStatement(query);
					pstmt.setString(1, id);
					pstmt.executeUpdate();
					result = 1;
				}
				else {
					result = 0;
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("Exception : " + e);
		}finally {
			if(rs != null)try {rs.close();}catch(SQLException sqle1) {}
			if(pstmt != null)try {pstmt.close();}catch(SQLException sqle2) {}
			if(conn != null)try {conn.close();}catch(SQLException sqle3) {}
		}
		return result;
	}
}