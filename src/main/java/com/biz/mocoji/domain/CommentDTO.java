package com.biz.mocoji.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder

public class CommentDTO {

	private long c_seq;			// Comment SEQUENCE
	private String c_contentid;	// 외래키로 조회할 contentid
	private String c_writer;	// 작성자
	private String c_date;		// 작성일
	private String c_text;		// 내용
	private String c_areacode;	// 지역코드
	private String c_siguncode;	// 시군구코드
	
}
