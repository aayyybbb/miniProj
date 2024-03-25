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

}
