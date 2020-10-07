/***************************************************************************	
 * 	Description:			Graphical User interface for the Covid-19 
 * 							application. 
 * 							This is the class that sets up the layout and
 * 							behavior of the application
 * 
 * 	Author:				 	C18727971 Thamsanqa Sibanda
 * 
 * 	Date:				 	20 March 2020
 ***************************************************************************/

package com.app.c19;

// Imported Libraries
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

@SuppressWarnings("serial")
public class Gui extends JFrame {
	// Class Attributes
	
	// test train Machine class
	Classifier dataSet;
	// Welcome page attributes
	private JLabel welcomeLabel, instructionHeadingLabel, instructionLabel;
	private JPanel welcomePanel;

	// Train Machine page attributes
	private boolean trained = false;
	private JLabel samplesLabel, accuracyLabel, accuracyPercentage,no_of_samples, selectDatasetLabel, fileNameBox;
	private JButton retrainMachineButton, addSingleSampleButton, browseButton, trainMachineButton;
	private JPanel trainedPanel, trainMachinePanel;
	// FileChooser attributes
	private JFileChooser selectFile;
	int returnVal;
	private String filePath;
	
	// Test Patient Attributes
	private String[] temperature = {"hot","normal","cool"};
	private String[] aches = {"yes","no"};
	private String[] cough = {"yes","no"};
	private String[] soreThroat = {"yes","no"};
	private String[] TravelledFromDangerZone = {"yes","no"};
	private boolean showResults = false;
	private JLabel  nLabel, tLabel, aLabel, cLabel, stLabel, tfdzLabel, patientNameLabel, patientName, diagDescription, testResult;
	private JButton diagnoseButton, backButton, saveResultButton;
	private JTextField nameTextField;
	private JComboBox<String> temperatureComboBox, achesComboBox,coughComboBox,sorethComboBox,travdzoneComboBox;
	private JPanel diagnosePatientPanel, diagnosesResultsPanel;
	private long result;
	
	// Main Gui Attributes
	private JLayeredPane pages, machinePage, diagnosePage;
	private JPanel leftPanel, topPanel, welcomePagePanel,machinePagePanel,patientPagePanel, gridPanel;
	private JLabel logo;
	private JLabel trademark, description;
	private JLabel dashboard;
	private JLabel welcome, machine, patient;
	private JLabel wHeading, mHeading, pHeading;
	private boolean wselected=true,mselected=false,pselected=false;
	
	// Menu Attributes
	private Font menu = new Font("Segoe UI", Font.PLAIN,18);
	private Font pageHeadingFont = new Font("Segoe UI", Font.BOLD,18);
	private Color pageHeadingsFontColor = new Color(81,92,104);
	private Border pageHeadingBorder = BorderFactory.createEmptyBorder(23, 33, 12, 0);
	private GridBagConstraints gc;
	// Menu selected item state settings
	private EmptyBorder eBorder = new EmptyBorder (12,70,12,45); // top, left, bottom, right
	private MatteBorder lBorder = new MatteBorder(0,5,0,0, new Color(130,191,171));
	// Menu hover unselected state settings
	private EmptyBorder ehBorder = new EmptyBorder (12, 70, 12, 45); // 
	private MatteBorder lhBorder = new MatteBorder(0,5,0,0, new Color(81,92,104));
	// Menu unselected state settings
	private EmptyBorder euBorder = new EmptyBorder (12, 70, 12, 45); // 
	private MatteBorder luBorder = new MatteBorder(0,5,0,0, new Color(97,110,125));
	
	// Constructor
	public Gui(String title) {
		
		// Set up main Window
		super(title);	
		setBounds(450,100,874,870);// program window size and positions
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/imgs/WindowIcon.png")));
		java.awt.Container mainContainer = this.getContentPane();
		mainContainer.setLayout(new BorderLayout(0,0));
		
		leftPanel = new JPanel();
		leftPanel.setBackground(new Color(97,110,125));
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
	
		// Banner Logo
		createBannerLogo();
		
		// Menu Elements
		welcomeMenuLabel();
		machineMenuLabel();
		patientMenuLabel();
			
		// Test patient, train machine, Welcome page elements
		welcomePage();
		machinePage();
		diagnosePage();
	
		// Menu Label Listeners
		welcomeMenuItem();
		machineMenuItem();
		testPatientMenuItem();
		
		//topPanel
		topPanel = new JPanel();
		topPanel.setLayout(new FlowLayout(0,0,0));
		topPanel.setBackground(new Color(97,110,125));
		
		//Left Panel
		gridPanel = new JPanel();
		gridPanel.setLayout(new BoxLayout(gridPanel, BoxLayout.PAGE_AXIS));
		gridPanel.setBackground(new Color(97,110,125));
		
		//Body Panel
		pages = new JLayeredPane();
		pages.setBackground(Color.WHITE);
		pages.setOpaque(true);
		
		// Welcome Page
		welcomePagePanel = new JPanel();
		welcomePagePanel.setLayout(new BoxLayout(welcomePagePanel, BoxLayout.PAGE_AXIS));
		welcomePagePanel.setBackground(Color.WHITE);
		welcomePagePanel.setBounds(0,0,635,550);
		welcomePagePanel.setVisible(true);
		
		// Train Machine Page
		machinePagePanel = new JPanel();
		machinePagePanel.setLayout(new BoxLayout(machinePagePanel, BoxLayout.PAGE_AXIS));
		machinePagePanel.setBackground(Color.WHITE);
		machinePagePanel.setBounds(0,0,635,650);
		machinePagePanel.setVisible(false);

		// Patient Diagnoses page
		patientPagePanel = new JPanel();
		patientPagePanel.setLayout(new BoxLayout(patientPagePanel, BoxLayout.PAGE_AXIS));
		patientPagePanel.setBackground(Color.WHITE);
		patientPagePanel.setBounds(0,0,635,650);
		patientPagePanel.setVisible(false);
				
		// setup and reset grid bag constraints
		gc = new GridBagConstraints();
		resetGridBagConstraints();
		
		topPanel.add(logo);
		topPanel.add(gridPanel);
		gridPanel.add(trademark);
		gridPanel.add(description);
		
		leftPanel.add(dashboard);
		leftPanel.add(welcome);
		leftPanel.add(machine);
		leftPanel.add(patient);

		pages.add(welcomePagePanel);
		pages.add(machinePagePanel);
		pages.add(patientPagePanel);
		
		
		// Welcome Page elements
		welcomePageElements();
		
		// Train Machine Page elements
		machinePage = new JLayeredPane();
		trainedPanel();
		trainMachinePanel();
		
		// Diagnose Patient Page Elements
		diagnosePage = new JLayeredPane();
		displayDiagnosesResults();
		displayTestPatientPanel();

		// Main Application window Layout
		mainContainer.add(pages, BorderLayout.CENTER);
		mainContainer.add(leftPanel, BorderLayout.WEST);
		mainContainer.add(topPanel, BorderLayout.NORTH);
	
	}// end constructor
	
	// Display contents of welcome page
	private void welcomePageElements() {
		
		welcomePanel = new JPanel();
		welcomePanel.setLayout(new GridBagLayout());
		welcomePanel.setBackground(Color.white);
		welcomePanel.setVisible(true);
		
		welcomeLabel = new JLabel("<html>This program will give a probability diagnoses of the Coronavirus."
				+ "<br>It is based on Naïve Bayes probability and will give a percentage"
				+ "<br>of how likely a patient’s known symptoms indicate whether the patient"
				+ "<br>has been infected by the Coronavirus or not.");
		welcomeLabel.setFont(new Font("Open Sans", Font.PLAIN,13));
		
		instructionHeadingLabel = new JLabel("Instructions");
		instructionHeadingLabel.setFont(new Font("Segoe UI", Font.BOLD,16));
		
		instructionLabel = new JLabel("<html>To use this program, the machine must first be trained."
				+ "<br>Click on the Train Machine menu button on the left to train the machine."
				+ "<br>Select a dataset to use to train the machine and then click on the train "
				+ "<br>machine button under the form."
				
				+ "<br><br>The machine can be retrained using a new dataset, this will reset the machine"
				+ "<br>and retrain it using the newly selected dataset. "
				
				+ "<br><br>To test diagnose a patient’s symptoms, click on the Test Patient menu item on"
				+ "<br>the left, select the patient’s symptoms and click the Diagnose button at the"
				+ "<br>bottom of the form."
				+ "<br><br>Diagnoses results for each patient can be saved to a file inside the system"
				+ "<br>default documents folder in a file named \" <b>Diagnoses Results.csv</b> \".");
		instructionLabel.setFont(new Font("Open Sans", Font.PLAIN,13));
	
		welcomePagePanel.add(welcomePanel);
		// ............... row 1 ................. //
		resetGridBagConstraints();
		gc.weightx = 1;
		gc.weighty = 0.1;
		gc.gridx = 1;
		gc.gridy = 1;
		gc.insets = new Insets(0,0,0,0);
		welcomePanel.add(wHeading,gc);
		// ............... row 2 ................. //
		gc.weightx = 1;
		gc.weighty = 0.4;
		gc.gridx = 1;
		gc.gridy = 2;
		gc.insets = new Insets(0,100,0,0);
		welcomePanel.add(welcomeLabel,gc);
		// ............... row 3 ................. //
		gc.weightx = 1;
		gc.weighty = 0.1;
		gc.gridx = 1;
		gc.gridy = 3;
		gc.insets = new Insets(10,100,0,0);
		welcomePanel.add(instructionHeadingLabel,gc);
		// ............... row 4 ................. //
		gc.weightx = 1;
		gc.weighty = 1;
		gc.gridx = 1;
		gc.gridy = 4;
		gc.insets = new Insets(10,100,0,0);
		welcomePanel.add(instructionLabel,gc);
	}// end welcomePageElements()
	
	// Display welcome page heading
	private void welcomePage() {
		wHeading = new JLabel("Welcome");
		wHeading.setForeground(pageHeadingsFontColor);
		wHeading.setIcon(new ImageIcon(Gui.class.getResource("/imgs/WelcomeIcon.png")));
		wHeading.setFont(pageHeadingFont);
		wHeading.setBorder(pageHeadingBorder);
	}
	
	// Display Train Machine page heading
	private void machinePage() {
		mHeading = new JLabel("Train Machine");
		mHeading.setForeground(pageHeadingsFontColor);
		mHeading.setIcon(new ImageIcon(Gui.class.getResource("/imgs/TrainMachineIcon.png")));
		mHeading.setFont(pageHeadingFont);
		mHeading.setBorder(pageHeadingBorder);
	}
	
	// Display machine information if trained
	private void trainedPanel() {
		
		// trained panel
		trainedPanel = new JPanel();
		trainedPanel.setLayout(new GridBagLayout());
		trainedPanel.setBackground(Color.WHITE);
		trainedPanel.setBounds(0,0,480,450);
		trainedPanel.setVisible(false);
		
		// Trained panel elements		
		samplesLabel = new JLabel("Total Number Of Samples:");
		samplesLabel.setFont(new Font("Open Sans", Font.PLAIN,14));
		samplesLabel.setOpaque(true);
		
		no_of_samples = new JLabel("300");
		no_of_samples.setFont(new Font("Open Sans", Font.BOLD,14));
		no_of_samples.setOpaque(true);
		
		accuracyLabel = new JLabel("Sample Data Diagnoses Accuracy: ");
		accuracyLabel.setFont(new Font("Open Sans", Font.PLAIN,14));
		accuracyLabel.setOpaque(true);
		
		accuracyPercentage = new JLabel("75%");
		accuracyPercentage.setFont(new Font("Open Sans", Font.BOLD,14));
		accuracyPercentage.setOpaque(true);
		
		retrainMachineButton = new JButton("Retrain Machine");
		retrainMachineButton.setFont(new Font("Segoe UI", Font.PLAIN,14));
		retrainMachineButton.setBackground(new Color(97,110,125));
		retrainMachineButton.setForeground(Color.WHITE);
		retrainMachineButton.setFocusable(false);
		
		addSingleSampleButton = new JButton("Add Single Sample");
		addSingleSampleButton.setFont(new Font("Segoe UI", Font.PLAIN,14));
		addSingleSampleButton.setBackground(new Color(97,110,125));
		addSingleSampleButton.setForeground(Color.WHITE);
		addSingleSampleButton.setFocusable(false);
		
		retrainMachineButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	            	if(e.getSource()==retrainMachineButton) {
	            		
	            		try
	            		{
	            			filePath = null;
	            			fileNameBox.setText("Choose a file...");
	            			trained = false;
	                		trainedCheck();
	            		}
	            		catch(Exception errorFile) {
	            			JOptionPane.showMessageDialog(null,"Invalid file selected");
	            		}// end try catch
	        			
	        		}// end if
	            }// end actionPerfomed()
	
	        });// end trainMachineButton Action Listener
		
		// Trained Panel elements Layout
		// ...............row 1 : col 1................. //
		gc.weightx = 1;
		gc.weighty = 0.1;
		gc.gridx = 1;
		gc.gridy = 1;
		gc.insets = new Insets(30,100,0,0);
		trainedPanel.add(samplesLabel,gc);
		// ...............row 1 : col 2................. //
		gc.weightx = 1;
		gc.weighty = 0.1;
		gc.gridx = 2;
		gc.gridy = 1;
		gc.insets = new Insets(30,0,0,100);
		trainedPanel.add(no_of_samples,gc);
		// ...............row 2 : col 1................. //
		gc.weightx = 1;
		gc.weighty = 0.1;
		gc.gridx = 1;
		gc.gridy = 2;
		gc.insets = new Insets(0,100,0,0);
		trainedPanel.add(accuracyLabel,gc);
		// ...............row 2 : col 2................. //
		gc.weightx = 1;
		gc.weighty = 0.1;
		gc.gridx = 2;
		gc.gridy = 2;
		gc.insets = new Insets(0,0,0,0);
		trainedPanel.add(accuracyPercentage,gc);
		// ...............row 3 : col 1................. //
		gc.weightx = 1;
		gc.weighty = 1.0;
		gc.gridx = 1;
		gc.gridy = 3;
		gc.gridwidth = 2;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		gc.insets = new Insets(30,100,0,0);
		trainedPanel.add(retrainMachineButton,gc);

	}// end trainedPanel()
	
	// Display train Machine Panel elements
	private void trainMachinePanel() {
		
		//train Machine Panel elements
		trainMachinePanel = new JPanel();
		trainMachinePanel.setLayout(new GridBagLayout());
		trainMachinePanel.setBackground(Color.WHITE);
		trainMachinePanel.setBounds(0,0,480,450);
		trainMachinePanel.setVisible(false);
		
		selectDatasetLabel = new JLabel("Select dataset:");
		selectDatasetLabel.setFont(new Font("Open Sans", Font.PLAIN,14));
		
		fileNameBox = new JLabel("Choose a file...");
		fileNameBox.setOpaque(true);
		fileNameBox.setBackground(Color.WHITE);
		fileNameBox.setPreferredSize(new Dimension(250,30));
		EmptyBorder eBdr = new EmptyBorder (10, 10, 10, 10); 
	    LineBorder lBdr = new LineBorder (new Color(97,110,125));
	    fileNameBox.setBorder (BorderFactory.createCompoundBorder (lBdr, eBdr));
	    
	    browseButton = new JButton("Browse...");
	    browseButton.setFont(new Font("Segoe UI", Font.PLAIN,14));
	    browseButton.setBackground(new Color(97,110,125));
	    browseButton.setForeground(Color.WHITE);
	    browseButton.setFocusable(false);
	    
	    trainMachineButton = new JButton("Train Machine");
	    trainMachineButton.setFont(new Font("Segoe UI", Font.PLAIN,14));
	    trainMachineButton.setBackground(new Color(97,110,125));
	    trainMachineButton.setForeground(Color.WHITE);
	    trainMachineButton.setFocusable(false);
		
	    selectFile = new JFileChooser();
	    selectFile.setAcceptAllFileFilterUsed(false);
	    selectFile.setFileFilter(new FileNameExtensionFilter("Supported text files ( *.txt; *.csv )","csv","txt"));
	    
	    // Browse button action listener.
	    browseButton.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	if(e.getSource()==browseButton) {
	    			returnVal = selectFile.showOpenDialog(null);
	    			if(returnVal == JFileChooser.APPROVE_OPTION) {
	    				try {
	    					// source https://www.geeksforgeeks.org/path-getfilename-method-in-java-with-examples/
	    					filePath = selectFile.getSelectedFile().getAbsolutePath();
	    					Path path = Paths.get(filePath);
	    					Path fileName = path.getFileName(); 
	    					fileNameBox.setText(""+fileName);
	    				}
	    				catch(Exception csv) {
	    					fileNameBox.setText("Invalid file");
	    				}
	    				
	    			}else{
	    				filePath = null;
	    				fileNameBox.setText("Choose a file...");
	    			}// end inner if
	    		}// end if
	
	        }// end actionPerfomed()
	    });// end Action Listener
	    
	    // Train machin button action listener
	    trainMachineButton.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	if(e.getSource()==trainMachineButton) {
	        		try
	        		{
	        			dataSet = new Classifier(filePath);
	        			dataSet.resetClassifier();
	            		dataSet.trainMachine();
	            		trained = true;
	            		no_of_samples.setText(String.valueOf(dataSet.getNum_of_rows()));
	            		accuracyPercentage.setText(String.valueOf(dataSet.getAccuracyPercentage())+"%");
	            		trainedCheck();
	        		}
	        		catch(Exception errorFile) {
	        			JOptionPane.showMessageDialog(null,"Invalid file selected");
	        		}// end try catch
	    			
	    		}// end if
	        }// end actionPerfomed()
	
	    });// end trainMachineButton Action Listener
	    
	   
	    // Trained Panel elements Layout
	    // reset grid bag constraints
	 	resetGridBagConstraints();
		// ...............row 1 : col 1................. //
		gc.weightx = 1;
		gc.weighty = 0.1;
		gc.gridx = 1;
		gc.gridy = 1;
		gc.insets = new Insets(30,100,0,0);
		trainMachinePanel.add(selectDatasetLabel,gc);
		// ...............row 2 : col 1................. //
		gc.weightx = 1;
		gc.weighty = 0.1;
		gc.gridx = 1;
		gc.gridy = 2;
		gc.insets = new Insets(0,100,0,0);
		trainMachinePanel.add(fileNameBox,gc);
		// ...............row 2 : col 1................. //
		gc.weightx = 1;
		gc.weighty = 0.1;
		gc.gridx = 1;
		gc.gridy = 2;
		gc.insets = new Insets(0,360,0,0);
		trainMachinePanel.add(browseButton,gc);
		// ...............row 3 : col 1................. //
		gc.weightx = 1;
		gc.weighty = 2.0;
		gc.gridx = 1;
		gc.gridy = 3;
		gc.insets = new Insets(20,100,0,0);
		trainMachinePanel.add( trainMachineButton,gc);
		
		machinePagePanel.add(mHeading);
		machinePagePanel.add(machinePage);
		machinePage.add(trainedPanel);
		machinePage.add(trainMachinePanel);
	}// end trainMachinePanel()

	// Display diagnose page heading
	private void diagnosePage() {
		pHeading = new JLabel("Diagnose Patient Symptoms");
		pHeading.setForeground(pageHeadingsFontColor);
		pHeading.setIcon(new ImageIcon(Gui.class.getResource("/imgs/TestPatientIcon.png")));
		pHeading.setFont(pageHeadingFont);
		pHeading.setBorder(pageHeadingBorder);
		
	}// end diagnosePage()
	
	// Display patient diagnoses prediction
	private void displayDiagnosesResults() {
		
		diagnosePage.setBackground(Color.white);
		diagnosePage.setOpaque(true);

		diagnosesResultsPanel = new JPanel();
		diagnosesResultsPanel.setLayout(new GridBagLayout());
		diagnosesResultsPanel.setBackground(Color.WHITE);
		diagnosesResultsPanel.setBounds(0,0,600,450);
		diagnosesResultsPanel.setVisible(false);
				
		patientNameLabel = new JLabel("Patient Name:");
		patientNameLabel.setFont(new Font("Open Sans", Font.PLAIN,14));
		
		patientName = new JLabel("");
		patientName.setFont(new Font("Open Sans", Font.BOLD,14));
		
		diagDescription = new JLabel("<html>Probability of patient<br> testing positive for COVID-19: ");
		diagDescription.setFont(new Font("Open Sans", Font.PLAIN,14));
		
		testResult = new JLabel("");
		testResult.setFont(new Font("Open Sans", Font.BOLD,14));
		
		backButton = new JButton("Go Back");
		backButton.setFont(new Font("Segoe UI", Font.PLAIN,14));
		backButton.setBackground(new Color(97,110,125));
		backButton.setForeground(Color.WHITE);
		backButton.setFocusable(false);
		backButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	            	if(e.getSource()==backButton) {
	            		// enter code to go back
	            		showResults = false;
	            		tested();
	        		}// end if
	            }// end actionPerfomed()

	        });// end trainMachineButton Action Listener
		
		saveResultButton = new JButton("Save Results");
		saveResultButton.setFont(new Font("Segoe UI", Font.PLAIN,14));
		saveResultButton.setBackground(new Color(97,110,125));
		saveResultButton.setForeground(Color.WHITE);
		saveResultButton.setFocusable(false);
		saveResultButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if(e.getSource()==saveResultButton) {
            		// enter code to go back
            		boolean saved =false;
            		FileHandler saveResults = new FileHandler();
            		saved = saveResults.writeToFile(nameTextField.getText(),
							(String)temperatureComboBox.getSelectedItem(),
							(String)achesComboBox.getSelectedItem(),
							(String)coughComboBox.getSelectedItem(),
							(String)sorethComboBox.getSelectedItem(),
							(String)travdzoneComboBox.getSelectedItem(),
							diagnoses());
            		
            		if(saved) {
            			JOptionPane.showMessageDialog(null,"Results have been saved to \"Diagnoses Results.csv\" file in the system Documents folder.");
            		}else {
            			JOptionPane.showMessageDialog(null,"Results have been saved to \"Diagnoses Results.csv\" file in the system Documents folder.");
            		}// end if else ()
        		}// end if
            }// end actionPerfomed()

        });// end trainMachineButton Action Listener
		// Trained Panel elements Layout
		// ...............row 1 : col 1................. //
		gc.weightx = 1;
		gc.weighty = 0.1;
		gc.gridx = 1;
		gc.gridy = 1;
		gc.insets = new Insets(30,100,0,0);
		diagnosesResultsPanel.add(patientNameLabel,gc);
		// ...............row 1 : col 2................. //
		gc.weightx = 1;
		gc.weighty = 0.1;
		gc.gridx = 2;
		gc.gridy = 1;
		gc.insets = new Insets(30,0,0,100);
		diagnosesResultsPanel.add(patientName,gc);
		// ...............row 2 : col 1................. //
		gc.weightx = 1;
		gc.weighty = 0.1;
		gc.gridx = 1;
		gc.gridy = 2;
		gc.insets = new Insets(0,100,0,0);
		diagnosesResultsPanel.add(diagDescription,gc);
		// ...............row 2 : col 2................. //
		gc.weightx = 1;
		gc.weighty = 0.1;
		gc.gridx = 2;
		gc.gridy = 2;
		gc.insets = new Insets(0,0,0,0);
		diagnosesResultsPanel.add(testResult,gc);
		
		// ...............row 3 : col 1................. //
		gc.weightx = 0.1;
		gc.weighty = 1.0;
		gc.gridx = 1;
		gc.gridy = 3;
		//gc.gridwidth = 1;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		gc.insets = new Insets(20,100,0,0);
		diagnosesResultsPanel.add(backButton,gc);
		
		// ...............row 3 : col 1................. //
		gc.weightx = 1.0;
		gc.weighty = 1.0;
		gc.gridx = 1;
		gc.gridy = 3;
		//gc.gridwidth = 1;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		gc.insets = new Insets(20,200,0,0);
		diagnosesResultsPanel.add(saveResultButton,gc);
		
		diagnosePage.add(diagnosesResultsPanel);

	}// end displayDiagnosesResults()

	// Display Diagnose patient form
	private void displayTestPatientPanel() {
		
		diagnosePatientPanel = new JPanel();
		diagnosePatientPanel.setLayout(new GridBagLayout());
		diagnosePatientPanel.setBackground(Color.WHITE);
		diagnosePatientPanel.setBounds(0,0,600,500);
		diagnosePatientPanel.setVisible(true);
			
		nameTextField = new JTextField();
		nameTextField.setMinimumSize(new Dimension(180, 25));
		nameTextField.setPreferredSize(new Dimension(180, 25));
		nameTextField.setMaximumSize(new Dimension(180, 25));
		nLabel = new JLabel("Patient name:");
		nLabel.setFont(new Font("Open Sans", Font.PLAIN,14));
		
		temperatureComboBox = new JComboBox<>(temperature);
		inputBoxSize(temperatureComboBox);
		tLabel = new JLabel("Temperature:");
		tLabel.setFont(new Font("Open Sans", Font.PLAIN,14));
		
		achesComboBox = new JComboBox<>(aches);
		inputBoxSize(achesComboBox);
		aLabel = new JLabel("Aches:");
		aLabel.setFont(new Font("Open Sans", Font.PLAIN,14));
		
		coughComboBox = new JComboBox<>(cough);
		inputBoxSize(coughComboBox);
		cLabel = new JLabel("Cough:");
		cLabel.setFont(new Font("Open Sans", Font.PLAIN,14));
		
		sorethComboBox = new JComboBox<>(soreThroat);
		inputBoxSize(sorethComboBox);
		stLabel = new JLabel("Sore throat:");
		stLabel.setFont(new Font("Open Sans", Font.PLAIN,14));
		
		travdzoneComboBox = new JComboBox<>(TravelledFromDangerZone);
		inputBoxSize(travdzoneComboBox);
		tfdzLabel = new JLabel("Travelled from danger zone:");
		tfdzLabel.setFont(new Font("Open Sans", Font.PLAIN,14));
		
		diagnoseButton = new JButton("DIAGNOSE");
		diagnoseButton.setFont(new Font("Segoe UI", Font.PLAIN,14));
		diagnoseButton.setBackground(new Color(97,110,125));
		diagnoseButton.setForeground(Color.WHITE);
		diagnoseButton.setFocusable(false);
		
		// Diagnose Button action listener 
		diagnoseButton.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	if(e.getSource()==diagnoseButton) {
	        		try
	        		{
	        			if(trained) {
	        				if((nameTextField.getText()).contentEquals("") || (nameTextField.getText()) == null) {
	        					JOptionPane.showMessageDialog(null,"Please input a name.");
	        				}else {
	
		            			DiagnosePatient patientX = new DiagnosePatient(	nameTextField.getText(),
		            															(String)temperatureComboBox.getSelectedItem(),
		            															(String)achesComboBox.getSelectedItem(),
		            															(String)coughComboBox.getSelectedItem(),
		            															(String)sorethComboBox.getSelectedItem(),
		            															(String)travdzoneComboBox.getSelectedItem());
		            			patientX.testPatient();
		            			patientName.setText(patientX.getPatientName());
		            			result =  patientX.getHasCovidProb();
		            			testResult.setText("<html><br>" + patientX.getHasCovidProbString() + "%");
		            			showResults = true;
		            			tested();
	        				}// end inner if else()
	        			}
	        			else {
	        				JOptionPane.showMessageDialog(null,"You need to train the Machine with a data set before you can diagnose patients!\nPlease click \"Train Machine\" menu item on the left.");
	        			}// end outer if else()
	        		}
	        		catch(Exception errorFile) {
	        			JOptionPane.showMessageDialog(null,"Invalid file selected");
	        		}// end try catch

	    		}// end if
	        }// end actionPerfomed()
	
	    });// end trainMachineButton Action Listener
	
		// reset grid bag constraints
		resetGridBagConstraints();

		// ...............row 2................. //
		gc.weighty = 0.1;
		gc.gridy = 2;
		gc.insets = new Insets(0,100,0,0);
		diagnosePatientPanel.add(nLabel,gc);
		// ...............row 3................. //
		gc.weighty = 0.5;
		gc.insets = new Insets(2,100,0,0);
		gc.gridy = 3;
		diagnosePatientPanel.add(nameTextField,gc);
		// ...............row 4................. //
		gc.weighty = 0.1;
		gc.gridy = 4;
		gc.insets = new Insets(0,100,0,0);
		diagnosePatientPanel.add(tLabel,gc);
		// ...............row 5................. //
		gc.weighty = 0.5;
		gc.insets = new Insets(2,100,0,0);
		gc.gridy = 5;
		diagnosePatientPanel.add(temperatureComboBox,gc);
		// ...............row 6................. //
		gc.weighty = 0.1;
		gc.insets = new Insets(0,100,0,0);
		gc.gridy = 6;
		diagnosePatientPanel.add(aLabel,gc);
		// ...............row 7................. //
		gc.weighty = 0.5;
		gc.insets = new Insets(2,100,0,0);
		gc.gridy = 7;
		diagnosePatientPanel.add(achesComboBox,gc);
		// ...............row 8................. //
		gc.weighty = 0.1;
		gc.insets = new Insets(0,100,0,0);
		gc.gridy = 8;
		diagnosePatientPanel.add(cLabel,gc);
		// ...............row 9................. //
		gc.weighty = 0.5;
		gc.insets = new Insets(2,100,0,0);
		gc.gridy = 9;
		diagnosePatientPanel.add(coughComboBox,gc);
		// ...............row 10................. //
		gc.weighty = 0.1;
		gc.insets = new Insets(0,100,0,0);
		gc.gridy = 10;
		diagnosePatientPanel.add(stLabel,gc);
		// ...............row 11................. //
		gc.weighty = 0.5;
		gc.insets = new Insets(2,100,0,0);
		gc.gridy = 11;
		diagnosePatientPanel.add(sorethComboBox,gc);
		// ...............row 12................. //
		gc.weighty = 0.1;
		gc.insets = new Insets(0,100,0,0);
		gc.gridy = 12;
		diagnosePatientPanel.add(tfdzLabel,gc);
		// ...............row 13................. //
		gc.weighty = 0.5;
		gc.insets = new Insets(2,100,0,0);
		gc.gridy = 13;
		diagnosePatientPanel.add(travdzoneComboBox,gc);
		// ...............row 14................. //
		gc.weighty = 1;
		gc.insets = new Insets(15,100,0,0);
		gc.gridy = 14;
		diagnosePatientPanel.add(diagnoseButton,gc);
		
		diagnosePage.add(diagnosePatientPanel);
		patientPagePanel.add(pHeading);
		patientPagePanel.add(diagnosePage);
		
	}// end displayTestPatientPanel()
	
	// Panel chooser
	private void tested() {
		if(showResults) {
			diagnosePatientPanel.setVisible(false);
			diagnosesResultsPanel.setVisible(true);
		}else {
			diagnosesResultsPanel.setVisible(false);
			diagnosePatientPanel.setVisible(true);
		}// end if else()
	}// end tested
	
	// Test patient Menu label
 	private void testPatientMenuItem() {
		patient.addMouseListener(new MouseAdapter() {
			
			public void mouseClicked(MouseEvent event) {
				if(!pselected) {
					wselected=false;
					mselected=false;
					pselected=true;
					welcome.setForeground(Color.white);
					welcome.setBackground(new Color(97,110,125));
					welcome.setBorder(BorderFactory.createCompoundBorder (luBorder, euBorder));
					machine.setForeground(Color.white);
					machine.setBackground(new Color(97,110,125));
					machine.setBorder(BorderFactory.createCompoundBorder (luBorder, euBorder));
					patient.setBackground(new Color(81,92,104));
					patient.setForeground(new Color(204,255,241));
					patient.setBorder(BorderFactory.createCompoundBorder (lBorder, eBorder));
					
					welcomePagePanel.setVisible(false);
					machinePagePanel.setVisible(false);
					patientPagePanel.setVisible(true);
					
				}// end if()
				
			}// end mouseClicked()
			public void mousePressed(MouseEvent event) {
				
			}// end mousePressed()
			public void mouseReleased(MouseEvent event) {
				
			}// end mouseReleased()
			public void mouseEntered(MouseEvent event) {
				
				if(!pselected) {
					patient.setForeground(new Color(81,92,104));
					patient.setBackground(new Color(177,189,201));
			        patient.setBorder (BorderFactory.createCompoundBorder (lhBorder, ehBorder));
				}// end if()
				
			}// end mouseEntered()
			
			public void mouseExited(MouseEvent event) {
				if(!pselected) {
					patient.setForeground(Color.WHITE);
					patient.setBackground(new Color(97,110,125));
			        patient.setBorder (BorderFactory.createCompoundBorder (luBorder, euBorder));
				}// end if()
			}// end mouseExited()
		}); // end pMouse listener
	}// end testPatientMenuItem()
	
 	// Train Machine Menu label
	private void machineMenuItem() {
		machine.addMouseListener(new MouseAdapter() {
			
			public void mouseClicked(MouseEvent event) {
				if(!mselected) {
					wselected=false;
					mselected=true;
					pselected=false;
					welcome.setForeground(Color.white);
					welcome.setBackground(new Color(97,110,125));
					welcome.setBorder(BorderFactory.createCompoundBorder (luBorder, euBorder));
					patient.setForeground(Color.white);
					patient.setBackground(new Color(97,110,125));
					patient.setBorder(BorderFactory.createCompoundBorder (luBorder, euBorder));
					machine.setBackground(new Color(81,92,104));
					machine.setForeground(new Color(204,255,241));
					machine.setBorder(BorderFactory.createCompoundBorder (lBorder, eBorder));
					
					welcomePagePanel.setVisible(false);
					patientPagePanel.setVisible(false);
					machinePagePanel.setVisible(true);
					trainedCheck();
				}// end if()
				
			}// end mouseClicked()
	
			public void mousePressed(MouseEvent event) {
				
			}// end mousePressed()
			public void mouseReleased(MouseEvent event) {
				
			}// end mouseReleased()
			public void mouseEntered(MouseEvent event) {
				
				if(!mselected) {
					machine.setForeground(new Color(81,92,104));
					machine.setBackground(new Color(177,189,201));
			        machine.setBorder (BorderFactory.createCompoundBorder (lhBorder, ehBorder));
				}// end if()
				
			}// end mouseEntered()
			
			public void mouseExited(MouseEvent event) {
				if(!mselected) {
					machine.setForeground(Color.WHITE);
					machine.setBackground(new Color(97,110,125));
			        machine.setBorder (BorderFactory.createCompoundBorder (luBorder, euBorder));
				}// end if()
				
			}// end mouseExited()
		}); // end mMouse listener
	}// end machineMenuItem()
	
	// Welcome menu label
	private void welcomeMenuItem() {
		welcome.addMouseListener(new MouseAdapter() {
			
			public void mouseClicked(MouseEvent event) {
				if(!wselected) {
					
					// set clicked for welcome
					wselected=true;
					mselected=false;
					pselected=false;
					
					welcome.setBackground(new Color(81,92,104));
					welcome.setForeground(new Color(204,255,241));
					welcome.setBorder(BorderFactory.createCompoundBorder (lBorder, eBorder));
					machine.setForeground(Color.white);
					machine.setBackground(new Color(97,110,125));
					machine.setBorder(BorderFactory.createCompoundBorder (luBorder, euBorder));
					patient.setForeground(Color.white);
					patient.setBackground(new Color(97,110,125));
					patient.setBorder(BorderFactory.createCompoundBorder (luBorder, euBorder));
					
					patientPagePanel.setVisible(false);
					machinePagePanel.setVisible(false);
					welcomePagePanel.setVisible(true);
					
				}// end if()
				
			}// end mouseClicked()
			public void mousePressed(MouseEvent event) {
				
			}// end mousePressed()
			public void mouseReleased(MouseEvent event) {
				
			}// end mouseReleased()
			public void mouseEntered(MouseEvent event) {
				if(!wselected) {
					welcome.setForeground(new Color(81,92,104));
					welcome.setBackground(new Color(177,189,201));
			        welcome.setBorder (BorderFactory.createCompoundBorder (lhBorder, ehBorder));
				}// end if()
			}// end mouseEntered()
			public void mouseExited(MouseEvent event) {
				if(!wselected) {
					welcome.setForeground(Color.WHITE);
					welcome.setBackground(new Color(97,110,125));
			        welcome.setBorder (BorderFactory.createCompoundBorder (luBorder, euBorder));
				}// end if()
			}// end mouseExited()
		}); // end wMouse listener
	}// end welcomeMenuItem()

	// Method to setup Banner and logo
	private void createBannerLogo() {
		logo = new JLabel();
		logo.setForeground(Color.white);
		logo.setFont(new Font("Open Sans", Font.BOLD,36));
		logo.setVisible(true);
		logo.setIconTextGap(0);
		logo.setHorizontalAlignment(SwingConstants.CENTER);
		logo.setIcon(new ImageIcon(Gui.class.getResource("/imgs/logo.png")));
		
		trademark = new JLabel("COVID-19");
		trademark.setForeground(Color.white);
		trademark.setVerticalTextPosition(SwingConstants.BOTTOM);
		trademark.setFont(new Font("Segoe UI", Font.BOLD,36));
		
		description = new JLabel("Machine Learning Probability Diagnoses");
		description.setForeground(new Color(204,255,241));
		description.setVerticalTextPosition(SwingConstants.TOP);
		description.setFont(new Font("Open Sans", Font.PLAIN,13));
		
		dashboard = new JLabel("DASHBOARD");
		dashboard.setForeground(Color.white);
		dashboard.setVerticalTextPosition(SwingConstants.CENTER);
		dashboard.setFont(new Font("Open Sans", Font.BOLD,17));
		dashboard.setIcon(new ImageIcon(Gui.class.getResource("/imgs/DashBoardIcon.png")));
		dashboard.setBorder(BorderFactory.createEmptyBorder(35, 0, 30, 35)); // top, left,bottom, right
	} // end createBannerLogo()
	
	// Test Patient menu label settings
	private void patientMenuLabel() {
		patient = new JLabel("Test Patient");
		patient.setForeground(Color.white);
		patient.setBackground(new Color(97,110,125));
		menuLabel(patient);
		patient.setBorder(BorderFactory.createCompoundBorder(luBorder, euBorder));
	}// end patientMenuLabel()
	
	// Train Machine menu label settings
	private void machineMenuLabel() {
		machine = new JLabel("Train Machine");
		machine.setForeground(Color.white);
		machine.setBackground(new Color(97,110,125));
		menuLabel(machine);
		machine.setBorder(BorderFactory.createCompoundBorder(luBorder, euBorder));
	}// End machineMenuLabel()
	
	// Welcome menu Label settings
	private void welcomeMenuLabel() {
		welcome = new JLabel("Welcome");
		welcome.setForeground(new Color(204,255,241));
		welcome.setBackground(new Color(81,92,104));
		menuLabel(welcome);
	    welcome.setBorder (BorderFactory.createCompoundBorder(lBorder, eBorder));
	}// end welcomeMenuLabel()
	
	// Menu Label settings
	public void menuLabel(JLabel menuLabel) {
		menuLabel.setOpaque(true);
		menuLabel.setMinimumSize(new Dimension(229, 50));
		menuLabel.setPreferredSize(new Dimension(229, 50));
		menuLabel.setMaximumSize(new Dimension(229, 50));
		menuLabel.setVerticalTextPosition(SwingConstants.CENTER);
		menuLabel.setFont(menu);
	}// end menuLabel()
	
	// Patient page combo box settings
	public void inputBoxSize(JComboBox<String> comboBox) {
		comboBox.setSelectedIndex(1);
		comboBox.setMinimumSize(new Dimension(180, 25));
		comboBox.setPreferredSize(new Dimension(180, 25));
		comboBox.setMaximumSize(new Dimension(180, 25));
	}// end inputBoxSize() 
	
	// method to check if machine has been trained and display appropriate panel on the train machine page
	private void trainedCheck() {
		if(trained) {
			trainMachinePanel.setVisible(false);
			trainedPanel.setVisible(true);
		}else {
			trainedPanel.setVisible(false);
			trainMachinePanel.setVisible(true);
		}// end if else()
	}// end trainedCheck()
	
	// Reset GridBag Constraints
	private void resetGridBagConstraints() {
		gc.gridx = 0; // x position of every component
		gc.gridy = 0; // y position of every component
		gc.gridwidth = 1; // how many columns a component takes up
		gc.gridheight = 1; // how many rows a component will take up
		gc.weightx = 1.0; // x axis ratio
		gc.weighty = 1.0; // y axis ratio
		gc.insets = new Insets(0,0,0,0); //top left bottom right
		gc.anchor = GridBagConstraints.NORTHWEST;
		gc.fill = GridBagConstraints.NONE;
	}// end resetGridBagConstraints()
	
	// return yes/no diagnoses
	private String diagnoses() {
		if(result>50) {
			return "yes";
		}else {
			return "no";
		}
	}
}// end class
