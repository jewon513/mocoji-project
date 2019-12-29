package com.biz.mocoji.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biz.mocoji.domain.CommentDTO;
import com.biz.mocoji.domain.PageDTO;
import com.biz.mocoji.persistence.CommentDao;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CommentService {

	@Autowired
	SqlSession sqlSession;
	CommentDao cDao;
	
	@Autowired
	public void getDao() {
		cDao = sqlSession.getMapper(CommentDao.class);
	}
	
	// 댓글 리스트 전부 조회
	public List<CommentDTO> selectAll(){
		
		List<CommentDTO> list = cDao.selectAll(); 
		
		return list;
	}
	
	public List<CommentDTO> selectPagination(PageDTO pageDTO){
		
		List<CommentDTO> list = cDao.selectPagination(pageDTO.getOffset(), pageDTO.getLimit());
		
		return list;
	}
	
	public int getCount() {
		
		return cDao.getCount();
	}
	
	// 댓글 추가
	public int insertComment(String c_contentid, String c_writer, String c_text, String c_areacode, String c_sigungucode) {
		
		Date date = new Date();
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
		String curDate = sd.format(date);
		
		CommentDTO commentDTO = CommentDTO.builder().c_writer(c_writer).c_contentid(c_contentid).c_date(curDate).c_text(c_text).c_areacode(c_areacode).c_siguncode(c_sigungucode).build();
		
		int ret = cDao.insert(commentDTO);
		
		return ret;
	}
	
	public List<CommentDTO> viewCommentList(String contentId){
		
		List<CommentDTO> commentList = cDao.findByCommentId(contentId);
		
		for (CommentDTO commentDTO : commentList) {
			
			commentDTO.setC_text(commentDTO.getC_text().replace("\n", "<br>"));
			
		}
		
		return commentList;
		
	}
	
	public int deleteCommnet(long c_seq) {
		
		int ret = cDao.delete(c_seq);
		
		if(ret>0) {
			log.debug("데이터 삭제 완료");
		}else {
			log.debug("데이터 삭제 실패");
		}
		
		return ret;
	}
	
}
