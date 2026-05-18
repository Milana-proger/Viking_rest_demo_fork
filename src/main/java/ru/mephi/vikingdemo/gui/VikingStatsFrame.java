package ru.mephi.vikingdemo.gui;

import ru.mephi.vikingdemo.controller.VikingListener;
import ru.mephi.vikingdemo.model.Viking;
import ru.mephi.vikingdemo.service.VikingStatisticsService;

import javax.swing.*;
import java.awt.*;



public class VikingStatsFrame extends JFrame {

    private final VikingStatisticsService statsService;
    private final VikingListener listener;

    public VikingStatsFrame(VikingStatisticsService statsService, VikingListener listener) {
        this.statsService = statsService;
        this.listener = listener;

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

        JButton randomTallButton = new JButton("Случайный викинг ростом выше 180");
        randomTallButton.addActionListener(e -> listener.requestRandomTallViking());
        add(randomTallButton);

        JButton legendaryButton = new JButton("Викинги с легендарным снаряжением");
        legendaryButton.addActionListener(e -> listener.requestLegendaryVikings());
        add(legendaryButton);

        JButton redBeardedButton = new JButton("Рыжебородые (сортировка по возрасту)");
        redBeardedButton.addActionListener(e -> listener.requestRedBeardedSorted());
        add(redBeardedButton);
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

    // методы для 2го пункта задания (второе окно gui)
    private void showRandomTallViking() {
        Viking v = statsService.getRandomVikingAbove180();
        if (v == null) {
            JOptionPane.showMessageDialog(this, "Нет викингов ростом выше 180 см!");
        } else {
            JOptionPane.showMessageDialog(this,
                    "Случайный викинг выше 180 см:\n" +
                            "Имя: " + v.name() + "\n" +
                            "Возраст: " + v.age() + "\n" +
                            "Рост: " + v.heightCm() + " см");
        }
    }

    private void showLegendaryVikings() {
        var vikings = statsService.getVikingsWithLegendaryGear();
        if (vikings.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Нет викингов с легендарным снаряжением!");
        } else {
            StringBuilder sb = new StringBuilder("Викинги с легендарным снаряжением:\n\n");
            for (Viking v : vikings) {
                sb.append(v.name()).append(" (возраст ").append(v.age()).append(")\n");
            }
            JOptionPane.showMessageDialog(this, sb.toString());
        }
    }

    private void showRedBeardedSorted() {
        var vikings = statsService.getRedBeardedVikingsSortedByAge();
        if (vikings.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Нет рыжебородых викингов!");
        } else {
            StringBuilder sb = new StringBuilder("Рыжебородые викинги (по возрасту):\n\n");
            for (Viking v : vikings) {
                sb.append(v.name()).append(" - ").append(v.age()).append(" лет\n");
            }
            JOptionPane.showMessageDialog(this, sb.toString());
        }
    }

}