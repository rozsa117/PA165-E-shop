package cz.muni.fi.pa165.pokemon.league.participation.manager.service;

import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Trainer;
import cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions.NoAdministratorException;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * Service interface for object Trainer.
 *
 * @author Jiří Medveď 38451
 */
@Service
public interface TrainerService {

    /**
     * Create new trainer
     *
     * @param trainer Trainer
     * @param password Unencrypted password
     * @return new trainer id
     * @throws NoAdministratorException when there is not a single administrator
     */
    Trainer createTrainer(Trainer trainer, String password) throws NoAdministratorException;

    /**
     * Renames trainer
     *
     * @param trainer Trainer
     * @param newName New name
     * @param newSurname New Surname
     */
    void renameTrainer(Trainer trainer, String newName, String newSurname);

    /**
     * Get list of all trainers
     * 
     * @return list of all trainers
     */
    List<Trainer> getAllTrainers();

    /**
     * Find trainer by Id.
     * 
     * @param id The id of the trainer to find.
     * @return The found trainer.
     */
    Trainer getTrainerWithId(Long id);

    /**
     * Autenticate trainer with password
     * 
     * @param trainer Trainer
     * @param password Unencrypted password
     * @return true only if password matches stored hash
     */
    boolean authenticate(Trainer trainer, String password);

    /**
     * Is Gym Leader?
     * 
     * @param trainer Trainer
     * @return true only if the Trainer is a Gym Leader
     */
    boolean isGymLeader(Trainer trainer);
    
    /**
     * Set admin flag
     * 
     * @param trainer Trainer to be updated
     * @param admin New admin status
     * @throws NoAdministratorException when there is not a single admin after the
     * modification
     */
    void setAdmin(Trainer trainer, boolean admin) throws NoAdministratorException;
    
    /**
     * Change trainer password
     * 
     * @param trainer Trainer to be updated
     * @param oldPassword original password
     * @param newPassword new password
     * @return true if password was successfully updated, false if authentication
     * of old password failed
     */
    boolean changePassword(Trainer trainer, String oldPassword, String newPassword);

    /**
     * Find trainer with given username
     *
     * @param username the username for which the trainer shall be found.
     * @return Found trainer, null if no such trainer exists.
     */
    Trainer findTrainerByUsername(String username);

}
