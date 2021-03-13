package de.dhbw.entities;

import de.dhbw.valueobjects.CoordinatesVO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.Objects;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
@Entity
public class PlayerEntity {

    @NonNull
    @Id
    private String name;
    @OneToOne
    @LazyCollection(LazyCollectionOption.FALSE)
    private CoordinatesVO position;
    @Column
    private int lifePoints = 0;

    /**
     * the new position is only allowed to differ in one coordinate and by one step!
     *
     * @param newPosition the new coordinates
     * @return true if the new position has been valid, otherwise false
     */
    public boolean isNewPosition(CoordinatesVO newPosition) {
        if (position == null) {
            return true;
        }
        if (position.equals(newPosition)) {
            return true;
        }
        if (position.getY() == newPosition.getY()) {
            return position.getX() == (newPosition.getX() + 1) || position.getX() == (newPosition.getX() - 1);
        } else if (position.getX() == newPosition.getX()) {
            return position.getY() == (newPosition.getY() + 1) || position.getY() == (newPosition.getY() - 1);
        }
        return false;
    }

    public void setPosition(CoordinatesVO newPosition) {
        if (isNewPosition(newPosition)) {
            this.position = newPosition;
        }
    }

    public void decreaseLifePoints() {
        if (lifePoints > 0) {
            lifePoints--;
            log.debug("Life points have been decreased for {}. Left: {}", getName(), lifePoints);
        } else {
            throw new IllegalArgumentException("Player " + getName() + " is already dead.");
        }
    }

    public void increaseLifePoints() {
        lifePoints++;
    }

    public boolean isAlive() {
        return lifePoints > 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) obj;
            return this.getName().equals(player.getName());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
