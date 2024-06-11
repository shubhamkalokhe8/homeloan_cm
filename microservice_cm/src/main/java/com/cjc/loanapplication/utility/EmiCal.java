package com.cjc.loanapplication.utility;

public class EmiCal {

	public static Double emiCalculator(Double loanAmountSanctioned, Float rateOfInterest, Integer years)
	{
		Float intrest=rateOfInterest/(12*100);
		years=years*12;
		
		return(loanAmountSanctioned*intrest*Math.pow(1+rateOfInterest,years))/(Math.pow(1+rateOfInterest,years)-1);
	}
}
