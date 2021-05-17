package de.dhbw.entities;

import de.dhbw.valueobjects.GameObjectCountVO;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.HashMap;
import java.util.Map;

@Getter
@Entity
public class PlayerStatistics {
    @Id
    @GeneratedValue
    private Long id;
    @OneToMany
    private final Map<Class<? extends GameObject>, GameObjectCountVO> statisticsPerItems = new HashMap<>();

    @SuppressWarnings("unused")
    public PlayerStatistics() {
    }

    public GameObjectCountVO getStatistic(Class<? extends GameObject> gameObject) {
        initGameObjectIfNotExists(gameObject);
        return statisticsPerItems.get(gameObject);
    }

    private void initGameObjectIfNotExists(Class<? extends GameObject> gameObject){
        if(!statisticsPerItems.containsKey(gameObject)){
            statisticsPerItems.put(gameObject, new GameObjectCountVO(0));
        }
    }

    public void decreaseStatistics(Class<? extends GameObject> gameObject) {
        initGameObjectIfNotExists(gameObject);
        GameObjectCountVO oldValue = getStatistic(gameObject);
        this.statisticsPerItems.put(gameObject, oldValue.decreasedCount());
    }

    public void increaseStatistics(Class<? extends GameObject> gameObject) {
        initGameObjectIfNotExists(gameObject);
        GameObjectCountVO oldValue = getStatistic(gameObject);
        this.statisticsPerItems.put(gameObject, oldValue.increasedCount());
    }

    public boolean isAlive() {
        return statisticsPerItems.get(Vaccination.class).equals(new GameObjectCountVO(0));
    }

}