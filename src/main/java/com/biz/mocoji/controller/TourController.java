package com.biz.mocoji.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.crypto.AEADBadTagException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.biz.mocoji.domain.AreaBaseDTO;
import com.biz.mocoji.domain.AreaDTO;
import com.biz.mocoji.domain.CommentDTO;
import com.biz.mocoji.domain.DetailCommonDTO;
import com.biz.mocoji.domain.DetailIntroDTO;
import com.biz.mocoji.domain.PageDTO;
import com.biz.mocoji.domain.StayDTO;
import com.biz.mocoji.service.CommentService;
import com.biz.mocoji.service.PageService;
import com.biz.mocoji.service.TourAppService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping(value = "/tour")
@Controller
public class TourController {
	
	@Autowired
	TourAppService tService;
	
	@Autowired
	CommentService cService;
	
	@Autowired
	PageService pService;

	@RequestMapping(value = "/main", method = RequestMethod.GET, produces = "text/json;charset=UTF-8")
	public String main(Model model, @RequestParam(value= "currentPageNo", required = false, defaultValue = "1") int currentPageNo) {
		
		List<AreaDTO> areaList = null;
		List<AreaBaseDTO> areaBaseList = null;
		List<AreaBaseDTO> tickerList= null;
		
		int totalCount = 0;
		try {
			totalCount = tService.getTotalCount("", 10);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		PageDTO pageDTO = pService.getPagination(totalCount, currentPageNo);
		
		try {
			areaList = tService.getArea();
			areaBaseList = tService.getAreaBase("C","", 10, currentPageNo);
			tickerList = tService.getTickerList("B", "", 5, 1);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		model.addAttribute("TICKERLIST",tickerList);
		model.addAttribute("Tcontroller", "main");
		model.addAttribute("TITLE", "main");
		model.addAttribute("BASELIST", areaBaseList);
		model.addAttribute("AREALIST", areaList);
		model.addAttribute("PAGE", pageDTO);
		
		return "main";
		
	}
	
	@RequestMapping(value = "baselist" , method=RequestMethod.GET)
	public String viewBaesList(Model model,
								@RequestParam(value="areacode", required = false, defaultValue = "1") String areaCode,
								@RequestParam(value="currentPageNo", required = false, defaultValue = "1") int currentPageNo) {
		
		List<AreaDTO> areaList = null;
		List<AreaBaseDTO> areaBaseList = null;
		int totalCount = 0;
		try {
			totalCount = tService.getTotalCount(areaCode, 10);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PageDTO pageDTO = pService.getPagination(totalCount, currentPageNo);
		
		try {
			areaList = tService.getArea();
			areaBaseList = tService.getAreaBase("C",areaCode, 10, currentPageNo);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String title = "";
		
		for (AreaDTO dto : areaList) {
			if(dto.getCode().equals(areaCode)) {
				title=dto.getName();
			}
		}
		
		model.addAttribute("Tcontroller", "baselist");
		model.addAttribute("TITLE", title);
		model.addAttribute("areacode", areaCode);
		model.addAttribute("AREALIST",areaList);
		model.addAttribute("BASELIST", areaBaseList);
		model.addAttribute("PAGE",pageDTO);
		
		return "main";
	}
	
	@RequestMapping(value="/detail", method = RequestMethod.GET, produces="text/json;charset=UTF-8")
	public String viewdetailIntro(@RequestParam(value = "contentid", required = false, defaultValue = "1")String contentId, 
									@RequestParam(value = "areacode", required = false, defaultValue = "1")String areacode, 
									@RequestParam(value = "sigungucode", required = false, defaultValue = "1")String sigungucode, 
			Model model) {
		
		DetailCommonDTO detailCommonDTO= null;
		DetailIntroDTO detailIntroDTO = null;
		List<AreaDTO> areaList = null;
		List<StayDTO> stayList = null;
		List<CommentDTO> commentList = null;
		
		try {
			areaList = tService.getArea();
			detailCommonDTO = tService.getDetailCommon(contentId);
			detailIntroDTO = tService.getDtailIntro(contentId);
			stayList = tService.getAccommodation(areacode, sigungucode);
			commentList = cService.viewCommentList(contentId);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
		model.addAttribute("AREACODE", areacode);
		model.addAttribute("AREALIST", areaList);
		model.addAttribute("stayList", stayList);
		model.addAttribute("detailCommonDTO", detailCommonDTO);
		model.addAttribute("detailIntroDTO", detailIntroDTO);
		model.addAttribute("commentList", commentList);
		
		return "detail";
	}
	
	
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public String search(@RequestParam(value="keyword", required = false , defaultValue = "")String keyword, 
						 @RequestParam(value="currentPageNo", required = false, defaultValue = "1")int currentPageNo,
			Model model) {
		
		List<AreaDTO> areaList = null;
		List<AreaBaseDTO> tickerList = null;
		List<AreaBaseDTO> searchList = null;
		
		int totalCount = 0;
		try {
			totalCount = tService.getSearchTotalCount(keyword);
			log.debug("검색결과 개수 : " + totalCount);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		PageDTO pageDTO = pService.getPagination(totalCount, currentPageNo);
		
		try {
			areaList = tService.getArea();
			tickerList = tService.getTickerList("B", "", 5, 1);
			searchList = tService.getSearchList(keyword, currentPageNo);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		model.addAttribute("TICKERLIST",tickerList);
		model.addAttribute("Tcontroller", "search");
		model.addAttribute("TITLE", "main");
		model.addAttribute("keyword", keyword);
		model.addAttribute("AREALIST", areaList);
		model.addAttribute("BASELIST", searchList);
		model.addAttribute("PAGE", pageDTO);
		
		return "main";
	}
	
}
