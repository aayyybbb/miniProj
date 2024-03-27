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

	public String insert(HttpServletRequest request, MemberVO memberVO) {
		int memberUpdated = memberService.insert(memberVO);
		int hobbyUpdated = hobbyService.insert(memberVO);
		if (memberUpdated != 0 && hobbyUpdated != 0) {
			return "memberList";
		} else {
			return "signUp";
		}
	}

	public String update(HttpServletRequest request, MemberVO memberVO) {
		int memberUpdated = memberService.update(memberVO);
		int hobbyUpdated = hobbyService.update(memberVO);
		if (memberUpdated != 0 && hobbyUpdated != 0) {
			MemberVO member = memberService.read(memberVO);
			request.setAttribute("memberDetail", member);
			return "memberDetail";
		}
		return "memberUpdate";
	}

	public String delete(HttpServletRequest request, MemberVO memberVO) {
		int updated = memberService.delete(memberVO);
		if (updated != 0) {
			return "memberList";
		} else {
			request.setAttribute("memberUpdate", memberVO);
			return "memberUpdate";
		}
	}

	public String updateForm(HttpServletRequest request, MemberVO memberVO) {
		MemberVO member = memberService.read(memberVO);
		request.setAttribute("memberUpdate", member);
		return "memberUpdate";
	}

}
