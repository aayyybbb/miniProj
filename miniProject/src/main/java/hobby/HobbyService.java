package hobby;

import java.util.List;

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
		if (memberVO.getHobbyVO() != null) {
			delete(memberVO);
		}
		return insert(memberVO);
	}

	public List<HobbyVO> list() {
		return hobbyDAO.hobbyList();
	}
	
	public List<HobbyVO> selectedList(MemberVO memberVO){
		return hobbyDAO.memberHobbyDetail(memberVO);
	}
}
