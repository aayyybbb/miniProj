package member;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hobby.HobbyVO;

/**
 * Servlet implementation class BoardServlet
 */
@WebServlet("/member.do")
public class MemberServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	MemberController memberController = new MemberController();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MemberServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doService(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doService(request, response);
	}

//	private Map<String, Object> convertMap(Map<String, String[]> map) {
//		Map<String, Object> result = new HashMap<>();
//
//		for (var entry : map.entrySet()) {
//			if (entry.getValue().length == 1) {
//				// 문자열 1건
//				result.put(entry.getKey(), entry.getValue()[0]);
//			} else {
//				// 문자열 배열을 추가한다
//				result.put(entry.getKey(), entry.getValue());
//			}
//		}
//
//		return result;
//	}

	private void doService(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		List<String> list = new ArrayList<>();

		MemberVO memberVO = new MemberVO();
		HobbyVO hobbyVO = new HobbyVO();

		memberVO.setId(request.getParameter("id"));
		memberVO.setPwd(request.getParameter("pwd"));
		memberVO.setName(request.getParameter("name"));
		memberVO.setAddr(request.getParameter("addr"));
		memberVO.setPhone(request.getParameter("phone"));
		memberVO.setGender(request.getParameter("gender"));
		memberVO.setAction(request.getParameter("action"));
		memberVO.setSearchKey(request.getParameter("searchKey"));

		String[] hList = request.getParameterValues("hobby");
		if (hList != null) {
			for (String h : hList) {
				list.add(h);
			}
		}

		hobbyVO.setHobbyId(list);
		memberVO.setHobbyVO(hobbyVO);

		System.out.println("memberVO " + memberVO);
		System.out.println("hobbyVO" + hobbyVO);

		String action = memberVO.getAction();
		String result = switch (action) {
		case "list" -> memberController.list(request, memberVO);
		case "view" -> memberController.read(request, memberVO);
		case "signUp" -> memberController.signUp(request);
		case "insert" -> memberController.insert(request, memberVO);
		case "update" -> memberController.update(request, memberVO);
		case "delete" -> memberController.delete(request, memberVO);
		case "updateForm" -> memberController.updateForm(request, memberVO);
		default -> "";
		};

		if (result.startsWith("redirect:")) {
			// 리다이렉트
			response.sendRedirect(result.substring("redirect:".length()));
		} else if (result.startsWith("{") || result.startsWith("[")) {
			// json 문자열을 리턴
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().append(result);
		} else {
			// 3. jsp 포워딩
			// 포워딩
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/member/" + result + ".jsp");
			rd.forward(request, response);
		}
	}

}
