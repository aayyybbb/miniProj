<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
    <input type="radio" id="male" name="gender" value="male"> <label for="male">남성</label>
    <input type="radio" id="female" name="gender" value="female"> <label for="female">여성</label><br>
    <label>취미:</label><br>
    <input type="checkbox" id="hobby1" name="hobby" value="1"> <label for="hobby1">Reading</label><br>
    <input type="checkbox" id="hobby2" name="hobby" value="2"> <label for="hobby2">Soccer</label><br>
    <input type="checkbox" id="hobby3" name="hobby" value="3"> <label for="hobby3">Cooking</label><br>
    <input type="checkbox" id="hobby4" name="hobby" value="4"> <label for="hobby4">Painting</label><br>
    <input type="checkbox" id="hobby5" name="hobby" value="5"> <label for="hobby5">Gardening</label><br>
    <div>
		<input type="button" value="수정" onclick="jsUpdate(${memberUpdate.id})">
        <a href="member.do?action=view&id=${memberUpdate.id}">취소</a>
    </div>
</form>

<script>
function jsUpdate(memberId) {
	if (confirm("정말로 수정하시겠습니까?")) {
		action.value = "update";
		ybFetch("member.do", "rForm", json => {
			if(json.status == 0) {
				//성공
				alert("회원정보를 수정 하였습니다");
				location = "member.do?action=view&id=" + memberId;
			} else {
				alert(json.statusMessage);
			}
		});
	}
}
</script>
</body>
</html>