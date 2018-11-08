package cz.muni.fi.pa165.pokemon.league.participation.manager.service;

import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.PokemonSpecies;
import cz.muni.fi.pa165.pokemon.league.participation.manager.enums.PokemonType;
import java.util.List;

/**
 * Interface of PokemonSpecies service.
 *
 * @author Tibor Zauko 433531
 */
public interface PokemonSpeciesService {

    /**
     * Creates a new Pokemon species.
     * @param species Species to create.
     */
    void createPokemonSpecies(PokemonSpecies species);

    /**
     * Changes typing of Pokemon species.
     * @param species           Species to change the typing of.
     * @param newPrimaryType    New primary type of the species.
     * @param newSecondaryType  New secondary type of the species.
     */
    void changeTyping(PokemonSpecies species, PokemonType newPrimaryType, PokemonType newSecondaryType);

    /**
     * Change the species' preevolution.
     * @param species           Species to change the evolution of.
     * @param newPreevolution   The new preevolution of the species. The created
     * evolution chain must not contain more than 3 species and must not be
     * circular (i.e. A evolves into B evolves into C evolves into A).
     */
    void changePreevolution(PokemonSpecies species, PokemonSpecies newPreevolution);

    /**
     * Remove the Pokemon species.
     * @param species Species to remove.
     */
    void remove(PokemonSpecies species);

    /**
     * Find a Pokemon species by its id.
     * @param id The id of the pokemon species.
     * @return Found Pokemon species or null if none found.
     */
    PokemonSpecies findPokemonSpeciesById(Long id);

    /**
     * Returns a list of all Pokemon species.
     * @return list containing all known Pokemon species.
     */
    List<PokemonSpecies> getAllPokemonSpecies();

    /**
     * Return a list of the direct evolutions of the given species.
     * @param species Species whose evolutions shall be found.
     * @return List containing all (possibly none) PokemonSpecies which evolve
     * directly from the given Pokemon species.
     */
    List<PokemonSpecies> getAllEvolutionsOfPokemonSpecies(PokemonSpecies species);
}