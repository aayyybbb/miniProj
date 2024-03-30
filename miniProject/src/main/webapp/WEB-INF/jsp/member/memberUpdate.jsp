<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
회원정보 수정 페이지

    <form id="rForm" action="member.do" method="post">
    <input type="hidden" name="action" value="update">
    <label>아이디:</label> <input type="text" id="id" name="id" value="${memberUpdate.id}"  readonly="readonly"><br>
    <label>비밀번호:</label> <input type="password" id="pwd" name="pwd" value="${memberUpdate.pwd}" required="required"><br>
    <label>이름:</label> <input type="text" id="name" name="name" value="${memberUpdate.name}" required="required"><br>
    <label>주소:</label> <input type="text" id="addr" name="addr" value="${memberUpdate.addr}" required="required"><br>
    <label>연락처:</label> <input type="text" id="phone" name="phone" value="${memberUpdate.phone}" required="required"><br>
<label>성별:</label> 
<input type="radio" id="male" name="gender" value="male" ${memberUpdate.gender eq 'male' ? 'checked' : ''}> <label for="male">남성</label>
<input type="radio" id="female" name="gender" value="female" ${memberUpdate.gender eq 'female' ? 'checked' : ''}> <label for="female">여성</label><br>
  <label>취미:</label><br>
<c:forEach var="hobby" items="${hobby}">
    <c:set var="isChecked" value="false"/>
    <c:forEach var="hobbyList" items="${hobbyList}">
        <c:if test="${hobbyList.hobby eq hobby.hobby}">
            <c:set var="isChecked" value="true"/>
        </c:if>
    </c:forEach>
    <input type="checkbox" id="${hobby.hobbyId}" name="hobby" value="${hobby.hobbyId}" ${isChecked ? 'checked' : ''}>
    <label for="${hobby.hobbyId}">${hobby.hobby}</label><br>
</c:forEach>
    <div>
		<input type="button" value="수정" onclick="jsUpdate()">
        <a href="member.do?action=view&id=${memberUpdate.id}">취소</a>
    </div>
</form>

<script type="text/javascript" src="<c:url value='/js/common.js'/>"></script>
<script>
function jsUpdate() {
		ybFetch("member.do", "rForm", json => {
			if(json.status == 0) {
				//성공
				alert("회원정보를 수정 하였습니다");
				location = "member.do?action=view&id=" + id.value;
			} else {
				alert(json.statusMessage);
			}
		});
	}
 </script>
</body>
</html>