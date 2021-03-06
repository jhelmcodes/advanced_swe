package de.dhbw.repositories;

import de.dhbw.entities.ranking.RankingEntity;

import java.util.List;

public interface RankingRepository {
    void save(RankingEntity rankingEntity);
    List<RankingEntity> getHighscore();
    void delete(RankingEntity rankingEntity);
}
