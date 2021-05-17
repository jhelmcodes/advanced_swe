package de.dhbw.entities;

import de.dhbw.valueobjects.CoordinatesVO;
import lombok.Getter;

@Getter
public abstract class GameObject {
    private CoordinatesVO coordinatesVO;

    public void doToPlayer(PlayerEntity playerEntity){
        playerEntity.getPlayerStatistics().increaseStatistics(this.getClass());
    }

    public abstract int getValue();

    public void setNewCoordinate(CoordinatesVO coordinatesVO){
        this.coordinatesVO = coordinatesVO;
    }
}