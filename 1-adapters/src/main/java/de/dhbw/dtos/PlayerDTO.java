package de.dhbw.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

/**
 * Design Pattern: Builder
 */
@Data
@AllArgsConstructor
public class PlayerDTO {
    private String name;
    private CoordinatesDTO position;
    private Map<String, Integer> statistics;

    public static PlayerDTO.PlayerDTOBuilder builder() {
        return new PlayerDTO.PlayerDTOBuilder();
    }

    public static class PlayerDTOBuilder {
        private String name;
        private CoordinatesDTO position;
        private Map<String, Integer> statistics;

        PlayerDTOBuilder() {
        }

        public PlayerDTO.PlayerDTOBuilder name(String name) {
            this.name = name;
            return this;
        }

        public PlayerDTO.PlayerDTOBuilder position(CoordinatesDTO position) {
            this.position = position;
            return this;
        }

        public PlayerDTO.PlayerDTOBuilder statistics(Map<String, Integer> statistics) {
            this.statistics = statistics;
            return this;
        }

        public PlayerDTO build() {
            return new PlayerDTO(this.name, this.position, this.statistics);
        }

        public String toString() {
            return "PlayerDTO.PlayerDTOBuilder(name=" + this.name + ", position=" + this.position +  ", statistics="
                    + this.statistics + ")";
        }
    }
}
