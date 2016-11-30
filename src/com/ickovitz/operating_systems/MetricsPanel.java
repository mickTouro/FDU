package com.ickovitz.operating_systems;

import java.util.Map;
import java.util.Map.Entry;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class MetricsPanel extends JPanel {
	private JTable table;
	private DefaultTableModel dtm;

	public MetricsPanel() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		resetTable();
	}

	public void resetTable() {
		this.table = new JTable();
		this.dtm = new DefaultTableModel();
		table.setVisible(true);
		table.setModel(dtm);
		add(table.getTableHeader());
		add(table);
	}

	public void displayMetrics(String algorithm, Map<String, Double> map) {
		if (table.getColumnCount() == 0) {
			dtm.addColumn("");

		}

		// add metric name to column headers
		if (table.getColumnCount() == 1) {
			for (String metricName : map.keySet()) {
				dtm.addColumn(metricName);
			}
		}

		Object o[] = new Object[map.size() + 1];
		o[0] = algorithm;
		int col = 1;
		for (String metricName : map.keySet()) {
			o[col] = map.get(metricName);
			col++;
		}
		dtm.addRow(o);
	}

	// removes all metrics
	public void reset() {
		this.removeAll();
		this.repaint();
	}
}
