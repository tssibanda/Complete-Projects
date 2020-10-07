/***************************************************************************	
 * 	Description:			Class to calculate probabilities of all features
 * 							 
 * 	Author:				 	C18727971 Thamsanqa Sibanda
 * 
 * 	Date:				 	20 March 2020
 ***************************************************************************/

package com.app.c19;

public class Classifier {
	
	// TrainMachine Class Attributes
	private FileHandler dataSet;
	private String[] data;
	private static int num_of_rows;
	private static int num_training_rows;
	private int accuracyPercentage;
	private int accuracyCounter = 0;
	
	// Algorithm Attributes
	private double yesProbability;
	private double noProbability;
	private static int PhasCovidYes=0;
	private static int PhasCovidNo=0;
	private static int total=0;
	
	// Features
	// Temperature
	private static int hotYes=0;
	private static int hotNo=0;
	private static int normalYes=0;
	private static int normalNo=0;
	private static int coolYes=0;
	private static int coolNo=0;
	// Aches
	private static int achesPosetiveYes=0;
	private static int achesPosetiveNo=0;
	private static int achesNegativeYes=0;
	private static int achesNegativeNo=0;
	// Coughing
	private static int coughPosetiveNo=0;
	private static int coughPosetiveYes=0;
	private static int coughNegativeNo=0;
	private static int coughNegativeYes=0;
	// Sore Throat
	private static int soreThroatPosetiveNo=0;
	private static int soreThroatPosetiveYes=0;
	private static int soreThroatNegativeNo=0;
	private static int soreThroatNegativeYes=0;
	// Traveled from Danger Zone
	private static int traveledDangerZonePosetiveNo=0;
	private static int traveledDangerZonePosetiveYes=0;
	private static int traveledDangerZoneNegativeNo=0;
	private static int traveledDangerZoneNegativeYes=0;
	// end features

	// Constructors
	public Classifier(String filePath) {
		dataSet = new FileHandler(filePath);
	}// end TrainMachine Constructor
	
	// constructor for testing patient 
	public Classifier() {

	}// end TrainMachine Constructor
		
	//train Machine Method
	public void trainMachine(){
		dataSet.openFile();
		data = dataSet.readFile();
		num_of_rows = data.length;
		num_training_rows = (int) Math.round(((data.length)) * (0.75));
		
		// loop to find probabilities of each feature
		for(int i=0;i<num_training_rows;i++) {
			
			String[] token = data[i].split(",");
			countPosetiveCases(token[5]);
			
			for(int j=0;j<6;j++) {
				
				classification(token[j],token[5], j);
				total = num_training_rows;
				
			}// end inner for loop
			
		}// end outer for loop
		
		selfEvaluation();
		
	}// end trainMachine()
	
	// Count yes and no Label
	public void countPosetiveCases(String data) {
		if(data.contentEquals("yes"))PhasCovidYes++;
		PhasCovidNo= total-PhasCovidYes;
	}// end countPosetiveCases() method
		
	// Method to calculate features
	public void classification(String data, String label, int j){
		
		switch(j)
		{
			case 0:// Temperature
				// hot yes
				if(data.contentEquals("hot") && label.contentEquals("yes")) hotYes++;

				// hot no
				if(data.contentEquals("hot") && label.contentEquals("no")) hotNo++;
				
				// normal yes
				if(data.contentEquals("normal") && label.contentEquals("yes")) normalYes++;
				
				// normal no
				if(data.contentEquals("normal") && label.contentEquals("no")) normalNo++;
				
				// cool yes
				if(data.contentEquals("cool") && label.contentEquals("yes")) coolYes++;
				
				// cool no
				if(data.contentEquals("cool") && label.contentEquals("no")) coolNo++;
				break;
				
			case 1: // Aches
				// achesPosetive yes
				if(data.contentEquals("yes") && label.contentEquals("yes")) achesPosetiveYes++;
				
				// achesPosetive no
				if(data.contentEquals("yes") && label.contentEquals("no")) achesPosetiveNo++;
				
				// achesNegative Yes
				if(data.contentEquals("no") && label.contentEquals("yes")) achesNegativeYes++;
				
				// achesNegative no
				if(data.contentEquals("no") && label.contentEquals("no")) achesNegativeNo++;
				break;
				
			case 2: // Cough
				// coughPosetive Yes
				if(data.contentEquals("yes") && label.contentEquals("yes")) coughPosetiveYes++;
				
				// coughPosetive no
				if(data.contentEquals("yes") && label.contentEquals("no")) coughPosetiveNo++;
				
				//coughNegative yes
				if(data.contentEquals("no") && label.contentEquals("yes")) coughNegativeYes++;
				
				// coughNegative no
				if(data.contentEquals("no") && label.contentEquals("no")) coughNegativeNo++;
				break;
				
			case 3: // Sore Throat
				if(data.contentEquals("yes") && label.contentEquals("yes")) soreThroatPosetiveYes++;
				
				// achesPosetive no
				if(data.contentEquals("yes") && label.contentEquals("no")) soreThroatPosetiveNo++;
				
				// normal yes
				if(data.contentEquals("no") && label.contentEquals("yes")) soreThroatNegativeYes++;
				
				// normal no
				if(data.contentEquals("no") && label.contentEquals("no")) soreThroatNegativeNo++;
				break;
				
			case 4: // Recently Traveled from Danger zone
				// traveledDangerZonePosetive Yes
				if(data.contentEquals("yes") && label.contentEquals("yes")) traveledDangerZonePosetiveYes++;
				
				// traveledDangerZonePosetive no
				if(data.contentEquals("yes") && label.contentEquals("no")) traveledDangerZonePosetiveNo++;
				
				// traveledDangerZoneNegative yes
				if(data.contentEquals("no") && label.contentEquals("yes"))traveledDangerZoneNegativeYes++;
				
				// traveledDangerZoneNegative no
				if(data.contentEquals("no") && label.contentEquals("no")) traveledDangerZoneNegativeNo++;
				break;
				
			case 5:
				break;

		}// end switch case
		
	}// end classifier() method
	
	// Method to calculate probability
	public String dataSetTestCases(String temperature, String aches, String cough, String soreThroat, String traveled) {
		
		// Probability variable selectors
		double tempYes = 0.0, achesYes = 0.0, coughYes = 0, soreThroatYes = 0.0, traveledYes = 0.0, tempNo = 0.0, achesNo = 0.0, coughNo = 0.0, soreThroatNo = 0.0, traveledNo = 0.0;
		
		// Temperature
		switch(temperature)
		{
			case "hot":
				tempYes = hotYes;
				tempNo = hotNo;
				break;
			case "normal":
				tempYes = normalYes;
				tempNo = normalNo;
				break;
			case "cool":
				tempYes = coolYes;
				tempNo = coolNo;
				break;
		} // end Temperature Switch case
		
		// aches
		switch(aches)
		{
			case "yes":
				achesYes = achesPosetiveYes;
				achesNo = achesPosetiveNo;
				break;
			case "no":
				achesYes = achesNegativeYes;
				achesNo = achesNegativeNo;
				break;
		}// end aches Switch case
		
		// cough
		switch(cough)
		{
			case "yes":
				coughYes = coughPosetiveYes;
				coughNo = coughPosetiveNo;
				break;
			case "no":
				coughYes = coughNegativeYes;
				coughNo = coughNegativeNo;
				break;
		}// end cough Switch case
		
		// sore throat
		switch(soreThroat)
		{
			case "yes":
				soreThroatYes = soreThroatPosetiveYes;
				soreThroatNo = soreThroatPosetiveNo;
				break;
			case "no":
				soreThroatYes = soreThroatNegativeYes;
				soreThroatNo = soreThroatNegativeNo;
				break;
		}// end sore throat Switch case
		
		// traveled from danger zone
		switch(traveled)
		{
			case "yes":
				traveledYes = traveledDangerZonePosetiveYes;
				traveledNo = traveledDangerZonePosetiveNo;
				break;
			case "no":
				traveledYes = traveledDangerZoneNegativeYes;
				traveledNo = traveledDangerZoneNegativeNo;
				break;
		}// end traveled from danger zone Switch case
		
		yesProbability =	(tempYes/PhasCovidYes)*
							(achesYes/PhasCovidYes)*
							(coughYes/PhasCovidYes)*
							(soreThroatYes/PhasCovidYes)*
							(traveledYes/PhasCovidYes)*
							((double)PhasCovidYes/num_training_rows);
		
		noProbability = 	(tempNo/PhasCovidNo)*
							(achesNo/PhasCovidNo)*
							(coughNo/PhasCovidNo)*
							(soreThroatNo/PhasCovidNo)*
							(traveledNo/PhasCovidNo)*
							((double)PhasCovidNo/num_training_rows);
		
		if(yesProbability/(yesProbability+noProbability) > noProbability/(yesProbability+noProbability)) return "yes";
		else if(yesProbability/(yesProbability+noProbability) < noProbability/(yesProbability+noProbability))return "no";
		else return "none";

	}// end dataSetTestCases()
	
	// Method to self evaluate accuracy
	public void selfEvaluation() {

		for(int i = num_training_rows; i<num_of_rows;i++) {

			String[] token = data[i].split(",");
			//System.out.println(token[0]+" "+token[1]+" "+token[2]+" "+token[3]+" "+token[4]);
			if(dataSetTestCases(token[0],token[1],token[2],token[3],token[4]).contentEquals(token[5]))accuracyCounter++;
		}// end for()
		accuracyPercentage = (int)(((double)accuracyCounter/(num_of_rows-num_training_rows))*100);
		
	}// end selfEvaluation()
	
	// Method to reset data before training
	public void resetClassifier() {
		  PhasCovidYes=0;
		  PhasCovidNo=0;
		  total=0;
		  hotYes=0;
		  hotNo=0;
		  normalYes=0;
		  normalNo=0;
		  coolYes=0;
		  coolNo=0;
		  achesPosetiveYes=0;
		  achesPosetiveNo=0;
		  achesNegativeYes=0;
		  achesNegativeNo=0;
		  coughPosetiveNo=0;
		  coughPosetiveYes=0;
		  coughNegativeNo=0;
		  coughNegativeYes=0;
		  soreThroatPosetiveNo=0;
		  soreThroatPosetiveYes=0;
		  soreThroatNegativeNo=0;
		  soreThroatNegativeYes=0;
		  traveledDangerZonePosetiveNo=0;
		  traveledDangerZonePosetiveYes=0;
		  traveledDangerZoneNegativeNo=0;
		  traveledDangerZoneNegativeYes=0;
	}// end resetClassifier()
	
	// Getters and Setters
	public int getNum_of_rows() {
		return num_of_rows;
	}// end getNum_of_rows()
	
	@SuppressWarnings("static-access")
	public void setNum_of_rows(int num_of_rows) {
		this.num_of_rows = num_of_rows;
	}// end setNum_of_rows()

	public int getAccuracyPercentage() {
		return accuracyPercentage;
	}

	public void setAccuracyPercentage(int accuracyPercentage) {
		this.accuracyPercentage = accuracyPercentage;
	}

	public double getYesProbability() {
		return yesProbability;
	}

	public void setYesProbability(double yesProbability) {
		this.yesProbability = yesProbability;
	}

	public double getNoProbability() {
		return noProbability;
	}

	public void setNoProbability(double noProbability) {
		this.noProbability = noProbability;
	}

	public int getPhasCovidYes() {
		return PhasCovidYes;
	}

	public void setPhasCovidYes(int phasCovidYes) {
		PhasCovidYes = phasCovidYes;
	}

	public int getPhasCovidNo() {
		return PhasCovidNo;
	}

	public void setPhasCovidNo(int phasCovidNo) {
		PhasCovidNo = phasCovidNo;
	}

	public int getTotal() {
		return total;
	}

	@SuppressWarnings("static-access")
	public void setTotal(int total) {
		this.total = total;
	}

	public int getHotYes() {
		return hotYes;
	}

	@SuppressWarnings("static-access")
	public void setHotYes(int hotYes) {
		this.hotYes = hotYes;
	}

	public int getHotNo() {
		return hotNo;
	}

	@SuppressWarnings("static-access")
	public void setHotNo(int hotNo) {
		this.hotNo = hotNo;
	}

	public int getNormalYes() {
		return normalYes;
	}

	@SuppressWarnings("static-access")
	public void setNormalYes(int normalYes) {
		this.normalYes = normalYes;
	}

	public int getNormalNo() {
		return normalNo;
	}

	@SuppressWarnings("static-access")
	public void setNormalNo(int normalNo) {
		this.normalNo = normalNo;
	}

	public int getCoolYes() {
		return coolYes;
	}

	@SuppressWarnings("static-access")
	public void setCoolYes(int coolYes) {
		this.coolYes = coolYes;
	}

	public int getCoolNo() {
		return coolNo;
	}

	@SuppressWarnings("static-access")
	public void setCoolNo(int coolNo) {
		this.coolNo = coolNo;
	}

	public int getAchesPosetiveYes() {
		return achesPosetiveYes;
	}

	@SuppressWarnings("static-access")
	public void setAchesPosetiveYes(int achesPosetiveYes) {
		this.achesPosetiveYes = achesPosetiveYes;
	}

	public int getAchesPosetiveNo() {
		return achesPosetiveNo;
	}

	@SuppressWarnings("static-access")
	public void setAchesPosetiveNo(int achesPosetiveNo) {
		this.achesPosetiveNo = achesPosetiveNo;
	}

	public int getAchesNegativeYes() {
		return achesNegativeYes;
	}

	@SuppressWarnings("static-access")
	public void setAchesNegativeYes(int achesNegativeYes) {
		this.achesNegativeYes = achesNegativeYes;
	}

	public int getAchesNegativeNo() {
		return achesNegativeNo;
	}

	@SuppressWarnings("static-access")
	public void setAchesNegativeNo(int achesNegativeNo) {
		this.achesNegativeNo = achesNegativeNo;
	}

	public int getCoughPosetiveNo() {
		return coughPosetiveNo;
	}

	@SuppressWarnings("static-access")
	public void setCoughPosetiveNo(int coughPosetiveNo) {
		this.coughPosetiveNo = coughPosetiveNo;
	}

	public int getCoughPosetiveYes() {
		return coughPosetiveYes;
	}

	@SuppressWarnings("static-access")
	public void setCoughPosetiveYes(int coughPosetiveYes) {
		this.coughPosetiveYes = coughPosetiveYes;
	}

	public int getCoughNegativeNo() {
		return coughNegativeNo;
	}

	@SuppressWarnings("static-access")
	public void setCoughNegativeNo(int coughNegativeNo) {
		this.coughNegativeNo = coughNegativeNo;
	}

	public int getCoughNegativeYes() {
		return coughNegativeYes;
	}

	@SuppressWarnings("static-access")
	public void setCoughNegativeYes(int coughNegativeYes) {
		this.coughNegativeYes = coughNegativeYes;
	}

	public int getSoreThroatPosetiveNo() {
		return soreThroatPosetiveNo;
	}

	@SuppressWarnings("static-access")
	public void setSoreThroatPosetiveNo(int soreThroatPosetiveNo) {
		this.soreThroatPosetiveNo = soreThroatPosetiveNo;
	}

	public int getSoreThroatPosetiveYes() {
		return soreThroatPosetiveYes;
	}

	@SuppressWarnings("static-access")
	public void setSoreThroatPosetiveYes(int soreThroatPosetiveYes) {
		this.soreThroatPosetiveYes = soreThroatPosetiveYes;
	}

	public int getSoreThroatNegativeNo() {
		return soreThroatNegativeNo;
	}

	@SuppressWarnings("static-access")
	public void setSoreThroatNegativeNo(int soreThroatNegativeNo) {
		this.soreThroatNegativeNo = soreThroatNegativeNo;
	}

	public int getSoreThroatNegativeYes() {
		return soreThroatNegativeYes;
	}

	@SuppressWarnings("static-access")
	public void setSoreThroatNegativeYes(int soreThroatNegativeYes) {
		this.soreThroatNegativeYes = soreThroatNegativeYes;
	}

	public int getTraveledDangerZonePosetiveNo() {
		return traveledDangerZonePosetiveNo;
	}

	@SuppressWarnings("static-access")
	public void setTraveledDangerZonePosetiveNo(int traveledDangerZonePosetiveNo) {
		this.traveledDangerZonePosetiveNo = traveledDangerZonePosetiveNo;
	}

	public int getTraveledDangerZonePosetiveYes() {
		return traveledDangerZonePosetiveYes;
	}

	@SuppressWarnings("static-access")
	public void setTraveledDangerZonePosetiveYes(int traveledDangerZonePosetiveYes) {
		this.traveledDangerZonePosetiveYes = traveledDangerZonePosetiveYes;
	}

	public int getTraveledDangerZoneNegativeNo() {
		return traveledDangerZoneNegativeNo;
	}

	@SuppressWarnings("static-access")
	public void setTraveledDangerZoneNegativeNo(int traveledDangerZoneNegativeNo) {
		this.traveledDangerZoneNegativeNo = traveledDangerZoneNegativeNo;
	}

	public int getTraveledDangerZoneNegativeYes() {
		return traveledDangerZoneNegativeYes;
	}

	@SuppressWarnings("static-access")
	public void setTraveledDangerZoneNegativeYes(int traveledDangerZoneNegativeYes) {
		this.traveledDangerZoneNegativeYes = traveledDangerZoneNegativeYes;
	}

	public int getNum_training_rows() {
		return num_training_rows;
	}
	
	@SuppressWarnings("static-access")
	public void setNum_training_rows(int num_training_rows) {
		this.num_training_rows = num_training_rows;
	}

}// end Classifier class
