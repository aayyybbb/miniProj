package member;

import java.util.List;

public class MemberService {
	MemberDAO memberDAO = new MemberDAO();

	public List<MemberVO> list(MemberVO memberVO) {
		List<MemberVO> list = memberDAO.list(memberVO);
		return list;
	}

}
