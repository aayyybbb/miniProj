package hobby;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import member.MemberVO;

public class HobbyDAO {
	private static Connection conn = null;
	private static PreparedStatement hobbyInsertPstmt = null;
	private static PreparedStatement hobbyDeletePstmt = null;
	private static PreparedStatement hobbyInfoPstmt = null;
	private static PreparedStatement memberHobbyDetailPstmt = null;
	static {
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/miniproj-db", "root", "1004");
			System.out.println("연결 성공");
			conn.setAutoCommit(false);

			hobbyInsertPstmt = conn.prepareStatement("insert into tb_member_hobby (member_id, hobby_id) values(?, ?)");
			hobbyDeletePstmt = conn.prepareStatement("delete from tb_member_hobby where member_id = ?");
			hobbyInfoPstmt = conn.prepareStatement("select * from tb_hobby");
			memberHobbyDetailPstmt = conn.prepareStatement(
					"select h.hobby_id, h.hobby from tb_hobby h join tb_member_hobby mh on h.hobby_id = mh.hobby_id where mh.member_id = ?");

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	public int insertHobby(MemberVO memberVO) {
		int updated = 0;
		try {
			for (int i = 0; i < memberVO.getHobbyVO().getHobbyIdList().size(); i++) {
				String hobby = memberVO.getHobbyVO().getHobbyIdList().get(i);
				hobbyInsertPstmt.setString(1, memberVO.getId());
				hobbyInsertPstmt.setString(2, hobby);
				hobbyInsertPstmt.executeUpdate();
				if (i == memberVO.getHobbyVO().getHobbyIdList().size() - 1) {
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

	public List<HobbyVO> hobbyList() {
		List<HobbyVO> list = new ArrayList<>();
		try {
			ResultSet rs = null;
			rs = hobbyInfoPstmt.executeQuery();
			while (rs.next()) {
				HobbyVO hobbyVO = new HobbyVO(rs.getString("hobby"), rs.getString("hobby_id"));

				list.add(hobbyVO);

				System.out.println("취미 저장");
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;

	}

	public List<HobbyVO> selectedHobbyList(MemberVO memberVO) {
		List<HobbyVO> list = new ArrayList<>();
		try {
			ResultSet rs = null;
			rs = hobbyInfoPstmt.executeQuery();
			while (rs.next()) {
				for (HobbyVO hobbyVO : memberHobbyDetail(memberVO)) {
					if (hobbyVO.getHobbyId() == rs.getString("hobby_id")
							&& hobbyVO.getHobby() == rs.getString("hobby")) {
						HobbyVO selectedHobby = new HobbyVO(rs.getString("hobby"), rs.getString("hobby_id"));
						list.add(selectedHobby);
					}
				}
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<HobbyVO> memberHobbyDetail(MemberVO memberVO) {
		List<HobbyVO> list = new ArrayList<>();
		System.out.println("취미 소환");
		try {
			ResultSet rs = null;
			memberHobbyDetailPstmt.setString(1, memberVO.getId());
			rs = memberHobbyDetailPstmt.executeQuery();
			while (rs.next()) {
				HobbyVO hobbyVO = new HobbyVO(rs.getString("hobby"), rs.getString("hobby_id"));
				list.add(hobbyVO);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}
