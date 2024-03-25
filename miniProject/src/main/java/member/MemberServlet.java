package member;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

	private void doService(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		String result = memberController.list(request, response);

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
