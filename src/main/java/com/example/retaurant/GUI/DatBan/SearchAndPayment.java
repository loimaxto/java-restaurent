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
import javax.swing.Timer;

public class SearchAndPayment {

    private JFrame frame;
    private JTextField searchBar;
    private List<String> searchResults;
    private List<String> selectedItems;
    private Timer searchTimer;

    public SearchAndPayment() {
        frame = new JFrame("Search and Payment");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setLayout(new FlowLayout());

        searchBar = new JTextField(20);
        searchBar.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (searchTimer != null) {
                    searchTimer.stop();
                }
                searchTimer = new Timer(1000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evt) {
                        performSearch(searchBar.getText());
                        searchTimer.stop();
                        searchTimer = null;
                    }
                });
                searchTimer.setRepeats(false);
                searchTimer.start();
            }
            
        });

        frame.add(searchBar);
        selectedItems = new ArrayList<>();

        frame.setVisible(true);
    }

    private void performSearch(String query) {
        searchResults = simulateSearchResults(query);

        JPopupMenu popupMenu = new JPopupMenu();

        if (searchResults.isEmpty()) {
            JMenuItem noResultsItem = new JMenuItem("Không có món ");
            noResultsItem.setEnabled(false); // Disable the item so it can't be clicked
            popupMenu.add(noResultsItem);

        } else {
            for (String result : searchResults) {
                JMenuItem menuItem = new JMenuItem(result);
                menuItem.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        selectedItems.add(result);
                        JOptionPane.showMessageDialog(frame, "thêm " + result);
                        popupMenu.setVisible(false);
                    }
                });
                popupMenu.add(menuItem);
            }
        }

        // Show the popup menu below the search bar
        Point location = searchBar.getLocationOnScreen();
        location.y += searchBar.getHeight();
        popupMenu.show(searchBar, 0, searchBar.getHeight());
        popupMenu.setPreferredSize(new Dimension(searchBar.getWidth(), popupMenu.getPreferredSize().height));
        popupMenu.revalidate();

    }

    private List<String> simulateSearchResults(String query) {
        List<String> results = new ArrayList<>();
        if (query.isEmpty()) {
            return results;
        }

        String[] sampleData = {"Apple sdf ad", "Banana", "Cherry", "Date", "Elderberry", "Fig", "Grape", "Honeydew"};
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