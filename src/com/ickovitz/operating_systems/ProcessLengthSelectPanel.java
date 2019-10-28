package com.ickovitz.operating_systems;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

public class ProcessLengthSelectPanel extends JPanel implements ActionListener {

	private JButton longProcess;
	private JButton shortProcess;
	private JButton mediumProcess;
	private JButton mixedProcess;
	private ProcessInformationPanel processPanel;

	public ProcessLengthSelectPanel(ProcessInformationPanel p) {
		this.processPanel = p;

		shortProcess = new JButton("Short Processes (1-10)");
		longProcess = new JButton("Long Processes (101-110)");
		mediumProcess = new JButton("Medium Processes (51-60)");
		mixedProcess = new JButton("Mixed Processes (1-100)");

		longProcess.addActionListener(this);
		shortProcess.addActionListener(this);
		mediumProcess.addActionListener(this);
		mixedProcess.addActionListener(this);

		add(shortProcess);
		add(mediumProcess);
		add(longProcess);
		add(mixedProcess);
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource().equals(shortProcess)) {
			processPanel.fillWithRandom(0);
		} else if (arg0.getSource().equals(mediumProcess)) {
			processPanel.fillWithRandom(50);
		} else if (arg0.getSource().equals(longProcess)) {
			processPanel.fillWithRandom(100);
		} else if (arg0.getSource().equals(mixedProcess)) {
			processPanel.fillWithMixedProcesses();
		}
	}
}