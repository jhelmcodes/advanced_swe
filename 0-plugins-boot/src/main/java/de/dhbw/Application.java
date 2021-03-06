package de.dhbw;

import de.dhbw.entities.board.BoardConfigurationEntity;
import de.dhbw.entities.board.BoardEntity;
import de.dhbw.entities.board.BoardLayoutEntity;
import de.dhbw.entities.board.ColleagueEntity;
import de.dhbw.entities.player.PlayerStatisticsEntity;
import de.dhbw.entities.ranking.RankingEntity;
import de.dhbw.repositories.BoardRepository;
import de.dhbw.services.HighscoreService;
import de.dhbw.valueobjects.CoordinatesVO;
import de.dhbw.valueobjects.PlanVO;
import de.dhbw.valueobjects.ProbabilityVO;
import de.dhbw.valueobjects.RadiusVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Date;
import java.util.UUID;

@SpringBootApplication
@EntityScan("de.dhbw.*")
@ComponentScan("de.dhbw.*")
@EnableJpaRepositories("de.dhbw.*")
@Slf4j
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner demo(BoardRepository boardRepository, HighscoreService rankingService) {
        PlanVO plan = new PlanVO(50, 80);
        ProbabilityVO probabilityVO = new ProbabilityVO(0.5);
        RadiusVO radiusVO = new RadiusVO(3);
        BoardLayoutEntity boardLayoutEntity = new BoardLayoutEntity(plan);
        BoardConfigurationEntity boardConfigurationEntity = new BoardConfigurationEntity(radiusVO, probabilityVO);
        BoardEntity board = new BoardEntity("default", boardLayoutEntity, boardConfigurationEntity);
        for (int y = 1; y < 5; y++) {
            for (int i = 0; i <= 12; i++) {
                for (int j = i * 5; j < (i * 5) + 10 && i % 4 == 0; j++) {
                    board.getBoardLayout().addObstacle(new CoordinatesVO(j, 10 * y));
                }
            }
        }

        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                for (int r = 0; r < 2; r++) {
                    for (int s = 0; s < 2; s++) {
                        board.getBoardLayout().addObstacle(new CoordinatesVO(12 + r + x * 20, 15 + s + y * 10));
                    }
                }
            }
        }

        ColleagueEntity fred = new ColleagueEntity("Fred");
        for (int x = 0; x < 10; x++) {
            fred.extendPath(new CoordinatesVO(11 + x, 11));
        }
        for (int y = 0; y < 5; y++) {
            fred.extendPath(new CoordinatesVO(21, 11 + y));
        }

        board.addColleague(fred);
        RankingEntity rankingEntity = new RankingEntity(UUID.randomUUID().toString(), "Ninjeanne", 123456, new PlayerStatisticsEntity(), new Date());
        return (args) -> {
            boardRepository.save(board);
            rankingService.saveNewRanking(rankingEntity);
        };
    }
}
