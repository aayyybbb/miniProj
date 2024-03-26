package member;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

public class MemberController {
	MemberService memberService = new MemberService();

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
		int updated = memberService.insert(memberVO);
		if (updated != 0) {
			return "memberList";
		} else {
			return "signUp";
		}
	}

	public String update(HttpServletRequest request, MemberVO memberVO) {
		int updated = memberService.update(memberVO);
		if (updated != 0) {
			request.setAttribute("memberDetail", memberVO);
			return "memberDetail";
		}
		return "memberUpdate";
	}

}
