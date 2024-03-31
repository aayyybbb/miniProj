<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>로그인화면</title>
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
        로그인 화면 
    </h1>
    <form id="rForm" action="member.do" method="post" >
    	<input type="hidden" name="action" value="login">
        <label>아이디 : </label> <input type="text" id="id" name="id" required="required"><br/>
        <label>비밀번호 : </label>   <input type="password" id="pwd" name="pwd" required="required"><br/>
    <div>
        <input type="submit" value="로그인">
        <a href="member.do?action=list">취소</a>
    </div>
    
    </form>
    
<script type="text/javascript" src="<c:url value='/js/common.js'/>"></script>
    
<script type="text/javascript">
    
    const rForm = document.getElementById("rForm");
    
    rForm.addEventListener("submit", e => {
    	//서버에 form data를 전송하지 않는다 
    	e.preventDefault();
    	
		ybFetch("member.do", "rForm", json => {
			if(json.status == 0) {
				//성공
				alert("로그인 되었습니다");
				location = "index.html";
			} else {
				alert(json.statusMessage);
			}
		});
    });
   
    
    </script>
    
</body>
</html>







