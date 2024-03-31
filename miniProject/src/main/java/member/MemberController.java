package member;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import hobby.HobbyService;
import hobby.HobbyVO;

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
		List<HobbyVO> list = hobbyService.list();
		request.setAttribute("hobby", list);
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
		List<HobbyVO> list = hobbyService.list();
		List<HobbyVO> selectedList = hobbyService.selectedList(memberVO);
		request.setAttribute("memberUpdate", member);
		request.setAttribute("hobbyList", selectedList);
		request.setAttribute("hobby", list);
		return "memberUpdate";
	}

	public String loginForm(HttpServletRequest request) {
		return "loginForm";
	}

	public int login(HttpServletRequest request, MemberVO memberVO) throws ServletException, IOException {
		System.out.println("로그인");
		System.out.println(memberVO.getPwd());
		MemberVO loginVO = memberService.read(memberVO);
		System.out.println(loginVO.getPwd());
		if (memberVO.getPwd().equals(loginVO.getPwd())) {
			HttpSession session = request.getSession();
			System.out.println("login session id = " + session.getId());
			session.setAttribute("loginVO", loginVO);
			return 1;
		} else {
			return 0;
		}
	}

	public String logout(HttpServletRequest request) {

		// 로그인 사용자의 정보를 세션에 제거한다
		HttpSession session = request.getSession();
		System.out.println("logout session id = " + session.getId());
		session.removeAttribute("loginVO"); // 특정 이름을 제거한다
		session.invalidate(); // 세션에 저장된 모든 자료를 삭제한다
		return "loginForm";
	}

	public String mypage(HttpServletRequest request) throws ServletException, IOException {
		System.out.println("상세보기");
		HttpSession session = request.getSession();
		MemberVO loginVO = (MemberVO) session.getAttribute("loginVO");
		if (loginVO == null) {
			return "redirect:member.do?action=loginForm";
		}
		request.setAttribute("loginVO", loginVO);
		return "mypage";
	}

}
