<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>userList.jsp</title>
	<jsp:include page="/WEB-INF/views/include/bs4.jsp" />
</head>
<body>
<p><br/></p>
<div class="container">
	<h2>회 원 리 스 트</h2>
	<table class="table table-hover">
		<tr class="table-dark text-dark">
			<th>번호</th>
			<th>아이디</th>
			<th>성명</th>
			<th>나이</th>
			<th>주소</th>
			<th>비고</th>
		</tr>
		<c:forEach var="vo" items="${vos}" varStatus="st">
			<tr>
				<td>${vo.idx}</td>
				<td>${vo.mid}</td>
				<td>${vo.name}</td>
				<td>${vo.age}</td>
				<td>${vo.address}</td>
				<td><a href="javascript:userDelete(${vo.idx})" class="btn btn-danger btn-sm">삭제</a></td>
			</tr>
		</c:forEach>
		<tr><td colspan="6" class="m-0 p-0"></td></tr>
	</table>
	<div><a href="${ctp}/" class="btn btn-warning">돌아가기</a></div>
</div>
<p><br/></p>
</body>
</html>