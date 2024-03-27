package hobby;

import member.MemberVO;

public class HobbyService {
	HobbyDAO hobbyDAO = new HobbyDAO();

	public int insert(MemberVO memberVO) {
		return hobbyDAO.insertHobby(memberVO);
	}

	public void delete(MemberVO memberVO) {
		hobbyDAO.deleteHobby(memberVO);
	}

	public int update(MemberVO memberVO) {
		if (memberVO.getHobbyVO().getHobbyId() != null) {
			delete(memberVO);
		}
		return insert(memberVO);
	}
}
