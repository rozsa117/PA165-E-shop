package cz.muni.fi.pa165.pokemon.league.participation.manager.dao;

import cz.muni.fi.pa165.pokemon.league.participation.manager.common.PersistenceApplicationContext;
import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Pokemon;
import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.PokemonSpecies;
import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Trainer;
import cz.muni.fi.pa165.pokemon.league.participation.manager.enums.PokemonType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import org.junit.Assert;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Unit tests for PokemonDAO implementations.
 *
 * @author Jiří Medveď 38451
 */
@ContextConfiguration(classes = PersistenceApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class PokemonDAOTest extends AbstractTestNGSpringContextTests {

    @Inject
    private PokemonDAO pokemonDao;

    @Inject
    private TrainerDAO trainerDao;

    @Inject
    private PokemonSpeciesDAO pokemonSpeciesDao;

    private Trainer trainer;
    private PokemonSpecies species;
    private Pokemon pokemon1;
    private Pokemon pokemon2;

    @BeforeMethod
    public void setUp() {
        species = new PokemonSpecies();
        species.setSpeciesName("Test species");
        species.setPrimaryType(PokemonType.FIRE);
        pokemonSpeciesDao.createPokemonSpecies(species);

        trainer = new Trainer();
        trainer.setName("Test trainer");
        trainer.setBorn(LocalDate.of(2000, Month.MARCH, 1));
        trainer.setPasswordHash("Password");
        trainer.setSurname("Surname");
        trainer.setUserName("Username");
        trainerDao.createTrainer(trainer);

        pokemon1 = new Pokemon();
        pokemon1.setNickname("Pokemon 1");
        pokemon1.setDateTimeOfCapture(LocalDateTime.of(2018, Month.MARCH, 1, 0, 0));
        pokemon1.setSpecies(species);
        pokemon1.setTrainer(trainer);
        pokemonDao.createPokemon(pokemon1);

        pokemon2 = new Pokemon();
        pokemon2.setNickname("Pokemon 2");
        pokemon2.setDateTimeOfCapture(LocalDateTime.of(2018, Month.MARCH, 1, 0, 1));
        pokemon2.setSpecies(species);
        pokemon2.setTrainer(trainer);
        pokemonDao.createPokemon(pokemon2);

    }

    /**
     * Test of createPokemon method with valid imput.
     */
    @Test
    public void testCreateValidPokemon() {
        Pokemon newPokemon = new Pokemon();
        newPokemon.setNickname("Pokemon 2");
        newPokemon.setDateTimeOfCapture(LocalDateTime.of(2018, Month.MARCH, 1, 0, 2));
        newPokemon.setSpecies(species);
        newPokemon.setTrainer(trainer);
        pokemonDao.createPokemon(newPokemon);

        List<Pokemon> pokemons = pokemonDao.getAllPokemon();
        Assert.assertEquals(3, pokemons.size());
        Assert.assertTrue(pokemons.contains(newPokemon));
    }

    /**
     * Test of createPokemon method with missing mandatory name.
     */
    @Test
    public void testCreatePokemonWithoutName() {
        Pokemon newPokemon = new Pokemon();

        assertThatExceptionOfType(PersistenceException.class).isThrownBy(() -> pokemonDao.createPokemon(newPokemon));

    }

    /**
     * Test of createPokemon method with null as imput.
     */
    @Test
    public void testCreateNullPokemon() {

        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> pokemonDao.createPokemon(null));

    }

    /**
     * Test of updatePokemon method
     */
    @Test
    public void testUpdatePokemon() {
        Pokemon pokemonToUpdate = pokemonDao.findPokemonById(pokemon1.getId());
        pokemonToUpdate.setNickname("Changed NickName");

        pokemonDao.updatePokemon(pokemonToUpdate);

        Pokemon found = pokemonDao.findPokemonById(pokemon1.getId());
        Assert.assertEquals(found, pokemonToUpdate);
        Assert.assertEquals("Changed NickName", found.getNickname());
    }

    /**
     * Test of updatePokemon method with null as imput.
     */
    @Test
    public void testUpdateNullPokemon() {

        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> pokemonDao.updatePokemon(null));

    }

    /**
     * Test of deletePokemon method
     */
    @Test
    public void testDeletePokemon() {
        pokemonDao.deletePokemon(pokemon1);

        List<Pokemon> pokemons = pokemonDao.getAllPokemon();
        Assert.assertEquals(1, pokemons.size());
    }

    /**
     * Test of deletePokemon method with null as imput.
     */
    @Test
    public void testDeleteNullPokemon() {

        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> pokemonDao.deletePokemon(null));

    }

    /**
     * Test of findPokemonById method
     */
    @Test
    public void testFindPokemonById() {
        Pokemon found = pokemonDao.findPokemonById(pokemon1.getId());
        Assert.assertEquals(pokemon1, found);
    }

    /**
     * Test of findPokemonById method - non existence
     */
    @Test
    public void testFindNonExistentPokemonById() {
        Pokemon found = pokemonDao.findPokemonById(Long.MAX_VALUE);
        Assert.assertNull(found);
    }

    /**
     * Test of getAllPokemon method - non existing Pokemon
     */
    @Test
    public void testGetAllPokemon() {
        assertThat(pokemonDao.getAllPokemon())
                .usingFieldByFieldElementComparator()
                .containsOnly(pokemon1, pokemon2);
    }

}
