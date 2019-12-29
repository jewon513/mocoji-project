package com.biz.mocoji.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.biz.mocoji.domain.CommentDTO;

public interface CommentDao {
	
	public int getCount();

	public List<CommentDTO> selectAll();
	public List<CommentDTO> selectPagination(@Param("offset")int offset, @Param("limit")int limit);
	public List<CommentDTO> findByCommentId(String contentId);
	
	public CommentDTO findBySeq(long c_seq);
	
	public int insert(CommentDTO commentDTO);
	public int delete (long c_seq);

}

	

