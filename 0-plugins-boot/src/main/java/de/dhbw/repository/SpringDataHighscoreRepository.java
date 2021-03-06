package de.dhbw.repository;

import de.dhbw.entities.ranking.RankingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataHighscoreRepository extends JpaRepository<RankingEntity, String> {
}
