package ru.mephi.vikingdemo.service;

import org.springframework.stereotype.Service;
import ru.mephi.vikingdemo.model.Viking;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class VikingService {
    // каждый раз при изменении создаётся новая копия списка 
    private final CopyOnWriteArrayList<Viking> vikings = new CopyOnWriteArrayList<>();
    private final VikingFactory vikingFactory;
    @Autowired
    public VikingService(VikingFactory vikingFactory) {
        this.vikingFactory = vikingFactory;
    }
    
    public List<Viking> findAll() {
        return List.copyOf(vikings);
    }

    public Viking createRandomViking() {
        

        Viking viking = vikingFactory.createRandomViking();

        vikings.add(viking);
        return viking;
    }

    public Viking addViking(Viking viking) {  //добавление конкретного викинга
        boolean exists = vikings.stream()
                .anyMatch(v -> v.name().equalsIgnoreCase(viking.name()));

        if (exists) {
            throw new RuntimeException("Viking with name " + viking.name() + " already exists");
        }

        vikings.add(viking);
        return viking;
    }

    public void deleteViking(String name) { //удаление викинга по имени
        boolean removed = vikings.removeIf(v -> v.name().equalsIgnoreCase(name));

        if (!removed) {
            throw new RuntimeException("Viking with name " + name + " not found");
        }
    }

    public Viking updateViking(String name, Viking updatedViking) { //перезапись параметров конкретного викинга
        int index = -1;
        for (int i = 0; i < vikings.size(); i++) {
            if (vikings.get(i).name().equalsIgnoreCase(name)) {
                index = i;
                break;
            }
        }

        if (index == -1) {
            throw new RuntimeException("Viking with name " + name + " not found");
        }

        Viking vikingToUpdate = new Viking( //создаем нового викинга с обновленными данными, сохраняя имя из запроса
                name,
                updatedViking.age(),
                updatedViking.heightCm(),
                updatedViking.hairColor(),
                updatedViking.beardStyle(),
                updatedViking.equipment()
        );

        vikings.set(index, vikingToUpdate);
        return vikingToUpdate;
    }
}
