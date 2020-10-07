/***************************************************************************	
 * 	Description:			Class to diagnose a patient's symptoms
 * 
 * 	Author:				 	C18727971 Thamsanqa Sibanda
 * 
 * 	Date:				 	20 March 2020
 ***************************************************************************/

package com.app.c19;

public class DiagnosePatient extends Classifier {
	
	// test case attributes
	private String patientName;
	private String temperature;
	private String aches;
	private String cough;
	private String soreThroat;
	private String traveled;
	private double yesProbability;
	private double noProbability;
	private long hasCovidProb;
	
	// Constructor
	public DiagnosePatient(String patientName, String temperature, String aches, String  cough, String soreThroat, String traveled) {
		
		super();
		this.patientName = patientName;
		this.temperature = temperature;
		this.aches = aches;
		this.cough = cough;
		this.soreThroat = soreThroat;
		this.traveled = traveled;
		
	}// end constructor
	
	// Methods
	public void testPatient() {
		
		//System.out.println(temperature+" "+aches+" "+cough+" "+soreThroat+" "+traveled );
		// Probability variable selectors
		double tempYes = 0.0, achesYes = 0.0, coughYes = 0.0, soreThroatYes = 0.0, traveledYes = 0.0, tempNo = 0.0, achesNo = 0.0, coughNo = 0.0, soreThroatNo = 0.0, traveledNo = 0.0;
		
		// Temperature
		switch(temperature)
		{
			case "hot":
				tempYes = getHotYes();
				tempNo = getHotNo();
				break;
			case "normal":
				tempYes = getNormalYes();
				tempNo = getNormalNo();
				break;
			case "cool":
				tempYes = getCoolYes();
				tempNo = getCoolNo();
				break;
		} // end Temperature Switch case
		
		// aches
		switch(aches)
		{
			case "yes":
				achesYes = getAchesPosetiveYes();
				achesNo = getAchesPosetiveNo();
				break;
			case "no":
				achesYes = getAchesNegativeYes();
				achesNo = getAchesNegativeNo();
				break;
		}// end aches Switch case
		
		// cough
		switch(cough)
		{
			case "yes":
				coughYes = getCoughPosetiveYes();
				coughNo = getCoughPosetiveNo();
				break;
			case "no":
				coughYes = getCoughNegativeYes();
				coughNo = getCoughNegativeNo();
				break;
		}// end cough Switch case
		
		// sore throat
		switch(soreThroat)
		{
			case "yes":
				soreThroatYes = getSoreThroatPosetiveYes();
				soreThroatNo = getSoreThroatPosetiveNo();
				break;
			case "no":
				soreThroatYes = getSoreThroatNegativeYes();
				soreThroatNo = getSoreThroatNegativeNo();
				break;
		}// end sore throat Switch case
		
		// traveled from danger zone
		switch(traveled)
		{
			case "yes":
				traveledYes = getTraveledDangerZonePosetiveYes();
				traveledNo = getTraveledDangerZonePosetiveNo();
				break;
			case "no":
				traveledYes = getTraveledDangerZoneNegativeYes();
				traveledNo = getTraveledDangerZoneNegativeNo();
				break;
		}// end traveled from danger zone Switch case
		
		yesProbability =	(tempYes/getPhasCovidYes())*
							(achesYes/getPhasCovidYes())*
							(coughYes/getPhasCovidYes())*
							(soreThroatYes/getPhasCovidYes())*
							(traveledYes/getPhasCovidYes())*
							((double)getPhasCovidYes()/getNum_training_rows());

		noProbability = 	(tempNo/getPhasCovidNo())*
							(achesNo/getPhasCovidNo())*
							(coughNo/getPhasCovidNo())*
							(soreThroatNo/getPhasCovidNo())*
							(traveledNo/getPhasCovidNo())*
							((double)getPhasCovidNo()/getNum_training_rows());
		
		hasCovidProb = (Math.round((yesProbability/(yesProbability+noProbability))*100));

	}// end testPatient()
		
	// Getters and setters
	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public String getHasCovidProbString() {

		return Long.toString(hasCovidProb);
	}
	public long getHasCovidProb() {

		return hasCovidProb;
	}

	public void setHasCovidProb(int hasCovidProb) {
		this.hasCovidProb = hasCovidProb;
	}
	
}// end Diagnose Patient class
