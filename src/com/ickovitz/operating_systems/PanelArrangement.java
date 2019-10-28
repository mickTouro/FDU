package com.ickovitz.operating_systems;

import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class PanelArrangement extends JPanel implements ActionListener {
	private ProcessInformationPanel processPanel;
	private ResultsPanel resultsPanel;
	private MetricsPanel metricsPanel;
	private ProcessLengthSelectPanel processLengthSelectPanel;
	private JScrollPane scrollPane;
	private JButton calculate;
	private JButton reset;

	public PanelArrangement() {
		setLayout(new BorderLayout());

		Dimension toolkit = Toolkit.getDefaultToolkit().getScreenSize();

		calculate = new JButton("Calculate");
		calculate.addActionListener(this);

		reset = new JButton("Reset");
		reset.addActionListener(this);

		JPanel bottomPanel = new JPanel();
		bottomPanel.add(calculate);
		bottomPanel.add(reset);

		add(bottomPanel, BorderLayout.SOUTH);

		this.processPanel = new ProcessInformationPanel();
		scrollPane = new JScrollPane(processPanel);
		scrollPane
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setPreferredSize(new Dimension(200, (int) toolkit
				.getHeight() - 100));

		add(scrollPane, BorderLayout.WEST);

		this.processLengthSelectPanel = new ProcessLengthSelectPanel(processPanel);
		add(processLengthSelectPanel, BorderLayout.NORTH);

		JPanel center = new JPanel();
		this.resultsPanel = new ResultsPanel();

		JScrollPane scrollPane2 = new JScrollPane(resultsPanel);
		scrollPane2
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane2
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane2.setPreferredSize(new Dimension((int) (toolkit.getWidth() - 300), (int) toolkit
				.getHeight() - 350));
		center.add(scrollPane2);

		this.metricsPanel = new MetricsPanel();
		JScrollPane scrollPane3 = new JScrollPane(metricsPanel);
		scrollPane3
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane3
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane3.setPreferredSize(new Dimension((int) (toolkit.getWidth() - 300), 220));
		center.add(scrollPane3);
		add(center, BorderLayout.CENTER);
	}



	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource().equals(calculate)) {
			this.resultsPanel.resetTable();
			this.metricsPanel.resetTable();

			CalculateInformation calcInfo = new CalculateInformation(processPanel.getProcessData());

			for(SchedulingProcessor sp : calcInfo.getSchedulingAlgorithms()){
				resultsPanel.displaySchedule(sp.getName(), sp.getTimingOrder());
				metricsPanel.displayMetrics(sp.getName(), sp.calculateMetrics());
			}
		} else if(arg0.getSource().equals(reset)){
			resultsPanel.reset();
			metricsPanel.reset();
		}
	}
}