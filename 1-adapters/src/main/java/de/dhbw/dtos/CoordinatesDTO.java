package de.dhbw.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Design Pattern: Builder
 */
@Getter
@AllArgsConstructor
public class CoordinatesDTO {
    private final int x;
    private final int y;

    public static CoordinatesDTO.CoordinatesDTOBuilder builder() {
        return new CoordinatesDTO.CoordinatesDTOBuilder();
    }

    public static class CoordinatesDTOBuilder {
        private int x;
        private int y;

        CoordinatesDTOBuilder() {
        }

        public CoordinatesDTO.CoordinatesDTOBuilder x(int x) {
            this.x = x;
            return this;
        }

        public CoordinatesDTO.CoordinatesDTOBuilder y(int y) {
            this.y = y;
            return this;
        }

        public CoordinatesDTO build() {
            return new CoordinatesDTO(this.x, this.y);
        }
    }
}
