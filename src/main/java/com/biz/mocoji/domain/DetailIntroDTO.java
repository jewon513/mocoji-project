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

public class DetailIntroDTO {

	private String agelimit;//	관람 가능연령
	private String bookingplace;//	예매처
	private String discountinfofestival;//	할인정보
	private String eventenddate;//	행사 종료일
	private String eventhomepage;//	행사 홈페이지
	private String eventplace;//	행사 장소
	private String eventstartdate;//	행사 시작일
	private String festivalgrade;//	축제등급
	private String placeinfo;//	행사장 위치안내
	private String playtime;//	공연시간
	private String program;//	행사 프로그램
	private String spendtimefestival;//	관람 소요시간
	private String sponsor1;//	주최자 정보
	private String sponsor1tel;//	주최자 연락처
	private String sponsor2;//	주관사 정보
	private String sponsor2tel;//	주관사 연락처
	private String subevent;//	부대행사
	private String usetimefestival;//	이용요금

	
}
