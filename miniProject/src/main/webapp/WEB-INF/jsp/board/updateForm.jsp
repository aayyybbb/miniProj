<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>수정화면</title>
    <style>
        label {
            display: inline-block;
            width: 120px;
        }
        input {
            margin-bottom: 10px; 
        }
    </style>
</head>
<body>
    <h1>
        게시물 수정 수정양식 
    </h1>
    <form id="rForm" action="board.do" method="post">
    	<input type="hidden" name="action" value="update">
        <label>게시물 번호: </label> <input type="text" id="bno" name="bno" value="${board.bno}" readonly="readonly"> <br/>
        <label>제목 : </label><input type="text" id="btitle" name="btitle" value="${board.btitle}"><br/>
        <label>내용: </label> <input type="text" id="bcontent" name="bcontent" value="${board.bcontent}"><br/>
    <div>
        <input type="submit" value="수정">
        <a href="board.do?action=view&bno=${board.bno}">취소</a>
    </div>
    
    </form>
<script type="text/javascript" src="<c:url value='/js/common.js'/>"></script>

<script type="text/javascript">
	
const rForm = document.getElementById("rForm");
rForm.addEventListener("submit", e => {
	//서버에 form data를 전송하지 않는다 
	e.preventDefault();
	
	myFetch("board.do", "rForm", json => {
		if(json.status == 0) {
			//성공
			alert("게시물 정보 수정을 성공 하였습니다");
			location = "board.do?action=view&bno=" + bno.value;
		} else {
			alert(json.statusMessage);
		}
	});
});

</script>    
    
</body>
</html>
