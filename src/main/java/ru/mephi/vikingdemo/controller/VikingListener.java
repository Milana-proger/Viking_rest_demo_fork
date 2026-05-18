/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ru.mephi.vikingdemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.mephi.vikingdemo.gui.VikingDesktopFrame;
import ru.mephi.vikingdemo.gui.VikingStatsFrame;
import ru.mephi.vikingdemo.model.Viking;
import ru.mephi.vikingdemo.service.VikingService;

import java.util.List;

/**
 *
 * @author test2023
 */
@Component
public class VikingListener {
    private VikingService service;
    private VikingDesktopFrame gui;
    private VikingStatsFrame statsFrame;

    @Autowired
    public VikingListener(VikingService service) {
        this.service = service;
    }
    
    public void setGui(VikingDesktopFrame gui){
        this.gui = gui;
    }

    void testAdd() {
        gui.addNewViking(service.createRandomViking());
    }

    // листенеры для методов сервиса
    public void notifyVikingAdded(Viking viking) {
        if (gui != null) {
            gui.addNewViking(viking);
        }
    }

    public void notifyVikingDeleted(String name) {
        if (gui != null) {
            gui.removeVikingByName(name);
        }
    }

    public void notifyVikingUpdated(Viking viking) {
        if (gui != null) {
            gui.updateVikingByName(viking);
        }
    }

    public void notifyVikingsGenerated(List<Viking> vikings) {
        if (gui != null) {
            for (Viking viking : vikings) {
                gui.addNewViking(viking);
            }
        }
    }

    // окно и метод обновления (листенер для статистики)
    public void setStatsFrame(VikingStatsFrame statsFrame) {
        this.statsFrame = statsFrame;
    }

    // Запрос случайного викинга выше 180 через HTTP
    public void requestRandomTallViking() {
        RestTemplate rest = new RestTemplate();
        Viking viking = rest.getForObject("http://localhost:8080/api/vikings/stats/random-tall", Viking.class);
        if (gui != null) {
            gui.showVikingInfo("Случайный викинг выше 180 см", viking);
        }
    }

    // Запрос викингов с легендарным снаряжением
    public void requestLegendaryVikings() {
        RestTemplate rest = new RestTemplate();
        ResponseEntity<Viking[]> response = rest.getForEntity("http://localhost:8080/api/vikings/stats/legendary", Viking[].class);
        List<Viking> vikings = java.util.Arrays.asList(response.getBody());
        if (gui != null) {
            gui.showVikingsList("Викинги с легендарным снаряжением", vikings);
        }
    }

    // Запрос рыжебородых, сортированных по возрасту
    public void requestRedBeardedSorted() {
        RestTemplate rest = new RestTemplate();
        ResponseEntity<Viking[]> response = rest.getForEntity("http://localhost:8080/api/vikings/stats/red-bearded", Viking[].class);
        List<Viking> vikings = java.util.Arrays.asList(response.getBody());
        if (gui != null) {
            gui.showVikingsList("Рыжебородые викинги (по возрасту)", vikings);
        }
    }
}
