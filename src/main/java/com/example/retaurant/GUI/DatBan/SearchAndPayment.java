/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.retaurant.GUI.DatBan;

/**
 *
 * @author light
 */import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class SearchAndPayment {

    private JFrame frame;
    private JTextField searchBar;
    private List<String> searchResults;
    private List<String> selectedItems;

    public SearchAndPayment() {
        frame = new JFrame("Search and Payment");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setLayout(new FlowLayout());

        searchBar = new JTextField(20);
        searchBar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performSearch(searchBar.getText());
            }
        });

        frame.add(searchBar);
        selectedItems = new ArrayList<>();

        frame.setVisible(true);
    }

    private void performSearch(String query) {
        searchResults = simulateSearchResults(query);

        if (searchResults.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "No results found.");
            return;
        }

        JPopupMenu popupMenu = new JPopupMenu();

        for (String result : searchResults) {
            JMenuItem menuItem = new JMenuItem(result);
            menuItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    selectedItems.add(result);
                    JOptionPane.showMessageDialog(frame, "Added: " + result);
                    popupMenu.setVisible(false);
                }
            });
            popupMenu.add(menuItem);
        }

        // Show the popup menu below the search bar
        Point location = searchBar.getLocationOnScreen();
        location.y += searchBar.getHeight();
        popupMenu.show(searchBar, 0, searchBar.getHeight());

    }

    private List<String> simulateSearchResults(String query) {
        List<String> results = new ArrayList<>();
        if (query.isEmpty()) {
            return results;
        }

        String[] sampleData = {"Apple", "Banana", "Cherry", "Date", "Elderberry", "Fig", "Grape", "Honeydew"};
        for (String item : sampleData) {
            if (item.toLowerCase().contains(query.toLowerCase())) {
                results.add(item);
            }
        }
        return results;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SearchAndPayment::new);
    }
}
