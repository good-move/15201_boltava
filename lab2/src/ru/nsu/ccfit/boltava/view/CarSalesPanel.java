package ru.nsu.ccfit.boltava.view;

import ru.nsu.ccfit.boltava.model.car.Car;
import ru.nsu.ccfit.boltava.model.factory.ICarPurchasedListener;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.HashMap;

public class CarSalesPanel extends JComponent implements ICarPurchasedListener {

    private JTable mTable;
    private JScrollPane mScrollPane;
    private JPanel mPanel;

    private CarSalesTableModel mTableModel;

    public CarSalesPanel(String[] carNames) {
        mTableModel = new CarSalesTableModel(carNames);
        mTable.setModel(mTableModel);
    }

    @Override
    public void onCarPurchased(Car car) {}

    @Override
    public void onCarPurchased(Car car, Integer salesCount) {
        mTableModel.setValueForCar(car.getSerial(), salesCount);
    }

    private class CarSalesTableModel extends AbstractTableModel {

        private ArrayList<Row> mTableData = new ArrayList<>();
        private HashMap<String, Row> mDataMap = new HashMap<>();
        private final String[] mColumnNames = { "Car", "Items Sold" };
        private int COLUMN_COUNT  = 2;

        CarSalesTableModel(String[] carNames) {
            for (String name : carNames) {
                Row row = new Row(name, 0);
                mTableData.add(row);
                mDataMap.put(name, row);
            }
        }

        @Override
        public int getRowCount() {
            return mTableData.size();
        }

        @Override
        public int getColumnCount() {
            return COLUMN_COUNT;
        }

        @Override
        public Object getValueAt(int row, int col) {
            if (row > mTableData.size() || col > COLUMN_COUNT) {
                throw new IllegalArgumentException("Table index is out of bounds");
            }

            if (col == 0) {
                return mTableData.get(row).getTitle();
            } else {
                return mTableData.get(row).getValue();
            }

        }

        @Override
        public String getColumnName(int index) {
            return mColumnNames[index];
        }

        @Override
        public void setValueAt(Object obj, int row, int col) {
            if (col > COLUMN_COUNT) {
                throw new IllegalArgumentException("Table index is out of bounds");
            }

            Row tableRow = mTableData.get(row);
            if (col == 1) {
                tableRow.setTitle((String)obj);
            } else {
                tableRow.setValue((Integer)obj);
            }

            fireTableCellUpdated(row, col);
        }

        public void setValueForCar(String carSerial, Integer sales) {
            Row row = mDataMap.get(carSerial);
            row.setValue(sales);
            fireTableDataChanged();
        }


        private class Row {

            private String mTitle = "";
            private Integer mValue = 0;

            Row(String title, Integer value) {
                mTitle = title;
                mValue = value;
            }

            void setTitle(String title) {
                mTitle = title;
            }

            void setValue(Integer value) {
                mValue = value;
            }

            String getTitle() {
                return mTitle;
            }

            Integer getValue() {
                return mValue;
            }

        }
    }

}
