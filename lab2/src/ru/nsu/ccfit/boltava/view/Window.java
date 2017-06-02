package ru.nsu.ccfit.boltava.view;

import javax.swing.*;

public class Window extends JFrame {

    private JPanel mRootPanel;
    private ControlPanel mControlPanel;
    private JLabel mControlPanelTitle;
    private WorkflowStatsPanel mWorkflowStats;


    public Window() {
        super("Factory");
        this.$$$setupUI$$$();
        this.setContentPane(mRootPanel);
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.pack();

        mControlPanelTitle.setText("Timeout Control Panel");

        this.setVisible(true);
    }

    private void createUIComponents() {
        String[] columns = new String[] { "Car", "Engine", "Body", "Accessory", "Sales" };
        String[] rows = new String[] { "Fiat", "Audi", "Ferrari", "Porsche", "BMW" };

        mWorkflowStats = new WorkflowStatsPanel(rows, columns);
    }

    private void $$$setupUI$$$() {
        createUIComponents();

        mRootPanel = new JPanel();

    }

}
