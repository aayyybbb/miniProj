package hobby;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import member.MemberVO;

public class HobbyDAO {
	private static Connection conn = null;
	private static PreparedStatement hobbyInsertPstmt = null;
	private static PreparedStatement hobbyDeletePstmt = null;

	static {
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/miniproj-db", "root", "1004");
			System.out.println("연결 성공");
			conn.setAutoCommit(false);

			hobbyInsertPstmt = conn.prepareStatement("insert into tb_member_hobby (member_id, hobby_id) values(?, ?)");
			hobbyDeletePstmt = conn.prepareStatement("delete from tb_member_hobby where member_id = ?");

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	public int insertHobby(MemberVO memberVO) {
		int updated = 0;
		try {
			for (int i = 0; i < memberVO.getHobbyVO().getHobbyId().size(); i++) {
				String hobby = memberVO.getHobbyVO().getHobbyId().get(i);
				hobbyInsertPstmt.setString(1, memberVO.getId());
				hobbyInsertPstmt.setString(2, hobby);
				hobbyInsertPstmt.executeUpdate();
				if (i == memberVO.getHobbyVO().getHobbyId().size() - 1) {
					updated = 1;
					conn.commit();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("취미 저장 실패");
		}
		return updated;
	}

	public void deleteHobby(MemberVO memberVO) {
		try {
			hobbyDeletePstmt.setString(1, memberVO.getId());
			hobbyDeletePstmt.executeUpdate();
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("취미 삭제 실패");
		}
	}

}
