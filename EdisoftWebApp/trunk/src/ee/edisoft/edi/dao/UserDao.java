package ee.edisoft.edi.dao;

import java.util.List;

import ee.edisoft.edi.model.User;

/**
 * Interface to abstract access to the data source that contains user data.
 * Using Data Access Object pattern.
 */
public interface UserDao {

	/**
	 * Creates a record for the specified user in the appropriate data source.
	 * 
	 * @param user The User object for which to create a record
	 * @return	the number of created records
	 * @throws DaoException if something goes wrong at Data Access layer
	 */
	public int create(User user) throws DaoException;
	
	/**
	 * Reads data for the user with the specified email from the appropriate data source.
	 * 
	 * @param email The email of the user whose data to read
	 * @return User object that encapsulates the appropriate data source record
	 * @throws DaoException if something goes wrong at Data Access layer
	 */
	public User read(String email) throws DaoException;
	
	/**
	 * Reads data for all users from the appropriate data source.
	 * 
	 * @return List of all users whose records are available in the appropriate data source
	 * @throws DaoException if something goes wrong at Data Access layer
	 */
	public List<User> readAll() throws DaoException;
	
	/**
	 * Updates user related records.
	 * 
	 * @param user The user whose records needs an update
	 * @return the number of updated records
	 * @throws DaoException if something goes wrong at Data Access layer
	 */
	public int update(User user) throws DaoException;
	
	/**
	 * Deletes record for the user with the specified id.
	 * 
	 * @param id The ID of the user whose record to delete
	 * @return number of deleted records
	 * @throws DaoException if something goes wrong at Data Access layer
	 */
	public int delete(int id) throws DaoException;
	
	/**
	 * Deletes all user related records from the appropriate data source.
	 * 
	 * @throws DaoException if something goes wrong at Data Access layer
	 */
	public void deleteAll() throws DaoException;
	
}
