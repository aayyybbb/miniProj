package board;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BoardDAO {
	// 1. 게시물 목록 만들기
	// 2. 삭제 구현
	// 3. 수정 구현
	// 4. 상세보기 구현
	// 5. 전체삭제 구현
	// 6. 등록 구현
	private static Connection conn = null;
	private static PreparedStatement boardListPstmt = null;
	private static PreparedStatement boardListPstmt2 = null;
	private static PreparedStatement boardInsertPstmt = null;
	private static PreparedStatement boardDeletePstmt = null;
	private static PreparedStatement boardDetailPstmt = null;
	private static PreparedStatement boardWriterPstmt = null;
	private static PreparedStatement boardUpdatePstmt = null;
	private static PreparedStatement boardDeleteAllPstmt = null;
	private static PreparedStatement viewCountUpdatePstmt = null;

	static {

		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/miniproj-db", "root", "1004");
			System.out.println("연결 성공");
			conn.setAutoCommit(false);

			boardListPstmt = conn.prepareStatement("select * from tb_board");
			boardListPstmt2 = conn.prepareStatement("select * from tb_board where title like ?");
			boardInsertPstmt = conn
					.prepareStatement("insert into tb_board (title, content, member_id) values ( ?, ?, ?)");
			boardDetailPstmt = conn.prepareStatement("select * from tb_board where board_id = ?");
			boardDeletePstmt = conn.prepareStatement("delete from tb_board where board_id = ?");
			boardDeleteAllPstmt = conn.prepareStatement("delete from tb_board");
			boardWriterPstmt = conn.prepareStatement(
					"select m.name from tb_member m join tb_board b on m.member_id = b.member_id where b.member_id = ? ");
			boardUpdatePstmt = conn.prepareStatement("update tb_board set title = ?, content = ? where board_id = ?");
			viewCountUpdatePstmt = conn.prepareStatement("update tb_board set viewcount = ? where board_id = ?");
			// 5. 결과 처리
			// 6. 연결 해제
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();

		}
	}

	public List<BoardVO> list(BoardVO boardVO) {
		List<BoardVO> list = new ArrayList<>();
		System.out.println("게시물 리스트");
		try {
			ResultSet rs = null;
			ResultSet wrs = null;
			String searchKey = boardVO.getSearchKey();
			if (searchKey != null && searchKey.length() != 0) {
				boardListPstmt2.setString(1, "%" + searchKey + "%");
				rs = boardListPstmt2.executeQuery();
			} else {
				rs = boardListPstmt.executeQuery();
			}

			while (rs.next()) {
				boardWriterPstmt.setString(1, rs.getString("member_id"));
				wrs = boardWriterPstmt.executeQuery();
				if (wrs.next()) {
					BoardVO board = new BoardVO(rs.getString("board_id"), rs.getString("title"),
							rs.getString("content"), rs.getString("date"), wrs.getString("name"));
					System.out.println(board);
					list.add(board);
				}
			}
			wrs.close();
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public int insert(BoardVO board) {
		int updated = 0;
		try {
			boardInsertPstmt.setString(1, board.getTitle());
			boardInsertPstmt.setString(2, board.getContent());
			boardInsertPstmt.setString(3, board.getMemberId());
			updated = boardInsertPstmt.executeUpdate();
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return updated;
	}

	public BoardVO read(BoardVO boardVO) {

		BoardVO board = null;
		try {
			boardDetailPstmt.setString(1, boardVO.getId());
			ResultSet wrs = boardWriterPstmt.executeQuery();
			ResultSet rs = boardDetailPstmt.executeQuery();
			if (wrs.next() && rs.next()) {
				board = new BoardVO(rs.getString("board_id"), rs.getString("title"), rs.getString("content"),
						wrs.getString("name"), rs.getString("date"), rs.getInt("viewCount") + 1);
				updateViewCount(board);
			}
			wrs.close();
			rs.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return board;
	}

	public int update(BoardVO board) {
		int updated = 0;
		try {
			boardUpdatePstmt.setString(1, board.getTitle());
			boardUpdatePstmt.setString(2, board.getContent());
			boardUpdatePstmt.setString(3, board.getId());
			updated = boardUpdatePstmt.executeUpdate();
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return updated;

	}

	public int delete(BoardVO boardVO) {
		int updated = 0;

		try {
			boardDeletePstmt.setString(1, boardVO.getId());
			updated = boardDeletePstmt.executeUpdate();
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return updated;
	}

	public int clear() {
		int updated = 0;
		try {
			updated = boardDeleteAllPstmt.executeUpdate();
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return updated;
	}

	public void updateViewCount(BoardVO boardVO) {
		try {
			viewCountUpdatePstmt.setInt(1, boardVO.getViewCount());
			viewCountUpdatePstmt.setString(2, boardVO.getId());
			viewCountUpdatePstmt.executeUpdate();
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}