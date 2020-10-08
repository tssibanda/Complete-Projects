/*******************************************************************
Program Description: Program to demonstrate GUI

JFrame is a screen
Panel is a section of screen
3 Steps => event programming
step 1 = put in the implments listener there are different listeners example, action listener
step 2 = add/ attach the listener to the element
Step 3 = put in the asscociated method - event handler

you can have more than 1 event handler

public class className extends otherClass implements listener1, listener2, listener3{

Date: 20 February 2020
Author: Thamsanqa Sibanda
Language & Compiler: Java Programming Language, Eclipse compiler
********************************************************************/

package com.gui.tut;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

											// Step 1
public class MyFirstScreen extends JFrame implements ActionListener {
	// attributes = usually the components that you'll have on the screen
	
	
	JLabel label1, topLabel;// to declare this variable you must import the jLabel class
	JButton button1, button2;
	JPanel centrePanel, southPanel, northPanel;
	
	
	// Constructor
	public MyFirstScreen(String myTitle) {
		super(myTitle);
		setSize(300,300);
		
		BorderLayout bl1 = new BorderLayout();
		setLayout(bl1);
		
		label1 = new JLabel("OO Programming Class");
		topLabel = new JLabel("Top north of the screen");
		button1 = new JButton("Click me");
		button2 = new JButton("Then me");
		
		
		// step 2
		// attach the listener to the button
		button1.addActionListener(this);
		button2.addActionListener(this);
		
		
		//set up panel
		centrePanel = new JPanel();
		southPanel = new JPanel();
		northPanel = new JPanel();
		
		
		northPanel.add(topLabel);
		centrePanel.add(label1);
		southPanel.add(button1);
		southPanel.add(button2);
		
		
		// order of elements matters.
		add(northPanel, BorderLayout.NORTH);
		add(centrePanel, BorderLayout.CENTER);
		add(southPanel, BorderLayout.SOUTH);
		//add(southPanel, BorderLayout.SOUTH);
		
		
		// set visible must be the last line of the constructor
		setVisible(true);
		
		
	}// end constructor
	
	
	
	// example of event handler
	// you don't call this directly, the system does
	
	// step 3
	@Override
	public void actionPerformed(ActionEvent myEvent) {
		
		if(myEvent.getSource()==button1) {
			// this will display a pop up
			JOptionPane.showMessageDialog(this, "Button 1 was clicked");
		}else {
			JOptionPane.showMessageDialog(this, "Second button clicked");
		}// end if
		
	}
	
}
