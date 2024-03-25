package member;

import java.util.List;

public class MemberService {
	MemberDAO memberDAO = new MemberDAO();

	public List<MemberVO> list() {
		List<MemberVO> list = memberDAO.list();
		return list;
	}

}
