package com.example.covid.service;

import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.covid.models.LatestStats;

@Service
public class CovidService {
	
	private static String URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_daily_reports/";
	
	List<LatestStats> latestStatsList = new ArrayList<>();
	
	@PostConstruct
	public void getData() throws IOException, InterruptedException {
		DateFormat dateFormat = new SimpleDateFormat("MM-dd-YYYY");
		Date date = Date.from(Instant.now().minus(Duration.ofDays(1)));
		String fileName = dateFormat.format(date).concat(".csv");
		URL = URL.concat(fileName);
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
		    .uri(URI.create(URL))
		    .build();
		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
		StringReader in = new StringReader(response.body());
		Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(in);
		
		for (CSVRecord record : records) {
			LatestStats stats = new LatestStats();
		    stats.setProvince(record.get("Province_State"));
		    stats.setCountry(record.get("Country_Region"));
		    if(StringUtils.hasText(record.get("Active")))
		    	stats.setActive(Long.parseLong(record.get("Active").trim()));
		    if(StringUtils.hasText(record.get("Confirmed")))
		    	stats.setConfirmed(Long.parseLong(record.get("Confirmed").trim()));
		    if(StringUtils.hasText(record.get("Deaths")))
		    	stats.setDeaths(Long.parseLong(record.get("Deaths").trim()));
		    if(StringUtils.hasText(record.get("Recovered")))
		    	stats.setRecovered(Long.parseLong(record.get("Recovered").trim()));
		    latestStatsList.add(stats);
		}
	}
	
	public List<LatestStats> getAllData(){
		return latestStatsList;
	}
	

}
