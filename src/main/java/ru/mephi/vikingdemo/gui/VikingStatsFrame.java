package ru.mephi.vikingdemo.gui;

import ru.mephi.vikingdemo.service.VikingStatisticsService;

import javax.swing.*;
import java.awt.*;

public class VikingStatsFrame extends JFrame {

    private final VikingStatisticsService statsService;

    public VikingStatsFrame(VikingStatisticsService statsService) {
        this.statsService = statsService;

        setTitle("Viking Statistics");
        setSize(400, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(2, 1, 10, 10));

        JButton maxIdButton = new JButton("Найти последнюю запись (max ID)");
        maxIdButton.addActionListener(e -> showMaxId());
        add(maxIdButton);

        JButton evenIdsButton = new JButton("Все четные ID");
        evenIdsButton.addActionListener(e -> showEvenIds());
        add(evenIdsButton);
    }

    private void showMaxId() {
        Integer maxId = statsService.findMaxId();
        if (maxId == null) {
            JOptionPane.showMessageDialog(this, "Нет викингов!");
        } else {
            JOptionPane.showMessageDialog(this, "Max ID (последняя запись): " + maxId);
        }
    }

    private void showEvenIds() {
        Integer[] evenIds = statsService.findEvenIds();
        if (evenIds.length == 0) {
            JOptionPane.showMessageDialog(this, "Нет четных ID!");
        } else {
            JOptionPane.showMessageDialog(this, "Четные ID: " + java.util.Arrays.toString(evenIds));
        }
    }
}