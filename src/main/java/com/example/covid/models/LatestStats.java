package com.example.covid.models;

import lombok.Data;

@Data
public class LatestStats {

	String country;
	String province;
	Long confirmed;
	Long deaths;
	Long recovered;
	Long active;
	@Override
	public String toString() {
		return "LatestStats [country=" + country + ", province=" + province + ", confirmed=" + confirmed + ", deaths="
				+ deaths + ", recovered=" + recovered + ", active=" + active + "]";
	}
	
	
}
