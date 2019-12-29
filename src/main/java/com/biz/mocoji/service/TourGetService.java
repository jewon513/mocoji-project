package com.biz.mocoji.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TourGetService {

	public String getQuery(String queryString) throws IOException {
		
		URL url = new URL(queryString);
		HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
		
		httpURLConnection.setRequestMethod("GET");
		
		int resCode = httpURLConnection.getResponseCode();
		
		log.debug("서버에서 응답받은 코드 : " + resCode);
		
		BufferedReader buffer = null;
		
		if(resCode == 200) {
			InputStreamReader is = new InputStreamReader(httpURLConnection.getInputStream());
			buffer = new BufferedReader(is);
		}else {
			InputStreamReader is = new InputStreamReader(httpURLConnection.getInputStream());
			buffer = new BufferedReader(is);
		}
		
		String rstString ="";
		String reader ="";
		
		while(true) {
			
			reader = buffer.readLine();
			if(reader == null) break;
			rstString += reader;
			
		}
		
		buffer.close();
		
		return rstString;
		
	}
	
}
