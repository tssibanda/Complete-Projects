/***************************************************************************	
 * 	Description:			This class handles all file processing, it opens
 * 							reads, and writes data to and from files.
 * 
 * 	Author:				 	C18727971 Thamsanqa Sibanda
 * 
 * 	Date:				 	20 March 2020
 ***************************************************************************/

package com.app.c19;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.filechooser.FileSystemView;

public class FileHandler {
	
	// FileHandler Attributes
	private String filePath;
	private File fileToProcess;
	private Scanner fileScanner;
	private String[] dataSet; 
	
	// File Handler Constructor
	public FileHandler(String filePath) {
		this.filePath = filePath;		
	}// end constructor
	
	// Save results to file constructor
	public FileHandler() {
		
	}// end constructor
	
	// Methods to process the files
	// Open File
 	public void openFile() {
		fileToProcess = new File(filePath);
	}// end openFile()
	
	// Read file
	public String[] readFile() {
		
		try{
			
			fileScanner = new Scanner(fileToProcess);
			int counter = 0;

			while(fileScanner.hasNext()) {
				counter++;
				fileScanner.next();
			}// end while()
			
			fileScanner.reset();
			fileScanner = new Scanner(fileToProcess);
			dataSet = new String[counter];
			
			for(int i = 0; i<counter;i++) { 
				dataSet[i]=fileScanner.next();
			}// end for()
			
			fileScanner.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}// end try catch
		
		return dataSet;
		
	}// end readFile()
	
	// save results of diagnoses to a file in the Documents folder
	public boolean writeToFile(String patientName, String temperature, String aches, String cough, String soreThroat, String traveled, String diagnosesResult) {
		String DocumentsFolder = FileSystemView.getFileSystemView().getDefaultDirectory().getPath();
		String saveFileName = "Diagnoses Results.csv";
		
		File saveResults = new File(DocumentsFolder+"\\"+saveFileName);
		FileWriter writer;
		
		if(saveResults.exists()) {
			
			try {
				writer = new FileWriter(saveResults, true);
				String temp = String.format("%s%s,%s,%s,%s,%s,%s,%s","\n",patientName, temperature, aches, cough, soreThroat, traveled, diagnosesResult);
				writer.write(temp);
				writer.flush();
				writer.close();
				return true;
			} catch (IOException e) {
				return false;
			}// end try catch
			
		}else {
			try {
				writer = new FileWriter(saveResults, true);
				String temp = String.format("%s%s,%s,%s,%s,%s,%s,%s","\n","Patient Name", "Temperature", "Aches", "Cough", "Sore Throat", "Recently Traveled from danger zone", "Has Covid19");
				String temp2 = String.format("%s%s,%s,%s,%s,%s,%s,%s","\n",patientName, temperature, aches, cough, soreThroat, traveled, diagnosesResult);
				writer.write(temp);
				writer.write(temp2);
				writer.flush();
				writer.close();
				return true;
			} catch (IOException e) {
				return false;
			}// end try catch
			
		}// end if else()

	}// end writeToFile();
	
}// End File Handler Class
