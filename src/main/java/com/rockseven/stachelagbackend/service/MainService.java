package com.rockseven.stachelagbackend.service;

import org.springframework.stereotype.Service;

import com.rockseven.stachelagbackend.util.CsvMapperUtil;

@Service
public class MainService {

	private CsvMapperUtil csvMapperUtil;

	public MainService(CsvMapperUtil csvMapperUtil) {
		this.csvMapperUtil = csvMapperUtil;
	}
	
	public void produceCsvReport() {
		csvMapperUtil.produceCsvFromDb();
		
	}
	
	
	
}
