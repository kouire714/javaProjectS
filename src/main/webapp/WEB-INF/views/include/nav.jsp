<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<p>
	<a href="${ctp}/member/memberMain" class="btn btn-success">홈</a>
	<a href="${ctp}/guest/guestList" class="btn btn-warning">방명록</a>
	<a href="${ctp}/board/boardList" class="btn btn-primary">게시판</a>
	<a href="${ctp}/pds/pdsList" class="btn btn-info">자료실</a>
	<a href="${ctp}/member/logout" class="btn btn-danger">로그아웃</a>
</p>