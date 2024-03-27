package member;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import hobby.HobbyDAO;
import hobby.HobbyVO;

public class MemberDAO {

	private static Connection conn = null;
	private static PreparedStatement memberListPstmt = null;
	private static PreparedStatement memberListPstmt2 = null;
	private static PreparedStatement memberInsertPstmt = null;
	private static PreparedStatement memberDeletePstmt = null;
	private static PreparedStatement memberUpdatePstmt = null;
	private static PreparedStatement memberDetailPstmt = null;
	private static PreparedStatement memberDetailWithOutHobbyPstmt = null;
	HobbyDAO hobbyDAO = new HobbyDAO();

	static {
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/miniproj-db", "root", "1004");
			System.out.println("연결 성공");
			conn.setAutoCommit(false);

			memberListPstmt = conn.prepareStatement("select * from tb_member");
			memberListPstmt2 = conn.prepareStatement("select * from tb_member where name like ?");
			memberInsertPstmt = conn.prepareStatement(
					"insert into tb_member (member_id, pwd, name, addr, phone, gender) values (?, ?, ?, ?, ?, ?)");
			memberDetailPstmt = conn.prepareStatement(
					"select m.*, group_concat(h.hobby) as hobbies from tb_member m inner join tb_member_hobby mh on m.member_id = mh.member_id inner join tb_hobby h on mh.hobby_id = h.hobby_id where m.member_id = ? group by  m.member_id");
			memberDeletePstmt = conn.prepareStatement("delete from tb_member where member_id = ?");
			memberUpdatePstmt = conn.prepareStatement(
					"update tb_member set pwd = ?, name = ?, addr = ?, phone = ?, gender = ? where member_id = ?");
			memberDetailWithOutHobbyPstmt = conn.prepareStatement("select * from tb_member where member_id = ?");

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	public List<MemberVO> list(MemberVO member) {

		List<MemberVO> list = new ArrayList<>();
		try {
			ResultSet rs = null;
			if (member != null && !member.isEmptySearchKey()) {
				// 검색 키워드 설정
				memberListPstmt2.setString(1, "%" + member.getSearchKey() + "%");
				rs = memberListPstmt2.executeQuery();
			} else {
				rs = memberListPstmt.executeQuery();
			}
			while (rs.next()) {
				MemberVO memberVO = new MemberVO(rs.getString("member_id"), rs.getString("pwd"), rs.getString("name"),
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
		ResultSet rs = null;
		System.out.println("데이터 가져오기");
		try {
			memberDetailPstmt.setString(1, memberVO.getId());
			rs = memberDetailPstmt.executeQuery();
			if (rs.next() && rs.getString("hobbies") != null) {
				HobbyVO hobby = new HobbyVO();
				hobby.setHobby(rs.getString("hobbies"));
				member = new MemberVO(rs.getString("member_id"), rs.getString("pwd"), rs.getString("name"),
						rs.getString("addr"), rs.getString("phone"), rs.getString("gender"), hobby);
			} else {
				memberDetailWithOutHobbyPstmt.setString(1, memberVO.getId());
				rs = memberDetailWithOutHobbyPstmt.executeQuery();
				if (rs.next()) {
					member = new MemberVO(rs.getString("member_id"), rs.getString("pwd"), rs.getString("name"),
							rs.getString("addr"), rs.getString("phone"), rs.getString("gender"));
				}
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(member);
		return member;
	}

	public int delete(MemberVO memberVO) {
		int updated = 0;
		try {
			memberDeletePstmt.setString(1, memberVO.getId());
			updated = memberDeletePstmt.executeUpdate();
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return updated;
	}

	public int update(MemberVO memberVO) {
		int updated = 0;
		try {
			memberUpdatePstmt.setString(1, memberVO.getPwd());
			memberUpdatePstmt.setString(2, memberVO.getName());
			memberUpdatePstmt.setString(3, memberVO.getAddr());
			memberUpdatePstmt.setString(4, memberVO.getPhone());
			memberUpdatePstmt.setString(5, memberVO.getGender());
			memberUpdatePstmt.setString(6, memberVO.getId());
			updated = memberUpdatePstmt.executeUpdate();
			conn.commit();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return updated;
	}

	public int insert(MemberVO memberVO) {
		int updated = 0;
		try {
			memberInsertPstmt.setString(1, memberVO.getId());
			memberInsertPstmt.setString(2, memberVO.getPwd());
			memberInsertPstmt.setString(3, memberVO.getName());
			memberInsertPstmt.setString(4, memberVO.getAddr());
			memberInsertPstmt.setString(5, memberVO.getPhone());
			memberInsertPstmt.setString(6, memberVO.getGender());
			updated = memberInsertPstmt.executeUpdate();
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return updated;
	}
}
