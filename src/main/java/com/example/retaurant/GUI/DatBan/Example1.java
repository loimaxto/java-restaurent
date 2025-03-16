/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.retaurant.GUI.DatBan;

/**
 *
 * @author light
 */
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Example1 extends JFrame {

    private JTable table;
    private MyTableModel tableModel;

    public Example1() {
        setTitle("Button Cell Example");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        tableModel = new MyTableModel();
        table = new JTable(tableModel);

        ButtonCellRenderer renderer = new ButtonCellRenderer();
        table.getColumnModel().getColumn(3).setCellRenderer(renderer);
        table.getColumnModel().getColumn(3).setCellEditor(new ButtonCellEditor(renderer));

        JScrollPane scrollPane = new JScrollPane(table);
        getContentPane().add(scrollPane);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    class MyTableModel extends AbstractTableModel {
        private String[] columnNames = {"Name", "Age", "City", "Actions"};
        private List<Object[]> data = new ArrayList<>();

        public MyTableModel() {
            data.add(new Object[]{"John Doe", 30, "New York", null});
            data.add(new Object[]{"Jane Smith", 25, "London", null});
            data.add(new Object[]{"Alice Johnson", 35, "Tokyo", null});
        }

        @Override
        public int getRowCount() {
            return data.size();
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            return data.get(rowIndex)[columnIndex];
        }

        @Override
        public String getColumnName(int column) {
            return columnNames[column];
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            if (columnIndex == 3) {
                return ButtonCellRenderer.class; // Return class of the renderer.
            }
            return super.getColumnClass(columnIndex);
        }

        @Override
        public boolean isCellEditable(int row, int col) {
            return col == 3; // Make only the button column editable
        }
    }

    class ButtonCellRenderer extends JPanel implements TableCellRenderer {

        private JButton detailButton;
        private JButton orderButton;
        private JButton cancelButton;

        public ButtonCellRenderer() {
            setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

            detailButton = new JButton("Detail");
            orderButton = new JButton("Order");
            cancelButton = new JButton("Cancel");

            add(detailButton);
            add(orderButton);
            add(cancelButton);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (isSelected) {
                setBackground(table.getSelectionBackground());
            } else {
                setBackground(table.getBackground());
            }
            return this;
        }

        public JButton getDetailButton() {
            return detailButton;
        }

        public JButton getOrderButton() {
            return orderButton;
        }

        public JButton getCancelButton() {
            return cancelButton;
        }
    }

    class ButtonCellEditor extends AbstractCellEditor implements TableCellEditor {

        private ButtonCellRenderer renderer;

        public ButtonCellEditor(ButtonCellRenderer renderer) {
            this.renderer = renderer;

            renderer.getDetailButton().addActionListener(e -> {
                int row = table.getSelectedRow();
                Object[] rowData = tableModel.data.get(row);
                System.out.println("Detail clicked for row: " + rowData[0]);
                // Access all data in rowData array.
                stopCellEditing();
            });

            renderer.getOrderButton().addActionListener(e -> {
                int row = table.getSelectedRow();
                Object[] rowData = tableModel.data.get(row);
                System.out.println("Order clicked for row: " + rowData[0]);
                stopCellEditing();
            });

            renderer.getCancelButton().addActionListener(e -> {
                int row = table.getSelectedRow();
                Object[] rowData = tableModel.data.get(row);
                System.out.println("Cancel clicked for row: " + rowData[0]);
                stopCellEditing();
            });

        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            return renderer;
        }

        @Override
        public Object getCellEditorValue() {
            return null;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Example1::new);
    }
}
