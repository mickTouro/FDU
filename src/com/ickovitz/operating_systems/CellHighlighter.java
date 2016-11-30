package com.ickovitz.operating_systems;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class CellHighlighter extends DefaultTableCellRenderer {
	private int numProcesses;

	public CellHighlighter(int numProcesses) {
		this.numProcesses = numProcesses;
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int col) {

		// Cells are by default rendered as a JLabel.
		JLabel l = (JLabel) super.getTableCellRendererComponent(table, value,
				isSelected, hasFocus, row, col);

		if (l.getText().equals(".")) {
			switch (row % (numProcesses + 1)) {
			case 0:
				l.setBackground(new Color(163, 73, 164));
				l.setForeground(new Color(163, 73, 164));
				break;
			case 1:
				l.setBackground(new Color(63, 72, 204));
				l.setForeground(new Color(63, 72, 204));
				break;
			case 2:
				l.setBackground(new Color(0, 162, 232));
				l.setForeground(new Color(0, 162, 232));
				break;
			case 3:
				l.setBackground(new Color(34, 177, 76));
				l.setForeground(new Color(34, 177, 76));
				break;
			case 4:
				l.setBackground(new Color(255, 242, 0));
				l.setForeground(new Color(255, 242, 0));
				break;
			case 5:
				l.setBackground(new Color(255, 127, 39));
				l.setForeground(new Color(255, 127, 39));
				break;
			case 6:
				l.setBackground(new Color(237, 28, 36));
				l.setForeground(new Color(237, 28, 36));
				break;
			case 7:
				l.setBackground(new Color(136, 0, 21));
				l.setForeground(new Color(136, 0, 21));
				break;
			default:
				l.setBackground(Color.BLACK);
				break;
			}
		} else if (l.getText().equals("*")) {
			l.setBackground(new Color(200, 200, 200));
			l.setForeground(new Color(200, 200, 200));
		} else {
			l.setBackground(Color.WHITE);
		}

		// Return the JLabel which renders the cell.
		return l;

	}
}