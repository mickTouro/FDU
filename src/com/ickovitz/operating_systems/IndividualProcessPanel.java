package com.ickovitz.operating_systems;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class IndividualProcessPanel extends JPanel {
	private JLabel processName;
	private JLabel serviceTime;
	private JTextField serviceTimeInput;
	private JLabel arrivalTIme;
	private JTextField arrivalTimeInput;
	public IndividualProcessPanel(int name){
	
		this.processName = new JLabel("Process "+ name);
		this.serviceTime = new JLabel("Service Time:");
		this.serviceTimeInput = new JTextField(3);
		this.serviceTimeInput.setText("0");
		this.arrivalTIme = new JLabel("Arrival Time:");
		this.arrivalTimeInput = new JTextField(3);
		this.arrivalTimeInput.setText("0");
		
		
		GridLayout grid = new GridLayout(3, 2);
		setLayout(grid);
		add(processName);
		add(new JLabel());
		add(serviceTime);
		add(serviceTimeInput);
		add(arrivalTIme);
		add(arrivalTimeInput);
		setBorder(BorderFactory.createLineBorder(Color.black));
	}
	public JLabel getProcessName() {
		return processName;
	}
	public JTextField getServiceTimeInput() {
		return serviceTimeInput;
	}
	public JTextField getArrivalTimeInput() {
		return arrivalTimeInput;
	}
	
}
