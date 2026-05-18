package ru.mephi.vikingdemo.service;

import org.springframework.stereotype.Service;
import ru.mephi.vikingdemo.model.BeardStyle;
import ru.mephi.vikingdemo.model.HairColor;
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

    // методы для первого блока из задания
    // 1. Подсчет викингов старше определенного возраста
    public long countByAgeGreaterThan(int age) {
        return vikingService.findAll().stream()
                .filter(v -> v.age() > age)
                .count();
    }

    // 2. Подсчет викингов младше определенного возраста
    public long countByAgeLessThan(int age) {
        return vikingService.findAll().stream()
                .filter(v -> v.age() < age)
                .count();
    }

    // 3. Подсчет викингов в диапазоне возраста (включительно)
    public long countByAgeBetween(int minAge, int maxAge) {
        return vikingService.findAll().stream()
                .filter(v -> v.age() >= minAge && v.age() <= maxAge)
                .count();
    }

    // 4. Подсчет викингов вне диапазона возраста
    public long countByAgeOutside(int minAge, int maxAge) {
        return vikingService.findAll().stream()
                .filter(v -> v.age() < minAge || v.age() > maxAge)
                .count();
    }

    // 5. Подсчет викингов по форме бороды И цвету волос (одновременно)
    public long countByBeardAndHair(BeardStyle beard, HairColor hair) {
        return vikingService.findAll().stream()
                .filter(v -> v.beardStyle() == beard && v.hairColor() == hair)
                .count();
    }

    // 6. Подсчет викингов, имеющих один топор или два топора
    public long countByAxesCount(int axesCount) {
        return vikingService.findAll().stream()
                .filter(v -> {
                    long axeCount = v.equipment().stream()
                            .filter(item -> item.name().toLowerCase().contains("axe"))
                            .count();
                    return axeCount == axesCount;
                })
                .count();
    }

    // 3 пункт задания
    // Обновление массива при изменении списка викингов
    private void updateIdsArray() {
        List<Viking> vikings = vikingService.findAll();
        ids = new Integer[vikings.size()];
        for (int i = 0; i < vikings.size(); i++) {
            ids[i] = i + 1;
        }
    }

    // 3) - 1. Найти последнюю запись (max ID)
    public Integer findMaxId() {
        updateIdsArray();
        if (ids.length == 0) return null;
        return java.util.Arrays.stream(ids)
                .max(Integer::compareTo)
                .orElse(null);
    }

    // 3) - 2. Все четные ID
    public Integer[] findEvenIds() {
        updateIdsArray();
        if (ids.length == 0) return new Integer[0];
        return java.util.Arrays.stream(ids)
                .filter(id -> id % 2 == 0)
                .toArray(Integer[]::new);
    }
}
