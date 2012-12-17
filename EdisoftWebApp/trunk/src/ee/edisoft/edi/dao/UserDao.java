package ee.edisoft.edi.dao;

import java.util.List;

import ee.edisoft.edi.model.User;

public interface UserDao {

	public int create(User user);
	
	public User read(String email);
	
	public List<User> readAll();
	
	public int update(User user);
	
	public int delete(int id);
	
	public void deleteAll();
}
