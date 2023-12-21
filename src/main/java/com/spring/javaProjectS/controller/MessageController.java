package com.spring.javaProjectS.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MessageController {

	@RequestMapping(value="/message/{msgFlag}", method = RequestMethod.GET)
	public String msgGet(@PathVariable String msgFlag, String mid, Model model, 
			@RequestParam(name="temp", defaultValue="", required=false)String temp,
			@RequestParam(name="idx", defaultValue="0", required=false)int idx,
			@RequestParam(name="pag", defaultValue="1", required=false) int pag,
			@RequestParam(name="pagSize", defaultValue="5", required=false) int pageSize) {
		//System.out.println("mid : " + mid);
		
		if(msgFlag.equals("userDeleteOk")) {
			model.addAttribute("msg", "user가 삭제 되었습니다.");
			model.addAttribute("url", "user/userList");
		}
		else if(msgFlag.equals("userDeleteNo")) {
			model.addAttribute("msg", "user가 삭제 실패~~");
			model.addAttribute("url", "user/userList");
		}
		if(msgFlag.equals("user2DeleteOk")) {
			model.addAttribute("msg", "user2가 삭제 되었습니다.");
			model.addAttribute("url", "user2/user2List");
		}
		else if(msgFlag.equals("user2DeleteNo")) {
			model.addAttribute("msg", "user2 삭제 실패~~");
			model.addAttribute("url", "user2/user2List");
		}
		else if(msgFlag.equals("user2InputOk")) {
			model.addAttribute("msg", "회원 가입 성공!!!");
			model.addAttribute("url", "user2/user2List");
		}
		else if(msgFlag.equals("user2InputNo")) {
			model.addAttribute("msg", "회원 가입 실패~~");
			model.addAttribute("url", "user2/user2List");
		}
		else if(msgFlag.equals("user2UpdateOk")) {
			model.addAttribute("msg", "정보 수정 성공!!!");
			model.addAttribute("url", "user2/user2List");
		}
		else if(msgFlag.equals("user2UpdateNo")) {
			model.addAttribute("msg", "정보 수정 실패~~");
			model.addAttribute("url", "user2/user2List");
		}
		else if(msgFlag.equals("guestInputOk")) {
			model.addAttribute("msg", "방명록에 글이 등록되었습니다.");
			model.addAttribute("url", "guest/guestList");
		}
		else if(msgFlag.equals("guestInputNo")) {
			model.addAttribute("msg", "방명록에 글올리기 실패~~~");
			model.addAttribute("url", "guest/guestInput");
		}
		else if(msgFlag.equals("adminLoginOk")) {
			model.addAttribute("msg", "관리자 인증 성공");
			model.addAttribute("url", "guest/guestList");
		}
		else if(msgFlag.equals("adminLoginNo")) {
			model.addAttribute("msg", "관리자 인증 실패~~");
			model.addAttribute("url", "guest/adminLogin");
		}
		else if(msgFlag.equals("guestDeleteOk")) {
			model.addAttribute("msg", "방명록 삭제 성공");
			model.addAttribute("url", "guest/guestList");
		}
		else if(msgFlag.equals("guestDeleteNo")) {
			model.addAttribute("msg", "방명록 삭제 실패~~");
			model.addAttribute("url", "guest/guestList");
		}
		else if(msgFlag.equals("mailSendOk")) {
			model.addAttribute("msg", "메일이 성공적으로 전송되었습니다.");
			model.addAttribute("url", "study/mail/mail");
		}
		else if(msgFlag.equals("memberLoginOk")) {
			model.addAttribute("msg", mid + "님 로그인 되셨습니다.");
			model.addAttribute("url", "member/memberMain");
		}
		else if(msgFlag.equals("memberLoginNo")) {
			model.addAttribute("msg", "회원 로그인 실패~~");
			model.addAttribute("url", "member/memberLogin");
		}
		else if(msgFlag.equals("memberLogout")) {
			model.addAttribute("msg", mid + "님 로그아웃 되셨습니다.");
			model.addAttribute("url", "member/memberLogin");
		}
		else if(msgFlag.equals("memberJoinOk")) {
			model.addAttribute("msg", "회원 가입 되셨습니다.");
			model.addAttribute("url", "member/memberLogin");
		}
		else if(msgFlag.equals("memberJoinNO")) {
			model.addAttribute("msg", "회원 가입 실패~~");
			model.addAttribute("url", "member/memberJoin");
		}
		else if(msgFlag.equals("memberDeleteOk")) {
			model.addAttribute("msg","회원 탈퇴 되었습니다.");
			model.addAttribute("url","member/memberLogin");
		}
		else if(msgFlag.equals("memberDeleteNo")) {
			model.addAttribute("msg","회원 탈퇴 실패~~");
			model.addAttribute("url","member/memberLogin");
		}
//		else if(msgFlag.equals("memberPwdUpdateOk")) {
//			model.addAttribute("msg","비밀번호를 확인하였습니다.");
//			model.addAttribute("url","member/memberPwdUpdate");
//		}
//		else if(msgFlag.equals("memberPwdUpdateNo")) {
//			model.addAttribute("msg","비밀번호 확인 실패~~");
//			model.addAttribute("url","member/memberPwdUpdate");
//		}
		else if(msgFlag.equals("boardInputOk")) {
			model.addAttribute("msg","게시판에 글이 등록되었습니다.");
			model.addAttribute("url","board/boardList");
		}
		else if(msgFlag.equals("boardInputNo")) {
			model.addAttribute("msg","게시판에 글 등록 실패~~");
			model.addAttribute("url","board/boardInput");
		}
		else if(msgFlag.equals("adminNo")) {
			model.addAttribute("msg","관리자만 접속하실 수 있습니다.");
			model.addAttribute("url","/");
		}
		else if(msgFlag.equals("memberLevelNo")) {
			model.addAttribute("msg","해당등급으로는 접근하실 수 없습니다.");
			model.addAttribute("url","/");
		}
		else if(msgFlag.equals("memberNo")) {
			model.addAttribute("msg","로그인 후 사용하세요.");
			model.addAttribute("url","/member/memberLogin");
		}
		else if(msgFlag.equals("boardDeleteOk")) {
			model.addAttribute("msg","게시글이 삭제되었습니다.");
			model.addAttribute("url","board/boardList?pag="+pag+"&pageSize="+pageSize);
		}
		else if(msgFlag.equals("boardDeleteNo")) {
			model.addAttribute("msg","게시글이 삭제 실패~~");
			model.addAttribute("url","board/boardContent?idx="+idx+"&pag="+pag+"&pageSize="+pageSize);
		}
		else if(msgFlag.equals("boardUpdateOk")) {
			model.addAttribute("msg","게시글이 수정되었습니다.");
			model.addAttribute("url","board/boardContent?idx="+idx+"&pag="+pag+"&pageSize="+pageSize);
		}
		else if(msgFlag.equals("boardUpdateNo")) {
			model.addAttribute("msg","게시글이 수정 실패~~.");
			model.addAttribute("url","board/boardUpdate?idx="+idx+"&pag="+pag+"&pageSize="+pageSize);
		}
		else if(msgFlag.equals("validatorError")) {
			model.addAttribute("msg","user 등록 실패~~" + temp + "를 확인하세요..");
			model.addAttribute("url","user2/user2List");
		}
		
		return "include/message";
	}
}
