package cz.muni.fi.pa165.pokemon.league.participation.manager.dto;

import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * DTO for Badge creation request
 *
 * @author Michal Mokros 456442
 */
public class BadgeCreateDTO {

    @NotNull
    private Long trainerId;

    @NotNull
    private Long gymId;

    public Long getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(Long trainerId) {
        this.trainerId = trainerId;
    }

    public Long getGymId() {
        return gymId;
    }

    public void setGymId(Long gymId) {
        this.gymId = gymId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BadgeCreateDTO that = (BadgeCreateDTO) o;
        return Objects.equals(trainerId, that.trainerId) &&
                Objects.equals(gymId, that.gymId);
    }

    @Override
    public int hashCode() {
        int hash = 13;
        hash = 29 * hash * Objects.hashCode(this.trainerId) * Objects.hashCode(this.gymId);
        return hash;
    }

    @Override
    public String toString() {
        return "BadgeCreateDTO{" +
                "trainerId=" + trainerId +
                ", gymId=" + gymId +
                '}';
    }
}
