package com.rockseven.stachelagbackend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rockseven.stachelagbackend.service.MainService;

@RestController
public class MainController {
	
	private MainService mainService;
	
	public MainController(MainService mainService) {
		this.mainService = mainService;
	}
	
	@GetMapping("/")
	public String payloadRequest() throws Exception {
		mainService.produceCsvReport();
		return "Please check for the report @ C:/temp/sightings.csv";
	}

}
