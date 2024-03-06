package com.saeyan.dto;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

//싱글톤
public class MemberDAO {
	private static MemberDAO instance = new MemberDAO();
	private Connection con = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	private MemberDAO() {
		
	}
	public static MemberDAO getInstance() {
		return instance;
	}
	
	public Connection getConnection() throws Exception {
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String user = "ezen";
		String password = "1234";
		
		Class.forName("oracle.jdbc.driver.OracleDriver");	//1. 드라이브 로드
		return DriverManager.getConnection(url, user, password);	//2. DB연결
	}
	public int confirmID(String userid) {	//ID중복체크
		int result = -1;
		String sql = "select userid from member where userid = ?";
		
		try {
			con = getConnection();	//1. DB 연결
			pstmt = con.prepareStatement(sql);	//2. SQL문 전송
			pstmt.setString(1, userid);	//3. 맵핑
			rs = pstmt.executeQuery();	//4. 실행 및 결과값 받기
			
			if(rs.next()) {
				result = 1;
			} else {
				result = -1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(con != null) con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
}
