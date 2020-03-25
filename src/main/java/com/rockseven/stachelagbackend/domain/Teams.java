package com.rockseven.stachelagbackend.domain;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Teams {
	
	   private int marker;
	    private String name;
	    private int serial;
	    private List<Positions> positions;

	    public Teams() {}
}
