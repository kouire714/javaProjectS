package com.spring.javaProjectS.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.javaProjectS.pagination.PageProcess;
import com.spring.javaProjectS.pagination.PageVO;
import com.spring.javaProjectS.service.BoardService;
import com.spring.javaProjectS.vo.Board2ReplyVO;
import com.spring.javaProjectS.vo.BoardVO;

@Controller
@RequestMapping("/board")
public class BoardController {
	
	@Autowired
	BoardService boardService;
	
	@Autowired
	PageProcess pageProcess;
	
	@RequestMapping(value = "/boardList", method = RequestMethod.GET)
	public String boardListGet(Model model,
			@RequestParam(name="pag", defaultValue = "1", required = false) int pag,
			@RequestParam(name="pageSize", defaultValue = "5", required = false) int pageSize) {
		PageVO pageVO = pageProcess.totRecCnt(pag, pageSize, "board", "", "");
		
		List<BoardVO> vos = boardService.getBoardList(pageVO.getStartIndexNo(), pageSize);
		
		model.addAttribute("vos", vos);
		model.addAttribute("pageVO", pageVO);
		
		return "board/boardList";
	}
	
	@RequestMapping(value = "/boardInput", method = RequestMethod.GET)
	public String boardInputGet() {
		return "board/boardInput";
	}
	
	@RequestMapping(value = "/boardInput", method = RequestMethod.POST)
	public String boardInputPost(BoardVO vo) {
		// content에 이미지가 저장되어 있다면, 저장된 이미지만 골라서 /resources/data/board/폴더에 저장시켜준다.
		if(vo.getContent().indexOf("src=\"/") != -1) boardService.imgCheck(vo.getContent());
		
		// 이미지들의 모든 복사작업을 마치면, ckeditor폴더경로를 borad폴더 경로로 변경처리한다.('/data/ckeditor/' ==> 'data/board/')
		vo.setContent(vo.getContent().replace("/data/ckeditor/", "/data/board/"));
		
		// content안의 내용정리가 끝나면 변경된 vo를 DB에 저장시켜준다.
		int res = boardService.setBoardInput(vo);
		
		if(res == 1) return "redirect:/message/boardInputOk";
		else return "redirect:/message/boardInputNo";
	}
	
	@RequestMapping(value = "/boardContent", method = RequestMethod.GET)
	public String boardContentGet(int idx, Model model,
			@RequestParam(name="pag", defaultValue = "1", required = false) int pag,
			@RequestParam(name="pageSize", defaultValue = "5", required = false) int pageSize) {
		// 조회수 증가하기
		boardService.setReadNumPlus(idx);
		
		BoardVO vo = boardService.getBoardContent(idx);
		
		// 이전글/다음글 가져오기
		BoardVO preVo  = boardService.getPreNexSearch(idx, "preVo");
		BoardVO nextVo = boardService.getPreNexSearch(idx, "nextVo");
		model.addAttribute("preVo", preVo);
		model.addAttribute("nextVo", nextVo);
		
		model.addAttribute("vo", vo);
		model.addAttribute("pag", pag);
		model.addAttribute("pageSize", pageSize);
		
		// 댓글(대댓글) 추가로 처리
		List<Board2ReplyVO> replayVOS = boardService.getBoard2Reply(idx);
		model.addAttribute("replayVOS", replayVOS);
		
		return "board/boardContent";
	}
	
	@RequestMapping(value = "/boardDelete", method = RequestMethod.GET)
	public String boardDeleteGet(int idx,
			@RequestParam(name="pag", defaultValue = "1", required = false) int pag,
			@RequestParam(name="pageSize", defaultValue = "5", required = false) int pageSize) {
		// 게시글에 사진이 존재한다면 서버에 저장된 사진파일을 먼저 삭제시킨다.
		BoardVO vo = boardService.getBoardContent(idx);
		if(vo.getContent().indexOf("src=\"/") != -1) boardService.imgDelete(vo.getContent());
		
		// 앞의 작업을 마치면, DB에서 실제로 존재하는 게시글을 삭제처리한다.
		int res = boardService.setBoardDelete(idx);
		
		if(res == 1) return "redirect:/message/boardDeletOk?idx="+idx+"&pag="+pag+"&pageSize="+pageSize;
		else return "redirect:/message/boardDeletNo?idx="+idx+"&pag="+pag+"&pageSize="+pageSize;
	}
	
	@RequestMapping(value = "/boardUpdate", method = RequestMethod.GET)
	public String boardUpdateGet(Model model, int idx,
			@RequestParam(name="pag", defaultValue = "1", required = false) int pag,
			@RequestParam(name="pageSize", defaultValue = "5", required = false) int pageSize) {
		// 수정화면으로 이동할시에는 먼저 원본파일의 그림파일이 존재한다면, 현재폴더(board)의 그림파일을 ckeditor폴더로 복사시킨다.
		BoardVO vo = boardService.getBoardContent(idx);
		if(vo.getContent().indexOf("src=\"/") != -1) boardService.imgBackup(vo.getContent());
		
		model.addAttribute("vo", vo);
		model.addAttribute("pag", pag);
		model.addAttribute("pageSize", pageSize);
		
		return "board/boardUpdate";
	}
	
	@RequestMapping(value = "/boardUpdate", method = RequestMethod.POST)
	public String boardUpdatePost(Model model, BoardVO vo,
			@RequestParam(name="pag", defaultValue = "1", required = false) int pag,
			@RequestParam(name="pageSize", defaultValue = "5", required = false) int pageSize) {
		// 수정된 자료가 원본자료와 완전히 동일하다면 수정할 필요가 없다. 즉, DB에 저장된 원본자료를 불러와서 현재 vo에 담긴 내용(content)과 비교해 본다.
		BoardVO origVo = boardService.getBoardContent(vo.getIdx());
		
		// content의 내용이 조금이라도 변경이 되었다면 내용을 수정한것이기에, 그림파일의 처리유무를 결정한다.
		if(!origVo.getContent().equals(vo.getContent())) {
			// 실제로 수정하기 버튼을 클릭하면, 기존 board폴더의 그림화일이 존재했다면 1:모두 삭제시킨다.(원본은 수정창에 들어오기전에 ckeditor폴더에 저장시켜두었다.) 2:그림파일의 경로를 'board'에서 'ckeditor'경로로 변경한다.
			if(origVo.getContent().indexOf("src=\"/") != -1) boardService.imgDelete(origVo.getContent());	// 1번 처리
			
			// 2번처리('board'폴더를 'ckeditor'로 경로를 변경처리한다.
			vo.setContent(vo.getContent().replace("/data/board/", "/data/ckeditor/"));
			
			// 앞의 작업이 끝나면, 파일을 처음 업로드한것과 같은 작업을 처리시켜준다.
			// 즉, content에 이미지가 저장되어 있다면, 저장된 이미지만 골라서 '/data/board/'폴더에 복사 저장시켜준다.
			boardService.imgCheck(vo.getContent());
			
			// 이미지들의 모든 복사작업을 마치면, ckeditor폴더경로를 borad폴더 경로로 변경처리한다.('/data/ckeditor/' ==> 'data/board/')
			vo.setContent(vo.getContent().replace("/data/ckeditor/", "/data/board/"));
		}
		// content안의 내용과 그림파일까지, 잘 정비된 vo를 DB에 Update 시켜준다.
		int res = boardService.setBoardUpdate(vo);
		
		model.addAttribute("idx", vo.getIdx());
		model.addAttribute("pag", pag);
		model.addAttribute("pageSize", pageSize);
		
		if(res == 1) return "redirect:/message/boardUpdateOk";
		else return "redirect:/message/boardUpdateNo";
	}
	
	// 부모댓글 입력처리(원본글에 대한 댓글)
	@ResponseBody
	@RequestMapping(value = "/boardReplyInput", method = RequestMethod.POST)
	public String boardReplyInputPost(Board2ReplyVO replyVO) {
		// 부모댓글의 경우는 re_step = 0, re_order=1 로 처리한다.(단, 원본글의 첫번째 부모댓글은 re_order=1 이지만, 2번째 이상이라면, 마지막 부모댓글의 re_order보다 +1 처리시켜준다.)
		Board2ReplyVO replyParentVO = boardService.getBoardParentReplyCheck(replyVO.getBoardIdx());
		
		if(replyParentVO == null) {
			replyVO.setRe_order(1);
		}
		else {
			replyVO.setRe_order(replyParentVO.getRe_order()+1);
		}
		replyVO.setRe_step(0);
		
		int res = boardService.setBoardReplyInput(replyVO);
		
		return res+"";
	}
	
	// 대댓글 입력처리((부모)댓글에 대한 답변글)
	@ResponseBody
	@RequestMapping(value = "/boardReplyInputRe", method = RequestMethod.POST)
	public String boardReplyInputRePost(Board2ReplyVO replyVO) {
		// 답변글일경우는 1.re_step은 부모의 re_step+1, 2.re_order는 부모의 re_order보다 큰 댓글은 모두 +1처리후, 3.자신의 re_order+1처리한다.
		
		replyVO.setRe_step(replyVO.getRe_step() + 1);
		
		boardService.setReplayOrderUpdate(replyVO.getBoardIdx(), replyVO.getRe_order());
		
		replyVO.setRe_order(replyVO.getRe_order() + 1);
		
		int res = boardService.setBoardReplyInput(replyVO);
		
		return res+"";
	}
	
	// 게시글 검색처리
	@RequestMapping(value = "/boardSearch", method = RequestMethod.GET)
	public String boardSearchGet(String search, Model model,
			@RequestParam(name="searchString", defaultValue = "", required = false) String searchString,
			@RequestParam(name="pag", defaultValue = "1", required = false) int pag,
		  @RequestParam(name="pageSize", defaultValue = "5", required = false) int pageSize) {
		PageVO pageVO = pageProcess.totRecCnt(pag, pageSize, "board", search, searchString);
		//System.out.println("pageVO : " + pageVO);
		
		List<BoardVO> vos = boardService.getBoardSearchList(pageVO.getStartIndexNo(), pageSize, search, searchString);
		
		String searchTitle = "";
		if(pageVO.getSearch().equals("title")) searchTitle = "글제목";
		else if(pageVO.getSearch().equals("name")) searchTitle = "글쓴이";
		else searchTitle = "글내용";
		
		model.addAttribute("vos", vos);
		model.addAttribute("pageVO", pageVO);
		model.addAttribute("searchTitle", searchTitle);
		model.addAttribute("searchCount", vos.size());
		
		return "board/boardSearchList";
	}
}
