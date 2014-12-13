package edu.ncsu.csc.itrust.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CheckEbolaAction {
	
	private String countries;
	//https://www.internationalsos.com/ebola/index.cfm?content_id=421&language_id=ENG
	public String countriesAtHighRisk = "Guinea,Liberia,Sierra Leone";
	public String countiresAtRisk = "Mali,Congo,Spain";

	
	private List<String> countiresList = new ArrayList<String>();
	private List<String> countiresAtHighRiskList = new ArrayList<String>();
	private List<String> countiresAtRiskList = new ArrayList<String>();
	
	public CheckEbolaAction(String countries) {
		super();
		this.countries = countries;

		countiresList = Arrays.asList(countries.split("\\s*,\\s*"));
		countiresAtHighRiskList = Arrays.asList(countriesAtHighRisk.split("\\s*,\\s*"));
		countiresAtRiskList = Arrays.asList(countiresAtRisk.split("\\s*,\\s*"));
		
	}

	public int checkEbolaRisk()
	{
		if(countries.isEmpty())
		{
		return 0;
		}
		else
		{
			int beenToHighRiskCountires = 0;
			int beenToRiskCountires = 0;
			
	        for(String c : countiresList) {
	            if(countiresAtHighRiskList.contains(c))
	            {
	            	beenToHighRiskCountires++;
	            }
	            else if (countiresAtRiskList.contains(c))
	            {
	            	beenToRiskCountires++;
	            }
	        }
	        int percentage = beenToHighRiskCountires * 20 + beenToRiskCountires * 5;
			return percentage;
		}
		
	}
	

	
	


}
