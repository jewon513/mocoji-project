package com.biz.mocoji.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.biz.mocoji.domain.AreaDTO;
import com.biz.mocoji.domain.CommentDTO;
import com.biz.mocoji.domain.PageDTO;
import com.biz.mocoji.service.CommentService;
import com.biz.mocoji.service.PageService;
import com.biz.mocoji.service.TourAppService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping(value = "/comment")
@Controller
public class CommentConroller {

	@Autowired
	TourAppService tService;

	@Autowired
	CommentService cService;
	
	@Autowired
	PageService pService;

	@RequestMapping(value = "/write", method = RequestMethod.POST)
	public String writeComment(
			@RequestParam(value = "contentid", required = false, defaultValue = "1") String contentId,
			@RequestParam(value = "areacode", required = false, defaultValue = "1") String areacode,
			@RequestParam(value = "sigungucode", required = false, defaultValue = "1") String sigungucode,
			@RequestParam(value = "writer", required = false, defaultValue = "1") String c_writer,
			@RequestParam(value = "text", required = false, defaultValue = "1") String c_text, Model model) {

		int ret = cService.insertComment(contentId, c_writer, c_text, areacode, sigungucode);
		
		List<CommentDTO> commentList = cService.viewCommentList(contentId);

		model.addAttribute("commentList", commentList);
		
		return "commentlist";

	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String deleteComment(
			@RequestParam(value = "seq", required = false, defaultValue = "1") long c_seq, 
			@RequestParam(value = "contentid", required = false, defaultValue = "1") String contentId,
			Model model){
		
		int ret = cService.deleteCommnet(c_seq);
		
		List<CommentDTO> commentList = cService.viewCommentList(contentId);
		
		model.addAttribute("commentList", commentList);
		
		return "commentlist";
	}

	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String viewCommentList(Model model, @RequestParam(value = "currentPageNo", required = false, defaultValue = "1")int currentPageNo) {

		int totalCount = cService.getCount();
		PageDTO pageDTO = pService.getPagination(totalCount, currentPageNo);
		
		log.debug("PAGEDTO 결과값 : " + pageDTO.toString());
		
		List<CommentDTO> commentList = cService.selectPagination(pageDTO);
		List<AreaDTO> areaList = null;

		try {
			areaList = tService.getArea();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("PAGE", pageDTO);
		model.addAttribute("commentList", commentList);
		model.addAttribute("AREALIST", areaList);
		return "allviewcomment";

	}

}
