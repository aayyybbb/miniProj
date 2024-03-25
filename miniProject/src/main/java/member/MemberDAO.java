package member;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MemberDAO {

	private static Connection conn = null;
	private static PreparedStatement memberListPstmt = null;
	private static PreparedStatement memberInsertPstmt = null;
	private static PreparedStatement memberDeletePstmt = null;
	private static PreparedStatement memberUpdatePstmt = null;
	private static PreparedStatement memberDetailPstmt = null;

	static {
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/miniproj-db", "root", "1004");
			System.out.println("연결 성공");
			conn.setAutoCommit(false);

			memberListPstmt = conn.prepareStatement("select * from tb_member");
			memberInsertPstmt = conn.prepareStatement(
					"insert into tb_member (id, pwd, name, addr, phone, gender) values (?, ?, ?, ?, ?, ?)");
			memberDetailPstmt = conn.prepareStatement(
					"SELECT m.*, GROUP_CONCAT(h.hobby) AS hobbies FROM tb_member m INNER JOIN tb_member_hobby mh ON m.id = mh.member_id INNER JOIN tb_hobby h ON mh.hobby_id = h.id WHERE m.id = ? GROUP BY m.id");
			memberDeletePstmt = conn.prepareStatement("delete from tb_member where id = ?");
			memberUpdatePstmt = conn.prepareStatement(
					"update tb_member set id = ?, pwd = ?, name = ?, addr = ?, phone = ?, gender = ?");

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	public List<MemberVO> list() {

		List<MemberVO> list = new ArrayList<>();
		try {
			ResultSet rs = memberListPstmt.executeQuery();
			while (rs.next()) {
				MemberVO memberVO = new MemberVO(rs.getString("id"), rs.getString("pwd"), rs.getString("name"),
						rs.getString("addr"), rs.getString("phone"), rs.getString("gender"));
				list.add(memberVO);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public MemberVO read(MemberVO memberVO) {
		MemberVO member = null;
		try {
			memberDetailPstmt.setString(1, memberVO.getId());
			ResultSet rs = memberDetailPstmt.executeQuery();
			if (rs.next()) {
				member = new MemberVO(rs.getString("id"), rs.getString("pwd"), rs.getString("name"),
						rs.getString("addr"), rs.getString("phone"), rs.getString("gender"));
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return member;
	}
}
