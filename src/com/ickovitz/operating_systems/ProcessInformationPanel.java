package com.ickovitz.operating_systems;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class ProcessInformationPanel extends JPanel implements ActionListener {
	int nextProc;
	List<IndividualProcessPanel> processList;
	JButton newButton;

	public ProcessInformationPanel() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.processList = new ArrayList<IndividualProcessPanel>();
		for (int i = 0; i < 5; i++) {
			processList.add(new IndividualProcessPanel(i));
		}

		nextProc = 5;
		newButton = new JButton("Add New Process");
		newButton.addActionListener(this);

		for (IndividualProcessPanel p : processList) {
			add(p);
		}



		add(newButton);

		fillWithDefaults();



	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource().equals(newButton)) {
			IndividualProcessPanel newProc = new IndividualProcessPanel(
					nextProc);
			processList.add(newProc);
			add(newProc);
			remove(newButton);
			add(newButton);
			getParent().validate();
			nextProc++;
		}
	}

	public HashMap<String, ProcessInfo> getProcessData() {
		HashMap processMap = new HashMap<String, ProcessInfo>();
		for (IndividualProcessPanel p : processList) {
			List<Integer> times = new ArrayList();
			int length = (Integer.parseInt(p.getServiceTimeInput().getText()));
			int arrival = (Integer.parseInt(p.getArrivalTimeInput().getText()));
			processMap.put(p.getProcessName().getText(), new ProcessInfo(length, arrival));
		}
		return processMap;
	}



	public void fillWithDefaults() {
		int i = 0;
		for (IndividualProcessPanel p : processList) {
			if (i == 0) {
				p.getServiceTimeInput().setText("3");
				p.getArrivalTimeInput().setText("0");
			} else if (i == 1) {
				p.getServiceTimeInput().setText("6");
				p.getArrivalTimeInput().setText("2");
			} else if (i == 2) {
				p.getServiceTimeInput().setText("4");
				p.getArrivalTimeInput().setText("4");

			} else if (i == 3) {
				p.getServiceTimeInput().setText("5");
				p.getArrivalTimeInput().setText("6");

			} else if (i == 4) {
				p.getServiceTimeInput().setText("2");
				p.getArrivalTimeInput().setText("8");
			}
			i++;
		}
	}

	public void fillWithRandom(int offset) {
		Random rand = new Random();

		for (int i = 0; i < processList.size(); i++) {
			processList.get(i).getServiceTimeInput()
					.setText("" + (rand.nextInt(10) + 1 + offset));
			processList.get(i).getArrivalTimeInput()
					.setText("" + rand.nextInt(offset == 0 ? 10 : offset));
		}
	}



	public void fillWithMixedProcesses() {
		Random rand = new Random();

		for (int i = 0; i < processList.size(); i++) {
			processList.get(i).getServiceTimeInput()
					.setText("" + (rand.nextInt(100) + 1));
			processList.get(i).getArrivalTimeInput()
					.setText("" + rand.nextInt(40));
		}
	}
}