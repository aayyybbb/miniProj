package member;

import java.util.List;

public class MemberService {
	MemberDAO memberDAO = new MemberDAO();

	public List<MemberVO> list(MemberVO memberVO) {
		List<MemberVO> list = memberDAO.list(memberVO);
		return list;
	}

	public MemberVO read(MemberVO member) {
		return memberDAO.read(member);
	}

	public int insert(MemberVO memberVO) {
		return memberDAO.insert(memberVO);
	}

	public int update(MemberVO memberVO) {
		return memberDAO.update(memberVO);
	}

	public int delete(MemberVO memberVO) {
		return memberDAO.delete(memberVO);
	}

}
