package com.example.covid.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.covid.models.LatestStats;
import com.example.covid.service.CovidService;

@RestController
@RequestMapping("/covid")
public class CovidController {
	
	@Autowired
	private CovidService covidService;
	
	@GetMapping("/health")
	public ResponseEntity<String> checkHealth(){
		return ResponseEntity.ok().body("OK");
	}
	
	@GetMapping("/all_stats")
	public ResponseEntity<List<LatestStats>> getAllData(){
		List<LatestStats> response = covidService.getAllData();
		return ResponseEntity.ok().body(response);
	}

}
