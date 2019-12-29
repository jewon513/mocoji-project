package com.biz.mocoji.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biz.mocoji.config.DataGoConfig;
import com.biz.mocoji.domain.AreaBaseDTO;
import com.biz.mocoji.domain.AreaDTO;
import com.biz.mocoji.domain.DetailCommonDTO;
import com.biz.mocoji.domain.DetailIntroDTO;
import com.biz.mocoji.domain.StayDTO;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TourAppService {
	
	@Autowired
	TourGetService tgService;
	
	
	// 공통 쿼리 만드는 부분
	private String getHeaderQuery(String servName) throws UnsupportedEncodingException {
		
		String queryString = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/" + servName;

		queryString += "?ServiceKey=" + DataGoConfig.goDataAuth; // 인증키를 여기다 넣으세요.
		queryString += "&MobileApp=" + URLEncoder.encode(DataGoConfig.MY_APP_NAME, "UTF-8"); // 나중에 프로젝트 이름이나 App 이름을	넣으세요.
		queryString += "&_type=json";
		queryString += "&MobileOS=ETC";
		
		return queryString;
				
	}
	
	// items 부분까지 parsing해서 JsonObject로 리턴하는 method
	// 필요에따라 return 값을 JsonArray로 parsing할 수 있다.
	private JsonObject getOitems (String rstString) {
		
		JsonElement jsonElement = JsonParser.parseString(rstString);
		
		JsonObject oRes = (JsonObject) jsonElement.getAsJsonObject().get("response");
		
		JsonObject oBody = (JsonObject) oRes.getAsJsonObject().get("body");
		
		JsonObject oitems = (JsonObject) oBody.getAsJsonObject().get("items");
		
		return oitems;
				
	}
	

	// 지역코드들을 조회
	public String makeAreaQuery() throws UnsupportedEncodingException {
		
		String AreaQuery = this.getHeaderQuery("areaCode");
		AreaQuery += String.format("&numOfRows=%d", 20); // 나중에 변수처리 하기 위해서 이렇게 했습니다.
		AreaQuery += String.format("&pageNo=%d", 1);
		
		return AreaQuery;
		
	}
	public List<AreaDTO> getArea() throws UnsupportedEncodingException, IOException{
		
		log.debug("Area 쿼리 :" + this.makeAreaQuery());
		
		String jsonQuery = tgService.getQuery(this.makeAreaQuery());
		
		log.debug("rst 쿼리 :" + jsonQuery);
		
		JsonObject oitems = this.getOitems(jsonQuery);
		
		JsonArray oitemList = (JsonArray) oitems.getAsJsonObject().get("item");
		
		TypeToken<List<AreaDTO>> AreaToken = new TypeToken<List<AreaDTO>>() {};
		Gson gson = new Gson();
		List<AreaDTO> AreaList = gson.fromJson(oitemList, AreaToken.getType());
		
		log.debug("List 목록 :" + AreaList.toString());
		
		return AreaList;
	}
	
	// areaCode(지역코드)를 매개변수로 받아서 행사 종료일이 오늘로부터 한달 이후인 행사들만 조회 
	public String makeTodayAreaBaseQuery(String arrange,String areaCode, int numOfRows, int pageNo) throws UnsupportedEncodingException {
		
		String AreaBaseQuery = this.getHeaderQuery("searchFestival");
		
		AreaBaseQuery += "&arrange=" + arrange;
		AreaBaseQuery += "&contentTypeId=15";
		AreaBaseQuery += String.format("&areaCode=%s", areaCode);
		AreaBaseQuery += "&eventEndDate=" +this.getDate();
		
		log.debug("이벤트 종료 날짜 : " + this.getDate());
		
		AreaBaseQuery += "&listYN=Y";
		AreaBaseQuery += String.format("&numOfRows=%d", numOfRows); 	// 한 페이지에 보여지는 데이터의 수
		AreaBaseQuery += String.format("&pageNo=%d", pageNo);		// 페이지 번호
		
		return AreaBaseQuery;
	}
	public List<AreaBaseDTO> getTickerList(String arrange, String areaCode, int numOfRows, int pageNo) throws UnsupportedEncodingException, IOException{
		
		log.debug("보내기전 areaCode : " + areaCode);
		log.debug("보내기전 makeAreaBaseQuery : " + this.makeTodayAreaBaseQuery(arrange, areaCode, numOfRows, pageNo));
		
		String jsonQuery = tgService.getQuery(this.makeTodayAreaBaseQuery(arrange, areaCode, numOfRows, pageNo));
		
		log.debug("받은 retString : " + jsonQuery);
		
		JsonElement jsonElement = JsonParser.parseString(jsonQuery);
		
		JsonObject oRes = (JsonObject) jsonElement.getAsJsonObject().get("response");
		
		JsonObject oBody = (JsonObject) oRes.getAsJsonObject().get("body");
		
		JsonObject oitems = (JsonObject) oBody.getAsJsonObject().get("items");
		
		JsonArray oitemList = (JsonArray) oitems.getAsJsonObject().get("item");
		
		TypeToken<List<AreaBaseDTO>> AreaToken = new TypeToken<List<AreaBaseDTO>>() {};
		
		Gson gson = new Gson();
		
		List<AreaBaseDTO> AreaBaseList = gson.fromJson(oitemList, AreaToken.getType());
		
		return AreaBaseList;
		
	}
	
	// areaCode(지역코드)를 매개변수로 받아서 해당하는 곳의 리스트를 조회
	public String makeAreaBaseQuery(String arrange,String areaCode, int numOfRows, int pageNo) throws UnsupportedEncodingException {
		
		String AreaBaseQuery = this.getHeaderQuery("searchFestival");
		
		AreaBaseQuery += "&arrange=" + arrange;
		AreaBaseQuery += "&contentTypeId=15";
		AreaBaseQuery += String.format("&areaCode=%s", areaCode);
		AreaBaseQuery += "&listYN=Y";
		AreaBaseQuery += String.format("&numOfRows=%d", numOfRows); 	// 한 페이지에 보여지는 데이터의 수
		AreaBaseQuery += String.format("&pageNo=%d", pageNo);		// 페이지 번호
		
		return AreaBaseQuery;
	}
	public List<AreaBaseDTO> getAreaBase(String arrange, String areaCode, int numOfRows, int pageNo) throws UnsupportedEncodingException, IOException{
		
		log.debug("보내기전 areaCode : " + areaCode);
		log.debug("보내기전 makeAreaBaseQuery : " + this.makeAreaBaseQuery(arrange, areaCode, numOfRows, pageNo));
		
		String jsonQuery = tgService.getQuery(this.makeAreaBaseQuery(arrange, areaCode, numOfRows, pageNo));
		
		log.debug("받은 retString : " + jsonQuery);
		
		JsonElement jsonElement = JsonParser.parseString(jsonQuery);
		
		JsonObject oRes = (JsonObject) jsonElement.getAsJsonObject().get("response");
		
		JsonObject oBody = (JsonObject) oRes.getAsJsonObject().get("body");
		
		JsonObject oitems = (JsonObject) oBody.getAsJsonObject().get("items");
		
		JsonArray oitemList = (JsonArray) oitems.getAsJsonObject().get("item");
		
		TypeToken<List<AreaBaseDTO>> AreaToken = new TypeToken<List<AreaBaseDTO>>() {};
		
		Gson gson = new Gson();
		
		List<AreaBaseDTO> AreaBaseList = gson.fromJson(oitemList, AreaToken.getType());
		
		return AreaBaseList;
	}
	public int getTotalCount(String areaCode, int numOfRows) throws UnsupportedEncodingException, IOException {
		log.debug("보내기전 areaCode : " + areaCode);
		log.debug("보내기전 makeAreaBaseQuery : " + this.makeAreaBaseQuery("c", areaCode, numOfRows, 10));
		
		String jsonQuery = tgService.getQuery(this.makeAreaBaseQuery("c", areaCode, numOfRows, 1));
		
		log.debug("받은 retString : " + jsonQuery);
		
		JsonElement jsonElement = JsonParser.parseString(jsonQuery);
		
		JsonObject oRes = (JsonObject) jsonElement.getAsJsonObject().get("response");
		
		JsonObject oBody = (JsonObject) oRes.getAsJsonObject().get("body");
		
		String totalCount = oBody.get("totalCount").getAsString();
		
		int intTotalCount = Integer.valueOf(totalCount);
		
		return intTotalCount;
	}
	
	
	
	// contentid로 공통정보 조회하는 쿼리 만들기
	public String makedetailCommonQuery(String contentId) throws UnsupportedEncodingException {
		
		String detailCommonQuery = this.getHeaderQuery("detailCommon");
		
		detailCommonQuery += "&contentId=" + contentId;
		detailCommonQuery += "&contentTypeId=15";
		detailCommonQuery += "&defaultYN=Y";
		detailCommonQuery += "&firstImageYN=Y";
		detailCommonQuery += "&areacodeYN=Y";
		detailCommonQuery += "&catcodeYN=Y";
		detailCommonQuery += "&addrinfoYN=Y";
		detailCommonQuery += "&mapinfoYN=Y";
		detailCommonQuery += "&overviewYN=Y";
		
		
		return detailCommonQuery;

	}
	public DetailCommonDTO getDetailCommon(String contentId) throws UnsupportedEncodingException, IOException{
		
		
		log.debug("보내기전 areaCode : " + contentId);
		log.debug("보내기전 makeAreaBaseQuery : " + this.makedetailCommonQuery(contentId));
		
		String jsonQuery = tgService.getQuery(this.makedetailCommonQuery(contentId));
		
		log.debug("받은 retString : " + jsonQuery);
		
		JsonObject oitems = this.getOitems(jsonQuery);
		
		JsonObject oitem = (JsonObject) oitems.getAsJsonObject().get("item");
		
		
		TypeToken<DetailCommonDTO> AreaToken = new TypeToken<DetailCommonDTO>() {};
		Gson gson = new Gson();
		DetailCommonDTO detailIntro = gson.fromJson(oitem, AreaToken.getType());
		
		return detailIntro;
	}
	
	
	// contentID로 행사 상세소개 조회하기
	public String makeDetailIntroQuery (String contentId) throws UnsupportedEncodingException {
		
		String detailIntroQuery = this.getHeaderQuery("detailIntro");
		detailIntroQuery += "&contentId=" + contentId;
		detailIntroQuery += "&contentTypeId=15"; 
		
		return detailIntroQuery;
		
	}
	public DetailIntroDTO getDtailIntro(String contentId) throws UnsupportedEncodingException, IOException {
		
		String jsonQuery = tgService.getQuery(this.makeDetailIntroQuery(contentId));
		
		JsonObject oitems = this.getOitems(jsonQuery);
		
		JsonObject oitem = (JsonObject) oitems.getAsJsonObject().get("item");
		
		TypeToken<DetailIntroDTO> typeToken = new TypeToken<DetailIntroDTO>() {};
		Gson gson = new Gson();
		DetailIntroDTO detailIntroDTO = gson.fromJson(oitem, typeToken.getType());
		
		return detailIntroDTO;
		
	}
	
	// 숙박 조회를 위해서 areacode와 sigungucode를 이용해서 query 생성
	public String makeAccommodationQuery (String areacode, String sigungucode) throws UnsupportedEncodingException {
		
		String AccommodationQuery = this.getHeaderQuery("searchStay");
		AccommodationQuery += "&listYN=Y";
		AccommodationQuery += "&arrange=B";
		AccommodationQuery += "&areaCode=" + areacode;
		AccommodationQuery += "&sigunguCode=" + sigungucode;
		AccommodationQuery += String.format("&numOfRows=%d", 4); 
		AccommodationQuery += String.format("&pageNo=%d", 1);		
				
		return AccommodationQuery;
	}
	public List<StayDTO> getAccommodation(String areacode, String sigungucode) throws UnsupportedEncodingException, IOException {
		
		String jsonQuery = tgService.getQuery(this.makeAccommodationQuery(areacode, sigungucode));
		
		
		// 숙박업소를 조회했는데 없을 경우 null 값으로 return 한다.
		JsonObject oitems= null;
		List<StayDTO> AccommodationList = null;
		try {
			oitems = this.getOitems(jsonQuery);
			
		} catch (Exception e) {
			return AccommodationList;
		}
		
		JsonArray oitem = null;
		Gson gson = new Gson();
		
		// 숙박업소를 조회했는데 결과값이 1일 경우 JsonArray로 변환하는 과정에서 Exception이 발생하기 때문에
		// JsonObject로 변환한 후 따로 ArrayList 선언 하여 값을 한개 추가 한 뒤에 return 해준다.
		try {
			oitem = (JsonArray) oitems.getAsJsonObject().get("item");
		} catch (Exception e) {
			JsonObject oitem2 = (JsonObject) oitems.getAsJsonObject().get("item");
			TypeToken<StayDTO> typeToken = new TypeToken<StayDTO>() {};
			StayDTO Accommodation = gson.fromJson(oitem2, typeToken.getType());
			AccommodationList = new ArrayList<StayDTO>();
			AccommodationList.add(Accommodation);
			return AccommodationList;
		}
		
		TypeToken<List<StayDTO>> typeToken = new TypeToken<List<StayDTO>>() {};
		AccommodationList = gson.fromJson(oitem, typeToken.getType());
		
		return AccommodationList;
	}
	
	// 키워드 조회를 위한 쿼리를 만드는 method
	public String makeSearchQuery(String keyword, int currentPageNo) throws UnsupportedEncodingException {
		
		String searchQuery = this.getHeaderQuery("searchKeyword");
		searchQuery += "&arrange=C";
		searchQuery += "&contentTypeId=15";
		searchQuery += "&keyword=" + URLEncoder.encode(keyword, "UTF-8");
		searchQuery += String.format("&numOfRows=%d", 10); 
		searchQuery += String.format("&pageNo=%d", currentPageNo);	
		
		return searchQuery;
	}
	public List<AreaBaseDTO> getSearchList(String keyword, int currentPageNo) throws UnsupportedEncodingException, IOException{
		
		String jsonQuery = tgService.getQuery(this.makeSearchQuery(keyword,currentPageNo));
		List<AreaBaseDTO> searchList = null;
		// 결과값이 없을 경우를 위한 예외처리 구문
		JsonObject oitems = null;
		try {
			oitems = this.getOitems(jsonQuery);
		} catch (Exception e) {
			// TODO: handle exception
			return searchList;
			
		}
		
		// 결과값이 1개일경우 Array로 받으면 exception이 발생하기 때문에 object로 받는 구문
		Gson gson = new Gson();
		JsonArray oitem = null;
		try {
			oitem = (JsonArray) oitems.getAsJsonObject().get("item");
		} catch (Exception e) {
			// TODO: handle exception
			JsonObject oitem2 = (JsonObject) oitems.getAsJsonObject().get("item");
			TypeToken<AreaBaseDTO> typeToken = new TypeToken<AreaBaseDTO>() {};
			searchList = new ArrayList<AreaBaseDTO>();
			AreaBaseDTO search = gson.fromJson(oitem2, typeToken.getType());
			searchList.add(search);
			return searchList;
		}
		
		TypeToken<List<AreaBaseDTO>> typeToken = new TypeToken<List<AreaBaseDTO>>() {};
		
		searchList = gson.fromJson(oitem, typeToken.getType());
		
		return searchList;
	}
	
	public int getSearchTotalCount(String keyword) throws IOException {
		
		String searchQuery = tgService.getQuery(this.makeSearchQuery(keyword,1));
		
		JsonElement jsonElement = JsonParser.parseString(searchQuery);
		
		JsonObject oRes = (JsonObject) jsonElement.getAsJsonObject().get("response");
		
		JsonObject oBody = (JsonObject) oRes.getAsJsonObject().get("body");
		
		String totalCount = oBody.get("totalCount").getAsString();
		
		int intTotalCount = Integer.valueOf(totalCount);
		
		return intTotalCount;
	}
	
	
	
	// 날짜 계산하기 위한 method
	
	private String getDate() {
		
		Date date = new Date();
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, 1);
		
		String aMonthLater = sf.format(cal.getTime());
		
		return aMonthLater;
	}
	
}


















