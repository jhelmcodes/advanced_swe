package de.dhbw.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;
import java.util.Objects;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class RankingEntity {

    @NonNull
    @Id
    private String uuid;//entity id
    @Column
    private String name;
    @Column
    private int earned_points;
    @Column
    private Date date;

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof RankingEntity) {
            RankingEntity rankingEntity = (RankingEntity) obj;
            return this.getName().equals(rankingEntity.getName());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }
}