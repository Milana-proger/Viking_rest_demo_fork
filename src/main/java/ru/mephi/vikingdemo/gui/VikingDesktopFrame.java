package ru.mephi.vikingdemo.gui;

import ru.mephi.vikingdemo.model.Viking;
import ru.mephi.vikingdemo.service.VikingService;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import ru.mephi.vikingdemo.model.HairColor;
import ru.mephi.vikingdemo.model.BeardStyle;
import ru.mephi.vikingdemo.service.EquipmentFactory;
import java.util.List;


public class VikingDesktopFrame extends JFrame {

    private final VikingService vikingService;
    private final VikingTableModel tableModel = new VikingTableModel();
    private JTable vikingTable;

    public VikingDesktopFrame(VikingService vikingService) {
        this.vikingService = vikingService;

        setTitle("Viking Demo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(1000, 420));
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JLabel header = new JLabel("Viking Demo", SwingConstants.CENTER);
        header.setFont(header.getFont().deriveFont(Font.BOLD, 18f));
        add(header, BorderLayout.NORTH);

        vikingTable = new JTable(tableModel);
        vikingTable.setRowHeight(28);
        add(new JScrollPane(vikingTable), BorderLayout.CENTER);

        JButton createButton = new JButton("Create random viking");
        createButton.addActionListener(event -> onCreateViking());

        JButton addButton = new JButton("Add custom viking");
        addButton.addActionListener(event -> onAddCustomViking());

        JButton deleteButton = new JButton("Delete selected viking");
        deleteButton.addActionListener(event -> onDeleteSelectedViking());

        JButton updateButton = new JButton("Update selected viking");
        updateButton.addActionListener(event -> onUpdateSelectedViking());

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(createButton);
        bottomPanel.add(addButton);
        bottomPanel.add(deleteButton);
        bottomPanel.add(updateButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void onCreateViking() {
        Viking viking = vikingService.createRandomViking();
        tableModel.addViking(viking);
    }
    
    public void addNewViking(Viking viking){
        tableModel.addViking(viking);
    }

    private void onAddCustomViking() {
        String name = JOptionPane.showInputDialog(this, "Name:");
        if (name == null || name.isBlank()) return;

        try {
            int age = Integer.parseInt(JOptionPane.showInputDialog(this, "Age:"));
            int height = Integer.parseInt(JOptionPane.showInputDialog(this, "Height (cm):"));

            HairColor hair = (HairColor) JOptionPane.showInputDialog(this, "Hair color:", "Select",
                    JOptionPane.QUESTION_MESSAGE, null, HairColor.values(), HairColor.Blond);

            BeardStyle beard = (BeardStyle) JOptionPane.showInputDialog(this, "Beard style:", "Select",
                    JOptionPane.QUESTION_MESSAGE, null, BeardStyle.values(), BeardStyle.SHORT);

            Viking newViking = vikingService.addViking(new Viking(name, age, height, hair, beard,
                    List.of(EquipmentFactory.createItem(), EquipmentFactory.createItem())));
            tableModel.addViking(newViking);
            JOptionPane.showMessageDialog(this, "Added!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void onDeleteSelectedViking() {
        int row = vikingTable.getSelectedRow();
        if (row == -1) return;

        Viking v = tableModel.getVikingAt(row);
        if (JOptionPane.showConfirmDialog(this, "Delete " + v.name() + "?", "", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            vikingService.deleteViking(v.name());
            tableModel.removeViking(row);
        }
    }

    private void onUpdateSelectedViking() {
        int row = vikingTable.getSelectedRow();
        if (row == -1) return;

        Viking old = tableModel.getVikingAt(row);

        try {
            String ageStr = JOptionPane.showInputDialog(this, "New age:", old.age());
            if (ageStr == null) return;
            int newAge = Integer.parseInt(ageStr);

            String heightStr = JOptionPane.showInputDialog(this, "New height:", old.heightCm());
            if (heightStr == null) return;
            int newHeight = Integer.parseInt(heightStr);

            HairColor newHair = (HairColor) JOptionPane.showInputDialog(this, "New hair color:", "Select",
                    JOptionPane.QUESTION_MESSAGE, null, HairColor.values(), old.hairColor());
            if (newHair == null) return;

            BeardStyle newBeard = (BeardStyle) JOptionPane.showInputDialog(this, "New beard style:", "Select",
                    JOptionPane.QUESTION_MESSAGE, null, BeardStyle.values(), old.beardStyle());
            if (newBeard == null) return;

            Viking updated = vikingService.updateViking(old.name(), new Viking(
                    old.name(), newAge, newHeight, newHair, newBeard, old.equipment()
            ));
            tableModel.updateViking(row, updated);
            JOptionPane.showMessageDialog(this, "Updated!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }
}
