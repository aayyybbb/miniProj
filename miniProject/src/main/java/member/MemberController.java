package member;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import hobby.HobbyService;

public class MemberController {
	MemberService memberService = new MemberService();
	HobbyService hobbyService = new HobbyService();

	public MemberController() {
		super();
	}

	public String list(HttpServletRequest request, MemberVO memberVO) {
		List<MemberVO> list = memberService.list(memberVO);
		request.setAttribute("memberList", list);
		return "memberList";
	}

	public String read(HttpServletRequest request, MemberVO memberVO) {
		MemberVO member = memberService.read(memberVO);
		request.setAttribute("memberDetail", member);
		return "memberDetail";
	}

	public String signUp(HttpServletRequest request) {
		return "signUp";
	}

	public int insert(HttpServletRequest request, MemberVO memberVO) {
		int memberUpdated = memberService.insert(memberVO);
		int hobbyUpdated = hobbyService.insert(memberVO);
		if (memberUpdated != 0 && hobbyUpdated != 0) {
			return 1;
		} else {
			return 0;
		}
	}

	public int update(HttpServletRequest request, MemberVO memberVO) {
		int memberUpdated = memberService.update(memberVO);
		int hobbyUpdated = hobbyService.update(memberVO);
		if (memberUpdated != 0 && hobbyUpdated != 0) {
			return 1;
		}
		return 0;
	}

	public int delete(HttpServletRequest request, MemberVO memberVO) {
		int updated = memberService.delete(memberVO);
		if (updated != 0) {
			return 1;
		} else {
			request.setAttribute("memberUpdate", memberVO);
			return 0;
		}
	}

	public String updateForm(HttpServletRequest request, MemberVO memberVO) {
		MemberVO member = memberService.read(memberVO);
		request.setAttribute("memberUpdate", member);
		return "memberUpdate";
	}

}
