package de.dhbw.services;

import de.dhbw.domainservice.BoardDomainService;
import de.dhbw.domainservice.MoveColleaguesDomainService;
import de.dhbw.entities.BoardEntity;
import de.dhbw.entities.ColleagueEntity;
import de.dhbw.entities.GameObject;
import de.dhbw.entities.Infection;
import de.dhbw.helper.ColleagueMovement;
import de.dhbw.repositories.BoardRepository;
import de.dhbw.valueobjects.CoordinatesVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

@Service
public class BoardService implements BoardDomainService, MoveColleaguesDomainService {
    private final BoardRepository boardRepository;
    private BoardEntity boardEntity;
    private List<ColleagueMovement> forwardAndBackMovements;
    private Timer colleagueMovementTimer;
    private final List<GameObject> gameObjects;

    @Autowired
    public BoardService(BoardRepository boardRepository, List<GameObject> gameObjects) {
        this.boardRepository = boardRepository;
        this.gameObjects = gameObjects;
    }

    public boolean isInitialized() {
        return boardEntity != null && forwardAndBackMovements != null;
    }

    public void reset() {
        stopMovingColleagues();
        boardEntity = null;
        forwardAndBackMovements = null;
    }

    public List<GameObject> getGameObjects(){
        return gameObjects;
    }

    public void initializeBoard(String boardName) {
        this.boardEntity = boardRepository.getBoardByName(boardName);
        this.forwardAndBackMovements = new ArrayList<>();
        for (ColleagueEntity colleague : boardEntity.getColleagues()) {
            forwardAndBackMovements.add(colleague.createColleagueIterator());
        }
        initializeCoordinatesForGameObjects();
    }

    private void initializeCoordinatesForGameObjects() {
        for (GameObject gameObject : getGameObjects()) {
            CoordinatesVO coordinatesVO;
            do {
                coordinatesVO = getCurrentBoard().getBoardLayout().getRandomCoordinate();
            } while (!isCoordinateEmpty(coordinatesVO));
            gameObject.setNewCoordinate(coordinatesVO);
        }
    }

    public BoardEntity getCurrentBoard() {
        return boardEntity;
    }

    public boolean isCoordinateEmpty(CoordinatesVO coordinatesVO) {
        boolean isEmpty = !boardEntity.getBoardLayout().isCoordinateBlocked(coordinatesVO);
        for (ColleagueMovement iterator : this.forwardAndBackMovements) {
            if (iterator.getCurrentPosition().equals(coordinatesVO)) {
                return false;
            }
        }
        return isEmpty;
    }

    public List<ColleagueMovement> getColleagueMovements() {
        return new ArrayList<>(forwardAndBackMovements); //forbid changing the original list
    }

    public void startMovingColleagues() {
        this.forwardAndBackMovements = new ArrayList<>();
        boardEntity.getColleagues().forEach(value -> {
            this.forwardAndBackMovements.add(value.createColleagueIterator());
        });
        TimerTask rankingPointTask = new TimerTask() {
            List<Infection> infections = new ArrayList<>();

            public void run() {
                getGameObjects().removeAll(infections);
                infections = new ArrayList<>();
                forwardAndBackMovements.forEach(movement -> {
                    movement.nextPosition();
                    Infection newInfection = new Infection(boardEntity.getBoardConfiguration(), movement.getCurrentPosition());
                    infections.add(newInfection);
                    getGameObjects().add(newInfection);
                });

            }
        };
        colleagueMovementTimer = new Timer("Colleague Movement Timer");
        colleagueMovementTimer.scheduleAtFixedRate(rankingPointTask, 0, 500);
    }

    public void stopMovingColleagues() {
        colleagueMovementTimer.cancel();
        colleagueMovementTimer = null;
    }
}
