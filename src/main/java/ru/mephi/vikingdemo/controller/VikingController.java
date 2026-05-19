package ru.mephi.vikingdemo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import ru.mephi.vikingdemo.model.Viking;
import ru.mephi.vikingdemo.service.VikingService;

import java.util.List;
import ru.mephi.vikingdemo.model.BeardStyle;
import ru.mephi.vikingdemo.model.HairColor;
import ru.mephi.vikingdemo.service.VikingStatisticsService;

@RestController
@RequestMapping("/api/vikings")
@Tag(name = "Vikings", description = "Операции с викингами")
public class VikingController {

    private final VikingService vikingService;
    private VikingListener vikingListener;
    private final VikingStatisticsService vikingStatisticsService;

    public VikingController(VikingService vikingService, VikingListener vikingListener, VikingStatisticsService vikingStatisticsService) {
        this.vikingService = vikingService;
        this.vikingListener = vikingListener;
        this.vikingStatisticsService = vikingStatisticsService;
    }
    
    @GetMapping
    @Operation(summary = "Получить список созданных викингов", 
            operationId = "getAllVikings")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Список успешно получен")
    })
    public List<Viking> getAllVikings() {
        System.out.println("GET /api/vikings called");
        return vikingService.findAll();
    }

    @GetMapping("/test")
    @Operation(summary = "Получить список тестовых викингов", 
            operationId = "getTest")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Список успешно получен")
    })
    public List<String> test() {
        System.out.println("GET /api/vikings/test called");
        return List.of("Ragnar", "Bjorn");
    }
    
    @PostMapping("/post")
    public void addViking(){
        vikingListener.testAdd();
    }

        @PostMapping
    @Operation(summary = "Добавить конкретного викинга", 
            operationId = "addViking")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Викинг успешно добавлен"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные")
    })
    public Viking addViking(@RequestBody Viking viking) {
        System.out.println("POST /api/vikings called with: " + viking.name());
            Viking result = vikingService.addViking(viking);
            vikingListener.notifyVikingAdded(result);
            return vikingService.addViking(viking);
    }

        @DeleteMapping("/{name}")
    @Operation(summary = "Удалить викинга из таблицы", 
            operationId = "deleteViking")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Викинг успешно удален"),
            @ApiResponse(responseCode = "404", description = "Викинг не найден")
    })
    public void deleteViking(@PathVariable String name) {
        System.out.println("DELETE /api/vikings/" + name + " called");
            vikingService.deleteViking(name);
            vikingListener.notifyVikingDeleted(name);
    }

        @PutMapping("/{name}")
    @Operation(summary = "Перезапись параметров конкретного викинга", 
            operationId = "updateViking")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Викинг успешно обновлен"),
            @ApiResponse(responseCode = "404", description = "Викинг не найден")
    })
    public Viking updateViking(@PathVariable String name, @RequestBody Viking updatedViking) {
        System.out.println("PUT /api/vikings/" + name + " called");
        Viking result = vikingService.updateViking(name, updatedViking);
        vikingListener.notifyVikingUpdated(result);
        return vikingService.updateViking(name, updatedViking);
    }

    @PostMapping("/mass-generate")
    @Operation(summary = "Массовая генерация викингов")
    public List<Viking> massGenerateVikings(@RequestParam int count) {
        System.out.println("POST /api/vikings/mass-generate called with count=" + count);
        List<Viking> generated = vikingService.generateRandomVikings(count);
        vikingListener.notifyVikingsGenerated(generated);
        return generated;
    }

    // для VikingStatisticsService (лямбды)

    @GetMapping("/stats/age/greater")
    public long countAgeGreaterThan(@RequestParam int age) {
        return vikingStatisticsService.countByAgeGreaterThan(age);
    }

    @GetMapping("/stats/age/less")
    public long countAgeLessThan(@RequestParam int age) {
        return vikingStatisticsService.countByAgeLessThan(age);
    }

    @GetMapping("/stats/age/between")
    public long countAgeBetween(@RequestParam int min, @RequestParam int max) {
        return vikingStatisticsService.countByAgeBetween(min, max);
    }

    @GetMapping("/stats/age/outside")
    public long countAgeOutside(@RequestParam int min, @RequestParam int max) {
        return vikingStatisticsService.countByAgeOutside(min, max);
    }

    @GetMapping("/stats/beard-hair")
    public long countByBeardAndHair(@RequestParam BeardStyle beard, @RequestParam HairColor hair) {
        return vikingStatisticsService.countByBeardAndHair(beard, hair);
    }

    @GetMapping("/stats/axes")
    public long countByAxesCount(@RequestParam int count) {
        return vikingStatisticsService.countByAxesCount(count);
    }

    // Случайный викинг ростом выше 180
    @GetMapping("/stats/random-tall")
    public Viking getRandomVikingAbove180() {
        return vikingStatisticsService.getRandomVikingAbove180();
    }

    // Все викинги с легендарным снаряжением
    @GetMapping("/stats/legendary")
    public List<Viking> getVikingsWithLegendaryGear() {
        return vikingStatisticsService.getVikingsWithLegendaryGear();
    }

    // Рыжебородые, сортированные по возрасту
    @GetMapping("/stats/red-bearded")
    public List<Viking> getRedBeardedVikingsSortedByAge() {
        return vikingStatisticsService.getRedBeardedVikingsSortedByAge();
    }

}
