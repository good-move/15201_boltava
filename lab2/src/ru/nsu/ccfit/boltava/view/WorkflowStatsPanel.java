package ru.nsu.ccfit.boltava.view;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.Arrays;
import java.util.HashMap;

public class WorkflowStatsPanel extends JComponent {

    private JTable mTable;
    private JScrollPane mScrollPane;
    private JPanel mPanel;

    public WorkflowStatsPanel(String[] rowNames, String[] columnNames) {
        mTable.setModel(new WorkflowStatsTableModel(rowNames, columnNames));
    }

    private void createUIComponents() {
    }

    private class WorkflowStatsTableModel extends AbstractTableModel {

        private HashMap<String, Integer[]> mTableData = new HashMap<>();
        private String[] mSortedRowNames;
        private String[] mColumnNames;

        public WorkflowStatsTableModel(String[] rowNames, String[] columnNames) {

            mColumnNames = columnNames;

            for (String name : rowNames) {
                Integer[] values = new Integer[columnNames.length];
                for (int i = 0; i < values.length; ++i) {
                    values[i] = 0;
                }
                mTableData.put(name, values);
            }

            Arrays.sort(rowNames);
            mSortedRowNames = rowNames;
        }

        @Override
        public int getRowCount() {
            return mTableData.size();
        }

        @Override
        public int getColumnCount() {
            return mColumnNames.length;
        }

        @Override
        public Object getValueAt(int row, int col) {
            if (row > mTableData.size() || col > mColumnNames.length) {
                throw new IllegalArgumentException("Table index is out of bounds");
            }

            if (col == 0) {
                return mSortedRowNames[row];
            }

            return mTableData.get(mSortedRowNames[row])[col];
        }
        @Override
        public String getColumnName(int index) {
            return mColumnNames[index];
        }

    }

}
