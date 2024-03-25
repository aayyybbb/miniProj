package member;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MemberController {
	MemberService memberService = new MemberService();

	public MemberController() {
		super();
	}

	public String list(HttpServletRequest request, HttpServletResponse response) {
		List<MemberVO> list = memberService.list();
		request.setAttribute("list", list);
		return "list";
	}
}
