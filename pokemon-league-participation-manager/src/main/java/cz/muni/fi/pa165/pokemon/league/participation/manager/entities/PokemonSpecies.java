package cz.muni.fi.pa165.pokemon.league.participation.manager.entities;

import cz.muni.fi.pa165.pokemon.league.participation.manager.enums.PokemonType;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Entity class for object PokemonSpecies.
 *
 * @author Jiří Medveď 38451
 */
@Entity
@Table(name = "POKEMON_SPECIES")
public class PokemonSpecies {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false, unique = true)
    private String speciesName;

    @NotNull
    @Enumerated
    private PokemonType primaryType;

    @Enumerated
    private PokemonType secondaryType;

    @OneToOne(optional = true)
    @JoinColumn(name = "evolvesFromId")
    private PokemonSpecies evolvesFrom;

    public PokemonSpecies() {
    }

    public PokemonSpecies(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSpeciesName() {
        return speciesName;
    }

    public PokemonType getPrimaryType() {
        return primaryType;
    }

    public PokemonType getSecondaryType() {
        return secondaryType;
    }

    public void setSecondaryType(PokemonType secondaryType) {
        this.secondaryType = secondaryType;
    }

    public PokemonSpecies getEvolvesFrom() {
        return evolvesFrom;
    }

    public void setEvolvesFrom(PokemonSpecies evolvesFrom) {
        this.evolvesFrom = evolvesFrom;
    }

    public void setSpeciesName(String speciesName) {
        this.speciesName = speciesName;
    }

    public void setPrimaryType(PokemonType primaryType) {
        this.primaryType = primaryType;
    }

    @Override
    public String toString() {
        return "PokemonSpecies{" + "id=" + id + ", speciesName=" + speciesName + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }

        if (!(obj instanceof PokemonSpecies)) {
            return false;
        }
        final PokemonSpecies other = (PokemonSpecies) obj;
        return Objects.equals(this.speciesName, other.getSpeciesName());
    }

}
