package com.biz.mocoji.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class StayDTO {

	private String addr1;//	주소
	private String addr2;//	상세주소
	private String areacode;//	지역코드
	private String benikia;//	베니키아 여부
	private String cat1;//	대분류
	private String cat2;//	중분류
	private String cat3;//	소분류
	private String contentid;//	콘텐츠ID
	private String contenttypeid;//	콘텐츠타입ID
	private String createdtime;//	등록일
	private String firstimage;//	대표이미지(원본)
	private String firstimage2;//	대표이미지(썸네일)
	private String goodstay;//	굿스테이 여부
	private String hanok;//	한옥 여부
	private String mapx;//	GPS X좌표
	private String mapy;//	GPS Y좌표
	private String mlevel;//	Map Level
	private String modifiedtime;//	수정일
	private String readcount;//	조회수
	private String sigungucode;//	시군구코드
	private String tel;//	전화번호
	private String title;//	제목

	
}
