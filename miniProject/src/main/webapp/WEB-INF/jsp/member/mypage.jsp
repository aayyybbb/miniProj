<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>나의페이지</title>
    <style>
        label {
            display: inline-block;
            width: 200px;
        }
        input {
            margin-bottom: 10px; 
        }
    </style>
</head>
<body>
    <h1>
       나의페이지
    </h1>
   
      <label>아이디 : ${loginVO.id}</label> <br/>
      <label>비밀번호 : ${loginVO.pwd}</label><br/>
      <label>이름: ${loginVO.name}</label><br/>
      <label>주소: ${loginVO.addr}</label><br/>
      <label>연락처: ${loginVO.phone}</label><br/>
      <label>성별: ${loginVO.gender}</label><br/>
      <label>취미: ${loginVO.hobbyVO.hobby}</label>

<script type="text/javascript" src="<c:url value='/js/common.js'/>"></script>
<script>
function jsDelete() {
	if (confirm("정말로 탈퇴하시겠습니까?")) {
		action.value = "delete";
		myFetch("member.do", "viewForm", json => {
			if(json.status == 0) {
				//성공
				alert("회원정보를 삭제 하였습니다");
				location = "user.do?action=list";
			} else {
				alert(json.statusMessage);
			}
		});
	}
}

function jsUpdateForm() {
	if (confirm("정말로 수정하시겠습니까?")) {
		//서버의 URL을 설정한다 
		action.value = "updateForm";
	
		//서버의 URL로 전송한다 
		viewForm.submit();
	}	
}

</script>
<!-- 두개의 폼을 하나로 합치는 방법 , js를 사용하여 처리  -->
	<form id="viewForm" method="post" action="member">
		<input type="hidden" id="action" name="action" value="">
		<input type="hidden" id="id" name="id" value="${loginVO.id}">
		<input type="button" value="삭제" onclick="jsDelete()">
		<input type="button" value="수정" onclick="jsUpdateForm()">
	</form>     
 
</body>
</html>

