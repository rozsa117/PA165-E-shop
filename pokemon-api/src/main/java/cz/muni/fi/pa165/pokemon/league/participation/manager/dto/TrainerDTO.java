package cz.muni.fi.pa165.pokemon.league.participation.manager.dto;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * DTO class for object Trainer.
 *
 * @author Jiří Medveď 38451
 */
public class TrainerDTO {

    private String passwordHash;

    private String userName;

    private String name;

    private String surname;

    private LocalDate born;

    private boolean admin;

    private boolean gymLeader;

    private Set<PokemonDTO> pokemons = new HashSet<>();

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public LocalDate getBorn() {
        return born;
    }

    public void setBorn(LocalDate born) {
        this.born = born;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public Set<PokemonDTO> getPokemons() {
        return pokemons;
    }

    public void setPokemons(Set<PokemonDTO> pokemons) {
        this.pokemons = pokemons;
    }

    public boolean isGymLeader() {
        return gymLeader;
    }

    public void setGymLeader(boolean gymLeader) {
        this.gymLeader = gymLeader;
    }

    @Override
    public String toString() {
        return "Trainer{ " + ", name = " + name + ", surname = " + surname + ", born = " + born + " }";
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.userName);
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
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TrainerDTO other = (TrainerDTO) obj;
        if (!Objects.equals(this.userName, other.userName)) {
            return false;
        }
        return true;
    }

}