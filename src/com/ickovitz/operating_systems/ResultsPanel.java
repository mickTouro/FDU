package com.ickovitz.operating_systems;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class ResultsPanel extends JPanel {
	private JTable table;

	DefaultTableModel dtm;

	public ResultsPanel() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		resetTable();
	}

	public void displaySchedule(String scheduleName, List<String> schedule) {
		if (table.getColumnCount() == 0) {
			dtm.addColumn("");

		}

		// add times column headers
		if (table.getColumnCount() + 1 < schedule.size()) {

			int numColsToAdd = schedule.size() - table.getColumnCount() + 1;
			for (int i = 0; i < numColsToAdd; i++) {
				int newCol = table.getColumnCount() - 1;
				dtm.addColumn(newCol);
			}
		}

		// set the sizes of the cells
		table.getColumnModel().getColumn(0).setMinWidth(200);
		table.getColumnModel().getColumn(0).setMaxWidth(200);

		for (int i = 1; i < table.getColumnCount(); i++) {
			table.getColumnModel().getColumn(i).setMinWidth(20);
			table.getColumnModel().getColumn(i).setMaxWidth(20);
			table.getColumnModel()
					.getColumn(i)
					.setCellRenderer(
							new CellHighlighter(new HashSet<String>(schedule)
									.size()));
		}

		Object[] divider = new Object[schedule.size() + 1];
		divider[0] = scheduleName;
		for (int col = 1; col < schedule.size() + 1; col++) {
			divider[col] = "*";
		}
		dtm.addRow(divider);

		// no duplicates, sorted by run fist
		List<String> sortedByRunTime = createSortedByRunTime(schedule);
		// add rows with data
		for (int i = 0; i < sortedByRunTime.size(); i++) {
			Object[] o = new Object[schedule.size() + 1];
			o[0] = sortedByRunTime.get(i);

			for (int col = 1; col < schedule.size() + 1; col++) {
				o[col] = schedule.get(col - 1).equals(sortedByRunTime.get(i)) ? "."
						: null;
			}
			dtm.addRow(o);
		}
	}

	public List<String> createSortedByRunTime(List<String> schedule) {
		List<String> sortedByRunTime = new ArrayList<String>();
		for (String p : schedule) {
			if (!sortedByRunTime.contains(p)) {
				sortedByRunTime.add(p);
			}
		}
		return sortedByRunTime;
	}

	public void resetTable() {
		this.table = new JTable();
		this.dtm = new DefaultTableModel();
		table.setVisible(true);
		table.setModel(dtm);
		add(table.getTableHeader());
		add(table);
	}

	public void reset() {
		this.removeAll();
		this.repaint();
	}
}
