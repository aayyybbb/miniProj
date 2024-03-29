<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>	
회원가입 페이지

    <form id="rForm" action="member.do" method="post">
    <input type="hidden" name="action" value="insert">
    <label>아이디:</label> <input type="text" id="id" name="id" required="required"><br>
    <label>비밀번호:</label> <input type="password" id="pwd" name="pwd" required="required"><br>
    <label>이름:</label> <input type="text" id="name" name="name" required="required"><br>
    <label>주소:</label> <input type="text" id="addr" name="addr" required="required"><br>
    <label>연락처:</label> <input type="text" id="phone" name="phone" required="required"><br>
    <label>성별:</label> 
    <input type="radio" id="male" name="gender" value="male"> <label for="male">남성</label>
    <input type="radio" id="female" name="gender" value="female"> <label for="female">여성</label><br>
<label>취미:</label><br>
<c:forEach var="hobby" items="${hobby}">
    <input type="checkbox" id="${hobby.hobbyId}" name="hobby" value="${hobby.hobbyId}">
    <label for="${hobby.hobbyId}">${hobby.hobby}</label><br>
</c:forEach>
  <div>
       	<input type="button" value="회원가입" onclick="jsSignUp()">	
        <input type="button" value="돌아가기" onclick="member.do?action=list">
    </div>
</form>
<script type="text/javascript" src="<c:url value='/js/common.js'/>"></script>
<script>
function jsSignUp() {
		ybFetch("member.do", "rForm", json => {
			if(json.status == 0) {
				//성공
				alert("회원가입되었습니다.");
				location = "member.do?action=view&id=" + id.value;
			} else {
				alert(json.statusMessage);
			}
		});
}
 </script>
</body>
</html>