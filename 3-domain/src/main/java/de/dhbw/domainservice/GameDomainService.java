package de.dhbw.domainservice;

import de.dhbw.entities.BoardEntity;
import de.dhbw.entities.GameObject;
import de.dhbw.entities.PlayerEntity;
import de.dhbw.entities.RankingEntity;
import de.dhbw.helper.GameAction;
import de.dhbw.valueobjects.CoordinatesVO;

import java.util.List;

public interface GameDomainService {
    List<GameAction<GameObject>> getGameActions();

    void startGame();
    boolean isRunning();
    PlayerEntity getCurrentPlayer();
    RankingEntity getCurrentRanking();
    BoardEntity getCurrentBoard();

    boolean movePlayer(CoordinatesVO newCoordinates);

    boolean isGameOver();
    void stopGame();
}
