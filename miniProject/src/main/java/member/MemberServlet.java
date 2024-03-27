package member;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

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
		String contentType = request.getContentType();
		MemberVO memberVO = new MemberVO();
		HobbyVO hobbyVO = new HobbyVO();
		if (contentType == null || contentType.startsWith("application/x-www-form-urlencoded")) {

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

		} else if (contentType.startsWith("application/json")) {
			BufferedReader reader = request.getReader();
			StringBuilder jsonBuilder = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				jsonBuilder.append(line);
			}
			String json = jsonBuilder.toString();
			// ObjectMapper를 사용하여 JSON 문자열을 JsonNode로 변환
			ObjectMapper mapper = new ObjectMapper();
			JsonNode rootNode = mapper.readTree(json);

			// 취미 목록 추출
			JsonNode hobbyNode = rootNode.has("hobby") ? rootNode.get("hobby") : null;
			List<String> hobbyList = new ArrayList<>();
			if (hobbyNode != null && hobbyNode.isArray()) {
				for (JsonNode hobby : hobbyNode) {
					hobbyList.add(hobby.asText());
				}
				memberVO.getHobbyVO().setHobbyId(hobbyList);
			}

			// MemberVO 객체 생성 및 설정
			memberVO.setId(rootNode.get("id").asText());
			memberVO.setPwd(rootNode.has("pwd") ? rootNode.get("pwd").asText() : null);
			memberVO.setName(rootNode.has("name") ? rootNode.get("name").asText() : null);
			memberVO.setAddr(rootNode.has("addr") ? rootNode.get("addr").asText() : null);
			memberVO.setPhone(rootNode.has("phone") ? rootNode.get("phone").asText() : null);
			memberVO.setGender(rootNode.has("gender") ? rootNode.get("gender").asText() : null);
			memberVO.setAction(rootNode.has("action") ? rootNode.get("action").asText() : null);
			memberVO.setSearchKey(rootNode.has("searchKey") ? rootNode.get("searchKey").asText() : null);
			// 받아온 MemberVO 객체를 이용하여 다른 작업 수행
		}

		String action = memberVO.getAction();
		Object result = switch (action) {
		case "list" -> memberController.list(request, memberVO);
		case "view" -> memberController.read(request, memberVO);
		case "signUp" -> memberController.signUp(request);
		case "insert" -> memberController.insert(request, memberVO);
		case "update" -> memberController.update(request, memberVO);
		case "delete" -> memberController.delete(request, memberVO);
		case "updateForm" -> memberController.updateForm(request, memberVO);
		default -> "";
		};
		if (result instanceof Integer && (Integer) result == 1) {
			response.setContentType("application/json;charset=UTF-8");
			response.setStatus(HttpServletResponse.SC_OK);
			response.getWriter().write("{\"status\": 0}");
		} else if (result instanceof String url) {
			if (url.startsWith("redirect:")) {
				// 리다이렉트
				response.sendRedirect(url.substring("redirect:".length()));
			} else {
				// 3. jsp 포워딩
				// 포워딩
				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/member/" + result + ".jsp");
				rd.forward(request, response);
			}
		}
	}
}
