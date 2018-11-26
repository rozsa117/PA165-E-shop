package cz.muni.fi.pa165.pokemon.league.participation.manager.service.facade;

import cz.muni.fi.pa165.pokemon.league.participation.manager.builders.GymBuilder;
import cz.muni.fi.pa165.pokemon.league.participation.manager.builders.TrainerBuilder;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.*;
import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Gym;
import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Trainer;
import cz.muni.fi.pa165.pokemon.league.participation.manager.enums.PokemonType;
import cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions.EntityIsUsedException;
import cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions.InsufficientRightsException;
import cz.muni.fi.pa165.pokemon.league.participation.manager.facade.GymFacadeImpl;
import cz.muni.fi.pa165.pokemon.league.participation.manager.service.GymService;
import cz.muni.fi.pa165.pokemon.league.participation.manager.service.TrainerService;
import cz.muni.fi.pa165.pokemon.league.participation.manager.service.config.ServiceConfiguration;
import cz.muni.fi.pa165.pokemon.league.participation.manager.service.utils.BeanMappingService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.test.context.ContextConfiguration;

import javax.inject.Inject;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.*;

/**
 * Tests for Gym Facade
 *
 * @author Michal Mokros 456442
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class GymFacadeTest {

    @Mock
    private GymService gymService;

    @Mock
    private TrainerService trainerService;

    @Mock
    private BeanMappingService beanMappingService;

    @Inject
    @InjectMocks
    private GymFacadeImpl gymFacade;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    private GymCreateDTO gymCreateDTO;
    private GymCreateDTO exceptionalGymCreateDTO;
    private UpdateGymLocationDTO updateGymLocationDTO;
    private ChangeGymTypeDTO changeGymTypeDTO;
    private ChangeGymLeaderDTO changeGymLeaderDTO;

    private Gym gym;
    private Gym newGym;
    private Trainer gymLeader;
    private Trainer newGymLeader;
    private Trainer exceptionalGymLeader;
    private GymDTO gymDTO;
    private GymDTO newGymDTO;
    private TrainerDTO gymLeaderDTO;
    private TrainerDTO newGymLeaderDTO;

    private List<Gym> getAllGymsList;

    @Before
    public void setUp() throws EntityIsUsedException, InsufficientRightsException {
        gymLeader = new TrainerBuilder()
                .id(1L)
                .isAdmin(false)
                .name("Gym")
                .surname("Leader")
                .userName("gymLeader64")
                .passwordHash("23213")
                .born(LocalDate.of(1990, 12, 12))
                .build();

        newGymLeader = new TrainerBuilder()
                .id(2L)
                .isAdmin(false)
                .name("New Gym")
                .surname("Leader")
                .userName("NewGymLeader67")
                .passwordHash("2314")
                .born(LocalDate.of(1985, 10, 2))
                .build();

        exceptionalGymLeader = new TrainerBuilder()
                .id(3L)
                .isAdmin(false)
                .name("Exception")
                .surname("!!")
                .userName("Boom")
                .passwordHash("123")
                .born(LocalDate.of(2016, 10, 1))
                .build();

        gym = new GymBuilder()
                .id(10L)
                .gymLeader(gymLeader)
                .location("Bratislava")
                .type(PokemonType.DARK)
                .build();

        newGym = new GymBuilder()
                .id(11L)
                .gymLeader(exceptionalGymLeader)
                .location("Breclav")
                .type(PokemonType.FAIRY)
                .build();

        gymCreateDTO = new GymCreateDTO();
        gymCreateDTO.setGymLeaderID(newGymLeader.getId());
        gymCreateDTO.setLocation("Breclav");
        gymCreateDTO.setType(PokemonType.FAIRY);

        exceptionalGymCreateDTO = new GymCreateDTO();
        exceptionalGymCreateDTO.setGymLeaderID(gymLeader.getId());
        exceptionalGymCreateDTO.setLocation("Brno");
        exceptionalGymCreateDTO.setType(PokemonType.FIRE);

        updateGymLocationDTO = new UpdateGymLocationDTO();
        updateGymLocationDTO.setGymID(gym.getId());
        updateGymLocationDTO.setNewLocation("Praha");

        changeGymTypeDTO = new ChangeGymTypeDTO();
        changeGymTypeDTO.setGymId(gym.getId());
        changeGymTypeDTO.setNewGymType(PokemonType.DRAGON);
        changeGymTypeDTO.setTrainerId(gym.getGymLeader().getId());

        changeGymLeaderDTO = new ChangeGymLeaderDTO();
        changeGymLeaderDTO.setGymID(gym.getId());
        changeGymLeaderDTO.setNewGymLeaderID(newGymLeader.getId());

        gymLeaderDTO = new TrainerDTO();
        gymLeaderDTO.setId(gymLeader.getId());
        gymLeaderDTO.setAdmin(gymLeader.isAdmin());
        gymLeaderDTO.setBorn(gymLeader.getBorn());
        gymLeaderDTO.setGymLeader(true);
        gymLeaderDTO.setName(gymLeader.getName());
        gymLeaderDTO.setSurname(gymLeader.getSurname());
        gymLeaderDTO.setPasswordHash(gymLeader.getPasswordHash());
        gymLeaderDTO.setUserName(gymLeader.getUserName());

        newGymLeaderDTO = new TrainerDTO();
        newGymLeaderDTO.setId(newGymLeader.getId());
        newGymLeaderDTO.setUserName(newGymLeader.getUserName());
        newGymLeaderDTO.setGymLeader(true);
        newGymLeaderDTO.setPasswordHash(newGymLeader.getPasswordHash());
        newGymLeaderDTO.setSurname(newGymLeader.getSurname());
        newGymLeaderDTO.setName(newGymLeader.getName());
        newGymLeaderDTO.setBorn(newGymLeader.getBorn());
        newGymLeaderDTO.setAdmin(newGymLeader.isAdmin());

        gymDTO = new GymDTO();
        gymDTO.setId(gym.getId());
        gymDTO.setGymLeader(gymLeaderDTO);
        gymDTO.setLocation(gym.getLocation());
        gymDTO.setType(gym.getType());

        newGymDTO = new GymDTO();
        newGymDTO.setId(newGym.getId());
        newGymDTO.setGymLeader(newGymLeaderDTO);
        newGymDTO.setType(newGymDTO.getType());
        newGymDTO.setLocation(newGymDTO.getLocation());

        getAllGymsList = Arrays.asList(gym, newGym);

        when(gymService.getAllGyms()).thenReturn(getAllGymsList);
        when(gymService.getGymLeader(gym)).thenReturn(gym.getGymLeader());
        when(gymService.getGymLeader(newGym)).thenReturn(newGym.getGymLeader());
        when(gymService.findGymById(gym.getId())).thenReturn(gym);
        when(gymService.findGymById(newGym.getId())).thenReturn(newGym);
        when(gymService.findGymByLeader(gym.getGymLeader())).thenReturn(gym);
        when(gymService.findGymByLeader(newGym.getGymLeader())).thenReturn(newGym);
        when(gymService.findGymsByType(gym.getType())).thenReturn(Collections.singletonList(gym));
        when(gymService.findGymsByType(newGym.getType())).thenReturn(Collections.singletonList(newGym));
        doThrow(new EntityIsUsedException("Entity is in use")).when(gymService).createGym(gym);
        doThrow(new InsufficientRightsException("This trainer has no rights to change type"))
                .when(gymService).changeGymType(gym, newGym.getGymLeader(), changeGymTypeDTO.getNewGymType());
        doThrow(new EntityIsUsedException("This trainer is leader of another gym"))
                .when(gymService).changeGymLeader(gym, exceptionalGymLeader);
        doThrow(new EntityIsUsedException("Badge for this gym exist"))
                .when(gymService).removeGym(newGym);
        when(trainerService.getTrainerWithId(changeGymTypeDTO.getTrainerId())).thenReturn(gymLeader);
        when(trainerService.getTrainerWithId(changeGymLeaderDTO.getNewGymLeaderID())).thenReturn(newGymLeader);
        when(trainerService.getTrainerWithId(exceptionalGymLeader.getId())).thenReturn(exceptionalGymLeader);

        when(beanMappingService.mapTo(gym, GymDTO.class)).thenReturn(gymDTO);
        when(beanMappingService.mapTo(gymDTO, Gym.class)).thenReturn(gym);
        when(beanMappingService.mapTo(newGym, GymDTO.class)).thenReturn(newGymDTO);
        when(beanMappingService.mapTo(newGymDTO, Gym.class)).thenReturn(newGym);
        when(beanMappingService.mapTo(gymLeader, TrainerDTO.class)).thenReturn(gymLeaderDTO);
        when(beanMappingService.mapTo(newGymLeader, TrainerDTO.class)).thenReturn(newGymLeaderDTO);
        when(beanMappingService.mapTo(getAllGymsList, GymDTO.class))
                .thenReturn(Arrays.asList(gymDTO, newGymDTO));
        when(beanMappingService.mapTo(Collections.singletonList(gym), GymDTO.class))
                .thenReturn(Collections.singletonList(gymDTO));
        when(beanMappingService.mapTo(gymCreateDTO, Gym.class)).thenReturn(newGym);
        when(beanMappingService.mapTo(exceptionalGymCreateDTO, Gym.class)).thenReturn(gym);
    }

    @Test
    public void createGymTest() throws EntityIsUsedException {
        assertThatExceptionOfType(EntityIsUsedException.class)
                .isThrownBy(() -> gymFacade.createGym(exceptionalGymCreateDTO));
        gymFacade.createGym(gymCreateDTO);
        verify(gymService, atLeastOnce()).createGym(newGym);
    }

    @Test
    public void updateGymLocationTest() {
        gymFacade.updateGymLocation(updateGymLocationDTO);
        verify(gymService, atLeastOnce()).updateGymLocation(gym, "Praha");
    }

    @Test
    public void changeGymTypeTest() throws InsufficientRightsException {
        changeGymTypeDTO.setTrainerId(newGym.getGymLeader().getId());
        assertThatExceptionOfType(InsufficientRightsException.class)
                .isThrownBy(() -> gymFacade.changeGymType(changeGymTypeDTO));
        changeGymTypeDTO.setTrainerId(gym.getGymLeader().getId());
        gymFacade.changeGymType(changeGymTypeDTO);
        verify(gymService, atLeastOnce()).changeGymType(gym, gym.getGymLeader(), changeGymTypeDTO.getNewGymType());
    }

    @Test
    public void changeGymLeaderTest() throws EntityIsUsedException {
        changeGymLeaderDTO.setNewGymLeaderID(exceptionalGymLeader.getId());
        assertThatExceptionOfType(EntityIsUsedException.class)
                .isThrownBy(() -> gymFacade.changeGymLeader(changeGymLeaderDTO));
        changeGymLeaderDTO.setNewGymLeaderID(newGymLeader.getId());
        gymFacade.changeGymLeader(changeGymLeaderDTO);
        verify(gymService, atLeastOnce()).changeGymLeader(gym, newGymLeader);
    }

    @Test
    public void removeGymTest() throws EntityIsUsedException {
        assertThatExceptionOfType(EntityIsUsedException.class)
                .isThrownBy(() -> gymFacade.removeGym(newGym.getId()));
        gymFacade.removeGym(gym.getId());
        verify(gymService, atLeastOnce()).removeGym(gym);
    }

    @Test
    public void findGymByIdTest() {
        assertThat(gymFacade.findGymById(gym.getId())).isEqualTo(gymDTO);
        verify(gymService, atLeastOnce()).findGymById(gym.getId());
    }

    @Test
    public void getAllGymsTest() {
        assertThat(gymFacade.getAllGyms()).containsOnly(gymDTO, newGymDTO);
        verify(gymService, atLeastOnce()).getAllGyms();
    }

    @Test
    public void getGymLeaderTest() {
        assertThat(gymFacade.getGymLeader(gym.getId())).isEqualTo(gymLeaderDTO);
        verify(gymService, atLeastOnce()).getGymLeader(gym);
    }

    @Test
    public void findGymsByTypeTest() {
        assertThat(gymFacade.findGymsByType(gym.getType())).containsOnly(gymDTO);
        verify(gymService, atLeastOnce()).getAllGyms();
    }

    @Test
    public void findGymByLeaderTest() {
        assertThat(gymFacade.findGymByLeader(gym.getGymLeader().getId())).isEqualTo(gymDTO);
        verify(gymService, atLeastOnce()).getAllGyms();
    }
}
