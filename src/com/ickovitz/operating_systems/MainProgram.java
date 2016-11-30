package com.ickovitz.operating_systems;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainProgram extends JFrame{
	public MainProgram(){
		setTitle("Scheduling Algorithms Comparison");
		getContentPane().add(new PanelArrangement());
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		setSize((int)d.getWidth(), (int)d.getHeight());
		setLocation(0,0);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	public static void main(String args[]){
		MainProgram application = new MainProgram();
		application.show();
	}
}