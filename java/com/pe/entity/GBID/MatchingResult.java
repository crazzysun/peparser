package com.pe.entity.GBID;

import com.pe.entity.Bean;

public class MatchingResult implements Bean
{
	private static final long serialVersionUID = 5789204278759579956L;

	private String fileName;
	private String allRulesRate;
	private String ntvRulesRate;
	private String increaseRate;
	
	public String getIncreaseRate()
	{
		return increaseRate;
	}
	public void setIncreaseRate(String increaseRate)
	{
		this.increaseRate = increaseRate;
	}
	public String getFileName()
	{
		return fileName;
	}
	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}
	public String getAllRulesRate()
	{
		return allRulesRate;
	}
	public void setAllRulesRate(String allRulesRate)
	{
		this.allRulesRate = allRulesRate;
	}
	public String getNtvRulesRate()
	{
		return ntvRulesRate;
	}
	public void setNtvRulesRate(String ntvRulesRate)
	{
		this.ntvRulesRate = ntvRulesRate;
	}
}
