# README #

Student Name: 	Thamsanqa Sibanda  
Student Number:	C18727971  
Course: 		DT211c 2nd Year  
Module:			Object Oriented Programming Semester 2  
Lecturer:		Susan  

### Short Description of how the application works ###

* Machine Learning using Naive Bayes
* [Video Demo Link](https://www.youtube.com/watch?v=O8QZCK_ljO0)  
*      
* This is a machine learning application that uses the **Naive Bayes**  
  classifier to predict a patient's symptoms to determine whether a  
  a patient has been infected by the _Covid-19 virus_. The application  
  takes in a user chosen data set and then allows the user to input  
  symptoms by selection, then gives a probability percentage of whether  
  the patient is infected or not.  

### Classes Created for this project ###

* **Control Class**  
* Control to run the COVID-19 Diagnoses prediction Machine Learning program.  
  Program takes in presented symptoms and gives a prediction, whether a patient  
  has been infected by the _Covid-19 virus_ or not.  
  
* **Gui Class**  
* Graphical User interface for the _Covid-19_ application.  
  This is the class that sets up the layout and behavior  
  of the application.  
  
* **FileHandler Class**  
* This class handles all file processing, it opens  
  reads, and writes data to and from files.  

* **Classifier Class**  
*  Class to calculate probabilities of all features. This is where  
   the application learns from the user selected data set in order  
   for it to give a probability of a diagnoses.  

* **DiagnosePatient Class**  
*  Class to diagnose a patient's symptoms.  
   This is the class that takes in the selected symptoms and processes  
   them using the data created/ calculated by the Classifier class.  

### Possible additions and changes if time allowed ###

* **Custom save to file**  
*  An option to allow a user to save the results to a user specified  
   folder with a custom file name.  

* **Add single confirmed case to trained data set**  
*  An add to current data set option that would allow the user to only add  
   a single row of data.
   
* **Error dialog box sounds**  
*  A sound to alert the user of any errors when operating the application.   
