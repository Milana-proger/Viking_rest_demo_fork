package ru.mephi.vikingdemo.service;

import org.springframework.stereotype.Service;
import ru.mephi.vikingdemo.model.Viking;

import java.util.List;
import java.util.stream.IntStream;

@Service
public class VikingStatisticsService {
    private final VikingService vikingService;

    public VikingStatisticsService(VikingService vikingService) {
        this.vikingService = vikingService;
    }

    private Integer[] ids;

    // Обновление массива при изменении списка викингов
    private void updateIdsArray() {
        List<Viking> vikings = vikingService.findAll();
        ids = new Integer[vikings.size()];
        for (int i = 0; i < vikings.size(); i++) {
            ids[i] = i + 1;
        }
    }

    // 1. Найти последнюю запись (max ID)
    public Integer findMaxId() {
        updateIdsArray();
        if (ids.length == 0) return null;
        return java.util.Arrays.stream(ids)
                .max(Integer::compareTo)
                .orElse(null);
    }

    // 2. Все четные ID
    public Integer[] findEvenIds() {
        updateIdsArray();
        if (ids.length == 0) return new Integer[0];
        return java.util.Arrays.stream(ids)
                .filter(id -> id % 2 == 0)
                .toArray(Integer[]::new);
    }
}
