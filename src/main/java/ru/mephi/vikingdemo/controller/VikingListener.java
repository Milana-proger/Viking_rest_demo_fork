/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ru.mephi.vikingdemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
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

}
