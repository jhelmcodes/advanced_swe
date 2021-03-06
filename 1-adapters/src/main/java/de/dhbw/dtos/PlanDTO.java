package de.dhbw.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PlanDTO {
    private final int width;
    private final int height;

    public static PlanDTO.PlanDTOBuilder builder() {
        return new PlanDTO.PlanDTOBuilder();
    }

    public static class PlanDTOBuilder {
        private int width;
        private int height;

        PlanDTOBuilder() {
        }

        public PlanDTO.PlanDTOBuilder width(int width) {
            this.width = width;
            return this;
        }

        public PlanDTO.PlanDTOBuilder height(int height) {
            this.height = height;
            return this;
        }

        public PlanDTO build() {
            return new PlanDTO(this.width, this.height);
        }
    }
}
