package com.rockseven.stachelagbackend.domain;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Payload {
	
	 private List<Teams> teams;
	    private String raceUrl;

	  public Payload() {}
}
