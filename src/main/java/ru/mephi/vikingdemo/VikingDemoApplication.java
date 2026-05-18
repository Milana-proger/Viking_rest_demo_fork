package ru.mephi.vikingdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.mephi.vikingdemo.gui.VikingDesktopFrame;
import ru.mephi.vikingdemo.gui.VikingStatsFrame;

import javax.swing.SwingUtilities;
import ru.mephi.vikingdemo.controller.VikingListener;
import ru.mephi.vikingdemo.service.VikingService;
import ru.mephi.vikingdemo.service.VikingStatisticsService;

@SpringBootApplication
public class VikingDemoApplication {

    public static void main(String[] args) {
        System.out.println(java.awt.GraphicsEnvironment.isHeadless());
        SpringApplication app = new SpringApplication(VikingDemoApplication.class);
        app.setHeadless(false);

        ConfigurableApplicationContext context = app.run(args);

        VikingService vikingService = context.getBean(VikingService.class);
        VikingListener vikingListener = context.getBean(VikingListener.class);
        VikingStatisticsService vikingStatisticsService = context.getBean(VikingStatisticsService.class);

        SwingUtilities.invokeLater(() -> {
            VikingDesktopFrame frame = new VikingDesktopFrame(vikingService);
            vikingListener.setGui(frame);
            frame.setVisible(true);

            VikingStatsFrame statsFrame = new VikingStatsFrame(vikingStatisticsService, vikingListener);
            vikingListener.setStatsFrame(statsFrame);
            statsFrame.setVisible(true);
        });
    }
}